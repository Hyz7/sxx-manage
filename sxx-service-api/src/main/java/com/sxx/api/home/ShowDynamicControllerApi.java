package com.sxx.api.home;

import com.sxx.framework.domain.dynamic.response.DynamicListResult;
import com.sxx.framework.domain.dynamic.response.DynamicListResult2;
import com.sxx.framework.domain.dynamic.response.DynamicResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br>
 * 〈思学行官网思学行动态数据展示接口〉
 *
 * @author hyz
 * @create 2019/1/9 0009
 * @since 1.0.0
 */
@Api(value = "思学行动态官网展示", description = "思学行官网思学行动态数据展示接口")
public interface ShowDynamicControllerApi {
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
     * 根据id查看编辑思学行动态信息
     *
     * @param id 信息id
     * @return 思学行动态信息
     */
    @ApiOperation("根据id查看编辑思学行动态信息")
    DynamicResult queryDynamic(Long id);

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
