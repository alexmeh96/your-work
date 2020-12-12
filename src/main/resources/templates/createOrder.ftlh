<#import "part/common.ftlh" as c>
<#import "part/order.ftlh" as o>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <h1>Create order</h1>
    ${message?ifExists}
    <@o.order "/order/create" true/>
</@c.page>