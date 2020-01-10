package com.virus.pt.common.util;

import com.virus.pt.common.enums.TrackerResponseEnum;
import com.virus.pt.model.dataobject.Peer;
import com.virus.pt.model.dto.TrackerResponse;
import com.dampcake.bencode.Bencode;

import java.util.*;

/**
 * @author intent
 * @date 2019/7/24 10:43
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class TrackerResponseUtils {
    public static String success(List<Peer> peers, int complete, int incomplete) {
        TrackerResponse trackerResponse = new TrackerResponse();
        trackerResponse.setComplete(complete);
        trackerResponse.setIncomplete(incomplete);
        trackerResponse.setPeers(peers);
        return success(trackerResponse);
    }

    public static String success(List<Peer> peers) {
        TrackerResponse trackerResponse = new TrackerResponse();
        trackerResponse.setPeers(peers);
        return success(trackerResponse);
    }

    public static String success() {
        return success(new TrackerResponse());
    }

    public static String success(TrackerResponse response) {
        Bencode bencode = new Bencode();
        byte[] encoded = bencode.encode(new HashMap<String, Object>() {
            private static final long serialVersionUID = 7069027412743075197L;

            {
                put("complete", response.getComplete());
                put("incomplete", response.getIncomplete());
                put("interval", response.getInterval());
                put("min interval", response.getMinInterval());
                if (response.getPeers() != null) {
                    put("peers", new ArrayList<Map>() {

                        private static final long serialVersionUID = 8306325499816838244L;

                        {
                            for (Peer peer : response.getPeers()) {
                                add(new HashMap<String, Object>() {

                                    private static final long serialVersionUID = 8257833573741457808L;

                                    {
                                        this.put("ip", peer.getIp());
                                        this.put("port", peer.getPort());
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        return new String(encoded, bencode.getCharset());
    }

    public static String error() {
        return error(TrackerResponseEnum.ERROR);
    }

    public static String error(TrackerResponseEnum resultEnum) {
        if (resultEnum == TrackerResponseEnum.HEADER_ERROR) {
            return "<h1 style='color: #f00;'>浏览器访问Tracker Server，已记录IP！</h1>";
        }
        Bencode bencode = new Bencode();
        byte[] encoded = bencode.encode(new HashMap<String, TrackerResponseEnum>() {
            private static final long serialVersionUID = -3677260407629045606L;

            {
                put("failure reason", resultEnum);
            }
        });
        return new String(encoded, bencode.getCharset());
    }
}
