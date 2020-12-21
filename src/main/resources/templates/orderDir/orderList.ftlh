<#import "../part/common.ftlh" as c>
<#import "../part/showOrders.ftlh" as s>
<#import "../part/category.ftlh" as cat>

<@c.page>
    <div class="row">
        <div class="col-6">
            <h3 class="text-center">Категории</h3>
            <#if categories??>
                <@cat.showCategory "/order/all" />
            <#else >
                <p>Нет категорий!</p>
            </#if>
        </div>
        <div class="col-6">
            <h3 class="text-center">Задания</h3>
            <#if orders??>
                <@s.showOrder "" />
            <#else >
                <p>Заданий нет!</p>
            </#if>

        </div>
    </div>
</@c.page>