<#import "parts/common.ftlh" as c>
<@c.mjpage>
    <div class="container">
        <div class="row" align="center">
            <p>${problemtext}</p>
            <p>${problem}</p>
        </div>
        <div class="row" align="center">
            <@c.ans "${answer}"/>
        </div>
    </div>


    <form action="/problems/add" method="post">
        <input type="submit" value="Submit" />
    </form>

    <script type="text/javascript">

        MyFunction = function(text,content){
            $.ajax({
                type: "POST",

            })
        }
    </script>

</@c.mjpage>