<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">
        @import url(https://fonts.googleapis.com/css?family=Roboto:400,300,600,400italic);
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-font-smoothing: antialiased;
            -moz-font-smoothing: antialiased;
            -o-font-smoothing: antialiased;
            font-smoothing: antialiased;
            text-rendering: optimizeLegibility;
        }
        body {
            font-family: "Roboto", Helvetica, Arial, sans-serif;
            font-weight: 100;
            font-size: 12px;
            line-height: 30px;
            color: #777;
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
        }
        .backButton {
            position: absolute;
            margin-left: 30px;
        }
        .container {
            max-width: 400px;
            width: 100%;
            margin: 0 auto;
            position: relative;
        }
        #client {
            background: #F9F9F9;
            padding: 25px;
            margin: 150px 0;
            box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
        }
        #client h3 {
            text-align: center;
            display: block;
            font-size: 30px;
            font-weight: 300;
            margin-bottom: 10px;
        }
        input {
            width: 100%;
        }
        fieldset {
            border: medium none !important;
            margin: 0 0 10px;
            min-width: 100%;
            padding: 0;
            width: 100%;
        }
        #client textarea {
            height: 100px;
            max-width: 100%;
            resize: none;
        }
        #client .submitBtn[type="submit"] {
            cursor: pointer;
            width: 100%;
            border: none;
            margin: 0 0 5px;
            padding: 10px;
            font-size: 15px;
        }
        #client .submitBtn[type="submit"]:hover {
            background: #43A047;
            -webkit-transition: background 0.3s ease-in-out;
            -moz-transition: background 0.3s ease-in-out;
            transition: background-color 0.3s ease-in-out;
        }
    </style>
</head>
<body>
<a href="/clients " class="backButton">
    <button type="submit" style="width: 100px" class="btn btn-primary">Back</button>
</a>
<div class="container">
    <form id="client" action="/clients/new" method="post">
        <h3>Create client</h3>
        <fieldset>
            First Name:
            <input name="firstName" placeholder="First Name" value="<#if client??>${client.firstName}<#else></#if>"
                   type="text" tabindex="1" required autofocus/>
        </fieldset>
        <fieldset>
            Last Name:
            <input name="lastName" placeholder="Last Name" value="<#if client??>${client.lastName}<#else></#if>"
                   type="text" tabindex="2" required autofocus/>
        </fieldset>
        <fieldset>
            Email:
            <input name="email" placeholder="Email" value="<#if client??>${client.email}<#else></#if>"
                   type="text" tabindex="3" required>
        </fieldset>
        <fieldset>
            <button name="Submit" type="submit" class="btn btn-secondary submitBtn" data-submit="...Sending">Save!
            </button>
        </fieldset>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>