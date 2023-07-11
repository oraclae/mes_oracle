package com.ruoyi.project.question.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.question.domain.WtglCjls;
import com.ruoyi.project.question.domain.WtglCjlsDTO;
import com.ruoyi.project.question.domain.WtglDacc;
import com.ruoyi.project.question.domain.Ywzd;
import com.ruoyi.project.question.domain.vo.NumberVO;
import com.ruoyi.project.question.domain.vo.WtxxVo;
import com.ruoyi.project.question.mapper.WtglCjlsMapper;
import com.ruoyi.project.question.service.IWtglCjlsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 提出问题Service业务层处理
 * 
 * @author LiHai
 * @date 2023-06-19
 */
@Service
public class WtglCjlsServiceImpl implements IWtglCjlsService
{
    @Autowired
    private WtglCjlsMapper wtglCjlsMapper;

    /**
     * 查询提出问题
     * 
     * @param LSID 提出问题主键
     * @return 提出问题
     */
    @Override
    public WtglCjls selectWtglCjlsByLSID(String LSID)
    {
        return wtglCjlsMapper.selectWtglCjlsByLSID(LSID);
    }

    /**
     * 查询提出问题列表
     * 
     * @param wtglCjls 提出问题
     * @return 提出问题
     */
    @Override
    public List<WtglCjls> selectWtglCjlsList(WtglCjlsDTO wtglCjls)
    {
        return wtglCjlsMapper.selectWtglCjlsList(wtglCjls);
    }

    /**
     * 查询接收问题列表
     *
     * @param wtglCjls 提出问题
     * @return 提出问题
     */
    @Override
    public List<WtglCjls> selectWtglDownCjlsList(WtglCjls wtglCjls)
    {
        return wtglCjlsMapper.selectWtglDownCjlsList(wtglCjls);
    }

    /**
     * 新增提出问题
     *
     * @param wtglCjls 提出问题
     * @return 结果
     */
    @Override
    public int insertWtglCjls(WtglCjls wtglCjls)
    {
        wtglCjls.setLSID(UUID.randomUUID().toString());
        wtglCjls.setWTZT("提交");
        wtglCjls.setBJZRR("被叫责任人");//被叫责任人赋值
        // 提报人赋值
        //String nickName = wtglCjlsMapper.getNickName(SecurityUtils.getUsername());
        wtglCjls.setTBR("admin");
        String wtlb = wtglCjls.getWTLB();
        int index = wtlb.lastIndexOf("问题");
        if(index >= 0){
            wtlb = wtlb.substring(0, index);
        }
        wtglCjls.setBJZRKS(wtlb+"科室");
        /*查询部门科室，并给创建部门科室赋值*/
        wtglCjls.setCJBMKS(wtlb+"科室");
        return wtglCjlsMapper.insertWtglCjls(wtglCjls);
    }

    /**
     * 修改提出问题
     * 
     * @param wtglCjls 提出问题
     * @return 结果
     */
    @Override
    public int updateWtglCjls(WtglCjls wtglCjls)
    {
        return wtglCjlsMapper.updateWtglCjls(wtglCjls);
    }

    /**
     * 批量删除提出问题
     * 
     * @param LSIDs 需要删除的提出问题主键
     * @return 结果
     */
    @Override
    public int deleteWtglCjlsByLSIDs(String[] LSIDs)
    {
        return wtglCjlsMapper.deleteWtglCjlsByLSIDs(LSIDs);
    }

    /**
     * 删除提出问题信息
     * 
     * @param LSID 提出问题主键
     * @return 结果
     */
    @Override
    public int deleteWtglCjlsByLSID(String LSID)
    {
        return wtglCjlsMapper.deleteWtglCjlsByLSID(LSID);
    }

    @Override
    public List<WtxxVo> selectywcjList(WtglCjls wtglCjls) {
        List<WtxxVo> list = wtglCjlsMapper.selectywcjList(wtglCjls);
        List<String> ids = new ArrayList<>();
        for (WtxxVo wtxxVo : list) {
            ids.add(wtxxVo.getID());
        }
        List<String> sfgzIds = wtglCjlsMapper.getsfgzIdsByIds(ids);
        for (WtxxVo wtxxVo : list) {
            wtxxVo.setSfgz(false);
            for (String sfgzId : sfgzIds) {
                if(wtxxVo.getID().equals(sfgzId)){
                    wtxxVo.setSfgz(true);
                }
            }
        }
        return list;
    }

