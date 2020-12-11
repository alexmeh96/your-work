<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>
<#include "part/security.ftlh">

<@c.page>
    <p>Main page</p>
    <p>Email ${name} </p>

    <p><a href="/order/create">Create order</a></p>
    <p><a href="/category/create">Create category</a></p>
    <p><a href="/category/all">Categories</a></p>
    <p><a href="/order/all">Orders</a></p>
    <p><a href="/order/user">My orders</a></p>

    <@l.logout />

</@c.page>