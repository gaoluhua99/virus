package com.virus.pt.pestilence.controller;

import com.virus.pt.common.enums.TrackerResponseEnum;
import com.virus.pt.common.enums.UserDataEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.RequestUtils;
import com.virus.pt.common.util.TrackerResponseUtils;
import com.virus.pt.common.util.CheckRuleUtils;
import com.virus.pt.common.util.IPUtils;
import com.virus.pt.model.dataobject.Peer;
import com.virus.pt.model.dataobject.Torrent;
import com.virus.pt.model.dataobject.UserData;
import com.virus.pt.model.vo.IPEndpointVo;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.virus.pt.common.constant.TrackerConst.*;

/**
 * @Author: intent
 * @Date: 19-11-2 上午12:09
 */
public abstract class AnnounceImpl implements Announce {
    private static final Logger logger = LoggerFactory.getLogger(AnnounceImpl.class);

    abstract Torrent getTorrentByHash(byte[] hash) throws DecoderException;

    abstract UserData getUserByPasskey(String passkey) throws TipException;

    abstract List<Peer> getAllPeers(Long tid);

    abstract String start(Peer peer, List<Peer> peers, Torrent torrent);

    abstract String complete(Peer peer, List<Peer> peers, Torrent torrent);

    abstract String stop(Peer peer, List<Peer> peers, Torrent torrent);

    abstract String pause(Peer peer, List<Peer> peers, Torrent torrent);

    abstract String work(Peer peer, List<Peer> peers, Torrent torrent);

    @Override
    public String retScrape(HttpServletRequest request) throws DecoderException {
        String infoHash = RequestUtils.getQueryString(request.getQueryString(), INFO_HASH_PARAM);
        TrackerResponseEnum trackerResponseEnum = CheckRuleUtils.checkHash(infoHash);
        if (trackerResponseEnum == TrackerResponseEnum.SUCCESS) {
            // 检查hash在种子库中是否存在
            if (getTorrentByHash(infoHash.getBytes()) != null) {
                return TrackerResponseUtils.success();
            }
            return TrackerResponseUtils.error(TrackerResponseEnum.NO_PEERS);
        }
        return TrackerResponseUtils.error(trackerResponseEnum);
    }

    @Override
    public String retAnnounce(HttpServletRequest request) throws DecoderException, TipException {
        String userAnent = request.getHeader(USER_ANENT);
        // 取得hash，这里只处理一个hash的情况
        String infoHash = RequestUtils.getQueryString(request.getQueryString(), INFO_HASH_PARAM);
        String passkey = request.getParameter(PASSKEY_PARAM);
        String peerId = request.getParameter(PEER_ID_PARAM);
        String left = request.getParameter(LEFT_PARAM);
        String upload = request.getParameter(UPLOAD_PARAM);
        String download = request.getParameter(DOWNLOAD_PARAM);
        String key = request.getParameter(KEY_PARAM);
        // 判断是不是endpoint
        String port = request.getParameter(PORT_PARAM);
        String ipv6 = request.getParameter(IPV6_PARAM);
        IPEndpointVo ipEndpoint = RequestUtils.getIPEndpoint(ipv6);
        if (ipEndpoint != null) {
            ipv6 = ipEndpoint.getHost();
            port = ipEndpoint.getPort();
        }
        String event = request.getParameter(EVENT_PARAM);
        logger.info("{} {} {} {} {} {} {} {} {} {} {}",
                userAnent, infoHash, passkey, PASSKEY_LEN,
                peerId, port, upload, download, left, event, key);
        TrackerResponseEnum trackerResponseEnum = CheckRuleUtils.checkRule(userAnent, infoHash, passkey, PASSKEY_LEN,
                peerId, port, upload, download, left, event, key);
        if (trackerResponseEnum == TrackerResponseEnum.SUCCESS) {
            // 判断用户状态
            UserData userData = getUserByPasskey(passkey);
            // 用户未激活，封禁等等
            if (userData == null || userData.getUserStatus() != UserDataEnum.ACTIVE.getCode()) {
                return TrackerResponseUtils.error(TrackerResponseEnum.ACCOUNT_STATUS_ERROR);
            }
            // 判断种子库中是否存在该种子
            Torrent torrent = getTorrentByHash(infoHash.getBytes());
            if (torrent == null) {
                return TrackerResponseUtils.error(TrackerResponseEnum.NO_REGISTER_TORRENT);
            } else {
                // 构造peer
                Peer peer = new Peer();
                peer.setPasskey(passkey);
                peer.setIp(IPUtils.getIpAddr(request));
                if (StringUtils.isNotBlank(ipv6)) {
                    peer.setIpv6(ipv6);
                }
                peer.setPort(Long.parseLong(port));
                peer.setPeerId(request.getParameter(PEER_ID_PARAM));
                peer.setKey(key);
                peer.setLeft(Long.parseLong(request.getParameter(LEFT_PARAM)));
                peer.setUploaded(Long.parseLong(request.getParameter(UPLOAD_PARAM)));
                peer.setDownloaded(Long.parseLong(request.getParameter(DOWNLOAD_PARAM)));
                peer.setLastConnectTime(System.currentTimeMillis());
                List<Peer> peers = getAllPeers(torrent.getId());
                // 判断event=started
                if (STARTED.equals(event)) {
                    return start(peer, peers, torrent);
                } else if (peers != null) {
                    if (COMPLETED.equals(event)) {
                        return complete(peer, peers, torrent);
                    } else if (STOP.equals(event)) {
                        return stop(peer, peers, torrent);
                    } else if (PAUSED.equals(event)) {
                        return pause(peer, peers, torrent);
                    } else if (StringUtils.isBlank(event)) {
                        return work(peer, peers, torrent);
                    } else {
                        return TrackerResponseUtils.error(TrackerResponseEnum.EVENT_ERROR);
                    }
                } else {
                    return TrackerResponseUtils.error(TrackerResponseEnum.NO_PEERS);
                }
            }
        } else {
            return TrackerResponseUtils.error(trackerResponseEnum);
        }
    }
}
