<#import "category.ftlh" as cat>
<#include "security.ftlh">

<#--<#if isCategory??>-->
<#--    <@cat.showCategory "/executor/category" />-->
<#--<#if category??>-->
<#--    <div>Категория: ${category.name}</div>-->
<#--    <#list category.executors as executor>-->
<#--        <div>Имя:<a href="/executor/${executor.id}">${executor.name}</a></div>-->
<#--        <div>Описание: ${executor.describe}</div>-->
<#--        <hr/>-->
<#--    </#list>-->

<#list executors as executor>
    <div class="card mt-3 " style="width: 30rem;">
        <div class="row">
            <div class="col-6">
                <img src="/image/${executor.avatarId}" class="card-img-top w-100" alt="...">
            </div>
            <div class="col-6">
                <div class="card-body ">
                    <#if executor.auth.id == currentUserId>
                    <a href="/profile">${executor.name}</a>
                    <#else>
                        <a href="/executor/${executor.id}">${executor.name}</a>
                    </#if>
                    <div>Описание: ${executor.describe}</div>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <#if executor.auth.id == currentUserId>
                <a href="/executor/update" >Изменить</a>
            <#else>
                <a href="/order/offerList/${executor.id}" class="btn btn-primary">Предложить задание</a>
            </#if>

        </div>
    </div>
</#list>
