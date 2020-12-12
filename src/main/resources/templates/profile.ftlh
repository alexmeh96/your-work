<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <h1>Profile page</h1>
    <#if profile??>
        <div>First name ${profile.firstName}</div>
        <div>First name ${profile.lastName}</div>
    </#if>
</@c.page>