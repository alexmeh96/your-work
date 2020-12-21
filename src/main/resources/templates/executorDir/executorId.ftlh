<#import "../part/common.ftlh" as c>
<#import "../part/category.ftlh" as cat>
<#include "../part/security.ftlh">
<@c.page>

    <div><b>Имя:</b> ${executor.name}</div>
    <div><b>Описание:</b> ${executor.describe}</div>
    <div><b>Номер телефона:</b> ${executor.phone}</div>
    <div><b>Email:</b> ${executor.email}</div>
    <div><b>Успешных:</b> ${profile.amountExecutedOrdersSuccess}</div>
    <div><b>Проваленных:</b> ${profile.amountExecutedOrdersWrong}</div>
    <div><b>Действующих:</b> ${profile.amountExecutionOrders}</div>

    <#if executor.auth.id == currentUserId>
        <h3>Мои предложения</h3>
        <#list executor.offers as offer>
            <div>
                <a href="/order/${offer.id}">${offer.name}</a>
            </div>

        </#list>
    <#else >
        <a class="btn btn-primary" href="/order/offerList/${executor.id}">Предложить задание</a>
    </#if>

</@c.page>