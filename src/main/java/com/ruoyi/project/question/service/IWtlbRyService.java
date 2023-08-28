package com.ruoyi.project.question.service;

import java.util.List;
import com.ruoyi.project.question.domain.WtlbRy;
import org.springframework.transaction.annotation.Transactional;

/**
 * 问题类别人员对照Service接口
 * 
 * @author ruoyi
 * @date 2023-08-25
 */
@Transactional
public interface IWtlbRyService 
{
    /**
     * 查询问题类别人员对照
     * 
     * @param xh 问题类别人员对照主键
     * @return 问题类别人员对照
     */
    public WtlbRy selectWtlbRyByXh(String xh);

    /**
     * 查询问题类别人员对照列表
     * 
     * @param wtlbRy 问题类别人员对照
     * @return 问题类别人员对照集合
     */
    public List<WtlbRy> selectWtlbRyList(WtlbRy wtlbRy);

    /**
     * 新增问题类别人员对照
     * 
     * @param wtlbRy 问题类别人员对照
     * @return 结果
     */
    public int insertWtlbRy(WtlbRy wtlbRy);

    /**
     * 修改问题类别人员对照
     * 
     * @param wtlbRy 问题类别人员对照
     * @return 结果
     */
    public int updateWtlbRy(WtlbRy wtlbRy);

    /**
     * 批量删除问题类别人员对照
     * 
     * @param xhs 需要删除的问题类别人员对照主键集合
     * @return 结果
     */
    public int deleteWtlbRyByXhs(String[] xhs);

    /**
     * 删除问题类别人员对照信息
     * 
     * @param xh 问题类别人员对照主键
     * @return 结果
     */
    public int deleteWtlbRyByXh(String xh);
}
