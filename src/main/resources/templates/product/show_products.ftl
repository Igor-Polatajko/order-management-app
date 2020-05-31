<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">

        body {
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-sm">
        <a href="/" class="btn btn-dark m-4 ">Main page</a>
    </div>
    <div class="col text-center">
        <select onchange="window.document.location.href='/products<#if name??>/find?name=${name}&<#else>?</#if>active='
                + this.options[this.selectedIndex].value;"
                class="form-control m-4 mx-auto mw-10">
            <option <#if active?? && active> selected</#if> value="true">Show active</option>
            <option <#if active?? && !active> selected </#if> value="false">Show archived</option>
        </select>
    </div>
    <div class="col">
        <a class="btn btn-success m-4 float-right" href="/products/new">Add product</a>
    </div>
    <div class="w-100"></div>
</div>
<div class="row">
    <div class="input-group col text-center">

        <form action="/products/find" class="form-inline mx-auto">
            <#if name??>
                <a href="/products" class="btn btn-secondary m-1">Reset</a>
            </#if>
            <input name="name" type="text" class="form-control" placeholder="Product name"
                   <#if name??>value="${name}"</#if>
                   aria-label="Product name" aria-describedby="basic-addon2">
            <div class="input-group-append">
                <button class="btn btn-info" type="submit">Search</button>
            </div>
            <#if active??>
                <input type="hidden" name="active" value="${active?string("true", "false")}"/>
            </#if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
<div class="container text-center mb-0">
    <h1 style="background-color: #eaeef1">
        <#if active?? && !active> Archived products <#else> Active products </#if>
    </h1>
</div>
<table border="2" class="table <#if active?? && !active>table-dark <#else> table-striped </#if>">
    <thead class="<#if active?? && !active>thead-light <#else> thead-dark </#if>">
    <tr class="d-flex">
        <th scope="col" class="col-1">ID</th>
        <th scope="col" class="col-4">Name</th>
        <th scope="col" class="col-1">Amount</th>
        <th scope="col" class="col-1">Price</th>
        <th class="col-5"></th>
    </tr>
    </thead>
    <tbody>
    <#list products.content as product>
        <tr class="d-flex">
            <th scope="row" class="col-1">${product.id}</th>
            <td class="col-4">${product.name}</td>
            <td class="col-1">${product.amount}</td>
            <td class="col-1">${product.price}</td>
            <th class="col-5 text-center">
                <a href="/orders/product/${product.id}">
                    <button class="btn btn-warning" type="submit">Orders</button>
                </a>
                <a href="/products/update/${product.id}">
                    <button class="btn btn-light" type="submit">Edit</button>
                </a>
                <#if product.active>
                    <form class="form-inline d-inline" action="/products/delete/${product.id}" method="post">
                        <button class="btn btn-dark" type="submit">Archive</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                <#else>
                    <form class="form-inline d-inline" action="/products/activate/${product.id}" method="post">
                        <button class="btn btn-primary" type="submit">Activate</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <form class="form-inline d-inline" action="/products/delete/${product.id}" method="post">
                        <button class="btn btn-danger" type="submit">Delete</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </#if>
            </th>
        </tr>
    </#list>

    </tbody>

</table>
<div class="row">
    <ul class="pagination mx-auto">
        <#list 1..products.totalPages as pageNumber>
            <form action="/products<#if name??>/find</#if>">
                <li class="page-item">
                    <button type="submit" <#if pageNumber - 1 == products.number>style="background-color: gray" </#if>
                            class="page-link">${pageNumber}
                    </button>
                </li>
                <#if name??>
                    <input type="hidden" name="name" value="${name}">
                </#if>
                <#if active??>
                    <input type="hidden" name="active" value="${active?string("true", "false")}">
                </#if>
                <input type="hidden" name="page" value="${pageNumber}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </#list>
    </ul>
</div>
</body>
</html>