    /**
     * 修改问题状态
     * @param ids 需要修改的id
     * @param wtzt 问题状态
     * @return 结果
     */
    @Override
    public int changewtzt(String[] ids, String wtzt) {
        return wtglCjlsMapper.changewtzt(ids,wtzt);
    }

    /**
     * 问题接收
     * @param selectedRows 需要修改的数据
     * @param whether 是否为责任人
     * @return 结果
     */
    @Override
    public int wtjsIt(List<WtglCjls> selectedRows, String whether) {
        int i = 0;
        Date date = new Date();
        for (WtglCjls selectedRow : selectedRows) {
            Date cjsj = selectedRow.getCJSJ();
            LocalDateTime date1 = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDateTime date2 = LocalDateTime.ofInstant(cjsj.toInstant(), ZoneId.systemDefault());
            long minutesDifference = ChronoUnit.MINUTES.between(date2, date1);
            selectedRow.setTBJSSJ(String.valueOf(minutesDifference)+"分");
        }
        if("是".equals(whether)){
            i = wtglCjlsMapper.wtjsYesIt(selectedRows);
        }else {
            i = wtglCjlsMapper.wtjsNoIt(selectedRows);
        }
        return i;
    }

    /**
     * 修改解决方案
     * @param id id
     * @param str 方案
     * @return 结果
     */
    @Override
    public int updatejjfa(String id, String str) {
        return wtglCjlsMapper.updatejjfa(id,str);
    }

    /**
     * 获取完成、未完成数量
     * @return 数量
     */
    @Override
    public List<NumberVO> getwcsl() {
        return wtglCjlsMapper.getwtztNumber();
    }

    /**
     * 获取部门科室数量
     * @return 数量
     */
    @Override
    public List<NumberVO> getcjksbmNumber() {
        return wtglCjlsMapper.getcjksbmNumber();
    }

    /**
     * 查询问题喊话的列表
     * @return 列表
     */
    @Override
    public List<WtglCjls> selectWtglCjlsSpeechList() {
        return wtglCjlsMapper.selectWtglCjlsSpeechList();
    }

    /**
     * 获取接收人列表
     * @param wtlb 问题类别
     * @return 接收人列表
     */
    @Override
    public List<String> getjsrBywtlb(String wtlb) {
//        List<String> jsrList = wtglCjlsMapper.getjsrBywtlb();
        List<String> jsrList = new ArrayList<>();
        jsrList.add("接收人1");
        jsrList.add("接收人2");
        jsrList.add("接收人3");
        jsrList.add("接收人4");
        return jsrList;
    }

    /**
     * 查询答案信息列表
     */
    @Override
    public List<WtglDacc> listDacc(WtglDacc wtglDacc) {
        return wtglCjlsMapper.listDacc(wtglDacc);
    }

    /**
     * 获取答案信息详细信息
     */
    @Override
    public WtglDacc getDacc(String daxxID) {
        return wtglCjlsMapper.getDacc(daxxID);
    }

    /**
     * 修改答案信息
     */
    @Override
    public int updateDacc(WtglDacc wtglDacc) {
        System.out.println(wtglDacc);
        return wtglCjlsMapper.updateDacc(wtglDacc);
    }

    /**
     * 删除答案信息
     */
    @Override
    public int delDacc(String[] daccIds) {
        return wtglCjlsMapper.delDacc(daccIds);
    }

    /**
     * 查询业务字典列表
     */
    @Override
    public List<Ywzd> listYwzd(Ywzd ywzd) {
        return wtglCjlsMapper.listYwzd(ywzd);
    }

    /**
     * 新增业务字典
     */
    @Override
    public int addYwzd(Ywzd ywzd) {
        return wtglCjlsMapper.addYwzd(ywzd);
    }

    /**
     * 获取业务字典详细信息
     */
    @Override
    public Ywzd getYwzd(String xh) {
        return wtglCjlsMapper.getYwzdByXH(xh);
    }

    /**
     * 修改业务字典
     */
    @Override
    public int updateYwzd(Ywzd ywzd) {
        return wtglCjlsMapper.updateYwzd(ywzd);
    }

    /**
     * 删除业务字典
     */
    @Override
    public int delYwzd(String[] xhs) {
        return wtglCjlsMapper.delYwzd(xhs);
    }
}
