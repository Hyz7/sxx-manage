package com.sxx.manage_media.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.sxx.framework.domain.course.Course;
import com.sxx.framework.domain.course.Teachplan;
import com.sxx.framework.domain.course.dto.CourseListDTO;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListDTOResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.mapper.CourseManageMapper;
import com.sxx.manage_media.mapper.CourseRepository;
import com.sxx.manage_media.mapper.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈课程管理业务层〉
 *
 * @author hyz
 * @create 2019/3/6 0006
 * @since 1.0.0
 */
@Service
public class CourseManageService {
    @Autowired
    private CourseManageMapper courseManageMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeachplanRepository teachplanRepository;

    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    public CourseListDTOResult queryCourseList(String courseTitle, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<CourseListDTO> courseListDTOS = courseManageMapper.queryList(courseTitle);
        List<CourseListDTO> courseListDTOSResult = courseListDTOS.getResult();
        return new CourseListDTOResult(CommonCode.SUCCESS, courseListDTOSResult);
    }

    /**
     * 根据id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息结果
     */
    public CourseResult queryCourseInformationByCourseId(String courseId) {
        Course course = courseManageMapper.queryCourseInformationByCourseId(courseId);
        return new CourseResult(CommonCode.SUCCESS, course);
    }

    /**
     * 添加课程
     *
     * @param course 课程信息
     * @return 结果
     */
    @Transactional
    public ResponseResult addCourse(Course course) {
        if (course == null ||
                StringUtils.isEmpty(course.getCourseTitle())) {
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        // 设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String coursePublicTime = simpleDateFormat.format(new Date());
        course.setCoursePublicTime(coursePublicTime);
        // 设置初始观看次数
        course.setCourseWatchCount((int) (1 + Math.random()) * 100);
        courseManageMapper.addCourse(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    public TeachplanNode findTeachplanList(String courseId) {
        return courseManageMapper.findTeachplanList(courseId);
    }

    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划信息
     * @return 结果
     */
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        // 判断参数合法性
        if (teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseId()) ||
                StringUtils.isEmpty(teachplan.getPName())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 获取课程id
        String courseId = teachplan.getCourseId();
        // 获取父节点id
        String parentId = teachplan.getParentId();
        // 如果父节点id为空
        if (StringUtils.isEmpty(parentId)){
            // 根据课程id获取根节点
            parentId = getTeachplanRoot(courseId);
        }
        // 获得父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentId);
        if (!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Teachplan teachplanNode = optional.get();
        // 获得父节点级别
        String grade = teachplanNode.getGrade();
        // 创建一个新节点
        Teachplan teachplanNew = new Teachplan();
        teachplanNew.setCourseId(courseId);
        teachplanNew.setParentId(parentId);
        BeanUtils.copyProperties(teachplan,teachplanNew);
        if ("1".equals(grade)){
            teachplanNew.setGrade("2");
        }else {
            teachplanNew.setGrade("3");
        }
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @org.jetbrains.annotations.Nullable
    private String getTeachplanRoot(String courseId) {
        // 根据课程id查询课程基本信息
        Optional<Course> optional = courseRepository.findById(courseId);
        if (!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        // 课程基本信息
        Course course = optional.get();
        List<Teachplan> teachplanList = teachplanRepository.findByCourseIdAndParentId(courseId, "0");
        // 判断是不是新课程
        if (teachplanList == null || teachplanList.size() <=0){
            // 创建一个新的课程计划
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseId(courseId);
            teachplan.setParentId("0");
            teachplan.setGrade("1");
            teachplan.setPName(course.getCourseTitle());
            teachplan.setStatus("0");
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }

        return teachplanList.get(0).getId();
    }

    /**
     * 修改更新课程信息
     *
     * @param course 原课程信息
     * @return 结果
     */
    public ResponseResult updateCourse(Course course) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String courseUpdateTime = simpleDateFormat.format(new Date());
        course.setCourseUpdateTime(courseUpdateTime);
        courseManageMapper.updateCourse(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
