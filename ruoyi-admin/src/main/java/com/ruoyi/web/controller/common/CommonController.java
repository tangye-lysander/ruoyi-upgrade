package com.ruoyi.web.controller.common;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.file.OssFileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用请求处理
 *
 * @author 棠野·Lysander
 */
@Tag(name = "通用文件接口", description = "通用文件上传与下载")
@RestController
@RequestMapping("/common")
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    private static final String FILE_DELIMITER = ",";
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @Operation(summary = "通用下载请求", description = "按文件名下载本地已生成的文件，可指定是否下载后删除")
    @GetMapping("/download")
    public void fileDownload(
            @Parameter(description = "文件名称", required = true) String fileName,
            @Parameter(description = "是否下载后删除文件") Boolean delete,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @Operation(summary = "通用上传请求（单个）", description = "上传单个文件至对象存储，返回访问 URL 及文件名")
    @PostMapping("/upload")
    public AjaxResult uploadFile(
            @Parameter(description = "上传的文件", required = true) MultipartFile file) throws Exception {
        try {
            // 文件校验（大小、扩展名）
            OssFileUtils.checkAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
            // 生成 objectKey（形如 /img/2026/07/26/xxx.jpg）
            String objectKey = OssFileUtils.buildObjectKey(OssFileUtils.getExtension(file));
            // 上传 OSS 并返回新文件名称
            FileInfo fileInfo = fileStorageService.of(file)
                    .setPath(OssFileUtils.getStoragePath(objectKey))
                    .setSaveFilename(OssFileUtils.getSaveFilename(objectKey))
                    .upload();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", fileInfo.getUrl());
            ajax.put("fileName", objectKey);
            ajax.put("newFileName", FileUtils.getName(objectKey));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @Operation(summary = "通用上传请求（多个）", description = "批量上传文件至对象存储，返回逗号分隔的 URL 及文件名列表")
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(
            @Parameter(description = "上传的文件列表", required = true) List<MultipartFile> files) throws Exception {
        try {
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files) {
                // 文件校验（大小、扩展名）
                OssFileUtils.checkAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
                // 生成 objectKey（形如 /img/2026/07/26/xxx.jpg）
                String objectKey = OssFileUtils.buildObjectKey(OssFileUtils.getExtension(file));
                // 上传 OSS
                FileInfo fileInfo = fileStorageService.of(file)
                        .setPath(OssFileUtils.getStoragePath(objectKey))
                        .setSaveFilename(OssFileUtils.getSaveFilename(objectKey))
                        .upload();
                urls.add(fileInfo.getUrl());
                fileNames.add(objectKey);
                newFileNames.add(FileUtils.getName(objectKey));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMITER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMITER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMITER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMITER));
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @Operation(summary = "本地资源通用下载", description = "根据本地资源路径下载文件")
    @GetMapping("/download/resource")
    public void resourceDownload(
            @Parameter(description = "本地资源相对路径", required = true) String resource,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + FileUtils.stripPrefix(resource);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }
}
