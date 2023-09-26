package com.ruoyi.project.pc.service;

import java.util.List;
import com.ruoyi.project.pc.domain.DaRl;

/**
 * 日期Service接口
 *
 * @author qxd
 * @date 2023-09-23
 */
public interface IDaRlService
{
    /**
     * 查询日期
     *
     * @param id 日期主键
     * @return 日期
     */
    public DaRl selectDaRlById(String id);

    /**
     * 查询日期列表
     *
     * @param daRl 日期
     * @return 日期集合
     */
    public List<DaRl> selectDaRlList(DaRl daRl);

    /**
     * 新增日期
     *
     * @param daRl 日期
     * @return 结果
     */
    public int insertDaRl(DaRl daRl);

    /**
     * 修改日期
     *
     * @param daRl 日期
     * @return 结果
     */
    public int updateDaRl(DaRl daRl);

    /**
     * 批量删除日期
     *
     * @param ids 需要删除的日期主键集合
     * @return 结果
     */
    public int deleteDaRlByIds(String[] ids);

    /**
     * 删除日期信息
     *
     * @param id 日期主键
     * @return 结果
     */
    public int deleteDaRlById(String id);

    /**
     * 生成日期的方法
     */
    void addGenerationDate(DaRl daRl);
}
