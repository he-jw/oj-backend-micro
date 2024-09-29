package com.hejingwei.ojbackendcommon.config;

import com.hejingwei.ojbackendcommon.utils.AliOssUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 */
@Configuration
@Slf4j
@ConfigurationProperties(prefix = "oj.alioss")
@Data
public class OssConfiguration {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(){
        log.info("开始创建阿里云文件上传工具类{}");
        return new AliOssUtil(endpoint,
                accessKeyId,
                accessKeySecret,
                bucketName);
    }
}
