<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
        .backButton {
            position: absolute;
            margin-left: 30px;
        }

        body {
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
            align-items: center;
            padding-top: 30px;
        }
    </style>
</head>
<body>
<form action="/" class="backButton">
    <button type="submit" style="width: 100px" class=" btn btn-dark">Back</button>
</form>
<form action="/products/new" style="margin:auto; width: 50%; display: block; padding-bottom:10px ">
    <button type="submit" style="margin:auto; width: 50%; display: block" class="btn btn-info">Add new product</button>
</form>
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