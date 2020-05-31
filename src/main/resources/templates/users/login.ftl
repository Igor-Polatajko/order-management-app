<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="w-100 h-100 d-flex align-items-center justify-content-center">
    <div class="col-md-8">
        <form method="POST" action="/login" class="form-signin">
            <h2 class="form-heading text-center p-3">Log in</h2>
            <#if error??>
                <p class="text-danger text-center">${error}</p>
            </#if>
            <#if message??>
                <p class="text-primary text-center">${message}</p>
            </#if>
            <div class="form-group">
                <input name="username" type="text" class="form-control m-3" placeholder="Username"
                       autofocus="true"/>
                <input name="password" type="password" class="form-control m-3" placeholder="Password"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button class="btn btn-primary float-right p-3" type="submit">Log In</button>
                <h4 class="text-center"><a href="/registration">Create an account</a></h4>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

</body>
</html>

