package com.sxx.framework.domain.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据实体〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Data
@ToString
@Table(name="data")
public class DataEntity implements Serializable {
    @Id
    @ApiModelProperty("主键")
    private Integer dataId;
    @ApiModelProperty("标题")
    private String dataTitle;
    @ApiModelProperty("描述")
    private String dataDesc;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("所属行业")
    private String industry;
    @ApiModelProperty("价格")
    private Integer price;
    @ApiModelProperty("报告类型")
    private String type;
    @ApiModelProperty("下载次数")
    private Integer downloadCount;
    @ApiModelProperty("行业id")
    private Integer dataCategoryId;
    @ApiModelProperty("分类id")
    private Integer dataClassId;
    @ApiModelProperty("data key from aws s3")
    private String dataKey;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("eTag")
    private String eTag;
}
