// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_CLIENTES= `${CONFIG.API_URL}/clientes`;

// --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarClientes() {


    // --Listado de los clientes registrados en la tabla de la vista.
    listarClientes();


    // FILTRAR CLIENTES
    // --Busqueda del cliente por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaClientes tr:not(:first-child)");
        fila.forEach(f => {
            const identificacion = f.cells[1].textContent.trim();
            const nombre = f.cells[2].textContent.toLowerCase();
            f.style.display = nombre.includes(filtro) || identificacion.includes(filtro) ? "" : "none";
        })
    })
    
    
    // CREAR CLIENTES
    // --Proceso de creacion de un nuevo cliente desde el boton "Añadir".
    document.getElementById("formCliente").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idCliente").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_CLIENTES + `/${id}` : API_CLIENTES

        // --Captura de datos del formulario.
        const datos = {
            identificacionCliente: document.getElementById("identificacionCliente").value,
            nombreCliente: document.getElementById("nombreCliente").value,
            correoCliente: document.getElementById("correoCliente").value,
            telefonoCliente: document.getElementById("telefonoCliente").value,
            direccionCliente: document.getElementById("direccionCliente").value,
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalCliente();
            listarClientes();
        };

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE CLIENTES
// --Proceso encargado de acceder al endpoint, buscar y listar los clientes existentes.
async function listarClientes() {
    const respuesta = await fetch(API_CLIENTES);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaClientes");
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
            <td class="col_xs">${a.idCliente ?? ""}</td>
            <td class="col_ml">${a.identificacionCliente ?? ""}</td>
            <td class="col_lg">${a.nombreCliente ?? ""}</td>
            <td class="col_lg">${a.correoCliente ?? ""}</td>
            <td class="col_ml">${a.telefonoCliente ?? ""}</td>
            <td class="col_ml">${a.direccionCliente ?? ""}</td>
            <td class="col_ms">
                <button class="btn_editar" onclick="editarCliente(${a.idCliente})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}


// EDITAR CLIENTE.
// --Proceso de modificacion y acceso a eliminacion de clientes desde el boton "editar".
async function editarCliente(id){
    const respuesta = await fetch (API_CLIENTES + `/${id}`);
    const cliente = await respuesta.json();

    document.getElementById("idCliente").value = cliente.idCliente;
    document.getElementById("identificacionCliente").value = cliente.identificacionCliente;
    document.getElementById("nombreCliente").value = cliente.nombreCliente;
    document.getElementById("correoCliente").value = cliente.correoCliente;
    document.getElementById("telefonoCliente").value = cliente.telefonoCliente;
    document.getElementById("direccionCliente").value = cliente.direccionCliente;

    abrirModalCliente();
}

// ELIMINAR CLIENTE.
async function eliminarCliente(){
    if (!confirm("¿Eliminar este cliente?")) return;

    const id = document.getElementById("idCliente").value;
    const respuesta = await fetch(API_CLIENTES + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModalCliente();
        listarClientes();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo eliminar el cliente.");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de clientes.
function abrirModalCliente() {
    let id = document.getElementById("idCliente").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (id){
        titulo.textContent = "Modificación de cliente";
        btnEliminar.style.display = "block";
    } else {
        titulo.textContent = "Creación de cliente";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modalCliente").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalCliente() {
    document.getElementById("modalCliente").style.display = "none";
    document.getElementById("formCliente").reset();
    document.getElementById("idCliente").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalCliente(e){
    if (e.key === 'Escape'){
        cerrarModalCliente();
    }
}

document.addEventListener("keydown", escModalCliente);