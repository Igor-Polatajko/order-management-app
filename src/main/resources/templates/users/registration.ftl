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
        <form method="POST" action="/registration" class="form-signin">
            <h2 class="form-heading text-center p-3">Registration</h2>
            <#if error??>
                <div>
                    <h5 class="text-danger">Errors: </h5>
                    <pre class="text-danger">${error}</pre>
                </div>
            </#if>
            <div class="form-group">
                <input name="username" type="text" class="form-control m-3" placeholder="Username"
                       autofocus="true"/>
                <input name="firstName" type="text" class="form-control m-3" placeholder="First name"/>
                <input name="lastName" type="text" class="form-control m-3" placeholder="Last name"/>
                <input name="password" type="password" class="form-control m-3" placeholder="Password"/>
                <input name="passwordRepeated" type="password" class="form-control m-3" placeholder="Repeat password"/>


                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button class="btn btn-primary float-right p-3" type="submit">Create account</button>
                <h4 class="text-center"><a href="/login">Login page</a></h4>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

</body>
</html>
