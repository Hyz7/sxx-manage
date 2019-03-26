package com.sxx.manage_media;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.sxx.framework.model.aws.AwsS3Bucket;
import org.junit.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hyz
 * @create 2019/2/27 0027
 * @since 1.0.0
 */

public class TestFile {

    /**
     * 视频分块测试
     */
    @Test
    public void testChunk() throws Exception {
        // 源文件
        File sourceFile = new File("F:\\ffmpeg_test\\lucene.avi");
        // 分块目录
        String chunkFileFolder = "F:\\ffmpeg_chunk\\";
        // 定义块文件大小  1M
        long chunkSize = 1024 * 1024;

        // 块数
        long chunkFileSize = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        byte[] b = new byte[1024];
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");
        for (int i = 0; i < chunkFileSize; i++) {
            // 块文件
            File chunkFile = new File(chunkFileFolder + i);
            RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw");
            int len;
            while ((len = raf_read.read(b)) != -1) {
                raf_write.write(b,0,len);
                // 判断文件是否大于1M
                if (chunkFile.length() >= chunkSize){
                    break;
                }
            }
            raf_write.close();
        }
        raf_read.close();
    }

    /**
     * 视频合并
     */
    @Test
    public void testMerge() throws Exception {
        // 分块视频路径
        String chunkFileSource = "F:\\ffmpeg_chunk\\";
        // 块文件
        File chunkFileFolder = new File(chunkFileSource);
        // 合并后文件
        File mergeFile = new File("F:\\ffmpeg_test\\lucene_merge.avi");
        if (mergeFile.exists()){
            mergeFile.delete();
        }
        mergeFile.createNewFile();
        File[] listFiles = chunkFileFolder.listFiles();
        // 按升序排序
        List<File> files = Arrays.asList(listFiles);
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName())>Integer.parseInt(o2.getName())){
                    return 1;
                }
                return -1;
            }
        });
        // 用于写文件
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile,"rw");
        // 读文件
        byte[] b = new byte[1024];
        for (File file : files) {
            RandomAccessFile raf_read = new RandomAccessFile(file,"r");
            int len;
            while ((len = raf_read.read(b)) != -1){
                raf_write.write(b,0,len);
            }
            raf_read.close();
        }
        raf_write.close();
    }
    @Test
    public void test1(){
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        //AccessControlList acl = s3.getObjectAcl(AwsS3Bucket.SXX_Media_BUCKET, "lucene.mp4");
        //// set access for the grantee
        //EmailAddressGrantee grantee = new EmailAddressGrantee("060230259758");
        //Permission permission = Permission.valueOf("ReadAcp");
        //acl.grantPermission(grantee, permission);
        //s3.setObjectAcl(AwsS3Bucket.SXX_Media_BUCKET, "lucene.mp4", acl);
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(AwsS3Bucket.SXX_Media_BUCKET, "lucene.mp4");
        // 设置过期时间1小时
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        urlRequest.setExpiration(expiration);
        URL url = s3.generatePresignedUrl(urlRequest);
        System.out.println(url.toString());
    }


    @Test
    public void testDateFormatter(){
        double i = 55692;
        System.out.println(Math.round(642727196d/1024/1024));
    }
}
