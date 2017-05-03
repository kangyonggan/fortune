<#assign ctx="${(rca.contextPath)!''}">

<tr>
    <td><#include "project.ftl"/></td>
    <td>${key}</td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" href="#content/cache/${key}">查看值</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-url="${ctx}/dashboard/content/cache/${key}/clear"
                       data-role="clear-cache" title="清空${key}">清除</a>
                </li>
            </ul>
        </div>
    </td>
</tr>