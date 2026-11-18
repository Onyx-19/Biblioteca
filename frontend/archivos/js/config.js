const CONFIG = {
    API_URL: "http://localhost:8080"
}

const LOGIN = {
    API_URL: "http://localhost:8080/auth"
}

const AUTH = {
    getToken: () => localStorage.getItem("token"),
    getIdUsuario: () => localStorage.getItem("idUsuario"),
    getNombre: () => localStorage.getItem("nombre"),
    getRol: () => localStorage.getItem("rol"),
    getCambioPass: () => localStorage.getItem("cambioPassInicial") === "true",
    guardar: (data) => {
        localStorage.setItem("token", data.token);
        localStorage.setItem("idUsuario", data.idUsuario);
        localStorage.setItem("nombre", data.nombre);
        localStorage.setItem("rol", data.rol);
        localStorage.setItem("cambioPassInicial", data.cambioPassInicial);
    },
    cerrarSesion: () => {
        localStorage.removeItem("token");
        localStorage.removeItem("idUsuario");
        localStorage.removeItem("nombre");
        localStorage.removeItem("rol");
        localStorage.removeItem("cambioPassInicial");
    },
    headers: () => ({
        "Authorization": `Bearer ${AUTH.getToken()}`,
        "Content-Type": "application/json"
    })
}