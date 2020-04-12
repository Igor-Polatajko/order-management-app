<html>
<head>
    <style type="text/css">
        @import url(https://fonts.googleapis.com/css?family=Lato:700italic,400,400italic,700);
        /* CSS Button */

        html {
            font: 100%/1.5 Lato, Helvetica Neue, Helvetica, Arial, sans-serif;
            -webkit-font-smoothing: antialiased;
            background-image: linear-gradient(#c5c7cb, #f8f9fa);
            min-height: 100%;
            text-align: center;
        }

        body {
            margin-top: 85px
        }

        .butt {
            width: 300px;
            height: 150px;
            font-size: .825em;
            text-decoration: none;
            font-weight: 700;
            padding: .35em 1em;
            background-color: #eaeef1;
            background-image: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, .1));
            border-radius: 3px;
            color: rgba(0, 0, 0, .6);
            text-shadow: 0 1px 1px rgba(255, 255, 255, .7);
            box-shadow: 0 0 0 1px rgba(0, 0, 0, .2), 0 1px 2px rgba(0, 0, 0, .2), inset 0 1px 2px rgba(255, 255, 255, .7);
        }

        .butt:hover, .butt.hover {
            background-color: #fff;
        }

        .butt:active, .butt.active {
            background-color: #d0d3d6;
            background-image: linear-gradient(rgba(0, 0, 0, .1), rgba(0, 0, 0, 0));
            box-shadow: inset 0 0 2px rgba(0, 0, 0, .2), inset 0 2px 5px rgba(0, 0, 0, .2), 0 1px rgba(255, 255, 255, .2);
        }
    </style>
</head>
<body>
<p><a href="/products/" class=butt>Products</a></p>
<p><a href="/orders/" class="butt hover">Orders</a></p>
<p><a href="/clients/" class="butt active">Clients</a></p>

</body>
</html>
