$(function () {
    const urlParams = new URLSearchParams(window.location.search);
    var page = new Page(urlParams.get("id"));
    var apiDomain = "http://207.148.68.60:81";

    function Page(movieId) {
        let movie;

        this.init = async function () {
            await this.load();
            this.render();
        };

        this.load = async function () {
            movie = await getMovieById(movieId);
        };

        function refreshCarousel(name) {
            let navText = ["<i class='bx bx-chevron-left'></i>", "<i class='bx bx-chevron-right'></i>"];
            $(name).owlCarousel({
                items: 3,
                dots: false,
                nav: true,
                navText,
                margin: 10,
                responsive: {
                    500: {
                        items: 2,
                    },
                    1280: {
                        items: 4,
                    },
                    1600: {
                        items: 6,
                    },
                },
            });
        }

        this.render = function () {
            console.log(movie);
            document.getElementById("moviePoster").src = apiDomain + movie.backgroundUrl;
            document.getElementById("movieBg").src = apiDomain + movie.posterUrl;
            document.getElementById("descriptionContent").innerHTML = movie.description;
            document.getElementsByClassName("title1")[0].innerHTML = movie.name;
            document.getElementsByClassName("title2")[0].innerHTML = movie.nameInVietNamese;
            document.getElementById("movieImdb").innerHTML = movie.iMDB;
            document.getElementById("watchBtn").setAttribute("href", `../watch/watch.html?id=${movie.id}`)
            refreshCarousel("#trailerCarousel");
        };

        this.refresh = function () {};
    }

    page.init();
});
