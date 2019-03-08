package com.sxx.framework.domain.teacher.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师展示接口〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@Data
@ToString
public class TeacherDTO {
    @ApiModelProperty("中文名")
    private String teaCname;
    @ApiModelProperty("职位")
    private String teaJob;
    @ApiModelProperty("成就")
    private Map teaAchievement;
    @ApiModelProperty("擅长领域")
    private String teaArea;
    @ApiModelProperty("简介")
    private String teaDesc;
    @ApiModelProperty("照片")
    private String teaImage;
}
