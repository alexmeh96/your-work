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