package com.kangyonggan.app.fortune.model.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "resp")
public class Resp implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 响应码
     */
    @Column(name = "resp_co")
    private String respCo;

    /**
     * 响应码描述
     */
    @Column(name = "resp_msg")
    private String respMsg;

    /**
     * 交易状态
     */
    @Column(name = "trans_st")
    private String transSt;

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