package com.sxx.manage_media.controller;

import com.sxx.api.media.MediaUploadControllerApi;
import com.sxx.framework.domain.media.response.CheckChunkResult;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.service.MediaUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈媒资系统上传视频控制层〉
 *
 * @author hyz
 * @create 2019/2/28 0028
 * @since 1.0.0
 */
@RestController
@RequestMapping("/media/upload")
public class MediaUploadController implements MediaUploadControllerApi {
    @Autowired
    private MediaUploadService mediaUploadService;

    /**
     * 文件上传
     *
     * @param file      文件
     * @return 结果
     */
    @Override
    @PostMapping("/uploadMediaData")
    public ResponseResult uploadMedia(@RequestParam("file") MultipartFile file) {
        return mediaUploadService.uploadMedia(file);
    }

    /**
     * 文件上传注册
     *
     * @param fileMd5  文件md5
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param mimeType 文件mime类型
     * @param fileExt  文件扩展名
     * @return 结果
     */
    @Override
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimeType, String fileExt) {
        return null;
    }

    /**
     * 分块检查
     *
     * @param fileMd5   文件md5
     * @param chunk     分块文件
     * @param chunkSize 分块文件大小
     * @return 结果
     */
    @Override
    public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        return null;
    }

    /**
     * 上传分块
     *
     * @param file    文件
     * @param chunk   分块文件
     * @param fileMd5 文件md5
     * @return 结果
     */
    @Override
    public ResponseResult uploadchunk(MultipartFile file, Integer chunk, String fileMd5) {
        return null;
    }

    /**
     * 合并文件
     *
     * @param fileMd5  文件md5
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param mimeType 文件mime类型
     * @param fileExt  文件扩展名
     * @return 结果
     */
    @Override
    public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimeType, String fileExt) {
        return null;
    }
}
