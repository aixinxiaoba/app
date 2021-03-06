<#assign title="登录"/>
<#include "../../common/auth-form.ftl"/>

<@override name="main">
    <@panel style="background: #000 url('${ctx}/app/images/bg.jpg')">
        <@form action="${ctx}/login" class="login-form" success="success" error="error">
            <@input type="email" label="电子邮箱" name="email" required=true/>
            <@input label="密码" name="password" type="password" required=true validator="isPassword">
                <a href="${ctx}/forgot" target="_blank" class="forgot">忘记密码？</a>
            </@input>
        <div id="captcha-div" class="hidden2">
            <@captcha label="验证码" id="captcha" name="captcha"/>
        </div>

            <@actions>
                <@button name="登录" type="submit" icon="fa-check"/>
                <@button name="重置" type="reset" icon="fa-undo"/>
            </@actions>
        </@form>
    </@panel>
</@override>

<@override name="script">
<script>
    /**
     * 登录成功的回调
     *
     * @param response
     */
    function success(response) {
        var redirectUrl = '${redirectUrl}';
        if (redirectUrl !== '') {
            window.location.href = redirectUrl;
            return;
        }
        window.location.href = "${ctx}/dashboard";
    }

    /**
     * 登录失败的回调
     */
    function error() {
        $("#captcha-div img").attr('src', '${ctx}/captcha?r=' + Math.random());
        $("#captcha").val("");
        $("#captcha-div").removeClass("hidden2");
    }

    $("body").css({"background": "#000"});
</script>
</@override>

<@extends name="../layout.ftl"/>