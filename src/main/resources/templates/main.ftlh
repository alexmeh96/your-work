<#import "part/common.ftlh" as c>
<#import "part/showOrders.ftlh" as s>

<@c.page>
    <h1 class="text-center">YourWork!</h1>
    <div class="row">
        <div class="col-6">
            <h3 class="text-center">Новые задания</h3>
            <#if orders??>
                <@s.showOrder ""/>
                <#else >
                <p>Нет заданий!</p>
            </#if>
        </div>
        <div class="col-6">
            <h3 class="text-center">Топ исполнителей</h3>
            <#if executors??>
                <#include "part/showExecutors.ftlh">
            <#else >
                <p>Нет исполнителей!</p>
            </#if>

        </div>
    </div>

</@c.page>