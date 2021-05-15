<#macro page>
    <!DOCTYPE html>
    <head>
        <title>Here is the title</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
          crossorigin="anonymous">
    </head>
    <body>
    <div class = "container">
    <#nested>
    </div>
    </body>
    </html>
</#macro>
//TODO: add an argument of a page title
<#macro mjpage>
    <!DOCTYPE html>
    <html>
    <head>
        <title>Algebrator</title>
        <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
        <script type="text/javascript" id="MathJax-script" async
                src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js">
        </script>
        <script type="text/javascript" src="https://yastatic.net/jquery/3.3.1/jquery.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
                crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
              crossorigin="anonymous">
    </head>
    <body>
    <#include "navbar.ftlh">
    <div class="container-fluid mt-5">
        <#nested>
    </div>
    <script>
        window.onbeforeunload = function() {
            $.ajax(
                url: "/reset",
                method: 'reset',
                success: function() {
            })
        };
    </script>
    </body>
    </html>
</#macro>

<#macro ans answer>
    <p>
        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Показать ответ
        </button>
    </p>
    <div class="collapse" id="collapseExample">
        <div class="card card-body">
            ${answer}
        </div>
    </div>
</#macro>
