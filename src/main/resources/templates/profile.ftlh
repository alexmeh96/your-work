<#import "part/common.ftlh" as c>

<@c.page>
    <h1>Мой профиль</h1>

    <#if executorExist??>
        <p><a href="/executor/update">Редактировать исполнителя</a></p>
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