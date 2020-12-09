<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>

<@c.page>
    <p>Login page</p>
<#--    ${message}-->
    <@l.login "/login" />
    <a href="/registration">Sign Up</a>
</@c.page>