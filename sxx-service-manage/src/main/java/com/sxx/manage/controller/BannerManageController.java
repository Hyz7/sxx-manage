package com.sxx.manage.controller;

import com.sxx.api.banner.BannerManageControllerApi;
import com.sxx.framework.domain.banner.Banner;
import com.sxx.framework.domain.banner.response.BannerResult;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.service.BannerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈后台管理banner控制层〉
 *
 * @author hyz
 * @create 2019/4/3 0003
 * @since 1.0.0
 */
@RestController
@RequestMapping("/bannerManage")
public class BannerManageController implements BannerManageControllerApi {

    @Autowired
    private BannerManageService bannerManageService;

    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    @Override
    @GetMapping("/banner")
    public BannerResult queryBannerList() {
        return bannerManageService.queryBannerList();
    }

    /**
     * 添加banner信息
     *
     * @param bannerDesc banner描述
     * @param orderBy    排序字段
     * @param status     是否发布
     * @param file       banner图片
     * @return 结果
     */
    @Override
    @PostMapping("/banner")
    public ResponseResult addBanner(String bannerDesc, Integer orderBy,Boolean status, MultipartFile file) {
        return bannerManageService.addBanner(bannerDesc, orderBy, status, file);
    }

    /**
     * 根据id删除banner
     *
     * @param bannerId bannerId
     * @return 结果
     */
    @Override
    @DeleteMapping("/banner/{bannerId}")
    public ResponseResult deleteBanner(@PathVariable("bannerId") String bannerId) {
        return bannerManageService.deleteBanner(bannerId);
    }

    /**
     * 修改banner信息
     *
     * @param banner 修改后信息
     * @return 结果
     */
    @Override
    @PutMapping("/banner")
    public ResponseResult updateBanner(@RequestBody Banner banner) {
        return bannerManageService.updateBanner(banner);
    }

    /**
     * 添加或修改banner图
     *
     * @param bannerId bannerId
     * @param file     banner图
     * @return 结果
     */
    @Override
    @PostMapping("/bannerImage")
    public ResponseResult insertBannerImage(@RequestParam("bannerId") String bannerId, @RequestParam("file") MultipartFile file) {
        return bannerManageService.insertBannerImage(bannerId, file);
    }
}
