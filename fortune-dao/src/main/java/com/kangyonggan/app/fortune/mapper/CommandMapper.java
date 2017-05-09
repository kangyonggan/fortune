package com.kangyonggan.app.fortune.mapper;

import com.kangyonggan.app.fortune.model.dto.CommandDto;
import com.kangyonggan.app.fortune.model.vo.Command;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandMapper extends MyMapper<Command> {

    /**
     * 查找流水详情
     *
     * @param id
     * @return
     */
    CommandDto selectCommandById(@Param("id") Long id);
}