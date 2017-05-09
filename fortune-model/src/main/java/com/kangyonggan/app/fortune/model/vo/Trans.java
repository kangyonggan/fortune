package com.kangyonggan.app.fortune.model.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "trans")
public class Trans implements Serializable {
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
     * 交易码
     */
    @Column(name = "tran_co")
    private String tranCo;

    /**
     * 交易名称
     */
    @Column(name = "tran_nm")
    private String tranNm;

    /**
     * 单笔限额
     */
    @Column(name = "sing_quota")
    private BigDecimal singQuota;

    /**
     * 日累计限额
     */
    @Column(name = "date_quota")
    private BigDecimal dateQuota;

    /**
     * 交易暂停:{0:正常, 1:暂停}
     */
    @Column(name = "is_paused")
    private Byte isPaused;

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