package com.sxx.manage_media.controller;

import com.sxx.api.media.MediaUploadControllerApi;
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
     * @param courseId    课程id
     * @param teachplanId 课程计划id
     * @param file        文件
     * @return 结果
     */
    @Override
    @PostMapping("/uploadMediaData")
    public ResponseResult uploadMedia(@RequestParam("courseId") String courseId, @RequestParam("teachplanId") String teachplanId, @RequestParam("file") MultipartFile file) {
        return mediaUploadService.uploadMedia(courseId,teachplanId,file);
    }
}
