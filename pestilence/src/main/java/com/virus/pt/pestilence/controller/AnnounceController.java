package com.virus.pt.pestilence.controller;

import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.PeerStateEnum;
import com.virus.pt.common.enums.TrackerResponseEnum;
import com.virus.pt.common.enums.UserDataEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.TrackerResponseUtils;
import com.virus.pt.common.util.VirusUtils;
import com.virus.pt.db.service.PeerService;
import com.virus.pt.db.service.TorrentService;
import com.virus.pt.db.service.TorrentStatusService;
import com.virus.pt.db.service.UserDataService;
import com.virus.pt.model.dataobject.Peer;
import com.virus.pt.model.dataobject.Torrent;
import com.virus.pt.model.dataobject.UserData;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: intent
 * @Date: 19-11-2 下午2:19
 */
@RestController
@RequestMapping
public class AnnounceController extends AnnounceImpl {
    private static final Logger logger = LoggerFactory.getLogger(AnnounceController.class);

    @Autowired
    private TorrentService torrentService;

    @Autowired
    private TorrentStatusService torrentStatusService;

    @Autowired
    private PeerService peerService;

    @Autowired
    private UserDataService userDataService;

    @Override
    Torrent getTorrentByHash(byte[] hash) throws DecoderException {
        return torrentService.getByHash(URLCodec.decodeUrl(hash));
    }

    @Override
    UserData getUserByPasskey(String passkey) throws TipException {
        return userDataService.getByPasskey(passkey);
    }

    @Override
    List<Peer> getAllPeers(Long tid) {
        return peerService.getList(tid);
    }

    /**
     * @param peer
     * @param peers
     * @param torrent
     * @return
     */
    @Override
    String start(Peer peer, List<Peer> peers, Torrent torrent) {
        peer.setConnectTime(0);
        // 判断left是否等于0，等于0说明是辅种
        if (peer.getLeft() == 0) {
            peer.setState(PeerStateEnum.SEEDING.getCode());
            // 判断是否加入过完成表
            checkAddTorrentStatus(peer, torrent.getId(), true);
            // 添加到peers
            peerService.save(torrent.getId(), peer);
            // 统计本种子的做种和下载情况
            peerService.statistics(torrent.getId());
            // 统计正在做种、下载人数
            return TrackerResponseUtils.success(peers,
                    peerService.getSeedingCount(torrent.getId()), peerService.getDownloadingCount(torrent.getId()));
        } else {
            // 没有peer
            if (peers == null || peers.size() == 0) {
                logger.info("tid: {} 种子: {} 没有peers 用户: {} ip: {}",
                        torrent.getId(), torrent.getFileName(), peer.getPasskey(), peer.getIp());
                return TrackerResponseUtils.error(TrackerResponseEnum.NO_PEERS);
            } else {
                peer.setState(PeerStateEnum.DOWNLOADING.getCode());
                Peer oldPeer = peerService.get(torrent.getId(), peer);
                // 判断种子在TrackerResponse.INTERVAL时间内是否更新过数据
                if (oldPeer != null &&
                        oldPeer.getLastConnectTime() + VirusUtils.config.getTrackerInterval()
                                * ApiConst.SECOND_UNIT < peer.getLastConnectTime()) {
                    return TrackerResponseUtils.error(TrackerResponseEnum.REPEAT_DOWNLOAD);
                }
                // 判断该种子是否加入过下载表
                checkAddTorrentStatus(peer, torrent.getId(), false);
                // 添加到peers
                peerService.save(torrent.getId(), peer);
                // 统计本种子的做种和下载情况
                peerService.statistics(torrent.getId());
                // 统计正在做种、下载人数
                return TrackerResponseUtils.success(peers,
                        peerService.getSeedingCount(torrent.getId()), peerService.getDownloadingCount(torrent.getId()));
            }
        }
    }

    private boolean checkAddTorrentStatus(Peer peer, Long tid, boolean status) {
        // 判断是否加入过完成表
        if (!torrentStatusService.exist(peer.getPasskey(), tid, status)) {
            if (peer.getIpv6() != null) {
                torrentStatusService.save(peer.getPasskey(), tid, status, peer.getPeerId(), peer.getIpv6());
            } else {
                torrentStatusService.save(peer.getPasskey(), tid, status, peer.getPeerId(), peer.getIp());
            }
            return true;
        }
        return false;
    }

    /**
     * @param peer
     * @param peers
     * @param torrent
     * @return
     */
    @Override
    String complete(Peer peer, List<Peer> peers, Torrent torrent) {
        // 判断是否加入过完成表
        checkAddTorrentStatus(peer, torrent.getId(), true);
        // 判断是否在peers里面，在则设置新的状态，没在则直接加入peers
        Peer oldPeer = peerService.get(torrent.getId(), peer);
        if (oldPeer != null) {
            // 更新连接时间
            // 当前时间戳 - 上一次连接时间戳
            long currentMillis = peer.getLastConnectTime() - oldPeer.getLastConnectTime();
            peer.setConnectTime(oldPeer.getConnectTime() + currentMillis);
        }
        peer.setState(PeerStateEnum.SEEDING.getCode());
        peerService.save(torrent.getId(), peer);
        // 统计正在做种、下载人数
        return TrackerResponseUtils.success(peers,
                peerService.getSeedingCount(torrent.getId()), peerService.getDownloadingCount(torrent.getId()));
    }

