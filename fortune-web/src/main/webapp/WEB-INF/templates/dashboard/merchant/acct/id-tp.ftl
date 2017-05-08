<#list idTps as idTp>
    <#if idTp.code==merchAcct.merchIdTp>
        ${idTp.value}
    </#if>
</#list>