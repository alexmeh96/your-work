<#import "part/common.ftlh" as c>
<#import "part/order.ftlh" as o>

<@c.page>
    <p>Create order</p>
    <@o.order "/order/create" true/>
</@c.page>