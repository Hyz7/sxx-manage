package com.sxx.framework.domain.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner图实体〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@Data
@ToString
@Entity
@Table(name = "banner")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Banner implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    @ApiModelProperty("banner id")
    private String bannerId;
    @ApiModelProperty("banner图地址")
    private String bannerImage;
    @ApiModelProperty("banner图key")
    private String bannerImageKey;
    @ApiModelProperty("banner描述")
    private String bannerDesc;
    @ApiModelProperty("排序字段")
    private Integer orderBy;
    @ApiModelProperty("是否发布")
    private Boolean status;
    @ApiModelProperty("更新时间")
    private String updateTime;

}
