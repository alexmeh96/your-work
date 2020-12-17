<#import "../part/common.ftlh" as c>
<#import "../part/order.ftlh" as o>
<#import "../part/navbar.ftlh" as n>
<#import "../part/showOrders.ftlh" as s>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Другие задания</h1>

    <@s.showOrder "offer" />

</@c.page>