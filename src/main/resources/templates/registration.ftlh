<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <p>Registration page</p>
<#--    ${message?ifExists}-->
    <@l.login "/registration" true/>
</@c.page>