package com.sxx.framework.domain.dynamic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈动态分类〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Data
@ToString
@Entity
@Table(name="t_type")
public class DynamicType implements Serializable {
    @Id
    @ApiModelProperty("分类id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String typeId;

    @ApiModelProperty("分类名称")
    private String typeName;

}
