package com.virus.pt.core.service;

import com.virus.pt.common.exception.TipException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author intent
 * @date 2019/7/23 17:11
 * @about <link href='http://zzyitj.xyz/'/>
 */
public interface StorageService {

    void init() throws TipException;

    boolean store(MultipartFile file) throws TipException;

    Stream<Path> loadAll() throws TipException;

    Path load(String filename);

    Resource loadAsResource(String filename) throws TipException;

    boolean deleteAll();
}
