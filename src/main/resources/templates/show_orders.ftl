<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

    <style>
        .orders {
            font-size: large;
            overflow-x: scroll;
        }
    </style>
</head>
<body>

<div class="container orders">
    <#list orders as order>
        <div class="row my-3 p-4 bg-light">
            <div class="col-md-10 row">
                <div class="col-md-4">
                    <div class="p-4 rounded bg-white">
                        <strong>#${order.orderId} ${order.productName}</strong>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        Client:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${order.clientFirstName} ${order.clientLastName}</strong>
                        </div>
                    </div>
                    <div class="row">
                        Client email:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${order.clientEmail}</strong>
                        </div>
                    </div>
                    <div class="row">
                        Item price:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${order.itemPrice}</strong>
                        </div>
                    </div>
                    <div class="row">
                        Items amount:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${order.productOrderAmount}</strong>
                        </div>
                    </div>
                    <div class="row">
                        Total price
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${order.totalPrice}:</strong>
                        </div>
                    </div>
                    <div class="row">
                        Order time:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${order.createdDate}</strong>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2 d-flex justify-content-center">
                <div class="align-self-center">
                    <form method="POST" action="/orders/delete/${order.orderId}">
                        <button class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        </div>
    </#list>
</div>


</body>
</html>

