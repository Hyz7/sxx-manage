package com.sxx.framework.domain.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈培训课程实体类〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@Data
@ToString
@Entity
@Table(name = "course")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Course implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    @ApiModelProperty("课程id")
    private String courseId;
    @ApiModelProperty("课程标题")
    private String courseTitle;
    @ApiModelProperty("课程副标题")
    private String courseSubTitle;
    @ApiModelProperty("课程封面图片")
    private String courseImage;
    @ApiModelProperty("课程封面图片存储key")
    private String courseImageKey;
    @ApiModelProperty("课程授课老师")
    private String courseTeacher;
    @ApiModelProperty("课程授课老师图片")
    private String courseTeacherImage;
    @ApiModelProperty("课程授课老师图片存储key")
    private String courseTeacherImageKey;
    @ApiModelProperty("课程授课老师介绍")
    private String courseTeacherIntroduce;
    @ApiModelProperty("课程观看次数")
    private Integer courseWatchCount;
    @ApiModelProperty("课程优惠时间")
    private String coursePreferentialTime;
    @ApiModelProperty("课程活动价格")
    private String courseActivityPrice;
    @ApiModelProperty("课程原价")
    private String courseOriginalPrice;
    @ApiModelProperty("课程发布时间")
    private String coursePublicTime;
    @ApiModelProperty("课程更新时间")
    private String courseUpdateTime;
    @ApiModelProperty("课程介绍")
    private String courseIntroduce;
    @ApiModelProperty("介绍")
    private String introduce;
    @ApiModelProperty("课程是否发布:0表示未发布   1表示已发布")
    private String status;
}
