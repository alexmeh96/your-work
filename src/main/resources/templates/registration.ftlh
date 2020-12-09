<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>

<@c.page>
    <p>Registration page</p>
    ${message}
    <@l.login "/registration" />
</@c.page>