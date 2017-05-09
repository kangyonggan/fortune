<#if trans.isDeleted == 1>
<a href="javascript:" data-role="trans-delete" title="恢复交易"
   data-url="${ctx}/dashboard/merchant/trans/${trans.tranCo}/undelete">
    <span class="label label-danger arrowed-in">已删除</span>
</a>
<#else>
<a href="javascript:" data-role="trans-delete" title="删除交易"
   data-url="${ctx}/dashboard/merchant/trans/${trans.tranCo}/delete">
    <span class="label label-success arrowed-in">未删除</span>
</a>
</#if>