package com.ruoyi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author 棠野·Lysander
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
@Data
public class RuoYiConfig {
    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;
    /**
     * 验证码类型
     */
    private static String captchaType;
    /**
     * aliyun oss 配置
     */
    private static Oss oss;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        RuoYiConfig.captchaType = captchaType;
    }

    public static Oss getOss() {
        return oss;
    }

    public void setOss(Oss oss) {
        RuoYiConfig.oss = oss;
    }

    /**
     * aliyun OSS 相关配置
     */
    @Data
    public static class Oss {
        /**
         * 端点
         */
        private String endpoint;
        /**
         * 存储桶名称
         */
        private String bucketName;
        /**
         * 区域
         */
        private String region;
        /**
         * AK 访问密钥
         */
        private String accessKey;
        /**
         * SK 密钥
         */
        private String secretKey;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
}
