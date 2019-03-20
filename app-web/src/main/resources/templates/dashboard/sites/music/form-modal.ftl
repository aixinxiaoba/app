<#assign ctx="${(rca.contextPath)!''}"/>
<#assign isEdit=music.musicId??/>
<#assign modal_title="${isEdit?string('编辑', '新增')}音乐" />

<@override name="modal-body">
    <@form action="${ctx}/dashboard/sites/music/${isEdit?string('update', 'save')}" table_id="table" token=true>
        <#if isEdit>
            <@input name="musicId" label="音乐ID" value="${music.musicId}" readonly=true/>
            <@input label="用户ID" value="${music.userId!''}" readonly=true/>
        </#if>
        <@input name="content" label="视频代码" value="${(music.content!'')?html}" required=true range_length=[1, 512]/>
    </@form>
</@override>

<@override name="modal-footer">
    <@submit/>
</@override>

<@extends name="../../modal-layout.ftl"/>