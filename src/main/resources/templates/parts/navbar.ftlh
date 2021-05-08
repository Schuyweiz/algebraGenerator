<nav class="navbar navbar-dark bg-dark navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand me-auto mb-3 mb-lg-2" href="#">Алгебратор</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="dropdown" id="dropdownMenu">
            <ul class="navbar-nav me-auto mb-3 mb-lg-2">
                <li class="nav-item">
                    <a class="nav-link active nav-link-dark" aria-current="page" href="#">Создать файл с задачами</a>
                </li>
                <li class="nav-item dropdown"
                    aria-current="page">
                    <a class="nav-link dropdown-toggle active"
                       href="#"
                       id="navbarDarkDropdownMenuLink"
                       role="button"
                       data-bs-toggle="dropdown"
                       aria-expanded="true">
                        Сгенерировать задачу
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/basicmatrixop">Сложение/Вычитание/Умножение матриц</a></li>
                        <li><a class="dropdown-item" href="/matrixrank">Поиск ранга матрицы</a></li>
                        <li><a class="dropdown-item" href="/powern">Поиск n-ой степени матрицы</a></li>
                        <li><a class="dropdown-item" href="/matrixinv">Определить обратимость матрицы</a></li>
                        <li><a class="dropdown-item" href="/eigenvalues">Найти собственные значения</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
