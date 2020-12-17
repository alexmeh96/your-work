<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <p>Login page</p>
    ${messageError?ifExists}
    <@l.login "/login" false/>
</@c.page>