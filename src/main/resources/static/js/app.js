/* ---------- SEARCH ---------- */

const searchBar =
    document.getElementById("searchBar");

const searchResults =
    document.getElementById("searchResults");

const clearSearch =
    document.getElementById("clearSearch");


let searchTimeout = null;

let currentSearchRequest = 0;


/* ---------- CLEAR BUTTON ---------- */

function updateClearButton() {

    if (searchBar.value.trim() !== "") {

        clearSearch.style.display = "block";

    } else {

        clearSearch.style.display = "none";

    }

}


searchBar.addEventListener(
    "input",
    function() {

        updateClearButton();

        const searchTerm =
            searchBar.value.trim();


        clearTimeout(searchTimeout);


        if (searchTerm === "") {

            searchResults.innerHTML = "";

            searchResults.style.display = "none";

            return;

        }


        searchTimeout =
            setTimeout(
                function() {

                    searchMovies(searchTerm);

                },
                350
            );

    }
);


/* ---------- CLEAR SEARCH ---------- */

clearSearch.addEventListener(
    "click",
    function() {

        searchBar.value = "";

        searchResults.innerHTML = "";

        searchResults.style.display = "none";

        clearSearch.style.display = "none";

        searchBar.focus();

    }
);


/* ---------- ENTER = FULL SEARCH PAGE ---------- */

searchBar.addEventListener(
    "keydown",
    function(event) {

        if (event.key !== "Enter") {
            return;
        }


        const searchTerm =
            searchBar.value.trim();


        if (searchTerm === "") {
            return;
        }


        window.location.href =
            `/search?query=${encodeURIComponent(searchTerm)}`;

    }
);


/* ---------- LIVE SEARCH ---------- */

async function searchMovies(searchTerm) {

    const requestNumber =
        ++currentSearchRequest;


    searchResults.innerHTML =
        "<p class='search-message'>Searching...</p>";

    searchResults.style.display =
        "block";


    const url =
        `/api/movies/search?query=${encodeURIComponent(searchTerm)}`;


    try {

        const response =
            await fetch(url);


        if (!response.ok) {

            throw new Error(
                `Search failed with status ${response.status}`
            );

        }


        const data =
            await response.json();


        if (
            requestNumber !== currentSearchRequest
        ) {

            return;

        }


        displaySuggestions(
            data.results
        );


    } catch (error) {

        console.error(
            "Live search failed:",
            error
        );


        if (
            requestNumber === currentSearchRequest
        ) {

            searchResults.innerHTML =
                "<p class='search-message'>Unable to search right now.</p>";

        }

    }

}


/* ---------- DISPLAY TOP 5 SUGGESTIONS ---------- */

function displaySuggestions(movies) {

    searchResults.innerHTML = "";


    if (
        !movies ||
        movies.length === 0
    ) {

        searchResults.innerHTML =
            "<p class='search-message'>No movies found.</p>";

        return;

    }


    const topMovies =
        movies
            .sort(
                function(a, b) {

                    return (
                        (b.popularity || 0)
                        -
                        (a.popularity || 0)
                    );

                }
            )
            .slice(0, 5);


    topMovies.forEach(
        function(movie) {

            const movieCard =
                document.createElement("div");


            movieCard.classList.add(
                "search-suggestion"
            );


            const poster =
                movie.poster_path
                    ? `https://image.tmdb.org/t/p/w185${movie.poster_path}`
                    : null;


            const year =
                movie.release_date
                    ? movie.release_date.substring(0, 4)
                    : "Unknown";


            movieCard.innerHTML = `

                ${
                    poster
                        ? `
                            <img
                                src="${poster}"
                                alt="${movie.title}">
                          `
                        : `
                            <div class="suggestion-no-poster">
                                No Poster
                            </div>
                          `
                }


                <div class="suggestion-info">

                    <h3>
                        ${movie.title}
                    </h3>

                    <p>
                        ${year}
                    </p>

                    <p>
                        ⭐ ${
                            movie.vote_average
                                ? movie.vote_average.toFixed(1)
                                : "N/A"
                        }
                    </p>

                </div>

            `;


            movieCard.addEventListener(
                "click",
                function() {

                    window.location.href =
                        `/movie/${movie.id}`;

                }
            );


            searchResults.appendChild(
                movieCard
            );

        }
    );

}


/* ---------- TOP 10 MOVING POSTERS ---------- */

const movies = [

    "Movie 1",

    "Movie 2",

    "Movie 3",

    "Movie 4",

    "Movie 5",

    "Movie 6",

    "Movie 7",

    "Movie 8",

    "Movie 9",

    "Movie 10"

];


let currentMovie = 0;

let moving = true;


const poster =
    document.getElementById("top10Poster");

const posterNumber =
    document.getElementById("posterNumber");


function showMovie() {

    poster.textContent =
        movies[currentMovie];


    posterNumber.textContent =
        (currentMovie + 1)
        + " / "
        + movies.length;

}


function nextMovie() {

    if (!moving) {
        return;
    }


    currentMovie++;


    if (
        currentMovie >= movies.length
    ) {

        currentMovie = 0;

    }


    showMovie();

}


poster.addEventListener(
    "click",
    function() {

        moving = !moving;

    }
);


showMovie();


setInterval(
    nextMovie,
    7000
);