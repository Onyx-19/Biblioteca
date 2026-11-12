// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_INVENTARIOS = `${CONFIG.API_URL}/inventarios`;

// // --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarInventarios() {
    
    // CREAR REGISTRO INVENTARIO
    // --Proceso de creacion de un nuevo movimiento de inventario desde el boton "Añadir".
    document.getElementById("formInventario").addEventListener("submit", async function(e){
        e.preventDefault();

        const metodo = "POST";
        const url = API_INVENTARIOS;

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
            movimientoInventario: document.getElementById("tipoMovimiento").value,
            motivoMovimiento: document.getElementById("motivoMovimiento").value,
            idProveedor: document.getElementById("proveedorMovimiento").value,
            idSucursal: document.getElementById("sucursalDocumento").value,
            fechaHora: document.getElementById("fechaDocumento").value,
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
            cerrarModalInventario();
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
async function encabezadoDocumentoInventario(){
    const tableSelect = document.getElementById("tableSelect");
    
    tableSelect.innerHTML = `
        <tr>
            <tr class="input_cont">
            <th class="col_lm">Tipo de movimiento</th>
            <th class="col_lm">Motivo del movimiento</th>
            <th class="col_lm">Proveedor</th>
        </tr>
        <tr class="input_cont">
            <td class="col_lm">
                <select id="tipoMovimiento" class="campo_formulario v2" required></select>
            </td>
            <td class="col_lm">
                <select id="motivoMovimiento" class="campo_formulario v2" required></select>
            </td>
            <td>
                <select id="proveedorMovimiento" class="campo_formulario v2"></select>
            </td class="col_lm">
        </tr>
    `
}


// Captura los datos de asignacion del documento de movimiento de inventario.
async function asignacionDocumentoInventario() {    
    let sucursal = document.getElementById("selectSucursal");
    let nombreSucursal = sucursal.options[sucursal.selectedIndex].text;
    let idSucursal = sucursal.value;
    let idUsuario = await cargarUsuarioActivo()

    let fechaActual = new Date().toISOString().split("T")[0];
    
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
            <td class="col_ms">
                <p>Fecha:</p>
            </td>
            <td class="col_lm">
                <input type="date" class="campo_formulario v2" id="fechaDocumento" value="${fechaActual}" required>
            </td>
        </tr>
    `;
}

// Permite listar los items relacionados en el documento de inventario. Tambien agrega nuevos items a la lista.
function listarItemsInventario(){
    const tBody = document.getElementById("registroInventario");
    tBody.insertAdjacentHTML("beforeend", `
        <tr class="input_cont fila_item">
            <td class="col_lm">
                <select class="campo_formulario v2 libro_item" required></select>
            </td>
            <td class="col_xs">
                <input type="number" min="1" class="campo_formulario v2 unidades_item" placeholder="unidades" required>
            </td>
            <td class="col_xs">
                <button id="nuevoLibro" class="btn_annadir v2" type="button" onclick="adicionItemInventario()">
                    <i class="bi bi-plus"></i>
                </button>
            </td>
            <td class="col_xs">
                <button type="button" class="btn_eliminar v2" onclick="eliminarItemInventario(this)">
                    <i class="bi bi-dash"></i>
                </button>
            </td>
        </tr>
    `);

    const select = tBody.lastElementChild.querySelector(".libro_item");
    selectLibrosInventario(select);
}
// funciona simple ciclica para añadir items interactuando con el boton +.
function adicionItemInventario(){
    listarItemsInventario();
} 
// funcion que permite eliminar items de la lista del documento.
function eliminarItemInventario(boton) {
    const filas = document.querySelectorAll(".fila_item");
    if (filas.length > 1) {
        boton.closest("tr").remove();
    }
}



//SELECTS
// Funcion que permite listar y seleccionar los tipos de movimiento inventario.
async function tipoMovimiento(){
    const respuesta = await fetch (API_INVENTARIOS + "/tipos");
    const tipos = await respuesta.json();

    const select = document.getElementById("tipoMovimiento");
    select.innerHTML = '<option value="" disabled selected>Seleccione un tipo</option>'

    tipos.forEach(t => {
        const option = document.createElement("option");
        option.value = t;
        option.textContent = t;
        select.appendChild(option);
    });
}

// Funcion que permite listar y seleccionar los motivos de movimiento inventario.
async function motivoMovimiento(tipo){
    const respuesta = await fetch (API_INVENTARIOS + "/motivo");
    const motivos = await respuesta.json();
    
    const motivosPorTipo = {
        ENTRADA: ["COMPRA", "DONACION"],
        SALIDA: ["PERDIDA", "DANO"]
    }

    const select = document.getElementById("motivoMovimiento");
    select.innerHTML = '<option value="" disabled selected>Seleccione un motivo</option>';

    (motivosPorTipo[tipo] ?? []).forEach(m => {
        const option = document.createElement("option");
        option.value = m;
        option.textContent = m;
        select.appendChild(option);
    });
}

// Funcion que permite listar y seleccionar los proveedores existentes.
async function cargarProveedores() {
    const respuesta = await fetch(`${CONFIG.API_URL}/proveedores`);
    const proveedores = await respuesta.json();

    const select = document.getElementById("proveedorMovimiento");
    select.innerHTML = '<option value="" disabled selected>Seleccione un proveedor</option>';

    proveedores.forEach(p => {
        const option = document.createElement("option");
        option.value = p.idProveedor;
        option.textContent = p.nombreProveedor;
        select.appendChild(option);
    });
}

function actualizarProveedor(tipo) {
    const select = document.getElementById("proveedorMovimiento");
    const entrada = tipo === "ENTRADA";

    select.disabled = !entrada;
    select.required = entrada;
    select.value = "";
}

// Funcion que permite listar y seleccionar los libros existentes.
async function selectLibrosInventario(select) {
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
function abrirModalInventario() {

    encabezadoDocumentoInventario();
    tipoMovimiento();
    motivoMovimiento("");   // arranca vacío hasta elegir un tipo
    document.getElementById("tipoMovimiento").addEventListener("change", e => motivoMovimiento(e.target.value));

    cargarProveedores();
    actualizarProveedor("");   // arranca deshabilitado
    document.getElementById("tipoMovimiento").addEventListener("change", e => {
        motivoMovimiento(e.target.value);
        actualizarProveedor(e.target.value);
    });

    asignacionDocumentoInventario();
    listarItemsInventario();

    document.getElementById("modalInventario").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalInventario() {
    document.getElementById("modalInventario").style.display = "none";
    document.getElementById("formInventario").reset();
    document.querySelectorAll("#registroInventario .fila_item").forEach(f => f.remove());
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalInventario(e){
    if (e.key === 'Escape'){
        cerrarModalInventario();
    }
}

document.addEventListener("keydown", escModalInventario);