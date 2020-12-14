<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>
<#import "part/category.ftlh" as cat>
<#include "part/security.ftlh">

<@c.page>
    <@n.navbar />
    <@n.mes />
    <#if isCategory??>
        <h1>Order category</h1>
        <@cat.showCategory "/order/category" />
    <#else>
        <h1>Orders</h1>
        <#list orders as order>
            <div>Категория: ${order.category.name}</div>
            <div>Описание: ${order.describe}</div>
            <form action="/order/subscribe" method="post">
                <input type="hidden" name="orderId" value="${order.id}">
                <input type="hidden" name="userId" value="${currentUserId}">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <#if user??>
                    <#if order.author.id != currentUserId>
                        <button type="submit">Откликнуться</button>
                    <#else>
                        <a href="/order/update/${order.id}">Изменить</a>
                    </#if>
                </#if>
            </form>
            <hr/>
        </#list>
    </#if>
</@c.page>