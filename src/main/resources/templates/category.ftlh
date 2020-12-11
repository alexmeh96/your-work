<#import "part/common.ftlh" as c>

<@c.page>
    <h1>Category page!</h1>
    <#list categories as category>
        <div>${category.name}</div>
    </#list>
</@c.page>