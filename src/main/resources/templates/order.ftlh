<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <h1>Order page!</h1>
    <#list orders as order>
        <div>${order.describe}</div>
    </#list>
</@c.page>