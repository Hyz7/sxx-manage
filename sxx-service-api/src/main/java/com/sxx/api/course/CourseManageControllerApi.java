/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理接口〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
package com.sxx.api.course;


import com.sxx.framework.domain.course.Teachplan;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.framework.domain.course.vo.CourseNoneImageVO;
import com.sxx.framework.domain.course.vo.CourseVO;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "后台管理思学行课程管理接口", description = "提供操作思学行课程的增删改查")
public interface CourseManageControllerApi {
    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    @ApiOperation("根据课程标题分页模糊查询课程信息列表")
    CourseListResult queryCourseList(String courseTitle, Integer page, Integer size);

    /**
     * 根据id查询课程信息
     *
     * @return 课程信息
     */
    @ApiOperation("根据id查询课程信息")
    CourseResult queryCourseInformationByCourseId(String courseId);

    /**
     * 添加课程
     *
     * @param courseVO 课程信息
     * @return 结果
     */
    @ApiOperation("添加课程信息")
    ResponseResult addCourse(CourseVO courseVO);

    /**
     * 修改更新课程信息
     *
     * @param courseVO 修改的课程信息
     * @return 结果
     */
    @ApiOperation("修改更新课程信息")
    ResponseResult updateCourse(CourseNoneImageVO courseVO);

    /**
     * 修改图片
     *
     * @param courseId  课程id
     * @param imageName 图片类型
     * @param file      图片
     * @return 响应结果
     */
    @ApiOperation("修改课程图片")
    ResponseResult updateCourseImage(String courseId, String imageName, MultipartFile file);

    /**
     * 删除课程信息
     *
     * @param courseId 课程id
     * @return 结果
     */
    @ApiOperation("删除课程信息")
    ResponseResult deleteCourse(String courseId);

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    @ApiOperation("课程计划页面列表")
    TeachplanNode findTeachplanList(String courseId);

    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划信息
     * @return 结果
     */
    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    /**
     * 更新课程计划信息
     *
     * @param teachplan 更新的课程计划信息
     * @return 结果
     */
    @ApiOperation("更新课程计划信息")
    ResponseResult updateTeachplan(Teachplan teachplan);

    /**
     * 删除课程计划信息
     *
     * @param teachplanId 课程计划id
     * @return 结果
     */
    @ApiOperation("删除课程计划信息")
    ResponseResult deleteTeachplan(String teachplanId);
}
