<#assign ctx="${(rca.contextPath)!''}">

<tr id="merchAcct-${merchAcct.id}">
    <td>${merchAcct.merchAcctNo}</td>
    <td>${merchAcct.merchAcctNm}</td>
    <td><#include "id-tp.ftl"></td>
    <td>${merchAcct.merchIdNo}</td>
    <td>${merchAcct.merchMobile}</td>
    <td>${merchAcct.balance}元</td>
    <td><#if merchAcct.isMaster==1><span class="green">主卡<#else>副卡</#if></td>
    <td><@c.relative_date datetime=merchAcct.createdTime/></td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard/merchant/acct/${merchAcct.merchAcctNo}/edit"
               data-toggle="modal" data-target="#myModal"
               data-backdrop="static">编辑</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-role="acct-remove" title="删除银行卡"
                       data-url="${ctx}/dashboard/merchant/acct/${merchAcct.merchAcctNo}/remove">
                        删除
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>