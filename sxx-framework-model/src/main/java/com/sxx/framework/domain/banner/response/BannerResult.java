package com.sxx.framework.domain.banner.response;

import com.sxx.framework.domain.banner.Banner;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈banner响应结果〉
 *
 * @author hyz
 * @create 2019/4/2 0002
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class BannerResult extends ResponseResult {
    private List<Banner> bannerList;

    public BannerResult(ResultCode resultCode, List<Banner> bannerList) {
        super(resultCode);
        this.bannerList = bannerList;
    }
}
