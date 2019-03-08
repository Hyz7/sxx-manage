package com.sxx.framework.domain.ucenter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户〉
 *
 * @author hyz
 * @create 2018/12/20 0020
 * @since 1.0.0
 */
@Data
@ToString
@Entity
@Table(name="sys_user")
public class User implements Serializable {
    @Id
    @ApiModelProperty("主键")
    private Integer userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
