package com.kangyonggan.app.fortune.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Table(name = "merch_acct")
@Data
public class MerchAcct implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商户号
     */
    @Column(name = "merch_co")
    private String merchCo;

    /**
     * 商户银行卡号
     */
    @Column(name = "merch_acct_no")
    private String merchAcctNo;

    /**
     * 商户银行卡户名
     */
    @Column(name = "merch_acct_nm")
    private String merchAcctNm;

    /**
     * 商户手机号
     */
    @Column(name = "merch_mobile")
    private String merchMobile;

    /**
     * 商户证件号
     */
    @Column(name = "merch_id_no")
    private String merchIdNo;

    /**
     * 商户证件类型
     */
    @Column(name = "merch_id_tp")
    private String merchIdTp;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 是否主卡:{0:主卡, 1:备用卡}
     */
    @Column(name = "is_master")
    private Byte isMaster;

    /**
     * 逻辑删除:{0:未删除, 1:已删除}
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}