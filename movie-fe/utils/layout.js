// HEADER
document.getElementById("header").insertAdjacentHTML('beforeend', `
    <nav class="header__navbar">
        <ul class="header__navbar-list">
            <a href="../home/index.html" class="logo">
                <i class='bx bxl-firefox bx-tada color-red'></i><span class="main-color">Pikachu</span>FilmClub
            </a>
            <li class="header__navbar-item">
                <a href="search.html" class="header__navbar-item-link">
                    <i class='bx bx-search'></i>
                    Tìm Kiếm</a>
            </li>
            <li class="header__navbar-item">
                <a href="../category/index.html" class="header__navbar-item-link">Phim Hot</a>
            </li>
            <li class="header__navbar-item">
                <a href="../category/index.html" class="header__navbar-item-link">Phim Lẻ</a>
            </li>
            <li class="header__navbar-item">
                <a href="../category/index.html" class="header__navbar-item-link">Phim Bộ</a>
            </li>
            <li class="header__navbar-item">
                <a href="../category/index.html" class="header__navbar-item-link">Tất Cả Phim</a>
            </li>

        </ul>
        <ul class="header__navbar-list">
            <li>
                <a href="login.html" class="header__navbar-item header__navbar-item--bold ">
                    <span>Đăng Nhập</span>
                </a>
            </li>
        </ul>
        <!-- MOBILE MENU TOGGLE -->
        <div class="hamburger-menu" id="hamburger-menu">
            <div class="hamburger">
                <ul class="hamburger-menu-item" id="hamburger-menu-item">
                    <li><a href="#">
                            <i class='bx bx-search'></i>
                            Tìm Kiếm</a>
                    </li>
                    <li><a href="#">Phim Hot</a></li>
                    <li><a href="#">Phim Lẻ</a></li>
                    <li><a href="#">Phim Bộ</a></li>
                    <li><a href="#">Tất Cả Phim</a></li>
                    <li>
                        <a style="color: rgb(134, 19, 19);" href="login.html" class="header__navbar-item">
                            <span>Đăng Nhập</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
`)

// FOOTER
document.getElementById("footer").insertAdjacentHTML('beforeend', `
    <div class="footer-container">
        <div class="row">
            <div class="col-4 col-md-4 col-sm-12">
                <div class="content">
                    <a href="#" class="logo">
                        <i class='bx bxl-firefox bx-tada color-red'></i><span
                            class="main-color">Pikachu</span>FilmClub
                    </a>
                    <p class="text-white">
                        Xem phim online miễn phí chất lượng cao với phụ đề tiếng việt - thuyết minh - lồng
                        tiếng. PikachuFilmClub có nhiều thể loại phim phong phú, đặc sắc, nhiều bộ phim hay nhất
                        - mới nhất.
                    </p>
                </div>
            </div>
            <div class="col-8 col-md-8 col-sm-12">
                <div class="row">
                    <div class="col-3">
                        <div class="content">
                            <p><b>PikachuFilmClub</b></p>
                            <ul class="footer-menu" >
                                <li><a href="#" class="text-white">Trợ giúp</a></li>
                                <li><a href="#" class="text-white">Hỏi đáp</a></li>
                                <li><a href="#" class="text-white">Liên hệ</a></li>
                                <li><a href="#" class="text-white">Tin tức</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="content">
                            <p><b>Câu hỏi thường gặp</b></p>
                            <ul class="footer-menu">
                                <li><a href="#" class="text-white">Các cách xem</a></li>
                                <li><a href="#" class="text-white">Kiểm tra tốc độ</a></li>
                                <li><a href="#" class="text-white">Tùy chọn cookie</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="content">
                            <p><b>Thông tin</b></p>
                            <ul class="footer-menu">
                                <li><a href="#" class="text-white">Điều khoản sử dụng</a></li>
                                <li><a href="#" class="text-white">Chính sách riêng tư</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class="content">
                            <p><b>Designed</b></p>
                            <ul class="footer-menu">
                                <li>
                                    <a href="#">
                                        <img src="/assets/img/Chen.jpg" alt="">
                                    </a>
                                    <div class="social-list">
                                        <a href="#" class="social-item">
                                            <i class="bx bxl-facebook" style="color: rgb(14, 6, 138);"></i>
                                        </a>
                                        <a href="#" class="social-item">
                                            <i class="bx bxl-twitter" style="color: turquoise;"></i>
                                        </a>
                                        <a href="#" class="social-item">
                                            <i class="bx bxl-instagram"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
`);