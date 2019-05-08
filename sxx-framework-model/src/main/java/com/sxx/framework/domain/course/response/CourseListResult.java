package com.sxx.framework.domain.course.response;

import com.sxx.framework.domain.course.Course;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class CourseListResult extends ResponseResult {
    private List<Course> courseList;

    public CourseListResult(ResultCode resultCode, List<Course> courseList) {
        super(resultCode);
        this.courseList = courseList;
    }
}
