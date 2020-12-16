<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>
<#import "part/category.ftlh" as cat>
<#include "part/security.ftlh">

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Orders</h1>
    <div>Название: ${order.name}</div>
    <div>Категория: ${order.category.name}</div>
    <div>Описание: ${order.describe}</div>

    <#if user??>
        <#if order.author.id != currentUserId>
            <#if order.status.name()=="PROCESSING">
                <#if order.executor.id == executor.id >
                    <b>Вы выполняете это задание</b>
                </#if>
            <#elseif order.status.name()=="ACTIVE" >
                <#if isSubscribe >
                    <b>Вы отозвались на это задание</b>
                <#else >
                    <form action="/order/subscribe" method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="userId" value="${currentUserId}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit">Откликнуться</button>
                    </form>
                </#if>
            <#elseif order.status.name()=="DONE">
                <b>Вы выполнили это задание</b>
            <#elseif order.status.name()=="OFFER">
                <form action="/order/offerAnswer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                    <input type="hidden" name="getOffer" value="true">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Принять</button>
                </form>
                <form action="/order/offerAnswer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                    <input type="hidden" name="getOffer" value="false">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Отклонить</button>
                </form>
            </#if>


        <#else>
            <a href="/order/update/${order.id}">Изменить</a>
            <#if order.status.name()=="ACTIVE">
                <h3>Хотят выполнить работу:</h3>

                <#list order.subscribers as subscribe>
                    <a href="/executor/${subscribe.id}">${subscribe.firstName}</a>
                    <form action="/order/confirm" method="post">
                        <input type="hidden" name="orderId" value="${order.id}"/>
                        <input type="hidden" name="subscribeId" value="${subscribe.id}"/>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit">Подтвердить</button>
                    </form>
                </#list>

            <#elseif order.status.name()=="PROCESSING">
                <h3>выполняет работу:</h3>
                <a href="/executor/${order.executor.id}">${order.executor.firstName}</a>
                <form action="/order/done" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Выполнено</button>
                </form>
            <#elseif order.status.name()=="DONE">
                <h3>выполнил работу:</h3>
                <a href="/executor/${order.executor.id}">${order.executor.firstName}</a>
            </#if>
        </#if>
    </#if>


</@c.page>