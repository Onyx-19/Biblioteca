// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_LIBROS= `${CONFIG.API_URL}/libros`;

// --Funcion conectada con el index.js el cual inicializa los procesos alojados de forma automatica en cuanto se ingresa una nueva vista.
function inicializarLibros() {


    // --Listado de los libros registrados en la tabla de la vista.
    listarLibros();

    selectAutores("autorLibro");
    selectCategorias("categoriaLibro");


    // FILTRAR LIBROS
    // --Busqueda del libro por nombre.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tablaLibros tr:not(:first-child)");
        fila.forEach(f => {
            const titulo = f.cells[1].textContent.toLowerCase();
            const autor = f.cells[2].textContent.toLowerCase();
            const editorial = f.cells[3].textContent.toLowerCase();

            f.style.display = autor.includes(filtro) || titulo.includes(filtro) || editorial.includes(filtro) ? "" : "none";
        })
    })
    
    
    // CREAR LIBROS
    // --Proceso de creacion de un nuevo libro desde el boton "Añadir".
    document.getElementById("formLibro").addEventListener("submit", async function(e){
        e.preventDefault();

        const id = document.getElementById("idLibro").value;
        const metodo = id ? "PUT" : "POST";
        const url = id ? API_LIBROS + `/${id}` : API_LIBROS

        // --Captura de datos del formulario.
        const datos = {
            tituloLibro: document.getElementById("tituloLibro").value,
            editorial: document.getElementById("editorialLibro").value,
            isbn: document.getElementById("usbnLibro").value,
            annoPublicacion: document.getElementById("annoPublicacion").value,
            valorLibro: document.getElementById("valorLibro").value,
            idAutor: document.getElementById("autorLibro").value,
            idCategoria: document.getElementById("categoriaLibro").value
        };

        // --Envio de los datos al backend.
        const respuesta = await fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        // --Respuesta si el backend procesa la peticion con exito.
        if(respuesta.ok){
            cerrarModalLibro();
            listarLibros();
        };

        // --Respuesta si el backend retorna algun error.
        if (!respuesta.ok) {
            const error = await respuesta.json();
            alert(error.mensaje); // muestra solo el mensaje legible
            return;
        }
    });
};


// TABLA DE LIBROS
// --Proceso encargado de acceder al endpoint, buscar y listar los libros existentes.
async function listarLibros() {
    const respuesta = await fetch(API_LIBROS);
    const datos = await respuesta.json();
    
    const tabla = document.getElementById("tablaLibros");
    tabla.innerHTML = `
        <tr>
            <th class="col_xs">Id</th>
            <th class="col_lg">Titulo</th>
            <th class="col_lm">Autor</th>  
            <th class="col_ml">Categoria</th>           
            <th class="col_ml">Editorial</th>
            <th class="col_lm">ISBN</th>
            <th class="col_ms">Año</th>
            <th class="col_ml">Valor</th>
            <th class="col_ms">Acciones</th>           
        </tr>
    `;

    datos.forEach(l => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="col_xs">${l.idLibro ?? ""}</td>
            <td class="col_lg">${l.tituloLibro ?? ""}</td>
            <td class="col_lm">${l.nombreAutor ?? ""}</td>
            <td class="col_ml">${l.nombreCategoria ?? ""}</td>
            <td class="col_ml">${l.editorial ?? ""}</td>
            <td class="col_lm">${l.isbn ?? ""}</td>
            <td class="col_ms">${l.annoPublicacion ?? ""}</td>
            <td class="col_ml">${l.valorLibro ?? ""}</td>
            <td class="col_ms">
                <button class="btn_editar" onclick="editarLibro(${l.idLibro})">
                    <i class="bi bi-pencil-square"></i>
                </button>
            </td>
        `;
        tabla.appendChild(fila);        
    });
}

//SELECTS
// Funcion que permite listar y seleccionar los autores existentes.
async function selectAutores(idSelect) {
    const select = document.getElementById(idSelect);
    if (!select) return;

    const respuesta = await fetch(`${CONFIG.API_URL}/autores`);
    const autores = await respuesta.json();

    select.innerHTML = '<option value=""></option>';

    autores.forEach(a => {
        const option = document.createElement("option");
        option.value = a.idAutor;
        option.textContent = a.nombreAutor;
        select.appendChild(option);
    });

    // Convierte el select en un buscador.
    select.classList.remove("campo_formulario");
    new TomSelect(select, {
        placeholder: "Escriba para seleccionar un autor",
        maxOptions: 50
    });
}


// Funcion que permite listar y seleccionar las categorias existentes.
async function selectCategorias(idSelect) {
    // --Accede a el endpoint de categorias obteniendo los datos.
    const respuesta = await fetch(`${CONFIG.API_URL}/categorias`);
    const categorias = await respuesta.json();
    
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="" disabled selected>Seleccione una categoria</option>';
    
    // --Lista los datos de categorias obtenidos previamente.
    categorias.forEach(c => {
        const option = document.createElement("option");
        option.value = c.idCategoria;
        option.textContent = c.nombreCategoria;
        select.appendChild(option);
    });
}


// EDITAR LIBRO.
// --Proceso de modificacion y acceso a eliminacion de libros desde el boton "editar".
async function editarLibro(id){
    const respuesta = await fetch (API_LIBROS + `/${id}`);
    const libro = await respuesta.json();

    document.getElementById("idLibro").value = libro.idLibro;
    document.getElementById("tituloLibro").value = libro.tituloLibro;
    document.getElementById("editorialLibro").value = libro.editorial;
    document.getElementById("usbnLibro").value = libro.isbn;
    document.getElementById("annoPublicacion").value = libro.annoPublicacion;
    document.getElementById("valorLibro").value = libro.valorLibro;
    // document.getElementById("autorLibro").value = libro.idAutor;
    document.getElementById("categoriaLibro").value = libro.idCategoria;
    document.getElementById("autorLibro").tomselect.setValue(libro.idAutor, true);

    abrirModalLibro();
}


// ELIMINAR LIBRO.
async function eliminarLibro(){
    if (!confirm("¿Eliminar este libro?")) return;

    const id = document.getElementById("idLibro").value;
    const respuesta = await fetch(API_LIBROS + `/${id}`, {method: "DELETE"})

    if (respuesta.ok){
        cerrarModalLibro();
        listarLibros();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo eliminar el libro.");
    }
}


// FUNCIONALIDAD DE LA MODAL.
// --Apertura de la modal de creacion y modificacion de libros.
function abrirModalLibro() {
    let id = document.getElementById("idLibro").value;
    const titulo = document.querySelector(".form_header_h3");
    const btnEliminar = document.querySelector(".btn_eliminar");
    
    
    if (id){
        titulo.textContent = "Modificación de libro";
        btnEliminar.style.display = "block";
    } else {
        document.getElementById("autorLibro").tomselect.clear();
        titulo.textContent = "Creación de libro";
        btnEliminar.style.display = "none";
    }

    document.getElementById("modalLibro").style.display = "flex";
}

// --Proceso de cierre de modal.
function cerrarModalLibro() {
    document.getElementById("modalLibro").style.display = "none";
    document.getElementById("formLibro").reset();
    document.getElementById("idLibro").value = "";
}

// --Utilizar la tecla esc para cerrar la modal.
function escModalLibro(e){
    if (e.key === 'Escape'){
        cerrarModalLibro();
    }
}

document.addEventListener("keydown", escModalLibro);