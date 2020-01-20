package com.virus.pt.common.util;

import com.virus.pt.model.dataobject.Config;

import java.nio.file.Paths;

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
}
