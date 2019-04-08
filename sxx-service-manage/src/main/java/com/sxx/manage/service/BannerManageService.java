package com.sxx.manage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.sxx.framework.domain.banner.Banner;
import com.sxx.framework.domain.banner.response.BannerResult;
import com.sxx.framework.exception.ExceptionCast;
import com.sxx.framework.model.aws.AwsS3Bucket;
import com.sxx.framework.model.response.CommonCode;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.mapper.BannerManageMapper;
import com.sxx.manage.mapper.BannerManageRepository;
import com.sxx.utils.AWSS3Util;
import com.sxx.utils.DateUtil;
import com.sxx.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner业务层〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@Service
public class BannerManageService {

    @Autowired
    private BannerManageMapper bannerManageMapper;
    @Autowired
    private BannerManageRepository bannerManageRepository;

    /**
     * 查询banner图列表
     *
     * @return banner图列表
     */
    public BannerResult queryBannerList() {
        List<Banner> bannerList = bannerManageMapper.queryBannerList();
        return new BannerResult(CommonCode.SUCCESS, bannerList);
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
    public ResponseResult addBanner(String bannerDesc, Integer orderBy, Boolean status, MultipartFile file) {
        if (StringUtils.isEmpty(bannerDesc) ||
                file == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
            return null;
        }
        Banner banner = new Banner();
        banner.setBannerDesc(bannerDesc);
        banner.setOrderBy(orderBy);
        banner.setStatus(status);
        banner.setForwardUrl("#");
        try {
            // 添加图片
            String bannerImageKey = FileUtil.getSaveKey(Objects.requireNonNull(file.getOriginalFilename()));
            String filePublicUrl = AWSS3Util.uploadPublicFileAndGetFilePublicUrl(AwsS3Bucket.SXX_BANNER_BUCKET, bannerImageKey, file);
            banner.setBannerImageKey(bannerImageKey);
            banner.setBannerImage(filePublicUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置更新时间
        banner.setUpdateTime(DateUtil.getNowFormateDate());
        bannerManageRepository.save(banner);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id删除banner
     *
     * @param bannerId bannerId
     * @return 结果
     */
    public ResponseResult deleteBanner(String bannerId) {
        if (StringUtils.isEmpty(bannerId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<Banner> optional = bannerManageRepository.findById(bannerId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Banner banner = optional.get();
        // 判断是否有图片
        if (StringUtils.isNotEmpty(banner.getBannerImageKey())) {
            // 删除图片
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_BANNER_BUCKET, banner.getBannerImageKey());
        }
        bannerManageRepository.deleteById(bannerId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 修改banner信息
     *
     * @param banner 修改后信息
     * @return 结果
     */
    public ResponseResult updateBanner(Banner banner) {
        if (banner == null || StringUtils.isEmpty(banner.getBannerId())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String bannerId = banner.getBannerId();
        Optional<Banner> optional = bannerManageRepository.findById(bannerId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Banner bannerOld = optional.get();
        BeanUtil.copyProperties(banner, bannerOld, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        bannerOld.setUpdateTime(DateUtil.getNowFormateDate());
        bannerManageRepository.save(bannerOld);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加或修改banner图
     *
     * @param bannerId bannerId
     * @param file     banner图
     * @return 结果
     */
    public ResponseResult insertBannerImage(String bannerId, MultipartFile file) {
        if (StringUtils.isEmpty(bannerId) || file == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<Banner> optional = bannerManageRepository.findById(bannerId);
        if (!optional.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Banner banner = optional.get();
        // 判断是否已有图片
        if (StringUtils.isNotEmpty(banner.getBannerImageKey())) {
            // 删除图片
            AWSS3Util.deleteFile(AwsS3Bucket.SXX_BANNER_BUCKET, banner.getBannerImageKey());
        }
        // 上传图片
        try {
            String bannerImageKey = FileUtil.getSaveKey(Objects.requireNonNull(file.getOriginalFilename()));
            String filePublicUrl = AWSS3Util.uploadPublicFileAndGetFilePublicUrl(AwsS3Bucket.SXX_BANNER_BUCKET, bannerImageKey, file);
            banner.setBannerImage(filePublicUrl);
            banner.setBannerImageKey(bannerImageKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bannerManageRepository.save(banner);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
