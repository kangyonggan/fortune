package com.kangyonggan.app.fortune.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "merchant")
public class Merchant implements Serializable {
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
     * 商户名称
     */
    @Column(name = "merch_nm")
    private String merchNm;

    /**
     * 编码
     */
    private String charset;

    /**
     * 商户公钥路径
     */
    @Column(name = "public_key_path")
    private String publicKeyPath;

    /**
     * 发财付私钥路径
     */
    @Column(name = "private_key_path")
    private String privateKeyPath;

    /**
     * ftp主机名
     */
    @Column(name = "ftp_host")
    private String ftpHost;

    /**
     * ftp用户名
     */
    @Column(name = "ftp_user")
    private String ftpUser;

    /**
     * ftp密码
     */
    @Column(name = "ftp_pwd")
    private String ftpPwd;

    /**
     * ftp目录
     */
    @Column(name = "ftp_dir")
    private String ftpDir;

    /**
     * 是否是测试环境:{0:生产环境, 1:测试环境}
     */
    @Column(name = "is_debug")
    private Byte isDebug;

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