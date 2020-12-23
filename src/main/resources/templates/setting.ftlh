<#import "part/common.ftlh" as c>
<#import "part/login.ftlh" as l>

<@c.page>
    <h1>Страница настроек</h1>
    <form action="/profile/setting/email" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Email</label>
            <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="email"
                   required placeholder="email" value="${user.email}"
            >
            <div class="invalid-feedback">
                Поле не должно быть пустым
            </div>
            <#if emailError??>
                <div>
                    ${emailError}
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <div>
                <button type="submit" class="btn btn-primary mt-3">Обновить</button>
            </div>
        </div>
    </form>
    <form action="/profile/setting/password" method="post" class="needs-validation" novalidate>

        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Пароль</label>
            <input type="password" class="form-control" id="exampleInputPassword1" name="password" required
                   placeholder="пароль"
                   >
            <div class="invalid-feedback">
                Поле не должно быть пустым
            </div>
            <#if passwordError??>
                <div>
                    ${passwordError}
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <div>
                <button type="submit" class="btn btn-primary mt-3">Обновить</button>
            </div>
        </div>

    </form>

</@c.page>