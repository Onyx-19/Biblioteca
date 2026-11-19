// Funcion que permite restablcer la contraseña desde el login
function resetPass() {
    document.getElementById("modalReset").style.display = "flex";

    document.getElementById("formReset").addEventListener("submit", async (e) => {
        e.preventDefault();

        const userReset = document.getElementById("userReset").value;
        const mailReset = document.getElementById("mailReset").value;

        const respuesta = await fetch (`${LOGIN.API_URL}/recuperar`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ 
                identificacionUsuario: userReset, 
                correoUsuario: mailReset 
            })
        });

        if (respuesta.ok) {
            document.getElementById("modalReset").style.display = "none";
            alert ("Contraseña restablecida al valor inicial, puedes iniciar sesión con las credenciales iniciales.");
        } else {
            alert ("Los datos ingresados no coinciden.");
        }
    })
}

function cerrarModalReset(){
    document.getElementById("modalReset").style.display = "none";
    document.getElementById("formReset").reset();
}

// Permite definir si la contraseña esta recien restablecida y determina que campos mostrar en la modal.
function prepararModalPass(){
    const inicial = AUTH.getCambioPass() == true;
    document.getElementById("cierreModalPass").style.display = inicial ? "none" : "";
    document.getElementById("cancelModalPass").style.display = inicial ? "none" : "";
    document.getElementById("campoActual").style.display = inicial ? "none" : "";
    document.getElementById("modalPass").style.display = "flex";
}


// Cambio obligatorio de contraseña al iniciar sesion por primera vez. 
function cambioInicialPass() {
    if (AUTH.getCambioPass() == true) prepararModalPass();

    document.getElementById("formPass").addEventListener("submit", async (e) => {
        e.preventDefault();

        const password = document.getElementById("password").value;
        const confirmacion = document.getElementById("confirmacion").value;
        const actual = document.getElementById("actual").value;
        

        if (password != confirmacion) {
            alert("Las contraseñas no coinden");
            return;
        }

        const inicial = AUTH.getCambioPass() == true;
        const url = inicial ? 
                    `${CONFIG.API_URL}/usuarios/${AUTH.getIdUsuario()}/password` : 
                    `${CONFIG.API_URL}/usuarios/me/contrasena`;
        const metodo = inicial ? "PATCH" : "PUT";
        const body = inicial ? 
                    { contrasena: password } : 
                    { actual: actual, nueva: password };

        const respuesta = await fetch(url, {
            method: metodo,
            headers: AUTH.headers(),
            body: JSON.stringify(body)
        });

        if (respuesta.ok) {
            localStorage.setItem("cambioPassInicial", "false");
            e.target.reset();
            document.getElementById("modalPass").style.display = "none";
            alert("Proceso exitoso.")
        } else {
            alert("Error al cambiar la contraseña");
        };
    });
};


function cerrarModalPass(){
    const modal = document.getElementById("modalPass");
    const form = document.getElementById("formPass");
    if (modal) modal.style.display = "none";
    if (form) form.reset();
}