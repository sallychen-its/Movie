async function getAllCategories() {
    return await axios.get("/api/categories");
}
