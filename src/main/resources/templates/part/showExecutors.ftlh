<#import "category.ftlh" as cat>
<#if isCategory??>
    <h1>Категории исполнителей</h1>
    <@cat.showCategory "/executor/category" />
<#elseif category??>
    <h1>Executors</h1>
    <div>Категория: ${category.name}</div>
    <#list category.executors as executor>
        <div>Имя:<a href="/executor/${executor.id}">${executor.firstName}</a></div>
        <div>Фамилия: ${executor.lastName}</div>
        <div>Описание: ${executor.describe}</div>
        <hr/>
    </#list>
<#elseif executors??>
    <h1>Executors</h1>
    <#list executors as executor>
    <#--            <div>Категория: ${category.name}</div>-->
        <div>Имя:<a href="/executor/${executor.id}">${executor.firstName}</a></div>
        <div>Фамилия: ${executor.lastName}</div>
        <div>Описание: ${executor.describe}</div>
        <a href="/order/offerList/${executor.id}">Предложить задание</a>
        <hr/>
    </#list>
</#if>