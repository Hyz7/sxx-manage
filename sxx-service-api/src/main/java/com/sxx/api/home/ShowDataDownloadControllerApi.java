package com.sxx.api.home;

import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.domain.data.response.DataResult;
import com.sxx.framework.domain.response.DownloadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈资料下载接口〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Api(value = "思学行官网资料下载界面展示", description = "资料下载:提供对白皮书,视频,教程查询以及下载")
public interface ShowDataDownloadControllerApi {
    /**
     * 分页模糊查询资料下载数据内容
     *
     * @param dataClassName    当前分类名称
     * @param dataCategoryName 当前分类行业名称
     * @param name             查询名称
     * @param page             当前页数
     * @param size             当前页记录数
     * @return 结果
     */
    @ApiOperation("分页模糊查询资料下载数据内容")
    DataEntityResult findDataList(String dataClassName, String dataCategoryName, String name, Integer page, Integer size);

    /**
     * 资料下载
     *
     * @param dataId 下载的数据id
     * @return 结果
     */
    @ApiOperation("根据资料下载数据id进行资料下载")
    DownloadResult downloadData(Integer dataId);


    /**
     * 根据资料下载数据id查询资料详情
     *
     * @param dataId 资料id
     * @return 结果
     */
    @ApiOperation("根据资料下载数据id查询资料详情")
    DataResult findDataInfoByDataId(Integer dataId);
}
