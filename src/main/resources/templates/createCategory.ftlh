<#import "part/common.ftlh" as c>
<#import "part/category.ftlh" as cat>

<@c.page>
    <p>Create category</p>
    <@cat.category "/category/create" true/>
</@c.page>