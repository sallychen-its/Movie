$(function () {
    const urlParams = new URLSearchParams(window.location.search);
    var page = new Page(urlParams.get("category"));
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

        this.render = function () {
            console.log(movie);
            document.getElementsByClassName("item-watch-title")[0].innerHTML = movie.name;
            document.getElementsByClassName("imdb")[0].children[1].innerHTML = movie.iMDB;
            document.getElementsByClassName("time")[0].children[1].innerHTML = movie.time + " mins";
            document.getElementsByClassName("item-content-watch")[0].innerHTML = movie.description;

            let video = document.getElementById('video');
            let source = document.createElement('source');
            let videoName = movie.movieUrl.replace("/content/upload-dir/", "");

            source.setAttribute('src', `${apiDomain}/movie-admin/api/media/video/1639504559511_video_1.mp4`);
            source.setAttribute('type', 'video/mp4');
            video.appendChild(source);
        };

        this.refresh = function () {};
    }

    page.init();
});


function get(name){
    if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
       return decodeURIComponent(name[1]);
 }


 let url = "http://207.148.68.60:81/movie-admin/api/categories/";

 if (!get('category')) {
    url = "http://207.148.68.60:81/movie-admin/api/movies";
 } else {
    url += get('category');
 }

axios.get(url)
    .then(function (response) {
    if (!get('category')) {
        for (let index = 0; index < response.data.length; index++) {
            document.getElementById("movies").insertAdjacentHTML('beforeend', `<div class="grid-column-2 ">
                <!-- product-item-->
                <a href="#" class="movie-item">
                    <img src="http://207.148.68.60:81${response.data[index].posterUrl}" alt="">
                    <div class="movie-item-content">
                        <div class="movie-item-title">
                            ${response.data[index].name}
                        </div>
                        <div class="movie-infos">
                            <div class="movie-info imdb">
                                <i class="bx bxs-star"></i>
                                <span>${response.data[index].iMDB}</span>
                            </div>
                            <div class="movie-info time">
                                <i class="bx bxs-time"></i>
                                <span>${response.data[index].time} mins</span>
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
            </div>`);
        }
    } else {
        for (let index = 0; index < response.data.movies.length; index++) {
            document.getElementById("movies").insertAdjacentHTML('beforeend', `<div class="grid-column-2 ">
                <!-- product-item-->
                <a href="#" class="movie-item">
                    <img src="http://207.148.68.60:81${response.data.movies[index].posterUrl}" alt="">
                    <div class="movie-item-content">
                        <div class="movie-item-title">
                            ${response.data.movies[index].name}
                        </div>
                        <div class="movie-infos">
                            <div class="movie-info imdb">
                                <i class="bx bxs-star"></i>
                                <span>${response.data.movies[index].iMDB}</span>
                            </div>
                            <div class="movie-info time">
                                <i class="bx bxs-time"></i>
                                <span>${response.data.movies[index].time} mins</span>
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
            </div>`);
        }
    }


});
