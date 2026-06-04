const API_BASE_URL = "http://localhost:8080";

async function getQuote() {
    try {
        const response = await fetch(
            `${API_BASE_URL}/quote`
        );

        if (!response.ok) {
            throw new Error("API error");
        }

        const data = await response.json();

        document.getElementById("quote").innerText =
            data.quote;

    } catch (error) {

        document.getElementById("quote").innerText =
            "Unable to fetch quote.";
    }
}

async function loadVersion() {

    try {

        const response = await fetch(
            `${API_BASE_URL}/version`
        );

        const data = await response.json();

        document.getElementById("version").innerText =
            data.version;

    } catch (error) {

        document.getElementById("version").innerText =
            "Unavailable";
    }
}

async function loadHealth() {

    try {

        const response = await fetch(
            `${API_BASE_URL}/health`
        );

        const data = await response.json();

        document.getElementById("status").innerText =
            data.status;

    } catch (error) {

        document.getElementById("status").innerText =
            "DOWN";
    }
}

window.onload = () => {
    loadVersion();
    loadHealth();
};