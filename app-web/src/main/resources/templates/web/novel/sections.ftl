<#assign title="${novel.name}"/>

<@override name="style">
<style>
    .section-list {
        margin-top: 15px;
    }

    .left {
        float: left;
        width: 130px;
    }

    .right {
        margin-left: 24px;
        float: left;
        width: 858px;
    }

    .name {
        font-size: 22px;
    }

    .info {
        margin-top: 5px;
        font-size: 14px;
    }

    .info span {
        display: inline-block;
        width: 40%;
        margin-top: 2px;
    }

    .info a {
        color: #a72c11;
        text-decoration: none;
    }

    img {
        width: 124px;
        height: 154px;
        background-color: rgb(255, 255, 255);
        border-width: 1px;
        border-style: solid;
        border-color: rgb(221, 221, 221);
        border-image: initial;
        padding: 1px;
    }

    .summary {
        margin-top: 5px;
        border-top: 1px solid #D5D5D5;
        height: 65px;
        overflow: hidden;
        font-size: 13px;
        line-height: 21px;
        padding: 7px 0;
    }

    .header {
        font-size: 18px;
        text-align: center;
        background: #f5f5f5;
    }

    .section-list ul {
        list-style: none;
        margin-top: 6px;
        padding: 0;
    }

    .section-list ul li {
        float: left;
        width: 337px;
        border-bottom: 1px dashed #d5d5d5;
    }

    .section-list ul li a {
        color: #333;
        text-decoration: none;
        line-height: 32px;
        margin-left: 10px;
        font-size: 14px;
    }
</style>
</@override>

<@override name="main">
    <@panel>
        <@breadcrumbs>
            <@breadcrumb name="小说" href="${ctx}/novel"/>
            <@breadcrumb name=title/>
        </@breadcrumbs>

        <div class="novel-info border">
            <div class="left">
            <#if novel.cover!=''>
                <img src="${ctx}/${novel.cover}"/>
            <#else>
                <img src="${ctx}/app/images/nocover.jpg"/>
            </#if>
            </div>
            <div class="right">
                <div class="name">${novel.name}</div>

                <div class="info">
                    <span>作者：${novel.author}</span>
                    <span>最新章节：<#if lastSection??><a
                            href="${ctx}/novel/${novel.novelId}/${lastSection.sectionId}">${lastSection.title}</a><#else>
                        无</#if></span>
                    <span>来源：${enum('name', 'NovelSource', novel.source)}</span>
                    <span>最后更新：<#if lastSection??>${lastSection.createdTime?datetime}<#else>无</#if></span>
                </div>

                <div class="summary">
                    ${novel.summary}
                </div>
            </div>
            <@clear/>
        </div>

        <div class="section-list border">
            <div class="header">《${novel.name}》章节列表</div>
            <ul>
               <#list sections as section>
                   <li>
                       <a href="${ctx}/novel/${novel.novelId}/${section.sectionId}">${section.title}</a>
                   </li>
               </#list>
                <@clear/>
            </ul>
        </div>
    </@panel>
</@override>

<@extends name="../layout.ftl"/>