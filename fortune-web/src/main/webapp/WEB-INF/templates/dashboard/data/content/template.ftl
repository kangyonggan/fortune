<#list templates as t>
    <#if t.code==content.template>
    ${t.value}
    </#if>
</#list>