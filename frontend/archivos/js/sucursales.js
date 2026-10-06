// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_SUCURSALES = `${CONFIG.API_URL}/sucursales`;

// --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializar() {

    // --Listado de las sucursales registradas en la tabla de la vista.
    listarSucursales();

    // FILTRAR SUCURSAL
    // --Busqueda de la sucursal por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaSucursales tr:not(:first-child)");
        fila.forEach(f => {
            const nombre = f.cells[1].textContent.toLowerCase();
            f.style.display = nombre.includes(filtro) ? "" : "none";
        })
    })
    
    // CREAR SUCURSAL
    // --Proceso de creacion de una nueva sucursal desde el boton "Añadir".
    document.getElementById("formSucursal").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idSucursal").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_SUCURSALES + `/${id}` : API_SUCURSALES

        // --Captura de datos del formulario.
        const datos = {
            nombreSucursal: document.getElementById("nombreSucursal").value,
            direccionSucursal: document.getElementById("direccionSucursal").value,
            correoSucursal: document.getElementById("correoSucursal").value,
            telefonoSucursal: document.getElementById("telefonoSucursal").value
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModal();
            listarSucursales();
        }

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE SUCURSALES
// --Proceso encargado de acceder al endpoint, buscar y listar las sucursales existentes.
async function listarSucursales() {
    const respuesta = await fetch(API_SUCURSALES);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaSucursales");
    tabla.innerHTML = `
        <tr>
            <th class="center">Id</th>
            <th class="left">Nombre</th>
            <th class="left">Dirección</th>
            <th class="left">Correo</th>
            <th class="left">Teléfono</th>
            <th class="center">Acciones</th>
        </tr>
    `;
    datos.forEach(s => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="center">${s.idSucursal ?? ""}</td>
            <td class="left">${s.nombreSucursal ?? ""}</td>
            <td class="left">${s.direccionSucursal  ?? ""}</td>
            <td class="left">${s.correoSucursal ?? ""}</td>
            <td class="left">${s.telefonoSucursal ?? ""}</td>
            <td class="center">
                <button class="btn_editar" onclick="editarSucursal(${s.idSucursal})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}


// EDITAR SUCURSAL.
// --Proceso de modificacion y acceso a eliminacion de sucursales desde el boton "editar".
async function editarSucursal(id){
    const respuesta = await fetch (API_SUCURSALES + `/${id}`);
    const sucursal = await respuesta.json();

    document.getElementById("idSucursal").value = sucursal.idSucursal;
    document.getElementById("nombreSucursal").value = sucursal.nombreSucursal;
    document.getElementById("direccionSucursal").value = sucursal.direccionSucursal;
    document.getElementById("correoSucursal").value = sucursal.correoSucursal;
    document.getElementById("telefonoSucursal").value = sucursal.telefonoSucursal;

    abrirModal();
}

// ELIMINAR SUCURSAL.
async function eliminarSucursal(){
    if (!confirm("¿Eliminar esta sucursal?")) return;

    const id = document.getElementById("idSucursal").value;
    const respuesta = await fetch(API_SUCURSALES + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModal();
        listarSucursales();
    } else {
           const error = await respuesta.json();
           console.log(error); // verifica la estructura exacta
        alert("Error: " + error.mensaje || "No se pudo eliminar la sucursal");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de sucursal.
function abrirModal() {
    let id = document.getElementById("idSucursal").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (id){
        titulo.textContent = "Edición de sucursal";
        btnEliminar.style.display = "block";
    } else {
        titulo.textContent = "Creación de sucursal";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modalSucursal").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModal() {
    document.getElementById("modalSucursal").style.display = "none";
    document.getElementById("formSucursal").reset();
    document.getElementById("idSucursal").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModal(e){
    if (e.key === 'Escape'){
        cerrarModal();
    }
}

document.addEventListener("keydown", escModal);