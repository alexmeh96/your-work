<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Profile page</h1>

    <#if executorExist??>
        <p><a href="/executor/update">Редактировать свой профиль исполнителя</a></p>
    <#else>
        <p><a href="/executor/create">Сделать себя исполнителем</a></p>
    </#if>

    <#if profile??>
        <div>First name ${profile.firstName}</div>
        <div>First name ${profile.lastName}</div>
    </#if>
</@c.page>