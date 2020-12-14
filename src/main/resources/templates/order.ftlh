<#import "part/common.ftlh" as c>
<#import "part/order.ftlh" as o>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Create order</h1>
    <#if isCreate>
        <@o.orderForm "/order/create" true/>
    <#else >
        <@o.orderForm "/order/update" false/>
    </#if>

</@c.page>