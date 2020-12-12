<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>
<#import "part/navbar.ftlh" as n>
<#include "part/security.ftlh">


<@c.page>
    <@n.navbar />
    <h1>Main page</h1>
    <p>Email ${name} </p>

    <@l.logout />

</@c.page>