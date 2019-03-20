<#assign title="音乐"/>

<@override name="style">
<style>
    .music-list {
        margin: 0;
        padding: 0;
        min-height: 200px;
        list-style: none;
    }

    .music-list li {
        float: left;
        padding: 10px;
    }

    .music {
        position: relative;
        border: 1px solid #d5d5d5;
        margin: 0 auto;
    }

    .music iframe, .music embed {
        width: 330px;
        height: 86px;
        margin: 0 auto;
    }

    .empty {
        text-align: center;
        line-height: 80px;
    }
</style>
</@override>

<@override name="main">
<div class="main">
    <@panel>
        <@breadcrumbs>
            <@breadcrumb name=title/>
        </@breadcrumbs>

        <#if page.list?size gt 0>
        <ul class="music-list border">
            <#list page.list as music>
                <li>
                    <div class="music">
                        ${music.content}
                    </div>
                </li>
            </#list>
            <div class="clear"></div>
        </ul>

        <div style="height: 20px;"></div>

            <#assign url="music"/>
            <#assign params=""/>
            <#if (page.list)?? && page.pages gt 1>
            <ul class="pagination">
                <#if page.hasPreviousPage>
                    <li>
                        <a href="${ctx}/${url}?pageNum=1<#if params?has_content>&${params}</#if>" title="首页">
                            <i class="ace-icon fa fa-angle-double-left"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${ctx}/${url}?pageNum=${page.prePage}<#if params?has_content>&${params}</#if>"
                           title="上一页">
                            <i class="ace-icon fa fa-angle-left"></i>
                        </a>
                    </li>
                </#if>

                <#list page.navigatepageNums as nav>
                    <#if nav == page.pageNum>
                        <li class="active">
                            <a href="javascript:">${nav}</a>
                        </li>
                    <#else>
                        <li>
                            <a href="${ctx}/${url}?pageNum=${nav}<#if params?has_content>&${params}</#if>">${nav}</a>
                        </li>
                    </#if>
                </#list>

                <#if page.hasNextPage>
                    <li>
                        <a href="${ctx}/${url}?pageNum=${page.nextPage}<#if params?has_content>&${params}</#if>"
                           title="下一页">
                            <i class="ace-icon fa fa-angle-right"></i>
                        </a>
                    </li>

                    <li>
                        <a href="${ctx}/${url}?pageNum=${page.pages}<#if params?has_content>&${params}</#if>"
                           title="尾页">
                            <i class="ace-icon fa fa-angle-double-right"></i>
                        </a>
                    </li>
                </#if>
            </ul>
            </#if>
        <#else>
        <div class="empty">没有音乐</div>
        </#if>
    </@panel>
</div>
</@override>

<@extends name="../layout.ftl"/>