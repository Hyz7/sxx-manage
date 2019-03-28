package com.sxx.manage_media.mapper;

import com.sxx.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface TeachplanRepository extends JpaRepository<Teachplan, String> {
    /**
     * 根据课程id和父节点id查询课程计划列表
     *
     * @param courseId 课程id
     * @param parentId 父节点id
     * @return 课程计划列表
     */
    List<Teachplan> findByCourseIdAndParentId(String courseId, String parentId);

    /**
     * 根据课程id删除课程计划
     *
     * @param courseId 课程id
     */
    void deleteByCourseId(String courseId);
}
