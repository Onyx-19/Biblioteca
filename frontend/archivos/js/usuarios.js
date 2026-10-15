// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_USUARIOS = `${CONFIG.API_URL}/usuarios`;

// // --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarUsuarios() {

    // --Listado de usuarios registradas en la tabla de la vista.
    listarUsuarios();

    // 
    selectRoles();

    // -- Llama las sucursales disponibles para ser manipuladas.
    selectSucursales("sucursalUsuario")


    // FILTRAR USUARIOS
    // --Busqueda del usuario por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaUsuarios tr:not(:first-child)");
        fila.forEach(f => {
            const identificacion = f.cells[1].textContent.trim();
            const nombre = f.cells[2].textContent.toLowerCase();
            f.style.display = nombre.includes(filtro) || identificacion.includes(filtro) ? "" : "none";
        })
    })
    

    // CREAR USUARIO
    // --Proceso de creacion de un nueva usuario desde el boton "Añadir".
    document.getElementById("formUsuario").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idUsuario").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_USUARIOS + `/${id}` : API_USUARIOS

        // --Captura de datos del formulario.
        const datos = {
            identificacionUsuario: document.getElementById("identificacionUsuario").value,
            nombreUsuario: document.getElementById("nombreUsuario").value,
            correoUsuario: document.getElementById("correoUsuario").value,
            direccionUsuario: document.getElementById("direccionUsuario").value,
            telefonoUsuario: document.getElementById("telefonoUsuario").value,
            rolUsuario: document.getElementById("rolUsuario").value,
            idSucursal: parseInt(document.getElementById("sucursalUsuario").value)
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalUsuario();
            listarUsuarios();
        }

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE USUARIOS
// --Proceso encargado de acceder al endpoint, buscar y listar los usuarios existentes.
async function listarUsuarios() {
    const respuesta = await fetch(API_USUARIOS);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaUsuarios");
    tabla.innerHTML = `
        <tr>
            <th class="col_xs">Id</th>
            <th class="col_ml">Identificación</th>
            <th class="col_lm">Nombre</th>
            <th class="col_lg">Correo</th>
            <th class="col_ml">Rol</th>
            <th class="col_ml">Teléfono</th>
            <th class="col_ml">Dirección</th>
            <th class="col_ml">Sucursal</th>
            <th class="col_ms">Acciones</th>
        </tr>
    `;
    datos.forEach(u => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="col_xs">${u.idUsuario ?? ""}</td>
            <td class="col_ml">${u.identificacionUsuario ?? ""}</td>
            <td class="col_lm">${u.nombreUsuario ?? ""}</td>
            <td class="col_lg">${u.correoUsuario ?? ""}</td>
            <td class="col_ml">${u.rolUsuario ?? ""}</td>
            <td class="col_ml">${u.telefonoUsuario ?? ""}</td>
            <td class="col_ml">${u.direccionUsuario ?? ""}</td>
            <td class="col_ml">${u.nombreSucursal ?? ""}</td>
            <td class="col_ms">
                <button class="btn_editar" onclick="editarUsuario(${u.idUsuario})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}


// EDITAR USUARIO.
// --Proceso de modificacion y acceso a eliminacion de usuarios desde el boton "editar".
async function editarUsuario(id){
    const respuesta = await fetch (API_USUARIOS + `/${id}`);
    const usuario = await respuesta.json();

    document.getElementById("idUsuario").value = usuario.idUsuario;
    document.getElementById("identificacionUsuario").value = usuario.identificacionUsuario;
    document.getElementById("nombreUsuario").value = usuario.nombreUsuario;
    document.getElementById("correoUsuario").value = usuario.correoUsuario;
    document.getElementById("direccionUsuario").value = usuario.direccionUsuario;
    document.getElementById("telefonoUsuario").value = usuario.telefonoUsuario;
    document.getElementById("rolUsuario").value = usuario.rolUsuario;
    document.getElementById("sucursalUsuario").value = usuario.idSucursal;

    rolOriginal = usuario.rolUsuario;
    const selectRol = document.getElementById("rolUsuario");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (usuario.rolUsuario === "Administrador") {
        btnEliminar.style.display = "none";
        selectRol.innerHTML = `<option value="Administrador">Administrador</option>`;
        selectRol.value = "Administrador";
        selectRol.disabled = true;
    } else {
        btnEliminar.style.display = "";
        document.getElementById("rolUsuario").value = "";
        selectRoles();
        selectRol.disabled = false;
        selectRol.value = usuario.rolUsuario;
    }

    abrirModalUsuario();
}


// ELIMINAR USUARIO.
async function eliminarUsuario(){
    if (!confirm("¿Eliminar este usuario?")) return;

    const id = document.getElementById("idUsuario").value;
    const respuesta = await fetch(API_USUARIOS + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModalUsuario();
        listarUsuarios();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo eliminar el usuario");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de usuario.
function abrirModalUsuario() {
    const id = document.getElementById("idUsuario").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (id){
        titulo.textContent = "Modificación de usuarios";
        btnEliminar.style.display = "block";
        if(id == 1){
            btnEliminar.style.display = "none";
        }
    } else {
        titulo.textContent = "Creación de usuarios";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modal").style.display = "flex";
}

// --Lista los roles disponibles en la creacion y modificacion de usuario.
function selectRoles(){
    const roles = ["Manager", "Empleado"];
    const select = document.getElementById("rolUsuario");
    select.innerHTML = '<option value="">-- Selecciona el rol --</option>';

    roles.forEach(rol => {
        const option = document.createElement("option");
        option.value = rol;
        option.textContent = rol;
        select.appendChild(option);
    })
}

// --Lista las sucursales disponibles en la creacion y modificacion del usuario.
function selectSucursales(idSelect){
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">-- Selecciona sucursal --</option>';
    // --Accede a las sucursales disponibles en index.js.
    navbarSucursales("sucursalUsuario");   
}

// --Proceso de cierre de modal.
function cerrarModalUsuario() {
    document.getElementById("modal").style.display = "none";
    document.getElementById("formUsuario").reset();
    document.getElementById("idUsuario").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalUsuarios(e){
    if (e.key === 'Escape'){
        cerrarModalUsuario();
    }
}

document.addEventListener("keydown", escModalUsuarios);













// Hacer un eliminacion de usuarios logica, desactivacion u ocultacion de usuario.