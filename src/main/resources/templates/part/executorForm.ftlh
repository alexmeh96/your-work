<#macro executorForm path isCreate>

    <#if executorError??>
        <div>
            ${executorError}
        </div>
    </#if>

    <form class="needs-validation" action="${path}" method="post" novalidate enctype="multipart/form-data">

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
            <input type="text" class="form-control" name="name" id="validationCustom01"
                   <#if executor??>value="${executor.name}"</#if> required />
            <div class="invalid-feedback">
                поле не должно быть пустым.
            </div>
            <#if nameError??>
                <div>
                    ${nameError}
                </div>
            </#if>
        </div>

        <div class="mb-3">
            <label for="formFile" class="form-label">Аватарка</label>
            <input class="form-control" type="file" id="formFile" name="avatar">
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

        <div class="mb-3">
            <label for="validationCustom04" class="form-label">Email</label>
            <input type="email" class="form-control" name="email" id="validationCustom04"
                   <#if executor??>value="${executor.email}"</#if> required />
            <div class="invalid-feedback">
                поле не должно быть пустым.
            </div>
            <#if nameError??>
                <div>
                    ${nameError}
                </div>
            </#if>
        </div>

        <div class="mb-3">
            <label for="validationCustom05" class="form-label">Номер телефона</label>
            <input type="text" class="form-control" name="phone" id="validationCustom05"
                   <#if executor??>value="${executor.phone}"</#if> required />
            <div class="invalid-feedback">
                поле не должно быть пустым.
            </div>
            <#if nameError??>
                <div>
                    ${nameError}
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