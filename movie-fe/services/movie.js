var apiClient = new APIClient();

async function getMovieById(movieId) {
    return await apiClient.get(`/api/movies/${movieId}`)
}

