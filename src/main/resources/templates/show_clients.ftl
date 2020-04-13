<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>

    <style>
        .clients {
            font-size: large;
            overflow-x: scroll;
        }
    </style>
</head>
<body>


<div>
    <a href="/client/new" class="btn btn-outline-primary m-4">New + </a>
</div>

<div class="container clients">

    <#list clients as client>
        <div class="row my-3 p-4 bg-light">
            <div class="col-md-10 row">
                <div class="col-md-4">
                    <div class="p-4 rounded bg-white">
                        <strong>ID: ${client.id}</strong>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        Client:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${client.firstName} ${client.lastName}</strong>
                        </div>
                    </div>
                    <div class="row">
                        Client email:
                        <div class="px-2 rounded bg-white mx-3">
                            <strong>${client.email}</strong>
                        </div>
                    </div>
                </div>
            </div>
                <div class="col-md-2 d-flex justify-content-center">
                    <div class="align-self-center">
                            <button class="btn btn-danger" href="new_order">Create order</button>
                            <form method="POST" action="/clients/delete/${client.id}">
                                <button class="btn btn-danger">Delete</button>
                            </form>
                    </div>
                </div>
        </div>
    </#list>
</div>


</body>
</html>
