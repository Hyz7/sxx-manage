/**
 * 〈一句话功能简述〉<br>
 * 〈banner持久层〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
package com.sxx.manage.mapper;

import com.sxx.framework.domain.banner.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BannerManageMapper {
    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    @Select("select * from banner order by order_by asc, update_time desc")
    List<Banner> queryBannerList();
}
