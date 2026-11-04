package com.ruoyi.common.utils.file;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS 文件存储工具类
 *
 * 配合 X File Storage 使用，数据库仅存储 objectKey（形如 /img/2026/07/26/xxx.jpg），
 * 访问域名由前端拼接。
 *
 * @author 棠野·Lysander
 */
public class OssFileUtils {
    /**
     * OSS 存储目录前缀
     */
    public static final String OSS_PATH_PREFIX = "img";

    /**
     * 校验文件大小与扩展名（复用本地上传的校验规则）
     *
     * @param file             上传的文件
     * @param allowedExtension 允许的文件类型
     */
    public static void checkAllowed(MultipartFile file, String[] allowedExtension) throws Exception {
        FileUploadUtils.assertAllowed(file, allowedExtension);
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        return FileUploadUtils.getExtension(file);
    }

    /**
     * 生成 objectKey（日期目录 + UUID + 后缀）
     *
     * 形如 /img/2026/07/26/4834aaca7bcb4de799db582c1a55979d.jpg
     *
     * @param extension 文件后缀
     * @return objectKey
     */
    public static String buildObjectKey(String extension) {
        return StringUtils.format("/{}/{}/{}.{}", OSS_PATH_PREFIX, DateUtils.datePath(), IdUtils.fastSimpleUUID(), extension);
    }

    /**
     * 从 objectKey 中解析 X File Storage 上传所需的目录
     *
     * @param objectKey 存储路径，形如 /img/2026/07/26/xxx.jpg
     * @return 存储目录，形如 img/2026/07/26/
     */
    public static String getStoragePath(String objectKey) {
        String path = StringUtils.substringBeforeLast(StringUtils.stripStart(objectKey, "/"), "/");
        return path + "/";
    }

    /**
     * 从 objectKey 中解析实际保存的文件名
     *
     * @param objectKey 存储路径，形如 /img/2026/07/26/xxx.jpg
     * @return 文件名，形如 xxx.jpg
     */
    public static String getSaveFilename(String objectKey) {
        return StringUtils.substringAfterLast(objectKey, "/");
    }

    /**
     * 获取 OSS 访问域名（来自 ruoyi.oss 配置）
     *
     * 形如 https://astriva.oss-cn-hangzhou.aliyuncs.com/
     */
    public static String getDomain() {
        RuoYiConfig.Oss oss = RuoYiConfig.getOss();
        return StringUtils.format("https://{}.{}/", oss.getBucketName(), oss.getEndpoint());
    }

    /**
     * 根据 objectKey 拼接完整访问 URL
     *
     * @param objectKey 存储路径，形如 /img/2026/07/26/xxx.jpg
     * @return 完整 URL
     */
    public static String getFileUrl(String objectKey) {
        return getDomain() + StringUtils.stripStart(objectKey, "/");
    }
}
