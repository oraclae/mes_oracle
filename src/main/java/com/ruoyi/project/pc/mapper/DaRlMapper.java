package com.ruoyi.project.pc.mapper;

import java.util.List;

import com.ruoyi.project.pc.domain.DaRl;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 日期Mapper接口
 *
 * @author qxd
 * @date 2023-09-23
 */
public interface DaRlMapper {
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

    public int insertDaRls(@Param("daRls") List<DaRl> daRls);

    /**
     * 修改日期
     *
     * @param daRl 日期
     * @return 结果
     */
    public int updateDaRl(DaRl daRl);

    /**
     * 删除日期
     *
     * @param id 日期主键
     * @return 结果
     */
    public int deleteDaRlById(String id);

    /**
     * 批量删除日期
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDaRlByIds(String[] ids);

    /**
     * 根据日期获得是否上班
     */
    String selectByRq(String dateString);
}
