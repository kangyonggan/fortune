package com.kangyonggan.app.fortune.biz.service;

import com.kangyonggan.app.fortune.model.vo.Protocol;

import java.text.ParseException;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/5/4 0004
 */
public interface ProtocolService {

    /**
     * 查找签约协议
     *
     * @param merchCo
     * @param acctNo
     * @return
     */
    Protocol findProtocolByMerchCoAndAcctNo(String merchCo, String acctNo);

    /**
     * 更新协议, 根据协议号
     *
     * @param protocol
     */
    void updateProtocolByProtocolNo(Protocol protocol);

    /**
     * 保存协议
     *
     * @param protocol
     */
    void saveProtocol(Protocol protocol);

    /**
     * 查找协议
     *
     * @param protocolNo
     * @return
     */
    Protocol findProtocolByProtocolNo(String protocolNo);

    /**
     * 搜索协议号
     *
     * @param pageNum
     * @param startDate
     * @param endDate
     * @param protocolNo
     * @param acctNo
     * @return
     * @throws ParseException
     */
    List<Protocol> searchProtocols(int pageNum, String startDate, String endDate, String protocolNo, String acctNo) throws ParseException;

}
