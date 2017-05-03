<#assign ctx="${(rca.contextPath)!''}">

<tr id="content-${content.id}">
    <td><#include "template.ftl"></td>
    <td>${content.title}</td>
    <td><@c.relative_date datetime=content.createdTime/></td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" target="_blank" href="${ctx}/#content/${content.id}">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="#content/content/${content.id}/edit">编辑</a>
                </li>
                <li>
                    <a href="javascript:" data-role="content-delete" title="删除内容"
                       data-url="${ctx}/dashboard/content/content/${content.id}/delete">
                        删除
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>