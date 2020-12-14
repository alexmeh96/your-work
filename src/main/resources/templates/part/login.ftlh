<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div>
            <label>Email: <input type="email" name="email"/></label>
            <#if emailError??>
                <div>
                    ${emailError}
                </div>
            </#if>
        </div>
        <div>
            <label>Password: <input type="password" name="password"/></label>
            <#if passwordError??>
                <div>
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
            <div>
                <label>Password: <input type="password" name="password2"/></label>
                <#if password2Error??>
                    <div>
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Создать пользователя</a></#if>
        <div>
            <button type="submit"><#if isRegisterForm>Создать<#else>Войти</#if></button>
        </div>

    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Выход"/>
    </form>
</#macro>