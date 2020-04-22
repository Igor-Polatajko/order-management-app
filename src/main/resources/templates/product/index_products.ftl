<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
        .backForm {
            position: relative;
            left: 5%;
            display: inline;
        }

        .new-form {
            position: absolute;
            right: 5%;
            display: inline;
        }
        .search-form {
            display: inline;
            position: absolute;
            left: 40%;
            right: 40%;
        }
        .search-btn {
            display: inline;

        }
        .search-field {
            width: 200px;
            display: inline;
        }

        .new-btn {
            display: inline;

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
    <form action="/" class="backForm">
        <button type="submit" style="width: 100px" class="btn btn-dark">Back</button>
    </form>

    <form action="/products/like" class="search-form">
        <input name="name" type="text" class="form-control search-field" placeholder="Search product">
        <button type="submit" class="btn btn-light search-btn">Search</button>
    </form>

    <form action="/products/new"  class="new-form">
        <button type="submit" class="btn btn-info new-btn">Add new product</button>
    </form>
</div>

<table border="2" class="table table-striped" style="width: 90%; margin: auto;">
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
    <#list products as product>
        <tr>
            <th scope="row">${product.id}</th>
            <td>${product.name}</td>
            <td>${product.amount}</td>
            <td>${product.price}</td>
            <th scope="col">
            <span style="margin-left: 27%;">
                <form style='display:inline; width: 50px' action="/orders/product/${product.id}" method="get">
                    <button class="btn btn-warning" type="submit">Orders</button>
                </form>
                <form style='display:inline; width: 50px' action="/products/update/${product.id}" method="get">
                <button class="btn btn-light" type="submit">Edit</button>
                </form>
                <form style='display:inline; width: 50px' action="/products/delete/${product.id}" method="post">
                    <button class="btn btn-dark" type="submit">Delete</button>
                </form>
            </span>
            </th>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>