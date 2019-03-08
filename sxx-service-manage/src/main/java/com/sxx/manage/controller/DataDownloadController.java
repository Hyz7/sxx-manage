package com.sxx.manage.controller;

import com.sxx.api.data.DataDownloadControllerApi;
import com.sxx.framework.domain.data.DataEntity;
import com.sxx.framework.domain.data.response.DataEntityResult;
import com.sxx.framework.domain.data.response.DataResult;
import com.sxx.framework.domain.response.DownloadResult;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.service.DataDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 〈一句话功能简述〉<br>
 * 〈资料下载模块控制层〉
 *
 * @author hyz
 * @create 2018/12/28 0028
 * @since 1.0.0
 */
@RestController
@RequestMapping("/data")
public class DataDownloadController implements DataDownloadControllerApi {
    @Autowired
    private DataDownloadService dataDownloadService;

    /**
     * 添加资料下载数据
     *
     * @param file       文件
     * @param dataEntity 数据
     * @return 结果
     */
    @Override
    @PostMapping("/insertData")
    public ResponseResult insertData(@RequestParam(value = "file", required = false) MultipartFile file, @RequestPart("dataEntity") DataEntity dataEntity) {
        return dataDownloadService.insertData(file, dataEntity);
    }

    /**
     * @param dataClassName    当前分类名称
     * @param dataCategoryName 当前分类行业名称
     * @param name             查询名称
     * @param page             当前页数
     * @param size             当前页记录数
     * @return 结果
     */
    @Override
    @GetMapping("/findDataList")
    public DataEntityResult findDataList(String dataClassName, String dataCategoryName, String name,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return dataDownloadService.findDataList(dataClassName, dataCategoryName, name, page, size);
    }

    /**
     * 资料下载
     *
     * @param dataId 下载的数据id
     * @return 结果
     */
    @Override
    @GetMapping("/downloadData")
    public DownloadResult downloadData(@RequestParam("dataId") Integer dataId) {
        return dataDownloadService.downloadData(dataId);
    }

    /**
     * 根据id删除数据
     *
     * @param ids id
     * @return 结果
     */
    @Override
    @DeleteMapping("/deleteData")
    public ResponseResult deleteData(@RequestParam("ids") Integer[] ids) {
        return dataDownloadService.deleteData(ids);
    }

    /**
     * 更新资料下载数据
     *
     * @param file       更新文件
     * @param dataEntity 新数据
     * @return 结果
     */
    @Override
    @PostMapping("/updateData")
    public ResponseResult updateData(@RequestParam(value = "file", required = false) MultipartFile file, @RequestPart("dataEntity") DataEntity dataEntity) {
        return dataDownloadService.updateData(file, dataEntity);
    }

    /**
     * 根据资料下载数据id查询资料详情
     *
     * @param dataId 资料id
     * @return 结果
     */
    @Override
    @GetMapping("/findDataInfoByDataId")
    public DataResult findDataInfoByDataId(@RequestParam("dataId") String dataId) {
        return dataDownloadService.findDataInfoByDataId(dataId);
    }
}
