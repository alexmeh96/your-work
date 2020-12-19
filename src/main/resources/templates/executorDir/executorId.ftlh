<#import "../part/common.ftlh" as c>
<#import "../part/category.ftlh" as cat>
<#include "../part/security.ftlh">
<@c.page>

    <div>Имя: ${executor.firstName}</div>
    <div>Фамилия: ${executor.lastName}</div>
    <div>Описание: ${executor.describe}</div>

    <#if executor.auth.id == currentUserId>
        <h3>Мои предложения</h3>
        <#list executor.offers as offer>
            <div>
                <a href="/order/${offer.id}">${offer.name}</a>
            </div>

        </#list>
    <#else >
        <a class="btn btn-primary" href="/order/offerList/${executor.id}">Предложить задание</a>
    </#if>

</@c.page>