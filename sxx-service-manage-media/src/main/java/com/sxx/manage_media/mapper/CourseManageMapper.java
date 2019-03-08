/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理持久层〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
package com.sxx.manage_media.mapper;

import com.github.pagehelper.Page;
import com.sxx.framework.domain.course.Course;
import com.sxx.framework.domain.course.dto.CourseListDTO;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseManageMapper {
    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    Page<CourseListDTO> queryList(@Param("courseTitle") String courseTitle);

    /**
     * 根据id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @Select("select * from course where course_id = #{courseId}")
    Course queryCourseInformationByCourseId(@Param("courseId") String courseId);

    /**
     * 添加课程
     *
     * @param course 课程信息
     * @return 结果
     */
    void addCourse(Course course);

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    TeachplanNode findTeachplanList(@Param("courseId") String courseId);

    void updateCourse(Course course);
}
