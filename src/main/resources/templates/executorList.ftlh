<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>
<#import "part/category.ftlh" as cat>
<#include "part/security.ftlh">

<@c.page>
    <@n.navbar />
    <@n.mes />
    <#if isCategory??>
        <h1>Executor category</h1>
        <@cat.showCategory "/executor/category" />
    <#elseif category??>
        <h1>Executors</h1>
        <div>Категория: ${category.name}</div>
        <#list category.executors as executor>
            <div>Имя:<a href="/executor/${executor.id}">${executor.firstName}</a></div>
            <div>Фамилия: ${executor.lastName}</div>
            <div>Описание: ${executor.describe}</div>
        <#--            <form action="/order/subscribe" method="post">-->
        <#--                <input type="hidden" name="orderId" value="${order.id}">-->
        <#--                <input type="hidden" name="userId" value="${currentUserId}">-->
        <#--                <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <#--                <#if user??>-->
        <#--                    <#if order.author.id != currentUserId>-->
        <#--                        <button type="submit">Откликнуться</button>-->
        <#--                    <#else>-->
        <#--                        <a href="/order/update/${order.id}">Изменить</a>-->
        <#--                    </#if>-->
        <#--                </#if>-->
        <#--            </form>-->
            <hr/>
        </#list>
    <#elseif executors??>
        <h1>Executors</h1>
        <#list executors as executor>
        <#--            <div>Категория: ${category.name}</div>-->
            <div>Имя:<a href="/executor/${executor.id}">${executor.firstName}</a></div>
            <div>Фамилия: ${executor.lastName}</div>
            <div>Описание: ${executor.describe}</div>
        <#--            <form action="/order/subscribe" method="post">-->
        <#--                <input type="hidden" name="orderId" value="${order.id}">-->
        <#--                <input type="hidden" name="userId" value="${currentUserId}">-->
        <#--                <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <#--                <#if user??>-->
        <#--                    <#if order.author.id != currentUserId>-->
        <#--                        <button type="submit">Откликнуться</button>-->
        <#--                    <#else>-->
        <#--                        <a href="/order/update/${order.id}">Изменить</a>-->
        <#--                    </#if>-->
        <#--                </#if>-->
        <#--            </form>-->
            <hr/>
        </#list>
    </#if>
</@c.page>