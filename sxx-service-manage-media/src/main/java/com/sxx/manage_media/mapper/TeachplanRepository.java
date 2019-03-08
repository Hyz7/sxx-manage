package com.sxx.manage_media.mapper;

import com.sxx.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    List<Teachplan> findByCourseIdAndParentId(String courseId, String parentId);
}
