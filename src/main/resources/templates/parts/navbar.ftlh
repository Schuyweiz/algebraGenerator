<nav class="navbar navbar-dark bg-dark navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Алгебратор</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="dropdown" id="dropdownMenu">
            <ul class="navbar-nav me-auto mb-3 mb-lg-3">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Создать файл с задачами</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="btn btn-secondary btn-lg dropdown-toggle"
                            type="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        Сгенерировать задачу
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/basicmatrixop">Сложение/Вычитание/Умножение матриц</a></li>
                        <li><a class="dropdown-item" href="#">Поиск ранга матрицы</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
