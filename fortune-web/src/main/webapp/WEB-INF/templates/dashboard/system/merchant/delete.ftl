<#if merchant.isDeleted == 1>
<a href="javascript:" data-role="merchant-delete" title="恢复商户"
   data-url="${ctx}/dashboard/system/merchant/${merchant.id}/undelete">
    <span class="label label-danger arrowed-in">已删除</span>
</a>
<#else>
<a href="javascript:" data-role="merchant-delete" title="删除商户"
   data-url="${ctx}/dashboard/system/merchant/${merchant.merchCo}/delete">
    <span class="label label-success arrowed-in">未删除</span>
</a>
</#if>