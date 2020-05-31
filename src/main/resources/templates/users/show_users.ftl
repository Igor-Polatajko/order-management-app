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
        <select onchange="window.document.location.href='/users<#if nameQuery??>/find?q=${nameQuery}&<#else>?</#if>reverse='
                + this.options[this.selectedIndex].value;"
                class="form-control m-4 mx-auto mw-10">
            <option <#if reverse?? && !reverse || !reverse??> selected</#if> value="false">Active first</option>
            <option <#if reverse?? && reverse> selected </#if> value="true">Disabled first</option>
        </select>
    </div>
    <div class="col">
    </div>
</div>
<div class="row">
    <div class="input-group col text-center">

        <form action="/users/find" class="form-inline mx-auto">
            <#if nameQuery??>
                <a href="/users" class="btn btn-secondary m-1">Reset</a>
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
        <th scope="col" class="col-3">Name</th>
        <th scope="col" class="col-3">Username</th>
        <th scope="col" class="col-2">Role</th>
        <th class="col-3"></th>
    </tr>
    </thead>
    <tbody>
    <#list users.content as user>
        <tr class="d-flex
                    <#if user.active && user.role.name() = "ROLE_ADMIN">table-info</#if>
                    <#if user.active && user.role.name() = "ROLE_USER">table-light</#if>
                    <#if !user.active && user.role.name() = "ROLE_ADMIN">table-warning</#if>
                    <#if !user.active && user.role.name() = "ROLE_USER">table-secondary</#if>
                   ">
            <th scope="row" class="col-1">${user.id}</th>
            <td class="col-3">${user.firstName + " " + user.lastName}</td>
            <td class="col-3">${user.username}</td>
            <td class="col-2"><#if user.role = "ROLE_ADMIN">Admin<#else>User</#if></td>
            <th class="col-3 text-center">
                <form class="form-inline d-inline" action="/users/activate/${user.id}" method="post">
                    <button <#if user.active>disabled class="btn btn-secondary" <#else> class="btn btn-primary"</#if>
                            type="submit">Activate
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <form class="form-inline d-inline" action="/users/deactivate/${user.id}" method="post">
                    <button <#if !user.active>disabled class="btn btn-secondary" <#else> class="btn btn-danger" </#if>
                            type="submit">Deactivate
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </th>
        </tr>
    </#list>

    </tbody>

</table>
<div class="row">
    <ul class="pagination mx-auto">
        <#list 1..users.totalPages as pageNumber>
            <form action="/users<#if nameQuery??>/find</#if>">
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