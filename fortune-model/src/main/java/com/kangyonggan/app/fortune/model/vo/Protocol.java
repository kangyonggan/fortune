package com.kangyonggan.app.fortune.model.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "protocol")
public class Protocol implements Serializable {
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
     * 卡号
     */
    @Column(name = "acct_no")
    private String acctNo;

    /**
     * 协议号
     */
    private String protocol;

    /**
     * 户名
     */
    @Column(name = "acct_nm")
    private String acctNm;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 证件类型, 默认身份证：0
     */
    @Column(name = "id_tp")
    private String idTp;

    /**
     * 证件号码
     */
    @Column(name = "id_no")
    private String idNo;

    /**
     * 协议有效期
     */
    @Column(name = "expired_time")
    private Date expiredTime;

    /**
     * 是否解约:{0:正常, 1:已解约}
     */
    @Column(name = "is_unsign")
    private Byte isUnsign;

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