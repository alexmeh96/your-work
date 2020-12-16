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
            <div>Название: <a href="/order/${order.id}">${order.name}</a></div>
            <div>Категория: ${order.category.name}</div>
            <div>Описание: ${order.describe}</div>
            <#if isOffer??>
                <form action="/order/offer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${executor.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Предложить</button>
                </form>
            <#else >
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
            </#if>

            <hr/>
        </#list>
    </#if>
</@c.page>