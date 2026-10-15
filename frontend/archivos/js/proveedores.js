// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_PROVEEDORES= `${CONFIG.API_URL}/proveedores`;

// --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarProveedores() {


    // --Listado de los proveedores registrados en la tabla de la vista.
    listarProveedores();


    // FILTRAR PROVEEDORES
    // --Busqueda del proveedor por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaProveedores tr:not(:first-child)");
        fila.forEach(f => {
            const identificacion = f.cells[1].textContent.trim();
            const nombre = f.cells[2].textContent.toLowerCase();
            f.style.display = nombre.includes(filtro) || identificacion.includes(filtro) ? "" : "none";
        })
    })
    
    
    // CREAR PROVEEDORES
    // --Proceso de creacion de un nuevo proveedor desde el boton "Añadir".
    document.getElementById("formProveedor").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idProveedor").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_PROVEEDORES + `/${id}` : API_PROVEEDORES

        // --Captura de datos del formulario.
        const datos = {
            identificacionProveedor: document.getElementById("identificacionProveedor").value,
            nombreProveedor: document.getElementById("nombreProveedor").value,
            correoProveedor: document.getElementById("correoProveedor").value,
            telefonoProveedor: document.getElementById("telefonoProveedor").value,
            direccionProveedor: document.getElementById("direccionProveedor").value,
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalProveedor();
            listarProveedores();
        };

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE PROVEEDORES
// --Proceso encargado de acceder al endpoint, buscar y listar los proveedores existentes.
async function listarProveedores() {
    const respuesta = await fetch(API_PROVEEDORES);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaProveedores");
    tabla.innerHTML = `
        <tr>
            <th class="col_xs">Id</th>
            <th class="col_ml">Identificación</th>
            <th class="col_lg">Nombre</th>
            <th class="col_lg">Correo</th>
            <th class="col_ml">Teléfono</th>
            <th class="col_ml">Dirección</th>
            <th class="col_ms">Acciones</th>           
        </tr>
    `;

    datos.forEach(a => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="col_xs">${a.idProveedor ?? ""}</td>
            <td class="col_ml">${a.identificacionProveedor ?? ""}</td>
            <td class="col_lg">${a.nombreProveedor ?? ""}</td>
            <td class="col_lg">${a.correoProveedor ?? ""}</td>
            <td class="col_ml">${a.telefonoProveedor ?? ""}</td>
            <td class="col_ml">${a.direccionProveedor ?? ""}</td>
            <td class="col_ms">
                <button class="btn_editar" onclick="editarProveedor(${a.idProveedor})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}


// EDITAR PROVEEDOR.
// --Proceso de modificacion y acceso a eliminacion de proveedores desde el boton "editar".
async function editarProveedor(id){
    const respuesta = await fetch (API_PROVEEDORES + `/${id}`);
    const proveedor = await respuesta.json();

    document.getElementById("idProveedor").value = proveedor.idProveedor;
    document.getElementById("identificacionProveedor").value = proveedor.identificacionProveedor;
    document.getElementById("nombreProveedor").value = proveedor.nombreProveedor;
    document.getElementById("correoProveedor").value = proveedor.correoProveedor;
    document.getElementById("telefonoProveedor").value = proveedor.telefonoProveedor;
    document.getElementById("direccionProveedor").value = proveedor.direccionProveedor;

    abrirModalProveedor();
}

// ELIMINAR PROVEEDOR.
async function eliminarProveedor(){
    if (!confirm("¿Eliminar este proveedor?")) return;

    const id = document.getElementById("idProveedor").value;
    const respuesta = await fetch(API_PROVEEDORES + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModalProveedor();
        listarProveedores();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo eliminar el proveedor.");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de proveedores.
function abrirModalProveedor() {
    let id = document.getElementById("idProveedor").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (id){
        titulo.textContent = "Modificación de proveedor";
        btnEliminar.style.display = "block";
    } else {
        titulo.textContent = "Creación de proveedor";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modalProveedor").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalProveedor() {
    document.getElementById("modalProveedor").style.display = "none";
    document.getElementById("formProveedor").reset();
    document.getElementById("idProveedor").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalProveedor(e){
    if (e.key === 'Escape'){
        cerrarModalProveedor();
    }
}

document.addEventListener("keydown", escModalProveedor);