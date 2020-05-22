<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">

        .newBtn {
            display: inline;
            position: absolute;
            right: 5%;
            display: inline;
        }


        .pagination {
            margin-left: 5%;
        }

        .tableTitle {
            width: 100%;
            height: 100%;
            text-align: center;
            color: black;
        }

        body {
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
            align-items: center;
            padding-top: 30px;
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
<div class="container text-center">
    <h1 style="background-color: #eaeef1">
        <#if active?? && !active> Archived products <#else> Active products </#if>
    </h1>
</div>
<table border="2" <#if active?? && !active> class="table table-dark" <#else>  class="table table-striped" </#if>
       style="width: 90%; margin: auto;">

    <thead>
    <tr>
        <th scope="col" style="width: 10%">ID</th>
        <th scope="col" style="width: 30%">Name</th>
        <th scope="col" style="width: 10%">Amount</th>
        <th scope="col" style="width: 20%">Price</th>
        <th style="width: 30%"></th>
    </tr>
    </thead>
    <tbody>
    <#list products.content as product>
        <tr>
            <th scope="row">${product.id}</th>
            <td>${product.name}</td>
            <td>${product.amount}</td>
            <td>${product.price}</td>
            <th scope="col">
            <span style="margin-left: 27%;">
                <a style='display:inline; width: 50px' href="/orders/product/${product.id}">
                    <button class="btn btn-warning" type="submit">Orders</button>
                </a>
                <a style='display:inline; width: 50px' href="/products/update/${product.id}">
                <button class="btn btn-light" type="submit">Edit</button>
                </a>
                <#if product.active>
                    <form style='display:inline; width: 50px' action="/products/delete/${product.id}" method="post">
                        <button class="btn btn-dark" type="submit">Archive</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                <#else>
                    <form style='display:inline; width: 50px' action="/products/activate/${product.id}" method="post">
                        <button class="btn btn-primary" type="submit">Activate</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <form style='display:inline; width: 50px' action="/products/delete/${product.id}" method="post">
                        <button class="btn btn-danger" type="submit">Delete</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </#if>

            </span>
            </th>
        </tr>
    </#list>

    </tbody>

</table>
<#--<ul class="pagination">-->
<#--    <#list 1..products.totalPages as pageNumber>-->
<#--        <li class="page-item">-->
<#--            <a <#if pageNumber - 1 == products.number>style="background-color: gray" </#if>-->
<#--               class="page-link"-->
<#--               href="-->
<#--               /products-->
<#--<#if name??>/find?name=${name}&<#else>?</#if>-->
<#--page=${pageNumber}-->
<#--<#if active??>&active=${active?string("true", "false")}</#if>-->
<#--">${pageNumber}-->
<#--            </a></li>-->
<#--    </#list>-->
<#--</ul>-->

</body>
</html>