<#import "../part/common.ftlh" as c>
<#import "../part/category.ftlh" as cat>

<@c.page>
    <div class="row">
        <div class="col-6">
            <h3 class="text-center">Категории</h3>
            <#if categories??>
                <@cat.showCategory "/executor/all" />
            <#else >
                <p>Нет категорий!</p>
            </#if>
        </div>
        <div class="col-6">
            <h3 class="text-center">Исполнители</h3>
            <#if executors??>
                <#include "../part/showExecutors.ftlh">
            <#else >
                <p>Нет исполнителей!</p>
            </#if>

        </div>
    </div>

</@c.page>