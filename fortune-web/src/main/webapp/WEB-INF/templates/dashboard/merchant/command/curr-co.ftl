<#list allCurrs as t>
    <#if t.code==command.currCo>
    ${t.value}
    </#if>
</#list>