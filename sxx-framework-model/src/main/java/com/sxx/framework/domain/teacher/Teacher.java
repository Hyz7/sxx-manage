package com.sxx.framework.domain.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@Data
@ToString
@Table(name = "t_teacher")
public class Teacher implements Serializable {
    @ApiModelProperty("主键")
    private Integer teaId;
    @ApiModelProperty("中文名")
    private String teaCname;
    @ApiModelProperty("职位")
    private String teaJob;
    @ApiModelProperty("成就")
    private String teaAchievement;
    @ApiModelProperty("擅长领域")
    private String teaArea;
    @ApiModelProperty("简介")
    private String teaDesc;
    @ApiModelProperty("分类")
    private String teaType;
    @ApiModelProperty("照片")
    private String teaImage;
}
