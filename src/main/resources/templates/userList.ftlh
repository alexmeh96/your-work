<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>

<@c.page>
    <h1>Список пользователей</h1>

    <#list users as user>
        <p>email: <a href="/admin/user/${user.id}">${user.email}</a></p>
    </#list>

</@c.page>