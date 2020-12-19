<#import "../part/common.ftlh" as c>
<#import "../part/executorForm.ftlh" as e>

<@c.page>
    <#if isCreate>
        <h1>Создать профиль исполнителя</h1>
        <@e.executorForm "/executor/create" true/>
    <#else >
        <h1>Изменить профиль исполнителя</h1>
        <@e.executorForm "/executor/update" false/>
    </#if>

</@c.page>