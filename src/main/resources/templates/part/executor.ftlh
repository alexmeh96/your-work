<#macro executorForm path isCreate>

    <#if executorError??>
        <div>
            ${executorError}
        </div>
    </#if>
    <form action="${path}" method="post">
        <div>
            <label>Имя <input type="text" name="firstName" <#if executor??>value="${executor.firstName}"</#if> /></label>
            <#if firstNameError??>
                <div>
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div>
            <label>Фамилия <input type="text" name="lastName" <#if executor??>value="${executor.lastName}"</#if> /></label>
            <#if lastNameError??>
                <div>
                    ${lastNameError}
                </div>
            </#if>
        </div>
        <div>
            <label>Описание: <input type="text" name="describe" <#if executor??>value="${executor.describe}"</#if>/></label>
            <#if describeError??>
                <div>
                    ${describeError}
                </div>
            </#if>
        </div>
        <#if categories??>
            <div>
                <label for="category">Категории:</label>
                    <#if executor?? >
                        <#list categories as category>
                            <p><input type="checkbox" <#if executor_categories?seq_contains(category.name)>checked</#if> name="categoryName" value="${category.name}">${category.name}</p>
                        </#list>
                    <#else >
                        <#list categories as category>
                            <p><input type="checkbox"  name="categoryName" value="${category.name}">${category.name}</p>
                        </#list>
                    </#if>
                <#if categoryNameError??>
                    <div>
                        ${categoryNameError}
                    </div>
                </#if>
            </div>
        </#if>
        <#if executor??>
            <input type="hidden" name="executorId" value="${executor.id}"/>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div>
            <button type="submit"><#if isCreate>Создать<#else>Обновить</#if></button>
        </div>

    </form>

</#macro>