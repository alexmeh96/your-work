<#import "../part/common.ftlh" as c>
<#import "../part/navbar.ftlh" as n>
<#import "../part/executor.ftlh" as e>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <#if isCreate>
        <h1>Сделать профиль исполнителя</h1>
        <@e.executorForm "/executor/create" true/>
    <#else >
        <h1>Изменить профиль исполнителя</h1>
        <@e.executorForm "/executor/update" false/>
    </#if>

</@c.page>