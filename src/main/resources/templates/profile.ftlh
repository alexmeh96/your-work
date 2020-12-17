<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Профиль пользователя</h1>

    <#if executorExist??>
        <p><a href="/executor/update">Редактировать своего исполнителя</a></p>
    <#else>
        <p><a href="/executor/create">Создать исполнителя</a></p>
    </#if>
    <p><a href="#">Настройки</a></p>

    <#if profile??>
        <div>Имя: ${profile.name}</div>
        <p><b>Задания:</b></p>
        <div>Успешных: ${profile.amountExecutedOrdersSuccess}</div>
        <div>Проваленных: ${profile.amountExecutedOrdersWrong}</div>
        <div>Действующих: ${profile.amountExecutionOrders}</div>
        <div>Предложенных: ${profile.amountMakeOrders}</div>
    </#if>
</@c.page>