// CARGAR SUCURSALES EN EL SELECT 
let selectSucursales = [];
// --Accede a el endpoint de sucursales obteniendo los datos.
async function cargarSelectSucursales() {
    const respuesta = await fetch(`${CONFIG.API_URL}/sucursales`);
    selectSucursales = await respuesta.json();

    llenarSelectSucursales();
}
// --Lista los datos de sucursales obtenidos previamente.
function llenarSelectSucursales() {
    const select = document.getElementById("selectSucursal");

    selectSucursales.forEach(s => {
        const option = document.createElement("option");
        option.textContent = s.nombreSucursal;
        select.appendChild(option);
    });
}
// --Carga los datos de sucursales en el navbar.
document.addEventListener("DOMContentLoaded", cargarSelectSucursales);


// MODULOS
// --Accede y renderiza la "vista" del modulo activo en el apartado del contenido.
async function navegar(modulo){
    const respuesta = await fetch(`archivos/vistas/${modulo}.html`);
    const html = await respuesta.text();
    
    const contendor = document.getElementById("contenido");
    contendor.innerHTML = html;
    
    // --Ejecuta la función de inicialización alojada en cada "modulo.js" ejecutando procesos automaticos de carga.
    if (typeof inicializar === 'function') {
        inicializar();
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

