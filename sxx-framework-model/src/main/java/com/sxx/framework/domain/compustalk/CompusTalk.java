package com.sxx.framework.domain.compustalk;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 〈一句话功能简述〉<br>
 * 〈校园宣讲实体类〉
 *
 * @author hyz
 * @create 2019/5/5 0005
 * @since 1.0.0
 */
@Data
@Entity
@Table(name = "campus_talk")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CompusTalk implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String title;
    private String content;
    private String image;
    private String videoUrl;
    private Boolean isDelete;
    private String classify;
}
