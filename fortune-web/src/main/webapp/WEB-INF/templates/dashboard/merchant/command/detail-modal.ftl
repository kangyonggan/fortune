<#assign modal_title="交易详情" />

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tbody>
    <tr>
        <th width="25%">商户号</th>
        <td width="75%">${command.merchCo}</td>
    </tr>
    <tr>
        <th>协议号</th>
        <td>${command.protocolNo}</td>
    </tr>
    <tr>
        <th>商户流水号</th>
        <td>${command.merchSerialNo}</td>
    </tr>
    <tr>
        <th>发财付流水号</th>
        <td>${command.fpaySerialNo}</td>
    </tr>
    <tr>
        <th>交易类型</th>
        <td><#include "tran-co.ftl">(${command.tranCo})</td>
    </tr>
    <tr>
        <th>卡号</th>
        <td>${command.acctNo!''}</td>
    </tr>
    <tr>
        <th>户名</th>
        <td>${command.acctNm!''}</td>
    </tr>
    <tr>
        <th>证件类型</th>
        <td><#if command.idTp??><#include "id-tp.ftl">(${command.idTp})</#if></td>
    </tr>
    <tr>
        <th>证件号码</th>
        <td>${command.idNo!''}</td>
    </tr>
    <tr>
        <th>手机号</th>
        <td>${command.mobile!''}</td>
    </tr>
    <tr>
        <th>请求日期</th>
        <td>${command.reqDate}</td>
    </tr>
    <tr>
        <th>请求时间</th>
        <td>${command.reqTime}</td>
    </tr>
    <tr>
        <th>交易金额</th>
        <td>${command.amount}元</td>
    </tr>
    <tr>
        <th>币种</th>
        <td><#include "curr-co.ftl">(${command.currCo})</td>
    </tr>
    <tr>
        <th>账户类型</th>
        <td><#include "acct-tp.ftl">(${command.acctTp})</td>
    </tr>
    <tr>
        <th>交易状态</th>
        <td><#include "tran-st.ftl">(${command.tranSt})</td>
    </tr>
    <tr>
        <th>清算日期</th>
        <td>${command.settleDate}</td>
    </tr>
    <tr>
        <th>备注</th>
        <td>${command.remark}</td>
    </tr>
    <tr>
        <th>预留字段1</th>
        <td>${command.resv1}</td>
    </tr>
    <tr>
        <th>预留字段2</th>
        <td>${command.resv1}</td>
    </tr>
    <tr>
        <th>逻辑删除</th>
        <td>${(command.isDeleted==1)?string('是', '否')}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td><#if command.createdTime??>${command.createdTime?datetime}</#if></td>
    </tr>
    <tr>
        <th>更新时间</th>
        <td><#if command.updatedTime??>${command.updatedTime?datetime}</#if></td>
    </tr>
    </tbody>
</table>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@s.message "app.button.close"/>
</button>
</@override>

<@extends name="../../../modal-layout.ftl"/>