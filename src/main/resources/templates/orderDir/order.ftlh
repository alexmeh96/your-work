<#import "../part/common.ftlh" as c>
<#import "../part/orderForm.ftlh" as o>


<@c.page>
    <#if isCreate>
        <h1>Добавить задание</h1>
        <@o.orderForm "/order/create" true/>
    <#else >
        <h1>Изменить задание</h1>
        <@o.orderForm "/order/update" false/>
    </#if>

</@c.page>