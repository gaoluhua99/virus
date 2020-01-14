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
    private long complete;
    private long incomplete;
    private long interval;
    @JsonProperty("peer")
    private List<Peer> peers;
    @JsonIgnore
    @JsonProperty("min interval")
    private long minInterval;

    public TrackerResponse() {
        this(3600, 60, 0, 0, null);
    }

    public TrackerResponse(long interval, long minInterval, long complete, long incomplete, List<Peer> peers) {
        this.interval = interval;
        this.minInterval = minInterval;
        this.complete = complete;
        this.incomplete = incomplete;
        this.peers = peers;
    }
}
