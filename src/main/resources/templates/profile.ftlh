<#import "part/common.ftlh" as c>

<@c.page>
    <h1>Мой профиль</h1>

    <#if !executorExist??>
        <p><a href="/executor/create">Создать исполнителя</a></p>
    </#if>
<#--    <p><a href="#">Настройки</a></p>-->


    <div class="row">
    <#if executor??>
        <div class="card me-3" style="width: 50rem;">
            <div class="card-body">
                <h5 class="card-title mb-3">Анкета исполнителя</h5>
                <img src="/image/${executor.avatarId}" class="rounded float-start w-50  me-3" alt="...">
                <div class="mb-3">Имя: ${executor.name?if_exists}</div>
                <div class="mb-3">Описание: ${executor.describe?if_exists}</div>
                <div class="mb-3">Email: ${executor.email?if_exists}</div>
                <div class="mb-3">Номер телефона: ${executor.phone?if_exists}</div>
                <a href="/executor/update" class="btn btn-primary">Изменить</a>
            </div>
        </div>
    </#if>

    <#if profile??>
        <div class="card float-end" style="width: 30rem;">
            <div class="card-body">
                <p><b>Задания:</b></p>
                <div class="mb-3">Успешных: ${profile.amountExecutedOrdersSuccess}</div>
                <div class="mb-3">Проваленных: ${profile.amountExecutedOrdersWrong}</div>
                <div class="mb-3">Действующих: ${profile.amountExecutionOrders}</div>
                <div>Предложенных: ${profile.amountMakeOrders}</div>
            </div>
        </div>
    </#if>
    </div>

</@c.page>