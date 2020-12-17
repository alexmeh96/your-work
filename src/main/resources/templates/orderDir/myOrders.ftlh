<#import "../part/common.ftlh" as c>
<#import "../part/order.ftlh" as o>
<#import "../part/navbar.ftlh" as n>
<#include "../part/security.ftlh">
<#import "../part/showOrders.ftlh" as s>


<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Мои задания</h1>
    <a href="/order/create">Создать задание</a>
    <a href="/order/other">Другие задания</a>
    <@s.showOrder "" />

</@c.page>