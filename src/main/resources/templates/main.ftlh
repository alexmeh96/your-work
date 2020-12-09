<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>

<@c.page>
    <p>Main page</p>
    <p>Email ${email} </p>
    <@l.logout />

</@c.page>