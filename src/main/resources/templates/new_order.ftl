<html>
<head>
    <title>Bootstrap: Dropdown Select with ajax Live search</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.min.css">
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>

    <style>
        body {
            font-size: large;
        }
    </style>
</head>
<body>
<div class="container shadow" style="padding: 5rem; margin-top: 2rem;">
    <h1>Add order</h1>
    <div class="row">
        <form method="POST">
            <div class="col-lg-12">
                <div class="form-group">
                    <label for="exampleDropdown">Choose the product</label>
                    <select data-live-search="true" title="Product" class="form-control selectpicker">
                        <#list clients as client>
                            <option>Water Melon</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleDropdown">Choose the client</label>
                    <select data-live-search="true" title="Client" class="form-control selectpicker">
                        <#list products as product>
                            <option>Mango</option>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="example-number-input">Amount</label>
                <div>
                    <input class="form-control" type="number" value="1" id="example-number-input">
                </div>
            </div>

            <div class="row">
                <div class="mt-5">
                    <button class="btn btn-outline-primary">Submit</button>
                </div>
                <div class="mt-5 ml-3">
                    <a href="/orders" class="btn btn-outline-primary">Back</a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>