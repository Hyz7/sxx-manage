package com.sxx.manage_media.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sxx.framework.domain.course.Course;
import com.sxx.framework.domain.course.Teachplan;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.framework.domain.course.vo.CourseNoneImageVO;
import com.sxx.framework.domain.course.vo.CourseVO;
import com.sxx.framework.domain.media.MediaData;
import com.sxx.framework.domain.media.TeachplanMedia;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.mapper.*;
import com.sxx.utils.AWSS3Util;
import com.sxx.utils.DateUtil;
import com.sxx.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
@CacheConfig(cacheNames = {"course"})
public class CourseManageService {
    @Autowired
    private CourseManageMapper courseManageMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeachplanRepository teachplanRepository;
    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;
    @Autowired
    private MediaDataRepository mediaDataRepository;

    /**
     * 根据课程标题分页模糊查询课程信息列表
     *
     * @param courseTitle 课程标题
     * @return 课程信息列表
     */
    @Cacheable(key = "targetClass + methodName + #p0", value = "queryCourseList")
    public CourseListResult queryCourseList(String courseTitle, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<Course> courses = courseManageMapper.queryList(courseTitle);
        return new CourseListResult(CommonCode.SUCCESS, courses.getResult());
    }

    /**
     * 根据id查询课程信息
     *
     * @param courseId 课程id
     * @return 课程信息结果
     */
    @Cacheable(key = "#courseId")
    public CourseResult queryCourseInformationByCourseId(String courseId) {
        System.out.println("从数据库查数据...");
        Course course = courseManageMapper.queryCourseInformationByCourseId(courseId);
        return new CourseResult(CommonCode.SUCCESS, course);
    }

