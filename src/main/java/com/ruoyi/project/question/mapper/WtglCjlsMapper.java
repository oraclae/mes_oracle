package com.ruoyi.project.question.mapper;

import com.ruoyi.project.question.domain.*;
import com.ruoyi.project.question.domain.vo.NumberVO;
import com.ruoyi.project.question.domain.vo.WtlbXlVO;
import com.ruoyi.project.question.domain.vo.WtxxVo;
import com.ruoyi.project.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提出问题Mapper接口
 * 
 * @author LiHai
 * @date 2023-06-19
 */
public interface WtglCjlsMapper 
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
     * 删除提出问题
     * 
     * @param LSID 提出问题主键
     * @return 结果
     */
    public int deleteWtglCjlsByLSID(String LSID);

    /**
     * 批量删除提出问题
     * 
     * @param LSIDs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWtglCjlsByLSIDs(String[] LSIDs);

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
    int changewtzt(@Param("ids") List<String> ids,@Param("wtzt") String wtzt);

    /**
     * 修改解决方案
     * @param id id
     * @param str 方案
     * @return 结果
     */
    int updatejjfa(@Param("id")String id,@Param("str") String str);

    /**
     * 查询问题喊话的列表
     * @return 列表
     */
    List<WtglCjls> selectWtglCjlsSpeechList(String deptName);

    /**
     * 获取问题状态数量
     * @return 问题状态数量
     */
    List<NumberVO> getwtztNumber(String deptName);

    /**
     * 获取部门科室数量
     * @return 问题状态数量
     */
    List<NumberVO> getcjksbmNumber();

    /**
     * 问题接收是责任人
     * @param selectedRows id
     * @return 成功
     */
    int wtjsYesIt(List<WtglCjls> selectedRows);

    /**
     * 问题接收不是责任人
     * @param selectedRows 数据
     * @return 成功
     */
    int wtjsNoIt(List<WtglCjls> selectedRows);

    /**
     * 查询关注的问题列表
     * @param ids id
     * @return 列表
     */
    List<String> getsfgzIdsByIds(List<String> ids);

    /**
     * 查询答案信息列表
     */
    List<WtglDacc> listDacc(@Param("wtglDacc") WtglDacc wtglDacc);

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
    Ywzd getYwzdByXH(String xh);

    /**
     * 修改业务字典
     */
    int updateYwzd(Ywzd ywzd);

    /**
     * 删除业务字典
     */
    int delYwzd(String[] xhs);

    int updateDaccToRd(@Param("daxxid") String daxxid);

    /**
     * 查询提出问题按钮列表
     */
    List<UpButton> getUpButtons();

    /**
     * 查询提出问题按钮
     */
    UpButton getUpButton(String xh);

    /**
     * 新增提出问题按钮
     */
    int addUpButton(UpButton upButton);

    /**
     * 修改提出问题按钮
     */
    int updateUpButton(UpButton upButton);

    /**
     * 删除提出问题按钮
     */
    int delangl(String[] xhs);

    /**
     * 智能回复查询
     */
    List<WtglDacc> listDaccToZN(@Param("wtglDacc") WtglDacc wtglDacc);

    /**
     * 根据岗位与部门查询用户
     */
    List<SysUser> selectUserByPostAndDept(@Param("postName") String postName,@Param("deptId") Long deptId);

    /**
     * 根据部门id查询用户
     */
    List<SysUser> selectUserByDeptId(Long deptId);

    /**
     * 获取被叫人
     */
    WtlbRy selectBjrByWtlbAndDeptId(@Param("wtglCjls") WtglCjls wtglCjls,@Param("deptId") String deptId);

    /**
     * 获取问题类别细类
     */
    List<WtlbXlVO> getWtlbXlList();

    /**
     * 获取问题编号
     */
    String selectWtbh(String format);
}
