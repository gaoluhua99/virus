package com.virus.pt.common.util;

import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author intent
 * @date 2019/7/23 18:48
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class ImageUtils {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public static boolean isImage(InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        try {
            BufferedImage img = ImageIO.read(inputStream);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean saveImage(RenderedImage img, String type, File file) throws TipException {
        try {
            FileUtils.openOutputStream(file);
            return ImageIO.write(img, type, file);
        } catch (IOException e) {
            logger.error(ResultEnum.FILE_WRITE_ERROR.getMessage(), e);
            throw new TipException(ResultEnum.FILE_WRITE_ERROR);
        }
    }

    public static boolean saveImage(InputStream inputStream, String type, File file) throws TipException {
        try {
            FileUtils.openOutputStream(file);
            return ImageIO.write(ImageIO.read(inputStream), type, file);
        } catch (IOException e) {
            logger.error(ResultEnum.FILE_WRITE_ERROR.getMessage(), e);
            throw new TipException(ResultEnum.FILE_WRITE_ERROR);
        }
    }
}
