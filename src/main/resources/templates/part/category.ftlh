<#macro category path isCreate>

    <#if categoryError??>
        <div>
            ${categoryError}
        </div>
    </#if>
    <form action="${path}" method="post">
        <div>
            <label>Name <input type="text" name="name" /></label>
            <#if nameError??>
                <div>
                    ${nameError}
                </div>
            </#if>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>


        <div>
            <button type="submit"><#if isCreate>Create<#else>Edit</#if></button>
        </div>

    </form>

</#macro>

<#macro showCategory path>
    <p><a href="${path}/active">все задания</a></p>
    <#list categories as category>
        <p><a href="${path}/${category.id}">${category.name}</a></p>
    </#list>
</#macro>
