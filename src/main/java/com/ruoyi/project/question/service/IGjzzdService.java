package com.ruoyi.project.question.service;

import com.ruoyi.project.question.domain.Gjzzd;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关键字字典Service接口
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@Transactional
public interface IGjzzdService 
{
    /**
     * 查询关键字字典
     * 
     * @param xh 关键字字典主键
     * @return 关键字字典
     */
    public Gjzzd selectGjzzdByXh(String xh);

    /**
     * 查询关键字字典列表
     * 
     * @param gjzzd 关键字字典
     * @return 关键字字典集合
     */
    public List<Gjzzd> selectGjzzdList(Gjzzd gjzzd);

    /**
     * 新增关键字字典
     * 
     * @param gjzzd 关键字字典
     * @return 结果
     */
    public int insertGjzzd(Gjzzd gjzzd);

    /**
     * 修改关键字字典
     * 
     * @param gjzzd 关键字字典
     * @return 结果
     */
    public int updateGjzzd(Gjzzd gjzzd);

    /**
     * 批量删除关键字字典
     * 
     * @param xhs 需要删除的关键字字典主键集合
     * @return 结果
     */
    public int deleteGjzzdByXhs(String[] xhs);

    /**
     * 删除关键字字典信息
     * 
     * @param xh 关键字字典主键
     * @return 结果
     */
    public int deleteGjzzdByXh(String xh);
}
