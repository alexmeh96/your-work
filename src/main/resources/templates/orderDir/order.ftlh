<#import "../part/common.ftlh" as c>
<#import "../part/order.ftlh" as o>
<#import "../part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <#if isCreate>
        <h1>Создать задание</h1>
        <@o.orderForm "/order/create" true/>
    <#else >
        <h1>Изменить задание</h1>
        <@o.orderForm "/order/update" false/>
    </#if>

</@c.page>