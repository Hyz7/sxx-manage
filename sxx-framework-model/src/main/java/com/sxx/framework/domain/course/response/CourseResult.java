package com.sxx.framework.domain.course.response;

import com.sxx.framework.domain.course.Course;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈课程结果类〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class CourseResult extends ResponseResult {
    private Course course;

    public CourseResult(ResultCode resultCode, Course course) {
        super(resultCode);
        this.course = course;
    }
}
