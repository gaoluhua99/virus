package com.virus.pt.pestilence.controller;

import com.virus.pt.common.enums.PeerStateEnum;
import com.virus.pt.common.enums.TrackerResponseEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.TrackerResponseUtils;
import com.virus.pt.model.dataobject.Peer;
import com.virus.pt.model.dataobject.Torrent;
import com.virus.pt.model.dataobject.UserInfo;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
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

//    @Autowired
//    private TorrentService torrentService;
//
//    @Autowired
//    private UserInfoService userInfoService;
//
//    @Autowired
//    private PeerService peerService;

    @Override
    Torrent getTorrentByHash(byte[] hash) throws DecoderException {
//        return torrentService.getTorrentByHash(URLCodec.decodeUrl(hash));
        return null;
    }

    @Override
    UserInfo getUserByPasskey(String passkey) throws TipException {
//        return userInfoService.getUserInfoByPasskey(passkey);
        return null;
    }

    @Override
    List<Peer> getAllPeers(Integer tid) {
//        return peerService.getAllPeer(tid);
        return null;
    }

    /**
     * 未解决：多IP下载
     *
     * @param peer
     * @param peers
     * @param torrent
     * @return
     */
    @Override
    String start(Peer peer, List<Peer> peers, Torrent torrent) {
        peer.setConnectTime(0);
//        // 判断left是否等于0，等于0说明是辅种
//        if (peer.getLeft() == 0) {
//            peer.setState(PeerStateEnum.COMPLETE.getCode());
//            // 判断是否加入过完成表，没有加入则complete + 1
//            if (!peerService.existStateByUid(
//                    torrent.getId(), peer.getUid(), PeerStateEnum.COMPLETE.getCode())) {
//                peerService.addStateByUid(torrent.getId(), peer.getUid(), PeerStateEnum.COMPLETE.getCode());
//            }
//            // 添加到peers
//            peerService.savePeer(torrent.getId(), peer);
//            // 统计正在做种、下载人数
//            int[] complete = getComplete(peers);
//            return TrackerResponseUtils.success(peers, complete[0], complete[1]);
//        } else {
//            // 没有peer
//            if (peers == null || peers.size() == 0) {
//                return TrackerResponseUtils.error(TrackerResponseEnum.NO_PEER);
//            } else {
//                peer.setState(PeerStateEnum.DOWNLOAD.getCode());
//                // 判断该种子是否正在下载
//                // 判断是否加入过下载表
//                if (!peerService.existStateByUid(
//                        torrent.getId(), peer.getUid(), PeerStateEnum.DOWNLOAD.getCode())) {
//                    peerService.addStateByUid(torrent.getId(), peer.getUid(), PeerStateEnum.DOWNLOAD.getCode());
//                } else {
//                    // 只更新时间
//                    peerService.modifyByUid(torrent.getId(), peer.getUid(), PeerStateEnum.DOWNLOAD.getCode(), true);
//                }
//                // 添加到peers
//                peerService.savePeer(torrent.getId(), peer);
//                // 统计正在做种、下载人数
//                int[] complete = getComplete(peers);
//                return TrackerResponseUtils.success(peers, complete[0], complete[1]);
//            }
//        }
        return null;
    }

    /**
     * 未解决：统计做种时间
     *
     * @param peer
     * @param peers
     * @param torrent
     * @return
     */
    @Override
    String complete(Peer peer, List<Peer> peers, Torrent torrent) {
//        completing + 1
//        downloading - 1
//        存储到peers
//        if (!peerService.existStateByUid(
//                torrent.getId(), peer.getUid(), PeerStateEnum.COMPLETE.getCode())) {
//            peerService.addStateByUid(torrent.getId(), peer.getUid(), PeerStateEnum.COMPLETE.getCode());
//        }
//        Peer oldPeer = peerService.getPeer(torrent.getId(), peer);
//        if (oldPeer != null) {
//            // 更新连接时间
////            当前时间戳 - 上一次连接时间戳
//            long currentMillis = System.currentTimeMillis() - oldPeer.getLastConnectTime();
//            peer.setConnectTime(oldPeer.getConnectTime() + currentMillis);
//        }
//        peerService.modifyByUid(torrent.getId(), peer.getUid(), PeerStateEnum.DOWNLOAD.getCode(), false);
//        peerService.savePeer(torrent.getId(), peer);
//        // 统计正在做种、下载人数
//        int[] complete = getComplete(peers);
//        return TrackerResponseUtils.success(peers, complete[0], complete[1]);
        return null;
    }

    @Override
    String stop(Peer peer, List<Peer> peers, Torrent torrent) {
        return null;
    }

    @Override
    String pause(Peer peer, List<Peer> peers, Torrent torrent) {
        return null;
    }

    @Override
    String work(Peer peer, List<Peer> peers, Torrent torrent) {
        return null;
    }

    @GetMapping(value = "${config.pestilence.url.scrape}")
    public String scrape(HttpServletRequest request) throws DecoderException {
        return retScrape(request);
    }


    @GetMapping(value = "${config.pestilence.url.announce}")
    public String announce(HttpServletRequest request) throws DecoderException, TipException {
        return retAnnounce(request);
    }

    private int[] getComplete(List<Peer> peers) {
        int complete = 0;
        int incomplete = 0;
        for (Peer peer1 : peers) {
            if (peer1.getState() == PeerStateEnum.UPLOAD.getCode()) {
                complete++;
            } else if (peer1.getState() == PeerStateEnum.DOWNLOAD.getCode()) {
                incomplete++;
            }
        }
        return new int[]{complete, incomplete};
    }
}
