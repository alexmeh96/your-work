<#import "category.ftlh" as cat>

<#if isCategory??>
    <@cat.showCategory "/executor/category" />
<#elseif category??>
    <h1>Исполнители</h1>
    <div>Категория: ${category.name}</div>
    <#list category.executors as executor>
        <div>Имя:<a href="/executor/${executor.id}">${executor.firstName}</a></div>
        <div>Фамилия: ${executor.lastName}</div>
        <div>Описание: ${executor.describe}</div>
        <hr/>
    </#list>
<#elseif executors??>
    <h1>Исполнители</h1>
    <#list executors as executor>
    <#--            <div>Категория: ${category.name}</div>-->
        <div class="card mt-3 " style="width: 30rem;">
            <div class="card-header">
                <a href="/executor/${executor.id}">${executor.firstName}</a>
            </div>
        <div class="card-body">
            <div>Фамилия: ${executor.lastName}</div>
            <div>Описание: ${executor.describe}</div>
        </div>
        <div class="card-footer">
            <a href="/order/offerList/${executor.id}" class="btn btn-primary">Предложить задание</a>
        </div>
        </div>
    </#list>
</#if>