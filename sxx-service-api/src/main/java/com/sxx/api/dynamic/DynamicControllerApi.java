package com.sxx.api.dynamic;

import com.sxx.framework.domain.dynamic.Dynamic;
import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicListResult2;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import com.sxx.framework.domain.dynamic.response.DynamicTypeResponse;
import com.sxx.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行动态接口〉
 *
 * @author hyz
 * @create 2018/12/3 0003
 * @since 1.0.0
 */
@Api(value = "后台管理思学行动态接口", description = "提供操作思学行动态接口:新闻资讯,行业动态,学员动态的增,删,改,查")
public interface DynamicControllerApi {
    /**
     * 展示思学行动态列表
     *
     * @return 思学行动态列表响应结果
     */
    @ApiOperation("展示思学行动态列表")
    DynamicTypeResponse showDynamicTypeList();

    /**
     * 展示思学行动态信息
     *
     * @param page 当前页数
     * @param size 当前页记录数
     * @return 结果集
     */
    @ApiOperation("展示思学行动态信息")
    DynamicListResult showNewsInfoList(String name, Integer page, Integer size);

    /**
     * 添加思学行动态信息
     *
     * @param dynamic 思学行动态信息
     * @return 结果集
     */
    @ApiOperation("添加思学行动态信息")
    ResponseResult addDynamic(Dynamic dynamic);

    /**
     * 删除思学行动态信息
     *
     * @param id 信息id
     * @return 结果集
     */
    @ApiOperation("删除思学行动态信息")
    ResponseResult delDynamic(Long[] id);

    /**
     * 根据id查看编辑思学行动态信息
     *
     * @param id 信息id
     * @return 思学行动态信息
     */
    @ApiOperation("根据id查看编辑思学行动态信息")
    DynamicResult queryDynamic(Long id);

    /**
     * 更新修改思学行动态信息
     *
     * @param dynamic 动态信息
     * @return 结果集
     */
    @ApiOperation("更新修改思学行动态信息")
    ResponseResult updateDynamic(Dynamic dynamic);

    /**
     * 根据分类id分页模糊查询动态信息
     *
     * @param name   模糊查询标题名称
     * @param typeId 分类id
     * @param page   当前页数
     * @param size   当前页记录数
     * @return 结果集
     */
    @ApiOperation("根据分类id分页模糊查询动态信息")
    DynamicListResult2 showNewsListByTypeId(String name, Long typeId, Integer page, Integer size);


}
