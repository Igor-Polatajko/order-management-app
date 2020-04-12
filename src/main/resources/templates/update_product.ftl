<html>
<head>
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
            background: silver;
        }

        .container {
            max-width: 400px;
            width: 100%;
            margin: 0 auto;
            position: relative;
        }

        #product input[type="name"],
        #product input[type="amount"],
        #product input[type="price"],
        #product textarea,
        #product button[type="submit"] {

            font: 400 12px/16px "Roboto", Helvetica, Arial, sans-serif;
        }

        #product {
            background: #F9F9F9;
            padding: 25px;
            margin: 150px 0;
            box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
        }

        #product h3 {
            text-align: center;
            display: block;
            font-size: 30px;
            font-weight: 300;
            margin-bottom: 10px;
        }

        #product h4 {
            margin: 5px 0 15px;
            display: block;
            font-size: 13px;
            font-weight: 400;
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

        #product input[type="name"],
        #product input[type="amount"],
        #product input[type="price"],
        #product textarea {
            width: 100%;
            border: 1px solid #ccc;
            background: #FFF;
            margin: 0 0 5px;
            padding: 10px;
        }

        #product input[type="name"],
        #product input[type="amount"],
        #product input[type="price"],
        #product textarea:hover {

            -webkit-transition: border-color 0.3s ease-in-out;
            -moz-transition: border-color 0.3s ease-in-out;
            transition: border-color 0.3s ease-in-out;
            border: 1px solid #aaa;
        }

        #product textarea {
            height: 100px;
            max-width: 100%;
            resize: none;
        }

        #product button[type="submit"] {
            cursor: pointer;
            width: 100%;
            border: none;
            background: #4CAF50;
            color: #FFF;
            margin: 0 0 5px;
            padding: 10px;
            font-size: 15px;
        }

        #product button[type="submit"]:hover {
            background: #43A047;
            -webkit-transition: background 0.3s ease-in-out;
            -moz-transition: background 0.3s ease-in-out;
            transition: background-color 0.3s ease-in-out;
        }

        #product button[type="submit"]:active {
            box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.5);
        }

        #product input:focus,
        #product textarea:focus {
            outline: 0;
            border: 1px solid #aaa;
        }

        ::-webkit-input-placeholder {
            color: #888;
        }

        :-moz-placeholder {
            color: #888;
        }

        ::-moz-placeholder {
            color: #888;
        }

        :-ms-input-placeholder {
            color: #888;
        }
    </style>
</head>
<body>
<div class="container">
    <form id="product" action="/products/update" method="post">
        <h3>Product info</h3>
        <fieldset>
            Name:
            <input name="name" placeholder="Product name" value="${product.name}" type="text" tabindex="1" required autofocus/>
        </fieldset>
        <fieldset>
            Amount:
            <input name="amount" placeholder="Amount" value="${product.amount}" type="number" tabindex="2" required>
        </fieldset>
        <fieldset>
            Price:
            <input name="price" placeholder="Price" value="${product.price?c}" type="number" tabindex="3" required>
        </fieldset>
        <fieldset>
            <button name="Submit" type="submit" id="contact-submit" data-submit="...Sending">Done!</button>
        </fieldset>
        <input type="text" name="id" value="${product.id}" style="visibility: hidden">
    </form>
</div>
</body>
</html>