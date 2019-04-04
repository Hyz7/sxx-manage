package com.sxx.api.banner;

import com.sxx.framework.domain.banner.Banner;
import com.sxx.framework.domain.banner.response.BannerResult;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner门户管理api〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@Api(value = "后台管理门户网站banner图接口", description = "提供对banner的操作")
public interface BannerManageControllerApi {
    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    BannerResult queryBannerList();

    /**
     * 添加banner信息
     *
     * @param bannerDesc banner描述
     * @param orderBy    排序字段
     * @param status     是否发布
     * @param file       banner图片
     * @return 结果
     */
    ResponseResult addBanner(String bannerDesc,Integer orderBy,Boolean status,MultipartFile file);

    /**
     * 根据id删除banner
     *
     * @param bannerId bannerId
     * @return 结果
     */
    ResponseResult deleteBanner(String bannerId);

    /**
     * 修改banner信息
     *
     * @param banner 修改后信息
     * @return 结果
     */
    ResponseResult updateBanner(Banner banner);

    /**
     * 添加或修改banner图
     *
     * @param bannerId bannerId
     * @param file     banner图
     * @return 结果
     */
    ResponseResult insertBannerImage(String bannerId, MultipartFile file);
}
