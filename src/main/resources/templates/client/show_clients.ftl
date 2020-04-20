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
        th, td {
            border-bottom: 1px solid darkblue;
        }
    </style>
</head>
<body>
<form action="/" class="backButton">
    <button type="submit" style="width: 100px" class=" btn btn-primary">Back</button>
</form>
<form action="/clients/new" style="margin:auto; width: 50%; display: block; padding-bottom:10px ">
    <button type="submit" style="margin:auto; width: 50%; display: block" class="btn btn-info">Add new client</button>
</form>
<table border="2" class="table table-striped" style="width: 80%; margin: auto;">
    <thead class="thead-dark">
    <tr>
        <th scope="col" style="width: 10%">ID</th>
        <th scope="col" style="width: 30%">First Name</th>
        <th scope="col" style="width: 20%">Last Name</th>
        <th scope="col" style="width: 20%">Email</th>
        <th style="width: 20%"></th>
    </tr>
    </thead>
    <tbody>
    <#list clients as client>
        <tr>
            <th scope="row">${client.id}</th>
            <td>${client.firstName}</td>
            <td>${client.lastName}</td>
            <td>${client.email}</td>
            <th scope="col">
            <span style="margin-left: 13%;">
                <form style='display:inline; width: 50px' action="/orders/product/${client.id}" method="get">
                    <button class="btn btn-outline-info" type="submit">Orders</button>
                </form>
                <form style='display:inline; width: 50px ' action="/clients/update/${client.id}" method="get">
                    <button class="btn btn-outline-success" type="submit">Edit</button>
                </form>
                <form style='display:inline; width: 50px' action="/clients/delete/${client.id}" method="post" >
                    <button class="btn btn-outline-danger" type="submit">Delete</button>
                </form>
            </span>
            </th>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>