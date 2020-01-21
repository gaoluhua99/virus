package com.virus.pt.core.service.impl;

import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.core.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * @author intent
 * @date 2019/7/23 17:12
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class FileSystemStorageService implements StorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    public FileSystemStorageService(Path rootLocation) {
        this.rootLocation = rootLocation;
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    @Override
    public boolean store(MultipartFile file) throws TipException {
        try {
            if (file.isEmpty()) {
                throw new TipException(ResultEnum.FILE_EMPTY);
            }
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            if (filename.contains("..")) {
                // This is a security check
                throw new TipException(ResultEnum.FILE_READ_ERROR);
            }
            try (InputStream inputStream = file.getInputStream()) {

                return Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING) > 0;
            }
        } catch (IOException e) {
            throw new TipException(ResultEnum.FILE_READ_ERROR);
        }
    }

    @Override
    public Stream<Path> loadAll() throws TipException {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new TipException(ResultEnum.FILE_READ_ERROR);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws TipException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new TipException(ResultEnum.FILE_NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new TipException(ResultEnum.FILE_READ_ERROR);
        }
    }

    @Override
    public boolean deleteAll() {
        return FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() throws TipException {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new TipException(ResultEnum.STORAGE_INIT_ERROR);
        }
    }
}
