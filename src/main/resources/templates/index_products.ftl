<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<form action="/products/new">
<button type="submit" style="margin: 15px; alignment: right"
        class="btn btn-primary">Add new product</button>
</form>
<table border="3" class="table table-striped" style="width: 90%; margin: auto;">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Name</th>
        <th scope="col">Amount</th>
        <th scope="col">Price</th>
        <th></th>
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
            <form style='display:inline;' action="/products/update/${product.id}" method="get">
                <button class="btn btn-warning" type="submit">Edit</button>
            </form>
            <form style='display:inline;' action="/products/delete/${product.id}" method="post">
                <button class="btn btn-danger" type="submit">Delete</button>
            </form>
        </th>
    </tr>
    </#list>
    </tbody>
</table>
</body>
</html>