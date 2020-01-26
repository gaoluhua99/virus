package com.virus.pt.core.config;

import com.virus.pt.common.util.PathUtils;
import com.virus.pt.common.util.VirusUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("${config.virus.host}")
    private String virusHost;

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${server.servlet.context-path}")
    private String serverPath;

    @Value("${config.virus.file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${config.virus.file.uploadFolder}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String realPath = VirusUtils.getUploadFilePath() + PathUtils.SPEA + uploadFolder + PathUtils.SPEA;
        registry.addResourceHandler("/" + staticAccessPath + "/**")
                .addResourceLocations("file:" + realPath);
        logger.info("文件真实目录: {}**", realPath);
        if (serverPath.substring(0, 1).equals("/")) {
            logger.info("文件对外暴露的访问路径: {}:{}{}/{}/**",
                    virusHost, serverPort, serverPath, staticAccessPath);
        } else {
            logger.info("文件对外暴露的访问路径: {}:{}/{}/{}/**",
                    virusHost, serverPort, serverPath, staticAccessPath);
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("GET", "POST")
//                .allowedHeaders("Content-Type, Access-Control-Allow-Headers, Access-Control-Expose-Headers, Content-Disposition, Authorization, X-Requested-With")
                .exposedHeaders("Content-Disposition")
                .allowedOrigins("*")
                .maxAge(3600);
    }
}
