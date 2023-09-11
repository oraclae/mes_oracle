package com.ruoyi.project.question.service.impl;

import com.ruoyi.project.question.domain.ZhiNengWenDaDTO;
import com.ruoyi.project.question.domain.vo.ZhiNengWenDaSelectVO;
import com.ruoyi.project.question.mapper.ZhiNengWenDaMapper;
import com.ruoyi.project.question.service.IZhiNengWenDaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Author: LiHai
 * Date: 2023/7/25
 * Description:
 */
@Service
public class ZhiNengWenDaServiceImpl implements IZhiNengWenDaService {
    @Autowired
    ZhiNengWenDaMapper zhiNengWenDaMapper;

    /**
     * 查询
     */
    @Override
    public ZhiNengWenDaSelectVO selectDataByNotId(ZhiNengWenDaDTO zhiNengWenDaDTO) {
        String wtms = zhiNengWenDaDTO.getWTMS();
        if (wtms != null && !"".equals(wtms)) {
            List<String> gjzList = zhiNengWenDaMapper.selectGjz(wtms);
            if (gjzList.size() == 0) {
                return null;
            }
            zhiNengWenDaDTO.setGJZLIST(gjzList);
        }
        return zhiNengWenDaMapper.selectDataByNotId(zhiNengWenDaDTO);
    }
}
