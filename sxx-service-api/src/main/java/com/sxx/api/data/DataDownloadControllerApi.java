package com.sxx.api.data;

import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.domain.data.response.DataResult;
import com.sxx.framework.domain.response.DownloadResult;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈资料下载接口〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@Api(value = "资料下载", description = "资料下载:提供对白皮书,视频,教程的增,删,改,查以及下载")
public interface DataDownloadControllerApi {
    /**
     * 添加资料下载数据
     *
     * @param file       文件
     * @param dataEntity 数据
     * @return 结果
     */
    @ApiOperation("添加资料下载数据")
    ResponseResult insertData(MultipartFile file, DataEntity dataEntity);

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
     * 根据id删除数据
     *
     * @param ids id
     * @return 结果
     */
    @ApiOperation("根据资料下载数据id进行删除数据")
    ResponseResult deleteData(Integer[] ids);

    /**
     * 更新资料下载数据
     *
     * @param file       更新文件
     * @param dataEntity 新数据
     * @return 结果
     */
    @ApiOperation("更新资料下载数据")
    ResponseResult updateData(MultipartFile file, DataEntity dataEntity);

    /**
     * 根据资料下载数据id查询资料详情
     *
     * @param dataId 资料id
     * @return 结果
     */
    @ApiOperation("根据资料下载数据id查询资料详情")
    DataResult findDataInfoByDataId(String dataId);
}
