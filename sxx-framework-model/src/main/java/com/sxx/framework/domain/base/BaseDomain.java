package com.sxx.framework.domain.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈实体类通用类〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Data
@ToString
public class BaseDomain implements Serializable {
    private Long id;
}
