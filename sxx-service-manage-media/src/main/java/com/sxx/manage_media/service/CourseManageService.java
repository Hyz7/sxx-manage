package com.sxx.manage_media.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.sxx.framework.domain.course.Course;
import com.sxx.framework.domain.course.Teachplan;
import com.sxx.framework.domain.course.dto.CourseListDTO;
import com.sxx.framework.domain.course.ext.TeachplanNode;
import com.sxx.framework.domain.course.response.CourseListDTOResult;
import com.sxx.framework.domain.course.response.CourseResult;
import com.sxx.framework.domain.course.vo.CourseVO;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage_media.mapper.CourseManageMapper;
import com.sxx.manage_media.mapper.CourseRepository;
import com.sxx.manage_media.mapper.TeachplanRepository;
import com.sxx.utils.AWSS3Util;
import com.sxx.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * @param courseVO 课程信息
     * @return 结果
     */
    @Transactional
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
                PutObjectResult putObjectResult = AWSS3Util.uploadPublicFile(AwsS3Bucket.SXX_Course_BUCKET, key, courseImage);
                // 保存图片url
                String courseImageUrl = FileUtil.getFilePublicUrl(AwsS3Bucket.SXX_Course_BUCKET, key);
                course.setCourseImage(courseImageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
        teachplanNew.setCourseId(courseId);
        teachplanNew.setParentId(parentId);
        BeanUtils.copyProperties(teachplan, teachplanNew);
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
    @Transactional
    public ResponseResult updateCourse(CourseVO courseVO) {
        // 获得课程id,查询课程基本信息
        String courseId = courseVO.getCourseId();
        Optional<Course> optional = courseRepository.findById(courseId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        Course course = optional.get();
        // 将需要更新内容覆盖到原内容
        BeanUtils.copyProperties(courseVO, course);
        try {
            // 判断课程图片是否需要更新
            MultipartFile courseImage = courseVO.getCourseImage();
            if (courseImage != null) {
                boolean flag = this.isUpdate(AwsS3Bucket.SXX_Course_BUCKET, course.getCourseImageKey(), courseImage);
                if (flag) {
                    // 需要更新
                    // 获得存储key
                    String saveKey = FileUtil.getSaveKey(Objects.requireNonNull(courseImage.getOriginalFilename()));
                    // 上传新文件
                    AWSS3Util.uploadPublicFile(AwsS3Bucket.SXX_Course_BUCKET, saveKey, courseImage);
                    // 更新成功,保存key和url
                    course.setCourseImageKey(saveKey);
                    // 保存图片url
                    String filePublicUrl = FileUtil.getFilePublicUrl(AwsS3Bucket.SXX_Course_BUCKET, saveKey);
                    course.setCourseImage(filePublicUrl);
                }
            }
            // 判断讲师图片是否需要更新
            MultipartFile courseTeacherImage = courseVO.getCourseTeacherImage();
            if (courseTeacherImage != null) {
                boolean teaFlag = this.isUpdate(AwsS3Bucket.SXX_Course_BUCKET, course.getCourseImageKey(), courseTeacherImage);
                if (teaFlag) {
                    // 需要更新
                    // 获得存储key
                    String saveKey = FileUtil.getSaveKey(Objects.requireNonNull(courseTeacherImage.getOriginalFilename()));
                    // 上传新文件
                    AWSS3Util.uploadPublicFile(AwsS3Bucket.SXX_Course_BUCKET, saveKey, courseTeacherImage);
                    // 更新成功,保存key和url
                    course.setCourseTeacherImageKey(saveKey);
                    // 保存图片url
                    String filePublicUrl = FileUtil.getFilePublicUrl(AwsS3Bucket.SXX_Course_BUCKET, saveKey);
                    course.setCourseTeacherImage(filePublicUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置更新时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String courseUpdateTime = simpleDateFormat.format(new Date());
        course.setCourseUpdateTime(courseUpdateTime);
        courseManageMapper.updateCourse(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程id
     * @return 结果
     */
    @Transactional
    public ResponseResult deleteCourse(String courseId) {
        if (StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        // 根据课程id查询课程信息
        Optional<Course> optional = courseRepository.findById(courseId);
        Course course = optional.get();
        String courseImageKey = course.getCourseImageKey();
        // 删除课程图片
        if (StringUtils.isNotEmpty(courseImageKey)){
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_Course_BUCKET,courseImageKey);
        }
        // 删除授课导师图片
        String courseTeacherImageKey = course.getCourseTeacherImageKey();
        if (StringUtils.isNotEmpty(courseTeacherImageKey)){
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_Course_BUCKET,courseTeacherImageKey);
        }
        // 删除视频
        String courseVideoUrlKey = course.getCourseVideoUrlKey();
        if (StringUtils.isNotEmpty(courseVideoUrlKey)){
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_Course_BUCKET,courseVideoUrlKey);
        }
        // 删除数据库记录
        courseRepository.deleteById(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 判断文件是否需要更新,如果是则删除源文件
     *
     * @param bucket        储存桶名称
     * @param key           储存key
     * @param multipartFile 文件
     * @return 结果
     * @throws IOException IO异常
     */
    private boolean isUpdate(String bucket, String key, MultipartFile multipartFile) throws IOException {
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


}
