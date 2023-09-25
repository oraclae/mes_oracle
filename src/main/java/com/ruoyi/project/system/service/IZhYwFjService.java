package com.ruoyi.project.system.service;


import com.ruoyi.project.system.domain.ZhYwFj;

import java.util.List;

/**
 * 附件Service接口
 * 
 * @author qxd
 * @date 2023-07-19
 */
public interface IZhYwFjService 
{
    /**
     * 查询附件
     * 
     * @param xh 附件主键
     * @return 附件
     */
    public ZhYwFj selectZhYwFjByXh(String xh);

    /**
     * 查询附件列表
     * 
     * @param zhYwFj 附件
     * @return 附件集合
     */
    public List<ZhYwFj> selectZhYwFjList(ZhYwFj zhYwFj);

    /**
     * 新增附件
     * 
     * @param zhYwFj 附件
     * @return 结果
     */
    public int insertZhYwFj(ZhYwFj zhYwFj);

    /**
     * 修改附件
     * 
     * @param zhYwFj 附件
     * @return 结果
     */
    public int updateZhYwFj(ZhYwFj zhYwFj);

    /**
     * 批量删除附件
     * 
     * @param xhs 需要删除的附件主键集合
     * @return 结果
     */
    public int deleteZhYwFjByXhs(String[] xhs);

    /**
     * 删除附件信息
     * 
     * @param xh 附件主键
     * @return 结果
     */
    public int deleteZhYwFjByXh(String xh);

    /**
     * 根据id列表获取附件列表
     */
    List<ZhYwFj> selectZhYwFjListByIds(List<ZhYwFj> zhYwFjs);

    /**
     * 根据其他表id查询附件列表
     */
    List<ZhYwFj> selectFjListByIds(String[] qtbxhs);
}
