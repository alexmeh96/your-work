<#macro executorForm path isCreate>

    <#if executorError??>
        <div>
            ${executorError}
        </div>
    </#if>

    <form class="needs-validation" action="${path}" method="post" novalidate>

        <div class="form-check mb-3">
            <#if executor??>
                <input class="form-check-input" type="checkbox" id="activeCheck" name="active" value="true"
                       <#if executor.active>checked</#if> />
            <#else >
                <input class="form-check-input" type="checkbox" id="activeCheck" name="active" value="true"/>

            </#if>
            <label class="form-check-label" for="activeCheck">
                Активный исполнитель
            </label>
        </div>

        <div class="mb-3">
            <label for="validationCustom01" class="form-label">Имя</label>
            <input type="text" class="form-control" name="firstName" id="validationCustom01"
                   <#if executor??>value="${executor.firstName}"</#if> required />
            <div class="invalid-feedback">
                поле не должно быть пустым.
            </div>
            <#if firstNameError??>
                <div>
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div class="mb-3">
            <label for="validationCustom02" class="form-label">Фамилия</label>
            <input type="text" class="form-control" name="lastName" id="validationCustom02"
                   <#if executor??>value="${executor.lastName}"</#if> required />
            <div class="invalid-feedback">
                поле не должно быть пустым.
            </div>
            <#if lastNameError??>
                <div>
                    ${lastNameError}
                </div>
            </#if>
        </div>
        <div class="mb-3">
            <label for="validationCustom03" class="form-label">Описание</label>
            <input type="text" class="form-control" name="describe" id="validationCustom03"
                   <#if executor??>value="${executor.describe}"</#if> required />
            <div class="invalid-feedback">
                поле не должно быть пустым.
            </div>
            <#if describeError??>
                <div>
                    ${describeError}
                </div>
            </#if>
        </div>

        <#if categories??>
            <p>Категории:</p>
            <div class="form-check mb-3">
                <#if executor?? >
                    <#list categories as category>
                        <div>
                            <label class="form-check-label">${category.name}</label>
                            <input class="form-check-input" type="checkbox"
                                   <#if executor_categories?seq_contains(category.name)>checked</#if>
                                   name="categoryName" value="${category.name}"/>
                        </div>
                    </#list>
                <#else >
                    <#list categories as category>
                        <div>
                            <label class="form-check-label">${category.name}</label>
                            <input class="form-check-input" type="checkbox" name="categoryName"
                                   value="${category.name}"/>
                        </div>
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
            <button class="btn btn-primary" type="submit"><#if isCreate>Создать<#else>Обновить</#if></button>
        </div>

    </form>

</#macro>