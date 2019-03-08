package com.sxx.framework.domain.dynamic.ext;

import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行关键内容检索〉
 *
 * @author hyz
 * @create 2018/12/10 0010
 * @since 1.0.0
 */
@Data
@ToString
public class DynamicExt extends Dynamic {
    private String contentKey;
}
