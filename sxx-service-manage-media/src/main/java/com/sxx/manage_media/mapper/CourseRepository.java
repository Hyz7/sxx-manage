package com.sxx.manage_media.mapper;

import com.sxx.framework.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator.
 */
public interface CourseRepository extends JpaRepository<Course,String> {
}
