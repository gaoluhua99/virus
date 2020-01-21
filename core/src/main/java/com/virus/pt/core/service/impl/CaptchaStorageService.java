package com.virus.pt.core.service.impl;

import com.virus.pt.common.util.PathUtils;
import com.virus.pt.core.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * @author intent
 * @date 2019/7/23 17:28
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Service
public class CaptchaStorageService extends FileSystemStorageService implements StorageService {

    public CaptchaStorageService(@Value("${config.virus.file.uploadFolder}") String uploadFolder,
                                 @Value("${config.virus.file.captchaFolder}") String captchaFolder) {
        super(Paths.get(uploadFolder + PathUtils.SPEA + captchaFolder));
    }
}
