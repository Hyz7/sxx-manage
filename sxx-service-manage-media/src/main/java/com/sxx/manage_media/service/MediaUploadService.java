package com.sxx.manage_media.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.sxx.framework.domain.media.MediaData;
import com.sxx.framework.domain.media.TeachplanMedia;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.mapper.MediaDataRepository;
import com.sxx.manage_media.mapper.TeachplanMediaRepository;
import com.sxx.utils.AWSS3Util;
import com.sxx.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈媒资上传业务层〉
 *
 * @author hyz
 * @create 2019/3/5 0005
 * @since 1.0.0
 */
@Service
public class MediaUploadService {
    @Autowired
    private MediaDataRepository mediaDataRepository;

    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;

    /**
     * 文件上传
     *
     * @param courseId    课程id
     * @param teachplanId 课程计划id
     * @param file        文件
     * @return 结果
     */
    @Transactional(rollbackOn = Exception.class)
    public ResponseResult uploadMedia(String courseId, String teachplanId, MultipartFile file) {
        // 判断是否该课程计划已经有文件上传,如果有文件上传则先删除文件并删除media_data表中数据
        isUpload(teachplanId);
        // 创建中间表实体
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        MediaData mediaData = new MediaData();
        // 设置原始名称
        String originalFilename = file.getOriginalFilename();
        mediaData.setFileOriginalName(originalFilename);
        teachplanMedia.setMediaFileoriginalname(originalFilename);
        // 获得文件名
        String realFileName = originalFilename.substring(0, originalFilename.indexOf("."));
        // 设置储存key
        String fileName = realFileName + "_" + System.currentTimeMillis() +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        mediaData.setFileName(fileName);
        // 设置文件大小
        mediaData.setFileSize(file.getSize() * 1.0 / 1024 / 1024);
        // 设置文件类型
        mediaData.setFileType(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
        // 设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadTime = simpleDateFormat.format(new Date());
        mediaData.setUploadTime(uploadTime);
        // 文件上传并公开阅读权限
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        PutObjectResult putObjectResult;
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            putObjectResult = s3.putObject(
                    new PutObjectRequest(AwsS3Bucket.SXX_Media_BUCKET, fileName, file.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));// 设置文件权限为公开只读
            // 设置文件eTag
            mediaData.setTag(putObjectResult.getETag());
            // 设置文件URL
            String filePublicUrl = FileUtil.getFilePublicUrl(AwsS3Bucket.SXX_Media_BUCKET, fileName);
            mediaData.setFileUrl(filePublicUrl);
            teachplanMedia.setMediaUrl(filePublicUrl);
            // 成功
            mediaData.setProcessStatus("1");
            // 保存文件到
            mediaDataRepository.save(mediaData);
            // 设置媒资文件id
            teachplanMedia.setMediaId(mediaData.getFileId());
            // 保存课程计划_媒资中间表信息
            teachplanMediaRepository.save(teachplanMedia);
        } catch (IOException e) {
            // 失败
            mediaData.setProcessStatus("0");
            mediaDataRepository.save(mediaData);
            e.printStackTrace();
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 判断是否该课程计划已经有文件上传,如果有文件上传则先删除文件并删除media_data表中数据
     *
     * @param teachplanId 课程计划id
     */
    private boolean isUpload(String teachplanId) {
        Optional<TeachplanMedia> optional = teachplanMediaRepository.findById(teachplanId);
        if (!optional.isPresent()){
            return false;
        }
        // 已存在课程,删除课程
        TeachplanMedia teachplanMedia = optional.get();
        String mediaId = teachplanMedia.getMediaId();
        Optional<MediaData> mediaDataOptional = mediaDataRepository.findById(mediaId);
        if (!mediaDataOptional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return false;
        }
        MediaData mediaData = mediaDataOptional.get();
        // 获得文件key
        String fileName = mediaData.getFileName();
        AWSS3Util.deleteFile(AwsS3Bucket.SXX_Media_BUCKET,fileName);
        // 删除表中信息
        teachplanMediaRepository.deleteById(teachplanId);
        mediaDataRepository.deleteById(mediaData.getFileId());
        return true;
    }


}
