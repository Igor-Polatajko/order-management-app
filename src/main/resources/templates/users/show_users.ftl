<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <style type="text/css">

        body {
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
        }
    </style>
</head>
<body>
<div class="row">
    <div class="col-sm">
        <a href="/" class="btn btn-dark m-4 ">Main page</a>
    </div>
    <div class="col text-center">
        <select onchange="window.document.location.href='/admin/users<#if nameQuery??>/find?q=${nameQuery}&<#else>?</#if>reverse='
                + this.options[this.selectedIndex].value;"
                class="form-control m-4 mx-auto mw-10">
            <option <#if reverse?? && !reverse || !reverse??> selected</#if> value="false">Active first</option>
            <option <#if reverse?? && reverse> selected </#if> value="true">Archived first</option>
        </select>
    </div>
    <div class="col">
    </div>
</div>
<div class="row">
    <div class="input-group col text-center">

        <form action="/admin/users/find" class="form-inline mx-auto">
            <#if nameQuery??>
                <a href="/admin/users" class="btn btn-secondary m-1">Reset</a>
            </#if>
            <input name="q" type="text" class="form-control" placeholder="Name or username"
                   <#if nameQuery??>value="${nameQuery}"</#if>
                   aria-label="Name or username" aria-describedby="basic-addon2">
            <div class="input-group-append">
                <button class="btn btn-info" type="submit">Search</button>
            </div>
            <#if reverse??>
                <input type="hidden" name="reverse" value="${reverse?string("true", "false")}"/>
            </#if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
<div class="container text-center mb-0">
    <h1 style="background-color: #eaeef1">
        ${headline}
    </h1>
</div>
<table border="2" class="table table-striped">
    <thead class="thead-dark">
    <tr class="d-flex">
        <th scope="col" class="col-1">ID</th>
        <th scope="col" class="col-2">First Name</th>
        <th scope="col" class="col-2">Last Name</th>
        <th scope="col" class="col-3">Username</th>
        <th class="col-4"></th>
    </tr>
    </thead>
    <tbody>
    <#list users.content as user>
        <tr class="d-flex <#if user.active>table-warning<#else>table-Secondary</#if>">
            <th scope="row" class="col-1">${user.id}</th>
            <td class="col-2">${user.firstName}</td>
            <td class="col-2">${user.lastName}</td>
            <td class="col-3">${user.username}</td>
            <th class="col-4 text-center">
                <#if user.active>
                    <form class="form-inline d-inline" action="/admin/users/delete/${user.id}" method="post">
                        <button class="btn btn-dark" type="submit">Archive</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                <#else>
                    <form class="form-inline d-inline" action="/admin/users/activate/${user.id}" method="post">
                        <button class="btn btn-primary" type="submit">Activate</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <form class="form-inline d-inline" action="/admin/users/delete/${user.id}" method="post">
                        <button class="btn btn-danger" type="submit">Delete</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </#if>
            </th>
        </tr>
    </#list>

    </tbody>

</table>
<div class="row">
    <ul class="pagination mx-auto">
        <#list 1..users.totalPages as pageNumber>
            <form action="/admin/users<#if nameQuery??>/find</#if>">
                <li class="page-item">
                    <button type="submit" <#if pageNumber - 1 == users.number>style="background-color: gray" </#if>
                            class="page-link">${pageNumber}
                    </button>
                </li>
                <#if nameQuery??>
                    <input type="hidden" name="q" value="${nameQuery}">
                </#if>

                <#if reverse??>
                    <input type="hidden" name="reverse" value="${reverse?string("true", "false")}">
                </#if>
                <input type="hidden" name="page" value="${pageNumber}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </#list>
    </ul>
</div>
</body>
</html>