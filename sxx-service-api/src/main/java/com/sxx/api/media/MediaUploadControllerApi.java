package com.sxx.api.media;

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
     * @param courseId    课程id
     * @param teachplanId 课程计划id
     * @param file        文件
     * @return 结果
     */
    @ApiOperation("媒资文件上传")
    ResponseResult uploadMedia(String courseId,String teachplanId,MultipartFile file);
}

