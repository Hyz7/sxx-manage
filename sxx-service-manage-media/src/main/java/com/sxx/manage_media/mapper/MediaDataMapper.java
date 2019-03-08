package com.sxx.manage_media.mapper;

import com.sxx.framework.domain.media.MediaData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 媒资数据持久层
 */
@Mapper
public interface MediaDataMapper {
    /**
     * 保存媒资数据
     * @param mediaData 媒资数据
     */
    void saveMediaData(MediaData mediaData);
}
