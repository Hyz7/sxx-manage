package com.sxx.utils;

/**
 * 〈一句话功能简述〉<br>
 * 〈文件工具类〉
 *
 * @author hyz
 * @create 2019/3/12 0012
 * @since 1.0.0
 */
public class FileUtil {
    /**
     * 获得不同的存储key
     *
     * @param originalFilename 文件全称
     * @return 存储key
     */
    public static String getSaveKey(String originalFilename) {
        // 获得文件名
        String realFileName = originalFilename.substring(0, originalFilename.indexOf("."));
        // 设置储存key
        String fileName = realFileName + "_" + System.currentTimeMillis() +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        return fileName;
    }

    public static String getFilePublicUrl(String bucket, String fileKey) {
        return "https://s3.cn-northwest-1.amazonaws.com.cn/" + bucket + "/" + fileKey;
    }
}
