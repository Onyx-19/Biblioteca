// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_AUTORES= `${CONFIG.API_URL}/autores`;

// // --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarAutores() {


    // --Listado de las autores registradas en la tabla de la vista.
    listarAutores();


    // FILTRAR AUTORES
    // --Busqueda del autor por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaAutores tr:not(:first-child)");
        fila.forEach(f => {
            const nombre = f.cells[1].textContent.toLowerCase();
            f.style.display = nombre.includes(filtro) ? "" : "none";
        })
    })
    
    
    // CREAR AUTORES
    // --Proceso de creacion de un nuevo autor desde el boton "Añadir".
    document.getElementById("formAutor").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idAutor").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_AUTORES + `/${id}` : API_AUTORES

        // --Captura de datos del formulario.
        const datos = {
            nombreAutor: document.getElementById("nombreAutor").value,
            annoNacimiento: document.getElementById("annoNacimiento").value,
            annoDeceso: document.getElementById("annoDeceso").value,
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalAutor();
            listarAutores();
        };

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE AUTORES
// --Proceso encargado de acceder al endpoint, buscar y listar las autores existentes.
async function listarAutores() {
    const respuesta = await fetch(API_AUTORES);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaAutores");
    tabla.innerHTML = `
        <tr>
            <th class="col_xs">Id</th>
            <th class="col_lm">Nombre</th>
            <th class="col_lm">Año nacimiento</th>
            <th class="col_lm">Año deceso</th>
            <th class="col_ms">Acciones</th>           
        </tr>
    `;
    datos.forEach(a => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="col_xs">${a.idAutor ?? ""}</td>
            <td class="col_lm">${a.nombreAutor ?? ""}</td>
            <td class="col_ml">${a.annoNacimiento  ?? ""}</td>
            <td class="col_ml">${a.annoDeceso  ?? ""}</td>
            <td class="col_ms">
                <button class="btn_editar" onclick="editarAutor(${a.idAutor})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}


// EDITAR AUTOR.
// --Proceso de modificacion y acceso a eliminacion de autores desde el boton "editar".
async function editarAutor(id){
    const respuesta = await fetch (API_AUTORES + `/${id}`);
    const autor = await respuesta.json();

    document.getElementById("idAutor").value = autor.idAutor;
    document.getElementById("nombreAutor").value = autor.nombreAutor;
    document.getElementById("annoNacimiento").value = autor.annoNacimiento;
    document.getElementById("annoDeceso").value = autor.annoDeceso;


    abrirModalAutor();
}

// ELIMINAR AUTOR.
async function eliminarAutor(){
    if (!confirm("¿Eliminar este autor?")) return;

    const id = document.getElementById("idAutor").value;
    const respuesta = await fetch(API_AUTORES + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModalAutor();
        listarAutores();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo eliminar el autor.");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de autores.
function abrirModalAutor() {
    let id = document.getElementById("idAutor").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");

    if (id){
        titulo.textContent = "Modificación de autor";
        btnEliminar.style.display = "block";
    } else {
        titulo.textContent = "Creación de autor";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modalAutor").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalAutor() {
    document.getElementById("modalAutor").style.display = "none";
    document.getElementById("formAutor").reset();
    document.getElementById("idAutor").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalAutor(e){
    if (e.key === 'Escape'){
        cerrarModalAutor();
    }
}

document.addEventListener("keydown", escModalAutor);