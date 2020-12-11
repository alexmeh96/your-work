<#import "part/common.ftlh" as c>

<@c.page>
    <h1>Order page!</h1>
    <#list orders as order>
        <div>${order.describe}</div>
    </#list>
</@c.page>