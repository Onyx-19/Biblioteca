if (!AUTH.getToken()) {
    window.location.href = "login.html";
}


// NAVBAR SUCURSALES
async function navbarSucursales(idSelect) {
    // --Accede a el endpoint de sucursales obteniendo los datos.
    const respuesta = await fetch(`${CONFIG.API_URL}/sucursales`, {
        headers: AUTH.headers()
    });
    const sucursales = await respuesta.json();
    
    const select = document.getElementById(idSelect);
    
    // --Lista los datos de sucursales obtenidos previamente.
    sucursales.forEach(s => {
        const option = document.createElement("option");
        option.value = s.idSucursal;
        option.textContent = s.nombreSucursal;
        select.appendChild(option);
    });
}
//NAVBAR USUARIO ACTIVO
async function cargarUsuarioActivo() {
    document.getElementById("usuarioActivo").textContent = AUTH.getNombre();
    document.querySelector(".perfil_rol").textContent = AUTH.getRol();    
}

// --Carga los datos de sucursales en el navbar.
navbarSucursales("selectSucursal");

// MODULOS
// --Accede y renderiza la "vista" del modulo activo en el apartado del contenido.
async function navegar(modulo){
    const respuesta = await fetch(`archivos/vistas/${modulo}.html`, {
        headers: AUTH.headers()
    });
    const html = await respuesta.text();
    
    const contendor = document.getElementById("contenido");
    contendor.innerHTML = html;
    
    // --Ejecuta la función de inicialización alojada en cada "modulo.js" ejecutando procesos automaticos de carga.
    switch (modulo) {
        case "sucursales":
                inicializarSucursales();
            break;
            case "usuarios":
                inicializarUsuarios();
            break;
            case "categorias":
                inicializarCategorias();
            break;
            case "autores":
                inicializarAutores();
            break;
            case "proveedores":
                inicializarProveedores();
            break;
            case "clientes":
                inicializarClientes();
            break;
            case "libros":
                inicializarLibros();
            break;
            case "inventarios":
                inicializarInventarios();
            break;
            case "prestamos":
                inicializarPrestamos();
            break;
        default:
            break;
    }
}
// --Resalta el modulo activo en el navbar para diferenciarlo del resto.
document.addEventListener('DOMContentLoaded', () => {
    const modulos = document.querySelectorAll('.modulo');

    modulos.forEach(modulo => {
        modulo.addEventListener('click', () => {
            modulos.forEach(m => m.classList.remove('active'));
            modulo.classList.add('active');
        });
    });
});


// VALIDACIONES DE USUARIO AUTH
cargarUsuarioActivo();

// Cambio de contraseña obligatorio al iniciar sesion por primera vez.
document.addEventListener("DOMContentLoaded", () => {
    cambioInicialPass();
});

// Proceso que ejecuta el Logout
document.getElementById("btn_logout").addEventListener("click", () => {
    AUTH.cerrarSesion();
    window.location.href = "login.html";
});



// MENU DE USUARIO
// Funcion que habilita la modal para acceder a la modificacion del perfil activo.
async function configPerfil() {
    const respuesta = await fetch (API_USUARIOS + `/me`, {
        headers: AUTH.headers()
    });

    const usuario = await respuesta.json();
    document.getElementById("idPerfil").value = usuario.idUsuario;
    document.getElementById("identificacionPerfil").value = usuario.identificacionUsuario;
    document.getElementById("nombrePerfil").value = usuario.nombreUsuario;
    document.getElementById("correoPerfil").value = usuario.correoUsuario;
    document.getElementById("direccionPerfil").value = usuario.direccionUsuario;
    document.getElementById("telefonoPerfil").value = usuario.telefonoUsuario;

    document.getElementById("modalConfigPerfil").style.display = "flex";
}


// Actualiza los datos en el perfil del usuario activo
document.getElementById("formConfig").addEventListener("submit", async function(e){
    e.preventDefault();

    const metodo = "PUT";
    const url = API_USUARIOS + `/me`;
    // --Captura de datos del formulario.
    const datos = {
        identificacionUsuario: document.getElementById("identificacionPerfil").value,
        nombreUsuario: document.getElementById("nombrePerfil").value,
        correoUsuario: document.getElementById("correoPerfil").value,
        direccionUsuario: document.getElementById("direccionPerfil").value,
        telefonoUsuario: document.getElementById("telefonoPerfil").value,
    };

    // --Envio de los datos al backend.
    const respuesta = await fetch(url, {
        method: metodo,
        headers: AUTH.headers(),
        body: JSON.stringify(datos)
    });

    // --Respuesta si el backend procesa la peticion con exito.
    if(respuesta.ok) {
        cerrarModalConfig();
        alert("Proceso exitoso.")
    }

    // --Respuesta si el backend retorna algun error.
    if (!respuesta.ok) {
        const error = await respuesta.json();
        alert(error.mensaje); // muestra solo el mensaje legible
        return;
    }
});


// Reutiliza la modal del cambio obligatorio de contraseña y permite modificar la contraseña desde la modificacion del perfil activo.
function cambioPassConfig(){
    document.getElementById("modalPass").style.display = "flex";
    prepararModalPass();
}


// MODAL
// --Proceso de cierre de modal configuiracion de perfil.
function cerrarModalConfig() {
    const modal = document.getElementById("modalConfigPerfil");
    const form = document.getElementById("formConfig");
    const modalPass = document.getElementById("modalPass")

    if (modalPass && modalPass.style.display === "flex") return;

    if (modal) modal.style.display = "none";
    if (form) form.reset();
}

// --Utilizar la tecla esc para cerrar la modal configuiracion de perfil.
function escModalConfig(e){
    if (e.key === 'Escape'){
        cerrarModalConfig();
    }
}

document.addEventListener("keydown", escModalConfig);