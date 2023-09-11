package com.ruoyi.project.question.mapper;

import com.ruoyi.project.question.domain.SySlVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关键字字典Mapper接口
 * 
 * @author LiHai
 * @date 2023-08-25
 */
@Repository
public interface SyMapper
{
    /**
     * 获得代办数量
     */
    int getDbsl(String userId);

    /**
     * 获得呼叫数量
     */
    int getHjsl(@Param("bm") String bm,@Param("userId")  String userId);

    /**
     * 获得接收数量
     */
    int getJssl(@Param("bm") String bm,@Param("userId")  String userId);
}
