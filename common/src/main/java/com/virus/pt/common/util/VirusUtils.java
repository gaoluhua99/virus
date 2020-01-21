package com.virus.pt.common.util;

import com.virus.pt.model.dataobject.Config;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.file.Paths;
import java.util.Random;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 3:47 下午
 * @email zzy.main@gmail.com
 */
public class VirusUtils {

    public static Config config;

    public static String getUploadFilePath() {
        return Paths.get("").toAbsolutePath().toString();
    }

    /**
     * 随机一个数再MD5
     *
     * @return hash
     */
    public static String randomCode() {
        Random random = new Random();
        int code = random.nextInt(99999999);
        return DigestUtils.md5Hex(String.valueOf(code).getBytes());
    }
}
