<#import "category.ftlh" as cat>
<#include "security.ftlh">

<#macro showOrder value>
    <#if isCategory??>
        <@cat.showCategory "/order/category" />
    <#else>
        <#list orders as order>
            <div class="card mt-3" style="width: 30rem;">
                <div class="card-header"><a href="/order/${order.id}">${order.name}</a></div>
                <div class="card-body">
                    <div>Категория: ${order.category.name}</div>
                    <div>Описание: ${order.describe}</div>
                </div>
                <div class="card-footer text-muted">
                    <#if isOffer??>
                        <form action="/order/offer" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="hidden" name="executorId" value="${executor.id}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary" type="submit">Предложить</button>
                        </form>
                    <#else >
                        <#if user??>
                            <#if order.author.id != currentUserId>
                                <#if order.status.name()=="PROCESSING">
                                    <#if order.executor.id == executor.id >
                                        <b>Вы выполняете это задание</b>
                                    </#if>
                                <#elseif order.status.name()=="ACTIVE" >
                                    <#if value == "offer" >
                                        <b>Вы отозвались на это задание</b>
                                    <#else >
                                        <form action="/order/subscribe" method="post">
                                            <input type="hidden" name="orderId" value="${order.id}">
                                            <input type="hidden" name="userId" value="${currentUserId}">
                                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            <button class="btn btn-primary" type="submit">Откликнуться</button>
                                        </form>
                                    </#if>
                                <#elseif order.status.name()=="DONE">
                                    <b>Вы выполнили это задание</b>
                                <#elseif order.status.name()=="LOSE">
                                    <b>Вы провалили это задание</b>
                                <#elseif order.status.name()=="OFFER">
                                    <p>
                                    <form action="/order/offerAnswer" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}">
                                        <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                                        <input type="hidden" name="getOffer" value="true">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button class="btn btn-primary mb-3" type="submit">Принять</button>
                                    </form>

                                    <form action="/order/offerAnswer" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}">
                                        <input type="hidden" name="executorId" value="${order.offerExecutor.id}">
                                        <input type="hidden" name="getOffer" value="false">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button class="btn btn-primary" type="submit">Отклонить</button>
                                    </form>
                                    </p>
                                </#if>
                            <#else>
                                <#if order.status.name()=="ACTIVE">
                                    <form action="/order/delete" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}"/>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <p>
                                            <a href="/order/update/${order.id}">Изменить</a>
                                            <button class="btn btn-primary ms-3" type="submit">Удалить</button>
                                        </p>
                                    </form>

                                <#elseif order.status.name()=="PROCESSING">
                                    <p><b>выполняет задание:</b>
                                        <a href="/executor/${order.executor.id}">${order.executor.name}</a>
                                    <form action="/order/done" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}"/>
                                        <input type="hidden" name="executorId" value="${order.executor.id}"/>
                                        <input type="hidden" name="doneSuccess" value="true"/>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button class="btn btn-primary mb-3" type="submit">Выполнено</button>
                                    </form>
                                    <form action="/order/done" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}"/>
                                        <input type="hidden" name="executorId" value="${order.executor.id}"/>
                                        <input type="hidden" name="doneSuccess" value="false"/>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button class="btn btn-primary" type="submit">Провалено</button>
                                    </form>
                                    </p>
                                <#elseif order.status.name()=="OFFER">
                                    <p><b>вы предложили задание</b>
                                        <a href="/executor/${order.offerExecutor.id}">${order.offerExecutor.name}</a>
                                    </p>
                                    <form action="/order/cancel" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}"/>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button class="btn btn-primary" type="submit">Отменить</button>
                                    </form>
                                <#elseif order.status.name()=="DONE">
                                    <p><b>выполнил задание:</b>
                                        <a href="/executor/${order.executor.id}">${order.executor.name}</a>
                                    </p>
                                <#elseif order.status.name()=="LOSE">
                                    <p><b>провалил задание:</b>
                                        <a href="/executor/${order.executor.id}">${order.executor.name}</a>
                                    </p>
                                </#if>
                            </#if>
                        </#if>
                    </#if>
                </div>
            </div>
        </#list>
    </#if>
</#macro>