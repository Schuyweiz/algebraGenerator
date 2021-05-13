<#import "parts/common.ftlh" as c>
<@c.mjpage>
    <div class="container">
        <div class="row" align="center">
            <p>Задача
                ${problemtext}</p>
            <p>${problem}</p>
        </div>
        <div class="row" align="center">
            <@c.ans "${answer}"/>
        </div>
    </div>

</@c.mjpage>