    @Override
    String stop(Peer peer, List<Peer> peers, Torrent torrent) {
        // 判断是否在peers里面，在则删除
        Peer oldPeer = peerService.get(torrent.getId(), peer);
        if (oldPeer != null) {
            // 统计和更新数据
            peer.setState(oldPeer.getState());
            // 上一次距离现在的连接时间(毫秒) = 当前时间戳 - 上一次连接时间戳
            long currentMillis = peer.getLastConnectTime() - oldPeer.getLastConnectTime();
            // 当前上传量： peer的上传 - oldPeer的上传
            long currentUpload = peer.getUploaded() - oldPeer.getUploaded();
            long currentDownload = peer.getDownloaded() - oldPeer.getDownloaded();
            // 判断上传速度：当前上传量 / 上一次距离现在的连接时间
            long currentUploadSpeed = currentUpload / (currentMillis / ApiConst.SECOND_UNIT);
            // 如果上传速度大于config中设置的，则封号
            if (currentUploadSpeed > 104857600) {
                userDataService.setStatus(peer.getPasskey(), UserDataEnum.BAN.getCode());
                logger.info("用户: {} ip: {} client: {} tid: {} 种子: {} 因超速作弊账号已被ban",
                        peer.getPasskey(), peer.getIp(), peer.getPeerId(), torrent.getId(), torrent.getFileName());
            }
            // 查询种子优惠
            logger.info("用户: {} 停止 tid: {} 种子: {} 上传: {} 下载: {} 连接时间: {} status: {}"
                    , peer.getPasskey(), torrent.getId(), torrent.getFileName(), currentUpload,
                    currentDownload, oldPeer.getConnectTime() + currentMillis, peer.getState());
            // 更新上传、下载量
            userDataService.updateDataToRedis(peer.getPasskey(), currentUpload, currentDownload);
            peerService.remove(torrent.getId(), peer);
        }
        return TrackerResponseUtils.success();
    }

    @Override
    String pause(Peer peer, List<Peer> peers, Torrent torrent) {
        return this.stop(peer, peers, torrent);
    }

    @Override
    String work(Peer peer, List<Peer> peers, Torrent torrent) {
        // 判断是否在peers里面
        Peer oldPeer = peerService.get(torrent.getId(), peer);
        if (oldPeer != null) {
            peer.setState(oldPeer.getState());
            // 上一次距离现在的连接时间(毫秒) = 当前时间戳(也就是新的peer的上一次连接的时间戳) - 上一次连接时间戳
            long currentMillis = peer.getLastConnectTime() - oldPeer.getLastConnectTime();
            // 判断上一次到现在的连接时间是不是小于MIN_INTERVAL
            if (currentMillis / ApiConst.SECOND_UNIT < VirusUtils.config.getTrackerMinInterval()) {
                return TrackerResponseUtils.error(TrackerResponseEnum.MIN_INTERVAL);
            }
            // 当前上传量： peer的上传 - oldPeer的上传
            long currentUpload = peer.getUploaded() - oldPeer.getUploaded();
            long currentDownload = peer.getDownloaded() - oldPeer.getDownloaded();
            // 判断上传速度：当前上传量 / 上一次距离现在的连接时间
            long currentUploadSpeed = currentUpload / (currentMillis / ApiConst.SECOND_UNIT);
            logger.info("peer: {}, currentUploadSpeed: {}", peer.getIp(), currentUploadSpeed);
            // 如果上传速度大于config中设置的，则封号
            if (currentUploadSpeed > 104857600) {
                userDataService.setStatus(peer.getPasskey(), UserDataEnum.BAN.getCode());
                logger.info("用户: {} ip: {} client: {} tid: {} 种子: {} 因超速作弊账号已被ban",
                        peer.getPasskey(), peer.getIp(), peer.getPeerId(), torrent.getId(), torrent.getFileName());
            }
            // 查询种子优惠

            logger.info("用户: {} tid: {} 种子: {} 上传: {} 下载: {} 连接时间: {} status: {}"
                    , peer.getPasskey(), torrent.getId(), torrent.getFileName(), currentUpload,
                    currentDownload, oldPeer.getConnectTime() + currentMillis, peer.getState());
            // 更新上传、下载量
            userDataService.updateDataToRedis(peer.getPasskey(), currentUpload, currentDownload);
            // 更新连接时间
            peer.setConnectTime(oldPeer.getConnectTime() + currentMillis);
            peerService.save(torrent.getId(), peer);
        }
        return TrackerResponseUtils.error(TrackerResponseEnum.ERROR);
    }

    @GetMapping(value = "${config.pestilence.url.scrape}")
    public String scrape(HttpServletRequest request) throws DecoderException {
        return retScrape(request);
    }

    @GetMapping(value = "${config.pestilence.url.announce}")
    public String announce(HttpServletRequest request) throws DecoderException, TipException {
        return retAnnounce(request);
    }
}
