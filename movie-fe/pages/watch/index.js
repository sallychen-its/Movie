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
