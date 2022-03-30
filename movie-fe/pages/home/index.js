$(document).ready(() => {
    $('#hamburger-menu').click(() => {
        $('#hamburger-menu').toggleClass('active')
        $('#hamburger-menu-item').toggleClass('active')
    })
})


//
$(function () {

    var page = new Page();
    var apiDomain = "http://207.148.68.60:81";
    
    function Page() {
    
        var data;
        var carouselFeatureMoviesComponent = new CarouselFeatureMoviesComponent();
        var carouselMovieNominationsComponent = new CarouselMovieNominationsComponent();
        var carouselNewUpdatedMoviesOddComponent = new CarouselNewUpdatedMoviesOddComponent();
        var carouselNewUpdatedMoviesSeriesComponent = new CarouselNewUpdatedMoviesSeriesComponent();
    
        this.init = async function() {
            await this.load();
            carouselFeatureMoviesComponent.init();
            carouselMovieNominationsComponent.init();
            carouselNewUpdatedMoviesOddComponent.init();
            carouselNewUpdatedMoviesSeriesComponent.init();
        }

        function refreshCarousel(name) {
            let navText = ["<i class='bx bx-chevron-left'></i>", "<i class='bx bx-chevron-right'></i>"]
            $(name).owlCarousel({
                items: 5,
                dots: false,
                nav: true,
                navText,
                margin: 15,
                responsive: {
                    500: {
                        items: 2
                    },
                    1280: {
                        items: 4
                    },
                    1600: {
                        items: 6
                    }
                }
        
            })
        }
    
        this.render = function() {}
    
        this.load = async function() {
            data = await getAllCategories();
        }
    
        this.refresh = function() {}
        
        function CarouselFeatureMoviesComponent() {

            let featureMoviesData
            let navText = ["<i class='bx bx-chevron-left'></i>", "<i class='bx bx-chevron-right'></i>"]
        
            this.init = function () {
                this.load();
                this.render();
            }
        
            this.render = function () {
             
                for (let index = 0; index < 3; index++) {
                    document.getElementById("featuredMoviesBg").insertAdjacentHTML('beforeend',`
                        <div class="hero-slide-item slide-bg">
                            <img src="${apiDomain}${featureMoviesData[index].backgroundUrl}" alt="">
                            <div class="overlay"></div>
                            <div id="featuredMoviesBg" class="hero-slide-item-content">
                                <div class="item-content-wraper">
                                    <div class="item-content-title top-down">
                                        ${featureMoviesData[index].name}
                                    </div>
                                    <div class="movie-infos top-down delay-2">
                                        <div class="movie-info imdb">
                                            <i class="bx bxs-star"></i>
                                            <span>${featureMoviesData[index].iMDB}</span>
                                        </div>
                                        <div class="movie-info time">
                                            <i class="bx bxs-time"></i>
                                            <span>${featureMoviesData[index].time} mins</span>
                                        </div>
                                        <div class="movie-info">
                                            <span>HD</span>
                                        </div>
                                        <div class="movie-info">
                                            <span>16+</span>
                                        </div>
                                    </div>
                                    <div class="item-content-description top-down delay-4">
                                        ${featureMoviesData[index].description}
                                    </div>
                                    <div class="item-action top-down delay-6">
                                        <a href="../detail/index.html?id=${featureMoviesData[index].id}" class="header__navbar-item header__navbar-item--bold">
                                            <i class="bx bxs-right-arrow"></i>
                                            <span>Watch now</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `);
                }

        
                $('#featuredMoviesBg').owlCarousel({
                    items: 1,
                    dots: false,
                    loop: true,
                    navText,
                    nav: true,
                    autoplay: true,
                    autoplayHoverPause: true
                })

                for(let i = 3; i < featureMoviesData.length; i++) {
                    document.getElementById("top-movies-slide").insertAdjacentHTML('beforeend',`
                        <a href="../detail/index.html?id=${featureMoviesData[i].id}" class="movie-item">
                            <img src="${apiDomain}${featureMoviesData[i].posterUrl}" alt="">
                            <div class="movie-item-content">
                                <div class="movie-item-title">
                                    ${featureMoviesData[i].name}
                                </div>
                                <div class="movie-infos">
                                    <div class="movie-info">
                                        <i class="bx bxs-star"></i>
                                        <span>${featureMoviesData[i].iMDB}</span>
                                    </div>
                                    <div class="movie-info">
                                        <i class="bx bxs-time"></i>
                                        <span>${featureMoviesData[i].time} mins</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>HD</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>16+</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    `);
                }

                $('#top-movies-slide').owlCarousel({
                    items: 2,
                    dots: false,
                    loop: true,
                    navText,
                    autoplay: true,
                    autoplayHoverPause: true,
                    responsive: {
                        500: {
                            items: 3
                        },
                        1280: {
                            items: 4
                        },
                        1600: {
                            items: 6
                        }
                    }
                })
            }
        
            this.load = async function() {
                featureMoviesData = data[0].movies;
            }
        
            this.refresh = function () {}
        }

        function CarouselMovieNominationsComponent() {

            let movieNominationsData;

            this.init = function () {
                this.load();
                this.render();
            }

            this.render = async function() {
                for(let i = 0; i < movieNominationsData.length; i++) {
                    document.getElementById("movieNomination").insertAdjacentHTML('beforeend',`
                        <a href="../detail/index.html?id=${movieNominationsData[i].id}" class="movie-item">
                            <img src="${apiDomain}${movieNominationsData[i].posterUrl}" alt="">
                            <div class="movie-item-content">
                                <div class="movie-item-title">
                                    ${movieNominationsData[i].name}
                                </div>
                                <div class="movie-infos">
                                    <div class="movie-info">
                                        <i class="bx bxs-star"></i>
                                        <span>${movieNominationsData[i].iMDB}</span>
                                    </div>
                                    <div class="movie-info">
                                        <i class="bx bxs-time"></i>
                                        <span>${movieNominationsData[i].time} mins</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>HD</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>16+</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    `);
                }

                refreshCarousel("#movieNomination");
            }

            this.load = async function() {
                movieNominationsData = data[1].movies;
            }
        
            this.refresh = function () {}
        }

        function CarouselNewUpdatedMoviesOddComponent() {

            let newUpdatedMoviesOddData;

            this.init = function () {
                this.load();
                this.render();
            }

            this.render = async function() {
                for(let i = 0; i < newUpdatedMoviesOddData.length; i++) {
                    document.getElementById("newUpdatedMoviesOdd").insertAdjacentHTML('beforeend',`
                        <a href="../detail/index.html?id=${newUpdatedMoviesOddData[i].id}" class="movie-item">
                            <img src="${apiDomain}${newUpdatedMoviesOddData[i].posterUrl}" alt="">
                            <div class="movie-item-content">
                                <div class="movie-item-title">
                                    ${newUpdatedMoviesOddData[i].name}
                                </div>
                                <div class="movie-infos">
                                    <div class="movie-info">
                                        <i class="bx bxs-star"></i>
                                        <span>${newUpdatedMoviesOddData[i].iMDB}</span>
                                    </div>
                                    <div class="movie-info">
                                        <i class="bx bxs-time"></i>
                                        <span>${newUpdatedMoviesOddData[i].time} mins</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>HD</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>16+</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    `);
                }

                refreshCarousel("#newUpdatedMoviesOdd");
            }

            this.load = async function() {
                newUpdatedMoviesOddData = data[2].movies;
            }
        
            this.refresh = function () {
                this.render();
            }
        }

        function CarouselNewUpdatedMoviesSeriesComponent() {

            let newUpdatedMoviesSeriesData;

            this.init = function () {
                this.load();
                this.render();
            }

            this.render = async function() {
                
                for(let i = 0; i < newUpdatedMoviesSeriesData.length; i++) {
                    document.getElementById("newUpdatedMoviesSeries").insertAdjacentHTML('beforeend',`
                        <a href="../detail/index.html?id=${newUpdatedMoviesSeriesData[i].id}" class="movie-item">
                            <img src="${apiDomain}${newUpdatedMoviesSeriesData[i].posterUrl}" alt="">
                            <div class="movie-item-content">
                                <div class="movie-item-title">
                                    ${newUpdatedMoviesSeriesData[i].name}
                                </div>
                                <div class="movie-infos">
                                    <div class="movie-info">
                                        <i class="bx bxs-star"></i>
                                        <span>${newUpdatedMoviesSeriesData[i].iMDB}</span>
                                    </div>
                                    <div class="movie-info">
                                        <i class="bx bxs-time"></i>
                                        <span>${newUpdatedMoviesSeriesData[i].time} mins</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>HD</span>
                                    </div>
                                    <div class="movie-info">
                                        <span>16+</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    `);
                }

                refreshCarousel("#newUpdatedMoviesSeries");
            }

            this.load = async function() {
                newUpdatedMoviesSeriesData = data[3].movies;
            }
        
            this.refresh = function () {
                this.render();
            }
        }
    }
    
    page.init();
})
