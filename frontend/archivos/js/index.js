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

cargarUsuarioActivo();

document.getElementById("btn_logout").addEventListener("click", () => {
    AUTH.cerrarSesion();
    window.location.href = "login.html";
});

document.addEventListener("DOMContentLoaded", () => {
    cambioInicialPass();
});

// function modalCambioPass() {
//     const id = document.getElementById("idUsuario").value;
//     const titulo = document.querySelector(".form_header_h3");
//     const btnEliminar = document.querySelector(".btn_eliminar");

//     if (id){
//         titulo.textContent = "Modificación de usuarios";
//         btnEliminar.style.display = "block";
//         if(id == 1){
//             btnEliminar.style.display = "none";
//         }
//     } else {
//         selectRoles();
//         titulo.textContent = "Creación de usuarios";
//         btnEliminar.style.display = "none";
//     }

//     document.getElementById("modalUsuario").style.display = "flex";
// }

// // --Proceso de cierre de modal.
// function cerrarModalUsuario() {
//     const modal = document.getElementById("modalUsuario");
//     const form = document.getElementById("formUsuario");
//     if (modal) modal.style.display = "none";
//     if (form) form.reset();
// }

// // --Utilizar la tecla esc para cerrar la modal.
// function escModalUsuarios(e){
//     if (e.key === 'Escape'){
//         cerrarModalUsuario();
//     }
// }

// document.addEventListener("keydown", escModalUsuarios);