<#list allAcctTps as t>
    <#if t.code==command.acctTp>
    ${t.value}
    </#if>
</#list>