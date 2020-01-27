package com.virus.pt.core.service.impl;

import com.virus.pt.core.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/27 8:14 下午
 * @email zzy.main@gmail.com
 */
@Service
public class TorrentStorageService extends FileSystemStorageService implements StorageService {

    public TorrentStorageService(@Value("${config.virus.torrentsFolder}") String torrentsFolder) {
        super(Paths.get(torrentsFolder));
    }
}

