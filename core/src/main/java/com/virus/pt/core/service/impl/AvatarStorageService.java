package com.virus.pt.core.service.impl;

import com.virus.pt.common.util.PathUtils;
import com.virus.pt.core.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * @author intent
 * @date 2019/7/23 17:59
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Service
public class AvatarStorageService extends FileSystemStorageService implements StorageService {
    public AvatarStorageService(@Value("${config.virus.file.uploadFolder}") String uploadFolder,
                                @Value("${config.virus.file.avatarFolder}") String avatarFolder) {
        super(Paths.get(uploadFolder + PathUtils.SPEA + avatarFolder));
    }
}
