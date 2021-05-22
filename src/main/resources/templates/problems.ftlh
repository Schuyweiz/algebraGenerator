<#import "parts/common.ftlh" as c>
<@c.mjpage>
    <button type="button" class="btn btn-dark btn-lg btn-block btn-primary" onclick="refreshPage()">
        Другая задача
    </button>

    <button type="button" class="btn btn-dark btn-lg btn-block btn-primary" onclick="add()">
        Добавить задачу
    </button>
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

    </#if>



    <script>
        function refreshPage(){
            window.location.reload();
        }
    </script>
    <script>
        function  add() {
            console.log("Add method called");

            $.ajax({
                url: "/problems/add",
                method: 'POST',
                complete: function (){
                    refreshPage();
                }
            });
        }
    </script>


</@c.mjpage>