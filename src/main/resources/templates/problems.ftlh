<#import "parts/common.ftlh" as c>
<@c.mjpage>
    <#if problemtext??>
    <div class="container">
        <div class="row" align="center">
            <p>${problemtext}</p>
            <p>${problem}</p>
        </div>
        <div class="row" align="center">
            <@c.ans "${answer}"/>
        </div>
    </div>
        <button type="button" class="btn btn-dark btn-lg btn-block btn-primary" onclick="refreshPage()">
            Другая задача
        </button>

    <form action="/problems/add" method="post">
        <button type="submit" class="btn btn-dark btn-lg btn-block"  >
            Добавить задачу
        </button>
    </form>
    </#if>



    <script>
        function refreshPage(){
            window.location.reload();
        }
    </script>

</@c.mjpage>