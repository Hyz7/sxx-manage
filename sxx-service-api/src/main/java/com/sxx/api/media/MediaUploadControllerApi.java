package com.sxx.api.media;

import com.sxx.framework.domain.media.response.CheckChunkResult;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈学习视频接口〉
 *
 * @author hyz
 * @create 2019/2/26 0026
 * @since 1.0.0
 */
@Api(value = "后台管理思学行教学视频上传接口", description = "提供操作思学行教学视频上传")
public interface MediaUploadControllerApi {

    /**
     * 文件上传
     *
     * @param file      文件
     * @return 结果
     */
    @ApiOperation("媒资文件上传")
    ResponseResult uploadMedia(MultipartFile file);

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
    @ApiOperation("文件上传注册")
    ResponseResult register(String fileMd5,
                            String fileName,
                            Long fileSize,
                            String mimeType,
                            String fileExt);

    /**
     * 分块检查
     *
     * @param fileMd5   文件md5
     * @param chunk     分块文件
     * @param chunkSize 分块文件大小
     * @return 结果
     */
    @ApiOperation("分块检查")
    CheckChunkResult checkchunk(String fileMd5,
                                Integer chunk,
                                Integer chunkSize);

    /**
     * 上传分块
     *
     * @param file    文件
     * @param chunk   分块文件
     * @param fileMd5 文件md5
     * @return 结果
     */
    @ApiOperation("上传分块")
    ResponseResult uploadchunk(MultipartFile file,
                               Integer chunk,
                               String fileMd5);

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
    @ApiOperation("合并文件")
    ResponseResult mergechunks(String fileMd5,
                               String fileName,
                               Long fileSize,
                               String mimeType,
                               String fileExt);
}

