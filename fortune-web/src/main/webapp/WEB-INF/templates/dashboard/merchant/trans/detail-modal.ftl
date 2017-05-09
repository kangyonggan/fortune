<#assign modal_title="商户详情" />

<@override name="modal-body">
<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
    <tbody>
    <tr>
        <th width="25%">商户号</th>
        <td width="75%">${merchant.merchCo}</td>
    </tr>
    <tr>
        <th>商户名称</th>
        <td>${merchant.merchNm}</td>
    </tr>
    <tr>
        <th>字符集</th>
        <td>${merchant.charset}</td>
    </tr>
    <tr>
        <th>调试模式</th>
        <td><#include "debug.ftl"></td>
    </tr>
    <tr>
        <th>商户公钥路径</th>
        <td>${merchant.publicKeyPath!''}</td>
    </tr>
    <tr>
        <th>发财付私钥路径</th>
        <td>${merchant.privateKeyPath!''}</td>
    </tr>
    <tr>
        <th>FTP主机名</th>
        <td>${merchant.host!''}</td>
    </tr>
    <tr>
        <th>FTP用户名</th>
        <td>${merchant.user!''}</td>
    </tr>
    <tr>
        <th>FTP密码</th>
        <td>${merchant.pwd!''}</td>
    </tr>
    <tr>
        <th>FTP目录</th>
        <td>${merchant.dir!''}</td>
    </tr>
    <tr>
        <th>逻辑删除</th>
        <td>${(merchant.isDeleted==1)?string('是', '否')}</td>
    </tr>
    <tr>
        <th>创建时间</th>
        <td><#if merchant.createdTime??>${merchant.createdTime?datetime}</#if></td>
    </tr>
    <tr>
        <th>更新时间</th>
        <td><#if merchant.updatedTime??>${merchant.updatedTime?datetime}</#if></td>
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