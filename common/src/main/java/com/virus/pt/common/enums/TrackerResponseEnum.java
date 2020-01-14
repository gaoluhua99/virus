package com.virus.pt.common.enums;

import lombok.Getter;

/**
 * @author intent
 * @date 2019/7/24 10:46
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
public enum TrackerResponseEnum {
    // 基本规则
    ERROR("Error"),
    SUCCESS("Success"),
    HEADER_ERROR("Header error"),
    INFO_HASH_EMPTY("Info hash can't empty"),
    INFO_HASH_LEN_ERROR("Info hash length error"),
    PEER_ID_EMPTY("Peer id can't empty"),
    PEER_ID_LEN_ERROR("Peer id length error"),
    PASSKEY_LEN_ERROR("Passkey length error"),
    PORT_ERROR("Port error"),
    KEY_ERROR("Key error"),
    PORT_EMPTY("Port can't empty"),
    PASSKEY_EMPTY("Passkey can't empty"),
    EVENT_ERROR("Event error"),
    // 业务逻辑
    PASSKEY_ERROR("Passkey error"),
    ACCOUNT_STATUS_ERROR("Account status error"),
    MIN_INTERVAL("Min interval error"),
    EXCEED_THE_SPEED_LIMIT("Exceed the speed limit"),
    REPEAT_DOWNLOAD("Repeat download"),
    NO_REGISTER_TORRENT("The torrent not register in tracker"),
    NO_PEERS("No peers"),
    NO_SEED("No seed");

    private String message;

    TrackerResponseEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
