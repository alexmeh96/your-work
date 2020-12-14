<#import "part/common.ftlh" as c>
<#import "part/navbar.ftlh" as n>
<#import "part/executor.ftlh" as e>

<@c.page>
    <@n.navbar />
    <@n.mes />
    <h1>Сделать себя исполнителем</h1>
    <#if isCreate>
        <@e.executorForm "/executor/create" true/>
    <#else >
        <@e.executorForm "/executor/update" false/>
    </#if>

</@c.page>