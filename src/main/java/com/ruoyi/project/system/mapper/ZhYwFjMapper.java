package com.ruoyi.project.system.mapper;

import com.ruoyi.project.system.domain.ZhYwFj;

import java.util.List;

/**
 * 附件Mapper接口
 *
 * @author qxd
 * @date 2023-07-19
 */
public interface ZhYwFjMapper {
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
     * 删除附件
     *
     * @param xh 附件主键
     * @return 结果
     */
    public int deleteZhYwFjByXh(String xh);

    /**
     * 批量删除附件
     *
     * @param xhs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZhYwFjByXhs(String[] xhs);

    /**
     * 根据id列表获取附件列表
     */
    List<ZhYwFj> selectZhYwFjListByIds(List<ZhYwFj> zhYwFjs);

    /**
     * 查询其他表序号
     */
    String selectQtbxhByXh(String xh);

    /**
     * 通过其他表id查询附件数量
     */
    int countByXhs(String qtbxh);

    /**
     * 根据其他表id查询附件列表
     */
    List<ZhYwFj> selectFjListByIds(String[] qtbxhs);

    /**
     * 查询附件地址
     */
    List<String> selectFwjdzsByQtbxhs(List<String> xhs);
}
