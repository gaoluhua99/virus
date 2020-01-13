package com.virus.pt.model.dto;

import com.virus.pt.model.dataobject.Peer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author intent
 * @date 2019/7/24 11:43
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@Setter
public class TrackerResponse {
    public static final int INTERVAL = 3600;
    public static final int MIN_INTERVAL = 30;

    private long complete;
    private long incomplete;
    private long interval;
    @JsonProperty("peer")
    private List<Peer> peers;
    @JsonIgnore
    @JsonProperty("min interval")
    private long minInterval;

    public TrackerResponse() {
        this(0, 0, null);
    }

    public TrackerResponse(long complete, long incomplete, List<Peer> peers) {
        this.interval = INTERVAL;
        this.minInterval = MIN_INTERVAL;
        this.complete = complete;
        this.incomplete = incomplete;
        this.peers = peers;
    }
}
