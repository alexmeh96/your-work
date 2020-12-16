<#macro orderForm path isCreate>

    <#if orderError??>
        <div>
            ${orderError}
        </div>
    </#if>
    <form action="${path}" method="post">
        <div>
            <label>Название <input type="text" name="name" <#if order??>value="${order.name}"</#if>/></label>
            <#if nameError??>
                <div>
                    ${nameError}
                </div>
            </#if>
        </div>
        <div>
            <label>Describe <input type="text" name="describe" <#if order??>value="${order.describe}"</#if>/></label>
            <#if describeError??>
                <div>
                    ${describeError}
                </div>
            </#if>
        </div>

        <div>
            <label for="category">Category:</label>
            <select id="category" name="categoryName">
                <#if order??>
                    <#list categories as category>
                        <option value="${category.name}"
                                <#if category.name == order.category.name >selected</#if>>${category.name}</option>
                    </#list>
                <#else>
                    <option disabled selected>Выберите категорию</option>

                    <#list categories as category>
                        <option value="${category.name}">${category.name}</option>
                    </#list>
                </#if>
            </select>
        </div>

        <#if categoryNameError??>
            <div>
                ${categoryNameError}
            </div>
        </#if>

        <#if order??>
            <input type="hidden" name="orderId" value="${order.id}"/>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>


        <div>
            <button type="submit"><#if isCreate>Создать<#else>Изменить</#if></button>
        </div>

    </form>

</#macro>