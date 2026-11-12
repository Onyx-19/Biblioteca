// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_PRESTAMOS = `${CONFIG.API_URL}/prestamos`;

// // // --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarPrestamos() {
    
    // CREAR PRESTAMO
    // --Proceso de creacion de un nuevo prestamo desde el boton "Añadir".
    document.getElementById("formPrestamo").addEventListener("submit", async function(e){
        e.preventDefault();

        const metodo = "POST";
        const url = API_PRESTAMOS;

        // Lista los items agrupandolos en un array de objetos para ser enviado al back.
        const items = [];
        document.querySelectorAll(".fila_item").forEach(i => {
            items.push({
                idLibro: Number(i.querySelector(".libro_item").value),
                unidades: Number(i.querySelector(".unidades_item").value)                
            });
        });

        // --Captura de datos del formulario.
        const datos = {
            // Eliminar
                motivoMovimiento: "PRESTAMO",
                fechaRegistro: "2025-09-22T14:46:22.8828816",
                estadoPrestamo: "ACTIVO",


            idCliente: document.getElementById("clienteMovimiento").value,
            idSucursal: document.getElementById("sucursalDocumento").value,
            idUsuario: document.getElementById("usuarioDocumento").value,
            items: items
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalPrestamo();
        };

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};



//CAPTURA DE MOVIMIENTOS
async function encabezadoDocumentoPrestamo(){
    const tableSelectPrestamo = document.getElementById("tableSelectPrestamo");
    
    tableSelectPrestamo.innerHTML = `
        <tr class="input_cont">
            <th class="col_lm">Cliente</th>
        </tr>
        <tr class="input_cont">
            <td class="col_lm">
                <select id="clienteMovimiento" class="campo_formulario v2"></select>
            </td>
        </tr>
    `
}


// Captura los datos de asignacion del prestamo.
async function asignacionDocumentoPrestamo() {    
    let sucursal = document.getElementById("selectSucursal");
    let nombreSucursal = sucursal.options[sucursal.selectedIndex].text;
    let idSucursal = sucursal.value;
    let idUsuario = await cargarUsuarioActivo()

    let fechaDevolucion = new Date();
    fechaDevolucion.setDate(fechaDevolucion.getDate() + 15);
    fechaDevolucion = fechaDevolucion.toISOString().split("T")[0];
    
    let table = document.getElementById("tableDocumento");
    table.innerHTML = `
        <tr class="input_cont fila_encabezado">
            <td class="col_ms">
                <p>Sucursal:</p>
            </td>
            <td class="col_lg">
                <input hidden disabled id="sucursalDocumento" value="${idSucursal}">
                <input hidden disabled id="usuarioDocumento" value="${idUsuario}">
                <p class="campo_formulario v2">${nombreSucursal}</p>
            </td>
            <td class="col_ml">
                <p>Fecha de entrega:</p>
            </td>
            <td class="col_lm">
                <p class="campo_formulario v2">${fechaDevolucion}</p>
            </td>
        </tr>
    `;
}

// // Permite listar los items relacionados en el prestamo. Tambien agrega nuevos items a la lista.
function listarItemsPrestamo(){
    const tBody = document.getElementById("registroPrestamo");
    tBody.insertAdjacentHTML("beforeend", `
        <tr class="input_cont fila_item">
            <td class="col_lm">
                <select class="campo_formulario v2 libro_item" required></select>
            </td>
            <td class="col_xs">
                <input type="number" min="1" max="3" class="campo_formulario v2 unidades_item" placeholder="unidades" required>
            </td>
            <td class="col_xs">
                <button id="nuevoLibro" class="btn_annadir v2" type="button" onclick="adicionItemPrestamo()">
                    <i class="bi bi-plus"></i>
                </button>
            </td>
            <td class="col_xs">
                <button type="button" class="btn_eliminar v2" onclick="eliminarItemPrestamo(this)">
                    <i class="bi bi-dash"></i>
                </button>
            </td>
        </tr>
    `);

    const select = tBody.lastElementChild.querySelector(".libro_item");
    selectLibrosPrestamo(select);
}

// funciona simple ciclica para añadir items interactuando con el boton +.
function adicionItemPrestamo(){
    listarItemsPrestamo();
} 

// funcion que permite eliminar items de la lista del documento.
function eliminarItemPrestamo(boton) {
    const filas = document.querySelectorAll(".fila_item");
    if (filas.length > 1) {
        boton.closest("tr").remove();
    }
}



// //SELECTS
// Funcion que permite listar y seleccionar los clientes existentes.
async function cargarClientes() {
    const respuesta = await fetch(`${CONFIG.API_URL}/clientes`);
    const clientes = await respuesta.json();

    const select = document.getElementById("clienteMovimiento");
    select.innerHTML = '<option value="" disabled selected>Seleccione un cliente</option>';

    clientes.forEach(c => {
        const option = document.createElement("option");
        option.value = c.idCliente;
        option.textContent = c.nombreCliente;
        select.appendChild(option);
    });
}

// Funcion que permite listar y seleccionar los libros existentes.
async function selectLibrosPrestamo(select) {
    const respuesta = await fetch(`${CONFIG.API_URL}/libros`);
    const libros = await respuesta.json();

    select.innerHTML = '<option value="" disabled selected>Seleccione un libro</option>'

    libros.forEach(l => {
        const option = document.createElement("option");
        option.value = l.idLibro;
        option.textContent = l.tituloLibro;
        select.appendChild(option);
    });
}



// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion de movimientos de inventario.
function abrirModalPrestamo() {
    encabezadoDocumentoPrestamo();
    cargarClientes();
    asignacionDocumentoPrestamo();
    listarItemsPrestamo();

    document.getElementById("modalPrestamo").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalPrestamo() {
    document.getElementById("modalPrestamo").style.display = "none";
    document.getElementById("formPrestamo").reset();
    document.querySelectorAll("#registroPrestamo .fila_item").forEach(f => f.remove());
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalPrestamo(e){
    if (e.key === 'Escape'){
        cerrarModalPrestamo();
    }
}

document.addEventListener("keydown", escModalPrestamo);