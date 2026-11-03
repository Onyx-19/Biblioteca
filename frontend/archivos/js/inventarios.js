// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_INVENTARIOS = `${CONFIG.API_URL}/inventarios`;

// // --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarInventarios() {


//     // --Listado de los libros registrados en la tabla de la vista.
//     listarLibros();

//     selectAutores("autorLibro");
//     selectCategorias("categoriaLibro");


//     // FILTRAR LIBROS
//     // --Busqueda del libro por nombre.
//     document.getElementById("input_buscar").addEventListener("input", function(e){
//         const filtro = e.target.value.toLowerCase();

//         const fila = document.querySelectorAll("#tablaLibros tr:not(:first-child)");
//         fila.forEach(f => {
//             const titulo = f.cells[1].textContent.toLowerCase();
//             const autor = f.cells[2].textContent.toLowerCase();
//             const editorial = f.cells[3].textContent.toLowerCase();

//             f.style.display = autor.includes(filtro) || titulo.includes(filtro) || editorial.includes(filtro) ? "" : "none";
//         })
//     })
    
    
    // CREAR REGISTRO INVENTARIO
    // --Proceso de creacion de un nuevo libro desde el boton "Añadir".
    document.getElementById("formInventario").addEventListener("submit", async function(e){
        e.preventDefault();

        const metodo = "POST";
        const url = API_INVENTARIOS;

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



// // TABLA DE LIBROS
// // --Proceso encargado de acceder al endpoint, buscar y listar los libros existentes.
// async function listarLibros() {
//     const respuesta = await fetch(API_LIBROS);
//     const datos = await respuesta.json();
    
//     const tabla = document.getElementById("tablaLibros");
//     tabla.innerHTML = `
//         <tr>
//             <th class="col_xs">Id</th>
//             <th class="col_lg">Titulo</th>
//             <th class="col_lm">Autor</th>  
//             <th class="col_ml">Categoria</th>           
//             <th class="col_ml">Editorial</th>
//             <th class="col_lm">ISBN</th>
//             <th class="col_ms">Año</th>
//             <th class="col_ml">Valor</th>
//             <th class="col_ms">Acciones</th>           
//         </tr>
//     `;

//     datos.forEach(l => {
//         const fila = document.createElement("tr");
//         fila.innerHTML = `
//             <td class="col_xs">${l.idLibro ?? ""}</td>
//             <td class="col_lg">${l.tituloLibro ?? ""}</td>
//             <td class="col_lm">${l.nombreAutor ?? ""}</td>
//             <td class="col_ml">${l.nombreCategoria ?? ""}</td>
//             <td class="col_ml">${l.editorial ?? ""}</td>
//             <td class="col_lm">${l.isbn ?? ""}</td>
//             <td class="col_ms">${l.annoPublicacion ?? ""}</td>
//             <td class="col_ml">${l.valorLibro ?? ""}</td>
//             <td class="col_ms">
//                 <button class="btn_editar" onclick="editarLibro(${l.idLibro})">
//                     <i class="bi bi-pencil-square"></i>
//                 </button>
//             </td>
//         `;
//         tabla.appendChild(fila);        
//     });
// }



// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion de movimientos de inventario.
function abrirModalInventario() {
    const tableSelect = document.getElementById("tableSelect");
    
    tableSelect.innerHTML = `
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
                <select id="proveedorMovimiento" class="campo_formulario v2" required></select>
            </td class="col_lm">
        </tr>
    `

    tipoMovimiento();
    motivoMovimiento();
    proveedorMovimiento("proveedorMovimiento");
    capturaDocumento();
    listarItems();

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



//CAPTURA DE MOVIMIENTOS
async function capturaDocumento() {    
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

function listarItems(){
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
                <button id="nuevoLibro" class="btn_annadir v2" type="button" onclick="adicionItem()">
                    <i class="bi bi-plus"></i>
                </button>
            </td>
            <td class="col_xs">
                <button type="button" class="btn_eliminar v2" onclick="eliminarItem(this)">
                    <i class="bi bi-dash"></i>
                </button>
            </td>
        </tr>
    `);

    const select = tBody.lastElementChild.querySelector(".libro_item");
    selectLibros(select);
}

function adicionItem(){
    listarItems();
} 

function eliminarItem(boton) {
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
    select.innerHTML = '<option value="" disabled selected>--Seleccione un tipo--</option>'

    tipos.forEach(t => {
        const option = document.createElement("option");
        option.value = t;
        option.textContent = t;
        select.appendChild(option);
    });
}

// Funcion que permite listar y seleccionar los motivos de movimiento inventario.
async function motivoMovimiento(){
    const respuesta = await fetch (API_INVENTARIOS + "/motivo");
    const motivos = await respuesta.json();
    
    const select = document.getElementById("motivoMovimiento");
    select.innerHTML = '<option value="" disabled selected>--Seleccione un motivo--</option>'

    motivos.forEach(m => {
        const option = document.createElement("option");
        option.value = m;
        option.textContent = m;
        select.appendChild(option);
    });
}

// Funcion que permite listar y seleccionar los proveedores existentes.
async function proveedorMovimiento(idSelect){
    const respuesta = await fetch (`${CONFIG.API_URL}/proveedores`);
    const proveedores = await respuesta.json();

    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="" disabled selected>--Seleccione un proveedor--</option>';

    proveedores.forEach(p => {
        const option = document.createElement("option");
        option.value = p.idProveedor;
        option.textContent = p.nombreProveedor;
        select.appendChild(option);
    });
}

// Funcion que permite listar y seleccionar los libros existentes.
async function selectLibros(select) {
    // const select = document.querySelector(idSelect);

    const respuesta = await fetch(`${CONFIG.API_URL}/libros`);
    const libros = await respuesta.json();

    select.innerHTML = '<option value="" disabled selected>--Seleccione un libro--</option>'

    libros.forEach(l => {
        const option = document.createElement("option");
        option.value = l.idLibro;
        option.textContent = l.tituloLibro;
        select.appendChild(option);
    });
}