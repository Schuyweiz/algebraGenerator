<nav class="navbar navbar-dark bg-dark navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand me-auto mb-3 mb-lg-2" href="#">Алгебратор</a>
        <div class="dropdown" id="dropdownMenu">
            <ul class="navbar-nav me-auto mb-3 mb-lg-2">

                <#if size?? && size gt 0>
                    <li class="nav-item">
                        <a class="nav-link active nav-link-dark" aria-current="page" href="/reset">
                            Сбросить текущий выбор
                        </a>
                    </li>
                </#if>
                <li class="nav-item dropdown"
                    aria-current="page">
                    <a class="nav-link dropdown-toggle  active"
                       href="#"
                       id="navbarDarkDropdownMenuLink"
                       role="button"
                       data-bs-toggle="dropdown"
                       aria-expanded="true">
                        Сгенерировать задачу
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/problems?type=addsubmult">Сложение/Вычитание/Умножение матриц</a></li>
                        <li><a class="dropdown-item" href="/problems?type=rank">Поиск ранга матрицы</a></li>
                        <li><a class="dropdown-item" href="/problems?type=powern">Поиск n-ой степени матрицы</a></li>
                        <li><a class="dropdown-item" href="/problems?type=inverse">Определить обратимость матрицы</a></li>
                        <li><a class="dropdown-item" href="/problems?type=eigenvalues">Найти собственные значения</a></li>
                        <li><a class="dropdown-item" href="/problems?type=jordan">Привести к нормальной форме Жордана</a></li>
                        <li><a class="dropdown-item" href="/problems?type=qr">Найти QR разложение матрицы</a></li>
                        <li><a class="dropdown-item" href="/problems?type=orthdiag">Найти диагональную матрицу и ортогональное преобразование</a></li>
                        <li><a class="dropdown-item" href="/problems?type=svd">Провести сингулярное разложение над матрицей</a></li>
                        <li><a class="dropdown-item" href="/problems?type=rot">Найти угол и ось вращения ортогонального оператора</a></li>
                        <li><a class="dropdown-item" href="/problems?type=dimker">Найти размерность ядра и отображения матрицы перехода</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <#if size??>
                        <#if size gt 0>

                            <a class="nav-link active nav-link-dark " aria-current="page" href="/download" onclick="reloadPage()">
                                Экспортировать задачи и ответы
                                <#if items??>
                                    <span class="badge badge-light">${items}</span>
                                <#else>

                                </#if>

                            </a>

                        <#else>

                            <a class="nav-link disabled nav-link-dark" aria-current="page" >
                                Экспортировать задачи и ответы
                                <#if items??>
                                    <span class="badge badge-light">${items}</span>
                                <#else>

                                </#if>

                            </a>

                        </#if>

                    <#else >
                        <a class="nav-link disabled nav-link-dark" aria-current="page" >
                            Экспортировать задачи и ответы
                            <#if items??>
                                <span class="badge badge-light">${items}</span>
                            <#else>
                                <span class="badge badge-light">0</span>
                            </#if>

                        </a>

                    </#if>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script>
    function  func() {
        console.log("Ya ttut");

        $.ajax({
            url: "/reset",
            method: 'POST',
            success: function() {
                console.warn("warning1");
                console.debug("im here");
            }
        });
    }
</script>

<script>
    async function reloadPage(){
        await new Promise(r => setTimeout(r, 1000));
        window.location.reload();
    }
</script>
