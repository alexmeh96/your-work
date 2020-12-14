<#import "part/common.ftlh" as c>
<#import "part/category.ftlh" as cat>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Create category</h1>
    <@cat.category "/category/create" true/>
</@c.page>