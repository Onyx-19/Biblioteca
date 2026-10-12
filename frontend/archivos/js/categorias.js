// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_CATEGORIAS= `${CONFIG.API_URL}/categorias`;

// // --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarCategorias() {


    // --Listado de las categorias registradas en la tabla de la vista.
    listarCategorias();


    // FILTRAR CATEGORIA
    // --Busqueda de la categoria por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaCategorias tr:not(:first-child)");
        fila.forEach(f => {
            const nombre = f.cells[1].textContent.toLowerCase();
            f.style.display = nombre.includes(filtro) ? "" : "none";
        })
    })
    
    
    // CREAR CATEGORIA
    // --Proceso de creacion de una nueva categoria desde el boton "Añadir".
    document.getElementById("formCategoria").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idCategoria").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_CATEGORIAS + `/${id}` : API_CATEGORIAS

        // --Captura de datos del formulario.
        const datos = {
            nombreCategoria: document.getElementById("nombreCategoria").value,
            codigoEstante: document.getElementById("estanteriaCategoria").value,
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalCategoria();
            listarCategorias();
        };

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE CATEGORIAS
// --Proceso encargado de acceder al endpoint, buscar y listar las categorias existentes.
async function listarCategorias() {
    const respuesta = await fetch(API_CATEGORIAS);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaCategorias");
    tabla.innerHTML = `
        <tr>
            <th class="col_xs">Id</th>
            <th class="col_lm">Nombre</th>
            <th class="col_lm">Codigo Estante</th>
            <th class="col_ms">Acciones</th>           
        </tr>
    `;
    datos.forEach(s => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="col_xs">${s.idCategoria ?? ""}</td>
            <td class="col_lm">${s.nombreCategoria ?? ""}</td>
            <td class="col_ml">${s.codigoEstante  ?? ""}</td>
            <td class="col_ms">
                <button class="btn_editar" onclick="editarCategoria(${s.idCategoria})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}


// EDITAR CATEGORIA.
// --Proceso de modificacion y acceso a eliminacion de categorias desde el boton "editar".
async function editarCategoria(id){
    const respuesta = await fetch (API_CATEGORIAS + `/${id}`);
    const categoria = await respuesta.json();

    document.getElementById("idCategoria").value = categoria.idCategoria;
    document.getElementById("nombreCategoria").value = categoria.nombreCategoria;
    document.getElementById("estanteriaCategoria").value = categoria.codigoEstante;

    abrirModalCategoria();
}

// ELIMINAR CATEGORIA.
async function eliminarCategoria(){
    if (!confirm("¿Eliminar esta categoria?")) return;

    const id = document.getElementById("idCategoria").value;
    const respuesta = await fetch(API_CATEGORIAS + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModalCategoria();
        listarCategorias();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo eliminar la categoria");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de categorias.
function abrirModalCategoria() {
    let id = document.getElementById("idCategoria").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (id){
        titulo.textContent = "Modificación de categoria";
        btnEliminar.style.display = "block";
    } else {
        titulo.textContent = "Creación de categoria";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modalCategoria").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalCategoria() {
    document.getElementById("modalCategoria").style.display = "none";
    document.getElementById("formCategoria").reset();
    document.getElementById("idCategoria").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalCategoria(e){
    if (e.key === 'Escape'){
        cerrarModalCategoria();
    }
}

document.addEventListener("keydown", escModalCategoria);