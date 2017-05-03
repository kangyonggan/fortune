<#list types as e>
<#if e.type==dictionary.type>
    ${e.getName()}[${e.getType()}]
</#if>
</#list>