<#import "../part/common.ftlh" as c>
<#import "../part/orderForm.ftlh" as o>
<#import "../part/showOrders.ftlh" as s>


<@c.page>
    <h1>Мои задания</h1>

    <a class="btn btn-primary me-3" href="/order/create">Добавить задание</a>
    <a href="/order/other">Другие задания</a>
    <@s.showOrder "" />

</@c.page>