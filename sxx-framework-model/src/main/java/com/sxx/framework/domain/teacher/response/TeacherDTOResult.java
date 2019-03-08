package com.sxx.framework.domain.teacher.response;

import com.sxx.framework.domain.teacher.dto.TeacherDTO;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈授课导师响应结果〉
 *
 * @author hyz
 * @create 2019/1/15 0015
 * @since 1.0.0
 */
@Data
public class TeacherDTOResult extends ResponseResult {
    private List<TeacherDTO> teacherDTO;

    public TeacherDTOResult(ResultCode resultCode, List<TeacherDTO> teacherDTO) {
        super(resultCode);
        this.teacherDTO = teacherDTO;
    }
}
