<#list projects as proj>
    <#if proj.code==project>
        ${proj.value}
    </#if>
</#list>