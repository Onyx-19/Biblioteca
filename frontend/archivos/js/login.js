if (AUTH.getToken()) {
    window.location.href = "index.html";
}

document.addEventListener("DOMContentLoaded", () => {    
    document.getElementById("btn_login").addEventListener("click", async (e) => {
        e.preventDefault();

        const identificacionUsuario = document.getElementById("user").value;
        const contrasena = document.getElementById("password").value;
    
        try{
            const response = await fetch(LOGIN.API_URL, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ identificacionUsuario, contrasena })
        });
        
        if (!response.ok) throw new Error("Credenciales incorrectas.");
        
        const data = await response.json();
        AUTH.guardar(data);
        
        window.location.href = "index.html";
        } catch (error) {
            alert(error.message);
        }
    });
});