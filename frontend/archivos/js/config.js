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
    guardar: (data) => {
        localStorage.setItem("token", data.token);
        localStorage.setItem("idUsuario", data.idUsuario);
        localStorage.setItem("nombre", data.nombre);
        localStorage.setItem("rol", data.rol);
    },
    cerrarSesion: () => {
        localStorage.removeItem("token");
        localStorage.removeItem("idUsuario");
        localStorage.removeItem("nombre");
        localStorage.removeItem("rol");
    },
    headers: () => ({
        "Authorization": `Bearer ${AUTH.getToken()}`,
        "Content-Type": "application/json"
    })
}