<#import "../part/common.ftlh" as c>
<#import "../part/orderForm.ftlh" as o>
<#import "../part/showOrders.ftlh" as s>

<@c.page>
    <h1>Другие задания</h1>

    <@s.showOrder "offer" />

</@c.page>