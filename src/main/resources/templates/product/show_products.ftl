<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
        .backBtn {
            position: relative;
            left: 5%;
            display: inline;
            width: 100px;
        }

        .activePicker {
            display: inline;
            width: 160px;
            position: absolute;
            left: 20%;
        }

        .search-form {
            display: inline;
            position: absolute;
            left: 35%;
            right: 35%;
        }

        .search-btn {
            display: inline;

        }

        .search-field {
            width: 200px;
            display: inline;
        }

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
<div style="width: 100%; margin-bottom: 15px">
    <a href="/">
        <button class="backBtn btn btn-dark">Back</button>
    </a>
    <select  onchange="window.document.location.href='/products<#if name??>/find?name=${name}&<#else>?</#if>active='
    + this.options[this.selectedIndex].value;"
            class="form-control selectpicker activePicker">
        <option <#if active?? && active> selected</#if> value="true">Show active</option>
        <option <#if active?? && !active> selected </#if> value="false">Show archived</option>
    </select>
    <form action="/products/find" class="search-form">
        <input id="searchFieldInput" name="name" type="text" class="form-control search-field"
               <#if name??>value=${name}</#if>>
        <#if active??>
        <input type="hidden" name="active" value="${active?string("true", "false")}" />
        </#if>
        <button id="searchBtn" type="submit" class="btn btn-light search-btn">Search</button>
        <a href="/products">Reset</a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>


    <a href="/products/new">
        <button class="btn btn-info newBtn">Add new product</button>
    </a>
</div>

<table border="2" <#if active?? && !active> class="table table-dark" <#else>  class="table table-striped" </#if>
       style="width: 90%; margin: auto;">

    <thead>
    <tr style="background-color: #eaeef1">
        <th colspan="5">
            <h2 class="tableTitle"> <#if active?? && !active> Archived products <#else> Active products </#if></h2>
        </th>
    </tr>
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
                    </form>
                <#else>
                    <form style='display:inline; width: 50px' action="/products/activate/${product.id}" method="post">
                        <button class="btn btn-primary" type="submit">Activate</button>
                    </form>
                    <form style='display:inline; width: 50px' action="/products/delete/${product.id}" method="post">
                        <button class="btn btn-danger" type="submit">Delete</button>
                    </form>
                </#if>

            </span>
            </th>
        </tr>
    </#list>

    </tbody>

</table>
<ul class="pagination">
    <#list 1..products.totalPages as pageNumber>
        <li class="page-item">
            <a <#if pageNumber - 1 == products.number>style="background-color: gray" </#if>
               class="page-link"
               href="
               /products
<#if name??>/find?name=${name}&<#else>?</#if>
page=${pageNumber}
<#if active??>&active=${active?string("true", "false")}</#if>
">${pageNumber}
            </a></li>
    </#list>
</ul>

</body>
</html>