    /**
     * 添加课程
     *
     * @param courseVO 课程信息
     * @return 结果
     */
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "course", allEntries = true),
            @CacheEvict(value = "queryCourseList", allEntries = true)
    })
    public ResponseResult addCourse(CourseVO courseVO) {
        if (courseVO == null ||
                StringUtils.isEmpty(courseVO.getCourseTitle()) ||
                StringUtils.isEmpty(courseVO.getCourseSubTitle())) {
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseVO, course);
        // 判断是否上传封面图片
        MultipartFile courseImage = courseVO.getCourseImage();
        if (courseImage != null) {
            try {
                // 获得存储key
                String key = FileUtil.getSaveKey(Objects.requireNonNull(courseImage.getOriginalFilename()));
                // 保存key
                course.setCourseImageKey(key);
                // 上传封面图片
                String filePublicUrl = AWSS3Util.uploadPublicFileAndGetFilePublicUrl(AwsS3Bucket.SXX_COURSE_BUCKET, key, courseImage);
                course.setCourseImage(filePublicUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // 设置发布时间
        String coursePublicTime = DateUtil.getNowFormateDate();
        course.setCoursePublicTime(coursePublicTime);
        // 设置初始观看次数
        course.setCourseWatchCount((int) (Math.random() * 1000) + 201);
        // 默认设置课程状态为未发布
        course.setStatus("0");
        courseRepository.save(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程计划页面列表
     *
     * @param courseId 课程id
     * @return 结果
     */
    @Cacheable(key = "targetClass + methodName + #p0", value = "teachplan")
    public TeachplanNode findTeachplanList(String courseId) {
        if (courseId == null || StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        return courseManageMapper.findTeachplanList(courseId);
    }

    /**
     * 添加课程计划
     *
     * @param teachplan 课程计划信息
     * @return 结果
     */
    @Transactional
    @CacheEvict(allEntries = true, value = "teachplan")
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
        if (StringUtils.isEmpty(parentId)) {
            // 根据课程id获取根节点
            parentId = getTeachplanRoot(courseId);
        }
        // 获得父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Teachplan teachplanNode = optional.get();
        // 获得父节点级别
        String grade = teachplanNode.getGrade();
        // 创建一个新节点
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setCourseId(courseId);
        teachplanNew.setParentId(parentId);
        if ("1".equals(grade)) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @org.jetbrains.annotations.Nullable
    private String getTeachplanRoot(String courseId) {
        // 根据课程id查询课程基本信息
        Optional<Course> optional = courseRepository.findById(courseId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        // 课程基本信息
        Course course = optional.get();
        List<Teachplan> teachplanList = teachplanRepository.findByCourseIdAndParentId(courseId, "0");
        // 判断是不是新课程
        if (teachplanList == null || teachplanList.size() <= 0) {
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
     * @param courseVO 修改的课程信息
     * @return 结果
     */
    @Transactional(rollbackOn = Exception.class)
    @Caching(put = {
            @CachePut(key = "#courseVO.courseId")
    },
            evict = {
                    @CacheEvict(allEntries = true, value = "queryCourseList")
            })
    public ResponseResult updateCourse(CourseNoneImageVO courseVO) {
        // 获得课程id,查询课程基本信息
        String courseId = courseVO.getCourseId();
        Optional<Course> optional = courseRepository.findById(courseId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        Course course = optional.get();
        // 将需要更新内容覆盖到原内容
        BeanUtil.copyProperties(courseVO, course, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        // 设置更新时间
        String courseUpdateTime = DateUtil.getNowFormateDate();
        course.setCourseUpdateTime(courseUpdateTime);
        courseManageMapper.updateCourse(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 修改图片
     *
     * @param courseId  课程id
     * @param imageName 图片类型
     * @param file      图片
     * @return 响应结果
     */
    @Transactional(rollbackOn = Exception.class)
    @CacheEvict(allEntries = true, value = "queryCourseList")
    public ResponseResult updateCourseImage(String courseId, String imageName, MultipartFile file) {
        String courseImageName = "courseImage";
        // 根据课程id拿到课程基本信息
        Optional<Course> optional = courseRepository.findById(courseId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        Course course = optional.get();
        try {
            // 判断上传的图片类型
            if (imageName.equals(courseImageName)) {
                // 课程封面图片
                // 判断文件是否需要更新，需要则删除旧文件数据
                boolean flag = this.isUpdate(AwsS3Bucket.SXX_COURSE_BUCKET, course.getCourseImageKey(), file);
                if (flag) {
                    // 需要更新
                    // 获得存储key
                    String saveKey = FileUtil.getSaveKey(Objects.requireNonNull(file.getOriginalFilename()));
                    // 上传新文件
                    String filePublicUrl = AWSS3Util.uploadPublicFileAndGetFilePublicUrl(AwsS3Bucket.SXX_COURSE_BUCKET, saveKey, file);
                    // 更新成功,保存key和url
                    course.setCourseImageKey(saveKey);
                    course.setCourseImage(filePublicUrl);
                }
            } else {
                // 课程教师图片
                boolean flag = this.isUpdate(AwsS3Bucket.SXX_COURSE_BUCKET, course.getCourseTeacherImageKey(), file);
                if (flag) {
                    // 需要更新
                    // 获得存储key
                    String saveKey = FileUtil.getSaveKey(Objects.requireNonNull(file.getOriginalFilename()));
                    // 上传新文件
                    String filePublicUrl = AWSS3Util.uploadPublicFileAndGetFilePublicUrl(AwsS3Bucket.SXX_COURSE_BUCKET, saveKey, file);
                    // 更新成功,保存key和url
                    course.setCourseTeacherImageKey(saveKey);
                    course.setCourseTeacherImage(filePublicUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 保存课程信息
        courseRepository.save(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程id
     * @return 结果
     */
    @Transactional(rollbackOn = Exception.class)
    @Caching(evict = {
            @CacheEvict(allEntries = true, value = "queryCourseList"),
            @CacheEvict(key = "#courseId", beforeInvocation = true)
    })
    public ResponseResult deleteCourse(String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        // 根据课程id查询课程信息
        Optional<Course> optional = courseRepository.findById(courseId);
        Course course = optional.get();
        String courseImageKey = course.getCourseImageKey();
        // 删除课程图片
        if (StringUtils.isNotEmpty(courseImageKey)) {
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_COURSE_BUCKET, courseImageKey);
        }
        // 删除授课导师图片
        String courseTeacherImageKey = course.getCourseTeacherImageKey();
        if (StringUtils.isNotEmpty(courseTeacherImageKey)) {
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_COURSE_BUCKET, courseTeacherImageKey);
        }
        // 删除数据库记录
        courseRepository.deleteById(courseId);
        teachplanRepository.deleteByCourseId(courseId);
        teachplanMediaRepository.deleteByCourseId(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 判断文件是否需要更新,如果是则删除源文件
     *
     * @param bucket        储存桶名称
     * @param key           储存key
     * @param multipartFile 文件
     * @return 结果
     */
    private boolean isUpdate(String bucket, String key, MultipartFile multipartFile) {
        // 先判断是否原来已经传了图片，如果没有就直接返回
        if (key == null || StringUtils.isEmpty(key)) {
            return true;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        if (key.equals(originalFilename)) {
            // 图片没更新
            return false;
        }
        // 删除原来数据
        boolean b = AWSS3Util.deleteFile(bucket, key);
        if (!b) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return true;
    }

    /**
     * 更新课程计划信息
     *
     * @param teachplan 更新的课程计划信息
     * @return 结果
     */
    @Transactional(rollbackOn = Exception.class)
    @CacheEvict(allEntries = true, value = "teachplan")
    public ResponseResult updateTeachplan(Teachplan teachplan) {
        if (StringUtils.isEmpty(teachplan.getId())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        String id = teachplan.getId();
        Optional<Teachplan> optional = teachplanRepository.findById(id);
        if (!optional.isPresent()) {
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        Teachplan teachplanOld = optional.get();
        BeanUtil.copyProperties(teachplan, teachplanOld, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        teachplanRepository.save(teachplanOld);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除课程计划
     *
     * @param teachplanId 课程计划id
     * @return 结果
     */
    public ResponseResult deleteTeachplan(String teachplanId) {
        // 删除数据库信息
        // teachplan
        teachplanRepository.deleteById(teachplanId);
        // teachplan_media
        // 获取文件mediaId
        Optional<TeachplanMedia> optional = teachplanMediaRepository.findById(teachplanId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        TeachplanMedia teachplanMedia = optional.get();
        String mediaId = teachplanMedia.getMediaId();
        // 删除信息
        teachplanMediaRepository.deleteById(teachplanId);
        // media_data
        // 获得文件key
        Optional<MediaData> mediaDataOptional = mediaDataRepository.findById(mediaId);
        if (!mediaDataOptional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        MediaData mediaData = mediaDataOptional.get();
        String fileName = mediaData.getFileName();
        // 删除信息
        mediaDataRepository.deleteById(mediaId);
        // 删除aws s3文件信息
        AWSS3Util.deleteFile(AwsS3Bucket.SXX_MEDIA_BUCKET, fileName);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}
