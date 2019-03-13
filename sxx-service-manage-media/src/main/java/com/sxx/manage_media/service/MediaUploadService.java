package com.sxx.manage_media.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.sxx.framework.domain.media.MediaData;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.mapper.MediaDataMapper;
import com.sxx.utils.AWSS3Util;
import com.sxx.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private MediaDataMapper mediaDataMapper;

    /**
     * 文件上传
     *
     * @param file      文件
     * @return 结果
     */
    @Transactional
    public ResponseResult uploadMedia(MultipartFile file) {
        MediaData mediaData = new MediaData();
        // 设置原始名称
        String originalFilename = file.getOriginalFilename();
        mediaData.setFileOriginalName(originalFilename);
        // 获得文件名
        String realFileName = originalFilename.substring(0, originalFilename.indexOf("."));
        // 设置储存key
        String fileName = realFileName + "_" + System.currentTimeMillis() +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        mediaData.setFileName(fileName);
        // 设置文件大小
        mediaData.setFileSize(file.getSize());
        // 设置文件类型
        mediaData.setFileType(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
        // 设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uploadTime = simpleDateFormat.format(new Date());
        mediaData.setUploadTime(uploadTime);
        // 文件上传并公开阅读权限
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        PutObjectResult putObjectResult = null;
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
            // 成功
            mediaData.setProcessStatus("1");
            mediaDataMapper.saveMediaData(mediaData);
        } catch (IOException e) {
            // 失败
            mediaData.setProcessStatus("0");
            mediaDataMapper.saveMediaData(mediaData);
            e.printStackTrace();
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
