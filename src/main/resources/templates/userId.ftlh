<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>
<#import "part/navbar.ftlh" as n>

<@c.page>
    <@n.navbar />
    <p>Пользователь: ${user.email}</p>
    <p>Роли: </p>

    <p>Удалить пользователя:
    <form action="/admin/user/delete" method="post">
        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <input type="submit" value="Удалить">
    </form>
    </p>

</@c.page>