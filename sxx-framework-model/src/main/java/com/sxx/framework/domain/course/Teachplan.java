package com.sxx.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
@Entity
@Table(name="teachplan")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String pName;
    private String parentId;
    private String grade;
    private String pType;
    private String description;
    private String courseId;
    /**
     * 0表示未发布   1表示已发布
     */
    private String status;
    private Integer orderBy;
    private Double timeLength;
    private String tryLearn;

}
