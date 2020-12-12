<#include "security.ftlh">
<#macro navbar>
    <div>
        <a href="/order/create">Create order</a>
        <a href="/category/create">Create category</a>
        <a href="/category/all">Categories</a>
        <a href="/order/all">Orders</a>
        <a href="/order/user">My orders</a>
        <a href="/profile">My profile</a>
        <p>${name}</p>
    </div>

</#macro>