function cambioInicialPass() {
    document.getElementById("user").value = AUTH.getNombre();
    document.getElementById("user").disabled = true;

    if(AUTH.getCambioPass() == true) {
        document.getElementById("modalPass").style.display = "flex";
    }

    document.getElementById("formPass").addEventListener("submit", async (e) => {
        e.preventDefault();

        const password = document.getElementById("password").value;
        const confirmacion = document.getElementById("confirmacion").value;

        if (password != confirmacion) {
            alert("Las contraseñas no coinden");
            return;
        }

        const respuesta = await fetch(`${CONFIG.API_URL}/usuarios/${AUTH.getIdUsuario()}/password`, {
            method: "PATCH",
            headers: AUTH.headers(),
            body: JSON.stringify({ contrasena: password })
        });

        if (respuesta.ok) {
            localStorage.setItem("cambioPassInicial", "false");
            document.getElementById("modalPass").style.display = "none";
        } else {
            alert("Error al cambiar la contraseña");
        };
    });
};


function resetPass() {
    // document.getElementById("reset_pass").addEventListener("click", (e) => {
    //     e.preventDefault();
        document.getElementById("modalReset").style.display = "flex";
    // })

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
            alert ("Contraseña restablecida al valor inicial, ya puedes iniciar sesión.");
            document.getElementById("modalReset").style.display = "none";
            document.getElementById("formReset").reset();
        } else {
            alert ("Los datos ingresados no coinciden.");
        }
    })
}

    // return localStorage.getItem("cambioPassInicial") === "true";
// }
