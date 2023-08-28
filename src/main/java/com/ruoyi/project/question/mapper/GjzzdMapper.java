package com.ruoyi.project.question.mapper;

import com.ruoyi.project.question.domain.Gjzzd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关键字字典Mapper接口
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@Repository
public interface GjzzdMapper 
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
     * 删除关键字字典
     * 
     * @param xh 关键字字典主键
     * @return 结果
     */
    public int deleteGjzzdByXh(String xh);

    /**
     * 批量删除关键字字典
     * 
     * @param xhs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGjzzdByXhs(String[] xhs);

    /**
     * 根据关键字查询数量
     */
    int countByGjz(String gjz);
}
