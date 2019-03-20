<@override name="main">
    <@search_form id="form">
        <@input name="userId" label="用户ID"/>

        <@form_actions>
            <@query table_id="table"/>
            <@edit table_id="table" url="${_baseUrl}/{{musicId}}/edit"/>
            <@create url="${_baseUrl}/create"/>
            <@delete table_id="table" url="${_baseUrl}/delete?musicIds={{musicId}}"/>
            <@restore table_id="table" url="${_baseUrl}/restore?musicIds={{musicId}}"/>
        </@form_actions>
    </@search_form>

    <@table id="table" url="${_baseUrl}/list" form_id="form" checkbox=true>
        <@th field="musicId" title="音乐ID"/>
        <@th field="userId" title="用户ID"/>
        <@th field="content" title="音乐代码" auto_hide=true/>
        <@thYesNo field="isDeleted" title="逻辑删除" auto_hide=true/>
        <@thDatetime field="createdTime" title="创建时间"/>
    </@table>
</@override>

<@extends name="../../layout.ftl"/>