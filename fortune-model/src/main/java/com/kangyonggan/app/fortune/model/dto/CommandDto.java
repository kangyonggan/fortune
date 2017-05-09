package com.kangyonggan.app.fortune.model.dto;

import com.kangyonggan.app.fortune.model.vo.Command;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2017/5/9 0009
 */
@Data
public class CommandDto extends Command {

    private String acctNo;
    private String acctNm;
    private String idTp;
    private String idNo;
    private String mobile;

}
