package com.sxx.framework.domain.course.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 〈一句话功能简述〉<br>
 * 〈课程列表信息DTO〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@Data
@ToString
public class CourseListDTO {
    @ApiModelProperty("课程id")
    private String courseId;
    @ApiModelProperty("课程标题")
    private String courseTitle;
    @ApiModelProperty("课程副标题")
    private String courseSubTitle;
    @ApiModelProperty("课程封面图片")
    private String courseImage;
    @ApiModelProperty("课程活动价格")
    private String courseActivityPrice;
    @ApiModelProperty("课程原价")
    private String courseOriginalPrice;
}
