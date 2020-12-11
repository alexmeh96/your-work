<#macro order path isCreate>

    <#if orderError??>
        <div>
            ${orderError}
        </div>
    </#if>
    <form action="${path}" method="post">
        <div>
            <label>Describe <input type="text" name="describe" /></label>
            <#if describeError??>
                <div>
                    ${describeError}
                </div>
            </#if>
        </div>
        <div>
            <label>Category <input type="text" name="categoryName" /></label>
            <#if categoryNameError??>
                <div>
                    ${categoryNameError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>


        <div>
            <button type="submit"><#if isCreate>Create<#else>Edit</#if></button>
        </div>

    </form>

</#macro>