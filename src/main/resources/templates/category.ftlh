<#import "part/common.ftlh" as c>
<#import "part/category.ftlh" as cat>


<@c.page>
    <h1>Добавить категорию</h1>
    <@cat.category "/category/create" true/>
</@c.page>