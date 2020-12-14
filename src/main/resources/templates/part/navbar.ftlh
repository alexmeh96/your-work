<#import "login.ftlh" as l>
<#include "security.ftlh">

<#macro navbar>
    <div>
        <a href="/">Main</a>
        <a href="/order/category">Задания</a>
        <a href="/executor/category">Исполнители</a>
        <#if user??>
            <a href="/order/create">Создать задание</a>
            <a href="/order/user">Мои задания</a>
            <a href="/profile">Мой профиль</a>
        </#if>
        <#if isAdmin>
            <a href="/category/create">Создать категорию</a>
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