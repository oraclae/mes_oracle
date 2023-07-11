package com.ruoyi.project.question.service;

import com.ruoyi.project.question.domain.WtglCjls;
import com.ruoyi.project.question.domain.WtglCjlsDTO;
import com.ruoyi.project.question.domain.WtglDacc;
import com.ruoyi.project.question.domain.Ywzd;
import com.ruoyi.project.question.domain.vo.NumberVO;
import com.ruoyi.project.question.domain.vo.WtxxVo;

import java.util.List;

/**
 * 提出问题Service接口
 * 
 * @author LiHai
 * @date 2023-06-19
 */
public interface IWtglCjlsService 
{
    /**
     * 查询提出问题
     * 
     * @param LSID 提出问题主键
     * @return 提出问题
     */
    public WtglCjls selectWtglCjlsByLSID(String LSID);

    /**
     * 查询提出问题列表
     * 
     * @param wtglCjls 提出问题
     * @return 提出问题集合
     */
    public List<WtglCjls> selectWtglCjlsList(WtglCjlsDTO wtglCjls);

    /**
     * 查询接收问题列表
     *
     * @param wtglCjls 提出问题
     * @return 提出问题集合
     */
    public List<WtglCjls> selectWtglDownCjlsList(WtglCjls wtglCjls);

    /**
     * 新增提出问题
     * 
     * @param wtglCjls 提出问题
     * @return 结果
     */
    public int insertWtglCjls(WtglCjls wtglCjls);

    /**
     * 修改提出问题
     * 
     * @param wtglCjls 提出问题
     * @return 结果
     */
    public int updateWtglCjls(WtglCjls wtglCjls);

    /**
     * 批量删除提出问题
     * 
     * @param LSIDs 需要删除的提出问题主键集合
     * @return 结果
     */
    public int deleteWtglCjlsByLSIDs(String[] LSIDs);

    /**
     * 删除提出问题信息
     * 
     * @param LSID 提出问题主键
     * @return 结果
     */
    public int deleteWtglCjlsByLSID(String LSID);

    /**
     * 查询问题列表
     * @param wtglCjls 参数
     * @return 列表
     */
    List<WtxxVo> selectywcjList(WtglCjls wtglCjls);

    /**
     * 修改问题状态
     * @param ids 需要修改的id
     * @param wtzt 问题状态
     * @return 结果
     */
    int changewtzt(String[] ids, String wtzt);

    /**
     * 问题接收
     * @param selectedRows 需要修改的数据
     * @param whether 是否为责任人
     * @return 结果
     */
    int wtjsIt(List<WtglCjls> selectedRows, String whether);

    /**
     * 修改解决方案
     * @param id id
     * @param str 方案
     * @return 结果
     */
    int updatejjfa(String id, String str);

    /**
     * 获取完成、未完成数量
     * @return 数量
     */
    List<NumberVO> getwcsl();

    /**
     * 获取部门科室数量
     * @return 数量
     */
    List<NumberVO> getcjksbmNumber();

    /**
     * 查询问题喊话的列表
     * @return 列表
     */
    List<WtglCjls> selectWtglCjlsSpeechList();

    /**
     * 获取接收人列表
     * @param wtlb 问题类别
     * @return 接收人列表
     */
    List<String> getjsrBywtlb(String wtlb);

    /**
     * 查询答案信息列表
     */
    List<WtglDacc> listDacc(WtglDacc wtglDacc);

    /**
     * 获取答案信息详细信息
     */
    WtglDacc getDacc(String daxxID);

    /**
     * 修改答案信息
     */
    int updateDacc(WtglDacc wtglDacc);

    /**
     * 删除答案信息
     */
    int delDacc(String[] daccIds);

    /**
     * 查询业务字典列表
     */
    List<Ywzd> listYwzd(Ywzd ywzd);

    /**
     * 新增业务字典
     */
    int addYwzd(Ywzd ywzd);

    /**
     * 获取业务字典详细信息
     */
    Ywzd getYwzd(String xh);

    /**
     * 修改业务字典
     */
    int updateYwzd(Ywzd ywzd);

    /**
     * 删除业务字典
     */
    int delYwzd(String[] xhs);
}
