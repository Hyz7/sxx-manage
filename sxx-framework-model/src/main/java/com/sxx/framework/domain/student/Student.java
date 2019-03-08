package com.sxx.framework.domain.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈学员信息〉
 *
 * @author hyz
 * @create 2019/3/7 0007
 * @since 1.0.0
 */
@Data
@ToString
@Table(name = "student")
public class Student implements Serializable {
    @ApiModelProperty("主键")
    @Id
    private String studentId;
    @ApiModelProperty("学员真实姓名")
    private String studentRealName;
    @ApiModelProperty("学员网名")
    private String studentScreenName;
    @ApiModelProperty("学员手机号")
    private String studentMobilePhone;
    @ApiModelProperty("学员身份证")
    private String studentIdCard;
    @ApiModelProperty("是否付款")
    private Boolean isPay;
}
