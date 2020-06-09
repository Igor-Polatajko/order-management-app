<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
    </style>
    <title>Error page</title>
</head>
<body>
<div class="container h-100 d-flex justify-content-center">
    <div class="jumbotron my-auto">
        <h1 class="m-a text-center">${message}</h1>
        <div class="row">
            <a href="/" class="btn btn-outline-primary mt-4 mx-auto">Main page</a>
        </div>
    </div>
</div>
</body>
</html>