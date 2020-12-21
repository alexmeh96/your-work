<#macro category path isCreate>

    <#if categoryError??>
        <div>
            ${categoryError}
        </div>
    </#if>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label for="exampleInputName1" class="form-label">Название</label>

            <input type="text" name="name" id="exampleInputName1" class="form-control"/>
            <#if nameError??>
                <div>
                    ${nameError}
                </div>
            </#if>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <button type="submit" class="btn btn-primary"><#if isCreate>Create<#else>Edit</#if></button>

    </form>

</#macro>

<#macro showCategory path>
    <p><a href="${path}">все категории</a></p>
    <#list categories as category>
        <p><a href="${path}/${category.id}">${category.name}</a></p>
    </#list>
</#macro>
