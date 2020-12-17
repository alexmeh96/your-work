<#import "category.ftlh" as cat>
<#include "security.ftlh">

<#macro showOrder value>
    <#if isCategory??>
        <h1>Order category</h1>
        <@cat.showCategory "/order/category" />
    <#else>
        <#list orders as order>
            <div>Название: <a href="/order/${order.id}">${order.name}</a></div>
            <div>Категория: ${order.category.name}</div>
            <div>Описание: ${order.describe}</div>
            <#if isOffer??>
                <form action="/order/offer" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <input type="hidden" name="executorId" value="${executor.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <p>status:
                        <button type="submit">Предложить</button>
                    </p>
                </form>
            <#else >
                <#if user??>
                    <#if order.author.id != currentUserId>
                        <#if order.status.name()=="PROCESSING">
                            <#if order.executor.id == executor.id >
                                <p>status: <b>Вы выполняете это задание</b></p>
                            </#if>
                        <#elseif order.status.name()=="ACTIVE" >
                            <#if value == "offer" >
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
                        <#if order.status.name()=="ACTIVE">
                            <p>
                                <a href="/order/update/${order.id}">Изменить</a>
                            <form action="/order/delete" method="post">
                                <input type="hidden" name="orderId" value="${order.id}"/>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit">Удалить</button>
                            </form>
                            </p>
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
                        <#elseif order.status.name()=="OFFER">
                            <p>status: <b>вы предложили задание</b> ${order.offerExecutor.firstName}</p>
                            <form action="/order/cancel" method="post">
                                <input type="hidden" name="orderId" value="${order.id}"/>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit">Отменить</button>
                            </form>
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
            </#if>
            <hr/>
        </#list>
    </#if>
</#macro>