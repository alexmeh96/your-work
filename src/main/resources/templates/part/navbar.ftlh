<#import "login.ftlh" as l>
<#include "security.ftlh">

<#macro navbar>
    <div>
        <a href="/">Главная</a>
        <a href="/order/category">Задания</a>
        <a href="/executor/category">Исполнители</a>
        <#if user??>
            <a href="/order/user">Мои задания</a>
            <a href="/profile">Профиль</a>
        </#if>
        <#if isAdmin>
            <a href="/admin">Admin</a>
        </#if>

        <#if user??>
            <@l.logout />
        <#else>
            <a href="/login">Вход</a>
        </#if>

        <p>${name}</p>
    </div>

</#macro>
<#macro mes>
    <#if message??>
        <div>
            <b>${message}</b>
        </div>
    </#if>

</#macro>