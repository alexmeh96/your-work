<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <h1>Category page!</h1>
    <#list categories as category>
        <div>${category.name}</div>
    </#list>
</@c.page>