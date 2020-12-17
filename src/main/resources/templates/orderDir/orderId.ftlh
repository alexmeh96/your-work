<#import "../part/common.ftlh" as c>
<#import "../part/navbar.ftlh" as n>
<#import "../part/category.ftlh" as cat>
<#include "../part/security.ftlh">

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Orders</h1>
    <div>Название: ${order.name}</div>
    <div>Категория: ${order.category.name}</div>
    <div>Описание: ${order.describe}</div>

    <#if user??>
    <#--        Если это задание не пользователя-->
        <#if order.author.id != currentUserId>
            <#if order.status.name()=="PROCESSING">
                <#if order.executor.id == executor.id >
                    <p>status: <b>Вы выполняете это задание</b></p>
                </#if>
            <#elseif order.status.name()=="ACTIVE" >
                <#if isSubscribe >
                    <p>status: <b>Вы отозвались на это задание</b></p>
                <#else >
                    <form action="/order/subscribe" method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <input type="hidden" name="userId" value="${currentUserId}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <p>status:
                            <button type="submit">Откликнуться</button>
                        </p>
                    </form>
                </#if>
            <#elseif order.status.name()=="DONE">
                <p>status: <b>Вы выполнили это задание</b></p>
            <#elseif order.status.name()=="LOSE">
                <p>status: <b>Вы провалили это задание</b></p>
            <#elseif order.status.name()=="OFFER">
                <form action="/order/offerAnswer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                    <input type="hidden" name="getOffer" value="true">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <p>status:
                        <button type="submit">Принять</button>
                    </p>
                </form>
                <form action="/order/offerAnswer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                    <input type="hidden" name="getOffer" value="false">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <p>status:
                        <button type="submit">Отклонить</button>
                    </p>
                </form>
            </#if>


        <#else>
            <a href="/order/update/${order.id}">Изменить</a>
            <#if order.status.name()=="ACTIVE">
                <div>status: <b>Хотят выполнить задание:</b>

                    <#list order.subscribers as subscribe>
                        <a href="/executor/${subscribe.id}">${subscribe.firstName}</a>
                        <form action="/order/confirm" method="post">
                            <input type="hidden" name="orderId" value="${order.id}"/>
                            <input type="hidden" name="subscribeId" value="${subscribe.id}"/>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit">Подтвердить</button>
                        </form>
                    </#list>
                </div>

            <#elseif order.status.name()=="PROCESSING">
                <p>status: <b>выполняет задание:</b>
                    <a href="/executor/${order.executor.id}">${order.executor.firstName}</a>
                <form action="/order/done" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                <input type="hidden" name="executorId" value="${order.executor.id}"/>
                    <input type="hidden" name="doneSuccess" value="true"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Выполнено</button>
                </form>
                <form action="/order/done" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <input type="hidden" name="executorId" value="${order.executor.id}"/>
                    <input type="hidden" name="doneSuccess" value="false"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit">Провалено</button>
                </form>
                </p>
            <#elseif order.status.name()=="DONE">
                <p>status: <b>выполнил задание:</b>
                    <a href="/executor/${order.executor.id}">${order.executor.firstName}</a>
                </p>
            <#elseif order.status.name()=="LOSE">
                <p>status: <b>провалил задание:</b>
                    <a href="/executor/${order.executor.id}">${order.executor.firstName}</a>
                </p>
            </#if>
        </#if>
    </#if>


</@c.page>