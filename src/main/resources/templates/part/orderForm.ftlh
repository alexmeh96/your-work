<#macro orderForm path isCreate>

    <#if orderError??>
        <div>
            ${orderError}
        </div>
    </#if>
    <form action="${path}" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label for="validationCustom1" class="form-label">Название</label>
            <input type="text" class="form-control" id="validationCustom1"  name="name" <#if order??>value="${order.name}"</#if>
                                   required />
            <div class="valid-feedback">
                Выглядит хорошо!
            </div>
            <div class="invalid-feedback">
                Поле не должно быть пустым
            </div>
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <div class="mb-3">
            <label for="validationCustom2" class="form-label">Название</label>

            <input class="form-control" id="validationCustom1" type="text" name="describe"
                                   <#if order??>value="${order.describe}" </#if>required/>
            <div class="valid-feedback">
                Выглядит хорошо!
            </div>
            <div class="invalid-feedback">
                Поле не должно быть пустым
            </div>
            <#if describeError??>
                <div class="invalid-feedback">
                    ${describeError}
                </div>
            </#if>
        </div>

        <div class="mb-3">
            <label for="category">Категория</label>
            <select class="form-select"  id="category" name="categoryName" required>
                <#if order??>
                    <#list categories as category>
                        <option value="${category.name}"
                                <#if category.name == order.category.name >selected</#if>>${category.name}</option>
                    </#list>
                <#else>
                    <option disabled selected  value="">Выберите категорию</option>

                    <#list categories as category>
                        <option value="${category.name}">${category.name}</option>
                    </#list>
                </#if>
            </select>

            <div class="invalid-feedback">
                Please select a valid state.
            </div>
        </div>

        <#if categoryNameError??>
            <div class="invalid-feedback">
                ${categoryNameError}
            </div>
        </#if>

        <#if order??>
            <input type="hidden" name="orderId" value="${order.id}"/>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div>
            <button class="btn btn-primary" type="submit"><#if isCreate>Создать<#else>Изменить</#if></button>
        </div>

    </form>

</#macro>

