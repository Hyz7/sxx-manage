package com.sxx.manage_media.controller;

import com.sxx.api.course.CourseManageControllerApi;
import com.sxx.framework.domain.course.Teachplan;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListDTOResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.framework.domain.course.vo.CourseVO;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.service.CourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理控制层〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@RestController
@RequestMapping("/course")
public class CourseManageController implements CourseManageControllerApi {
    @Autowired
    private CourseManageService courseManageService;

    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    @Override
    @GetMapping("/queryCourseList")
    public CourseListDTOResult queryCourseList(String courseTitle,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return courseManageService.queryCourseList(courseTitle, page, size);
    }

    /**
     * 根据id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息结果
     */
    @Override
    @GetMapping("/queryCourseInformationByCourseId")
    public CourseResult queryCourseInformationByCourseId(@RequestParam("courseId") String courseId) {
        return courseManageService.queryCourseInformationByCourseId(courseId);
    }

    /**
     * 添加课程
     *
     * @param courseVO 课程信息
     * @return 结果
     */
    @Override
    @PostMapping("/addCourse")
    public ResponseResult addCourse(@ModelAttribute CourseVO courseVO) {
        return courseManageService.addCourse(courseVO);
    }

    /**
     * 修改更新课程信息
     *
     * @param courseVO 修改的课程信息
     * @return 结果
     */
    @Override
    @PostMapping("/updateCourse")
    public ResponseResult updateCourse(@ModelAttribute CourseVO courseVO) {
        return courseManageService.updateCourse(courseVO);
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程id
     * @return 结果
     */
    @Override
    @DeleteMapping("/deleteCourse")
    public ResponseResult deleteCourse(@RequestParam("courseId") String courseId) {
        return courseManageService.deleteCourse(courseId);
    }

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    @Override
    @GetMapping("/findTeachplanList")
    public TeachplanNode findTeachplanList(@RequestParam("courseId") String courseId) {
        return courseManageService.findTeachplanList(courseId);
    }


    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划信息
     * @return 结果
     */
    @Override
    @PostMapping("/addTeachplan")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseManageService.addTeachplan(teachplan);
    }
}
