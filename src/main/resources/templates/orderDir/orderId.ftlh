<#import "../part/common.ftlh" as c>
<#import "../part/category.ftlh" as cat>
<#include "../part/security.ftlh">


<@c.page>

    <h1>Orders</h1>
    <div><b>Название:</b> ${order.name}</div>
    <div><b>Категория:</b> ${order.category.name}</div>
    <div><b>Описание:</b> ${order.describe}</div>
    <div><b>Дата создания:</b> ${order.date}</div>
    <div><b>Email владельца:</b> ${order.emailOwner}</div>
    <div><b>Имя владельца:</b> ${order.nameOwner}</div>
    <div><b>Телефон владелца:</b> ${order.phoneOwner}</div>
    <div><b>Цена:</b> ${order.price}</div>

    <#if user??>
    <#--        Если это задание не пользователя-->
        <#if order.author.id != currentUserId>
            <#if order.status.name()=="PROCESSING">
                <#if order.executor.id == executor.id >
                    <p>Статус: <b>Вы выполняете это задание</b></p>
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
                            <button class="btn btn-primary ms-3" type="submit">Откликнуться</button>
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
                        <button class="btn btn-primary ms-3" type="submit">Принять</button>
                    </p>
                </form>
                <form action="/order/offerAnswer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                    <input type="hidden" name="getOffer" value="false">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <p>status:
                        <button class="btn btn-primary ms-3" type="submit">Отклонить</button>
                    </p>
                </form>
            </#if>


        <#else>
            <#if order.status.name()=="ACTIVE">
                <p>
                <form action="/order/delete" method="post">
                    <a class="btn btn-primary" href="/order/update/${order.id}">Изменить</a>
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary ms-3" type="submit">Удалить</button>
                </form>
                </p>
                <div>status: <b>Хотят выполнить задание:</b>

                    <#list order.subscribers as subscribe>
                        <a href="/executor/${subscribe.id}">${subscribe.name}</a>
                        <form action="/order/confirm" method="post">
                            <input type="hidden" name="orderId" value="${order.id}"/>
                            <input type="hidden" name="subscribeId" value="${subscribe.id}"/>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary ms-3" type="submit">Подтвердить</button>
                        </form>
                    </#list>
                </div>

            <#elseif order.status.name()=="PROCESSING">
                <p>status: <b>выполняет задание:</b>
                    <a href="/executor/${order.executor.id}">${order.executor.name}</a>
                <form action="/order/done" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <input type="hidden" name="executorId" value="${order.executor.id}"/>
                    <input type="hidden" name="doneSuccess" value="true"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary ms-3" type="submit">Выполнено</button>
                </form>
                <form action="/order/done" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <input type="hidden" name="executorId" value="${order.executor.id}"/>
                    <input type="hidden" name="doneSuccess" value="false"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary ms-3" type="submit">Провалено</button>
                </form>
                </p>
            <#elseif order.status.name()=="OFFER">
                <p>status: <b>вы предложили задание</b>
                    <a href="/executor/${order.offerExecutor.id}">${order.offerExecutor.name}</a>
                </p>
                <form action="/order/cancel" method="post">
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary ms-3" type="submit">Отменить</button>
                </form>
            <#elseif order.status.name()=="DONE">
                <p>status: <b>выполнил задание:</b>
                    <a href="/executor/${order.executor.id}">${order.executor.name}</a>
                </p>
            <#elseif order.status.name()=="LOSE">
                <p>status: <b>провалил задание:</b>
                    <a href="/executor/${order.executor.id}">${order.executor.name}</a>
                </p>
            </#if>
        </#if>
    </#if>


</@c.page>