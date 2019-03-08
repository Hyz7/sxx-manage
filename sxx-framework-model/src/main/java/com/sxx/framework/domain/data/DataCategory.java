package com.sxx.framework.domain.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈行业分类〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Data
@Entity
@ToString
@Table(name="data_category")
public class DataCategory implements Serializable {
    @Id
    @ApiModelProperty("主键")
    private Integer categoryId;
    @ApiModelProperty("名称")
    private Integer categoryName;
}
