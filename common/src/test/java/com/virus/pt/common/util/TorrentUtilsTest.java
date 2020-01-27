package com.virus.pt.common.util;

import com.virus.pt.model.dataobject.Torrent;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/27 8:28 下午
 * @email zzy.main@gmail.com
 */
class TorrentUtilsTest {

    @Test
    void getTorrentHash() {
        FileUtils.listFiles(new File("/Users/intent/Downloads"), new String[]{"torrent"}, false)
                .forEach(file -> {
                    try {
                        Torrent torrent = TorrentUtils.getTorrentHash(FileUtils.readFileToByteArray(file));
                        System.out.println(torrent);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}