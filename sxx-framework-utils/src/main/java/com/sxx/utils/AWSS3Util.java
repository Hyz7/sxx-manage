package com.sxx.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br>
 * 〈AWS_S3操作工具类〉
 *
 * @author hyz
 * @create 2019/3/12 0012
 * @since 1.0.0
 */
@Slf4j
public class AWSS3Util {
    private final static AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

    /**
     * 上传权限为公开读文件,并返回公开访问链接
     *
     * @param bucket 储存桶名称
     * @param key    储存key
     * @param file   储存文件
     * @return com.amazonaws.services.s3.model.PutObjectResult
     * @throws IOException IO异常
     */
    public static String uploadPublicFileAndGetFilePublicUrl(String bucket, String key, MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        s3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return "https://s3.cn-northwest-1.amazonaws.com.cn/" + bucket + "/" + key;
    }

    /**
     * 删除文件
     *
     * @param bucket 储存桶名称
     * @param key    储存key
     * @return 是否删除成功
     */
    public static boolean deleteFile(String bucket, String key) {
        try {
            s3.deleteObject(bucket, key);
        } catch (AmazonServiceException e) {
            log.error("File deletion failed,error :{}",e.getErrorMessage());
        }
        return true;
    }
}
