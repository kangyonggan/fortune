<#list allTrans as t>
<#if t.code==command.tranCo>
    ${t.value}
</#if>
</#list>