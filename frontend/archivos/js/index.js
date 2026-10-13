// NAVBAR SUCURSALES
async function navbarSucursales(idSelect) {
    // --Accede a el endpoint de sucursales obteniendo los datos.
    const respuesta = await fetch(`${CONFIG.API_URL}/sucursales`);
    const sucursales = await respuesta.json();
    
    const select = document.getElementById(idSelect);
    
    // --Lista los datos de sucursales obtenidos previamente.
    sucursales.forEach(s => {
        const option = document.createElement("option");
        option.value = s.idSucursal;
        option.textContent = s.nombreSucursal;
        select.appendChild(option);
    });
}
// --Carga los datos de sucursales en el navbar.
navbarSucursales("selectSucursal");

// MODULOS
// --Accede y renderiza la "vista" del modulo activo en el apartado del contenido.
async function navegar(modulo){
    const respuesta = await fetch(`archivos/vistas/${modulo}.html`);
    const html = await respuesta.text();
    
    const contendor = document.getElementById("contenido");
    contendor.innerHTML = html;
    
    // --Ejecuta la función de inicialización alojada en cada "modulo.js" ejecutando procesos automaticos de carga.
    switch (modulo) {
        case "sucursales":
                inicializarSucursales();
            break;
            case "usuarios":
                inicializarUsuarios();
            break;
            case "categorias":
                inicializarCategorias();
            break;
            case "autores":
                inicializarAutores();
            break;
        default:
            break;
    }
}
// --Resalta el modulo activo en el navbar para diferenciarlo del resto.
document.addEventListener('DOMContentLoaded', () => {
    const modulos = document.querySelectorAll('.modulo');

    modulos.forEach(modulo => {
        modulo.addEventListener('click', () => {
            modulos.forEach(m => m.classList.remove('active'));
            modulo.classList.add('active');
        });
    });
});

