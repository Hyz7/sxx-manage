package com.sxx.manage.controller;

import com.sxx.api.dynamic.DynamicControllerApi;
import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicListResult2;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.dynamic.response.DynamicTypeResponse;
import com.sxx.framework.model.response.ResponseResult;
import com.sxx.manage.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态展示控制层〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicController implements DynamicControllerApi {
    @Autowired
    private DynamicService dynamicService;

    /**
     * 展示思学行动态列表
     *
     * @return 思学行动态列表响应结果
     */
    @Override
    @GetMapping("/showDynamicTypeList")
    public DynamicTypeResponse showDynamicTypeList() {
        return dynamicService.showDynamicTypeList();
    }

    /**
     * 展示思学行动态信息
     *
     * @param page 当前页数
     * @param size 当前页记录数
     * @return 结果集
     */
    @Override
    @GetMapping("/showNewsInfoList")
    public DynamicListResult showNewsInfoList(String name,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return dynamicService.showNewsInfoList(name, page, size);
    }

    /**
     * 添加思学行动态信息
     *
     * @param dynamic 思学行动态信息
     * @return 结果集
     */
    @Override
    @PostMapping("/addDynamic")
    public ResponseResult addDynamic(@RequestBody Dynamic dynamic) {
        return dynamicService.addDynamic(dynamic);
    }

    /**
     * 删除思学行动态信息
     *
     * @param id 信息id
     * @return 结果集
     */
    @Override
    @DeleteMapping("/delDynamic")
    public ResponseResult delDynamic(@RequestParam("id") Long[] id) {
        return dynamicService.delDynamic(id);
    }

    /**
     * 根据id查看编辑思学行动态信息
     *
     * @param id 信息id
     * @return 思学行动态信息
     */
    @Override
    @GetMapping("/queryDynamic")
    public DynamicResult queryDynamic(@RequestParam("id") Long id) {
        return dynamicService.queryDynamic(id);
    }

    /**
     * 更新修改思学行动态信息
     *
     * @param dynamic 动态信息
     * @return 结果集
     */
    @Override
    @PostMapping("/updateDynamic")
    public ResponseResult updateDynamic(@RequestBody Dynamic dynamic) {
        return dynamicService.updateDynamic(dynamic);
    }

    /**
     * 根据分类id分页模糊查询动态信息
     *
     * @param name   模糊查询标题名称
     * @param typeId 分类id
     * @param page   当前页数
     * @param size   当前页记录数
     * @return 结果集
     */
    @Override
    @GetMapping("/showNewsListByTypeId")
    public DynamicListResult2 showNewsListByTypeId(String name, @RequestParam("typeId") Long typeId,
                                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return dynamicService.showNewsListByTypeId(name, typeId, page, size);
    }

    /**
     * 添加思学行动态展示图片
     *
     * @param id   id
     * @param file 图片
     * @return 结果
     */
    @Override
    @PostMapping("/addImage")
    public ResponseResult addDynamicImage(Long id, MultipartFile file) {
        return dynamicService.addDynamicImage(id, file);
    }

}
