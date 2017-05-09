<#list allIdTps as t>
    <#if t.code==command.idTp>
    ${t.value}
    </#if>
</#list>
