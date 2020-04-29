<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

    <style>
        .orders {
            font-size: large;
            overflow-x: hidden;
        }
    </style>
</head>
<body>
<div class="row">
    <div>
        <a href="/" class="btn btn-outline-primary m-4">Main page</a>
    </div>
    <div>
        <a href="/orders/new" class="btn btn-outline-success m-4">New + </a>
    </div>
</div>

<div class="container mt-3 bg-light p-3">
    <h2 class="m-a bg-white p-3 rounded text-center">
        ${headline}
    </h2>
</div>

<div class="container orders mt-5">

    <#if orders.content?size == 0>
        <h1>Orders list is empty!</h1>
    </#if>

    <div class="orders">
        <#list orders.content as order>
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
                            Total price:
                            <div class="px-2 rounded bg-white mx-3">
                                <strong>${order.totalPrice}</strong>
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

    <#if orders.totalPageNumber != 0 >
        <div class="container">
            <ul class="pagination">
                <li class="page-item  <#if !orders.hasPreviousPage >disabled</#if>">
                    <a class="page-link"
                       href="?page=${orders.currentPageNumber - 1}" tabindex="-1">
                        Previous
                    </a>
                </li>
                <#list 1..orders.totalPageNumber as pageNumber>
                    <li class="page-item <#if orders.currentPageNumber == pageNumber>active</#if>">
                        <a class="page-link" href="?page=${pageNumber}">${pageNumber}</a>
                    </li>
                </#list>
                <li class="page-item <#if !orders.hasNextPage >disabled</#if>">
                    <a class="page-link"
                       href="?page=${orders.currentPageNumber + 1}" tabindex="-1">
                        Next
                    </a>
                </li>
            </ul>
        </div>

    </#if>

</body>
</html>

