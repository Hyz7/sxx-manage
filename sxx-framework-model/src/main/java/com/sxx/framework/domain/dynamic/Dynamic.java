package com.sxx.framework.domain.dynamic;

import com.sxx.framework.domain.base.BaseDomain;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态父类〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Data
@ToString
@Table(name="t_dynamic")
public class Dynamic extends BaseDomain implements Serializable {
    @ApiModelProperty("分类id")
    private String typeId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("分类名称")
    private String typeName;
    @ApiModelProperty("外链")
    private String url;

}
