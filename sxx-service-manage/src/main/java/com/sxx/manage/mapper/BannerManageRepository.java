/**
 * 〈一句话功能简述〉<br>
 * 〈banner持久层〉
 *
 * @author hyz
 * @create 2019/4/3 0003
 * @since 1.0.0
 */
package com.sxx.manage.mapper;

import com.sxx.framework.domain.banner.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerManageRepository extends JpaRepository<Banner,String> {
}
