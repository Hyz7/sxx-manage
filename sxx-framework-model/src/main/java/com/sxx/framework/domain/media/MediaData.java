package com.sxx.framework.domain.media;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈媒资数据〉
 *
 * @author hyz
 * @create 2019/3/5 0005
 * @since 1.0.0
 */
@Data
@ToString
@Entity
@Table(name = "media_data")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class MediaData implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    //文件id
    private String fileId;
    //文件名称
    private String fileName;
    //文件原始名称
    private String fileOriginalName;
    //文件url
    private String fileUrl;
    //文件类型
    private String fileType;
    //文件大小
    private Double fileSize;
    //上传时间
    private String uploadTime;
    /**
     * 处理状态
     * 0失败
     * 1成功
     */
    private String processStatus;
    //tag标签用于查询
    private String tag;
}
