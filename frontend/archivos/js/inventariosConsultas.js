// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_CONSULTAS_INVENTARIO = `${CONFIG.API_URL}/inventarios`;

// TABLA DE STOCK
// --Proceso encargado de acceder al endpoint, buscar y listar el stock existente.
async function consultaStock() {
    const respuesta = await fetch(API_CONSULTAS_INVENTARIO + "/stock");
    const datos = await respuesta.json();

    const contTable = document.getElementById("cont_table");
    contTable.innerHTML = `
        <input id="input_buscar" class="input_buscar" type="text" placeholder="Buscar registro" title="Busqueda por sucursal, titulo o ISBN">
        <table id="tabla" class="list_table">
            <tr>
                <th class="col_ms">Sucursal</th>
                <th class="col_lg">Libro</th>  
                <th class="col_ms">Stock</th>         
                <th class="col_lm">ISBN</th>         
            </tr>    
        </table>
    `;

    datos.forEach(s => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td class="col_ms">${s.sucursal.nombreSucursal ?? ""}</td>
            <td class="col_lg">${s.libro.tituloLibro ?? ""}</td>
            <td class="col_ms">${s.disponible ?? ""}</td>
            <td class="col_lm">${s.libro.isbn ?? ""}</td>
        `;
        tabla.appendChild(fila);    
    });


    // FILTRADO STOCK
    // --Busqueda de la disponibilidad por titulo, sucursal o codigo.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tabla tr:not(:first-child)");
        fila.forEach(f => {
            const sucursal = f.cells[0].textContent.toLowerCase();
            const titulo = f.cells[1].textContent.toLowerCase();
            const isbn = f.cells[3].textContent.toLowerCase();

            f.style.display = sucursal.includes(filtro) || titulo.includes(filtro) || isbn.includes(filtro) ? "" : "none";
        });
    });
}


// TABLA DE MOVIMIENTO HISTORICO.
// 
async function inventarioHistorico() {
    const respuesta = await fetch(API_CONSULTAS_INVENTARIO);
    const datos = await respuesta.json();    

    const contTable = document.getElementById("cont_table");
    contTable.innerHTML = `
        <input id="input_buscar" class="input_buscar" type="text" placeholder="Buscar registro" title="Busqueda por sucursal, concepto, fecha o proveedor.">
        <table id="tabla" class="list_table">
            <tr>
                <th class="col_xs">id</th>
                <th class="col_ms">Sucursal</th>         
                <th class="col_mm">Movimiento</th>
                <th class="col_mm">Concepto</th>
                <th class="col_mm">Fecha</th>
                <th class="col_lm">Proveedor</th>  
                <th class="col_ml">Usuario</th>  
                <th class="col_ms">Detalle</th>
            </tr>    
        </table>
    `;

    datos.forEach(d => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <tr>
                <td class="col_xs">${d.idTransaccion ?? ""}</td>
                <td class="col_ms">${d.nombreSucursal ?? ""}</td>
                <td class="col_mm">${d.movimientoInventario ?? ""}</td>
                <td class="col_mm">${d.motivoMovimiento ?? ""}</td>
                <td class="col_mm">${d.fechaHora ?? ""}</td>
                <td class="col_lm">${d.nombreProveedor ?? ""}</td>
                <td class="col_ml">${d.nombreUsuario ?? ""}</td>
                <td class="col_ms">
                    <button class="btn_editar">
                        <i class="bi bi-card-list"></i>
                    </button>
                </td>
            </tr>
        `;

        fila.querySelector(".btn_editar").addEventListener("click", () => detalleItem(d));
        tabla.appendChild(fila);    
    });

    // FILTRADO HISTORICO
    // --Busqueda de la disponibilidad por sucursal, concepto, fecha o proveedor.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tabla tr:not(:first-child)");
        fila.forEach(f => {
            const sucursal = f.cells[1].textContent.toLowerCase();
            const concepto = f.cells[3].textContent.toLowerCase();
            const fecha = f.cells[4].textContent.toLowerCase();
            const proveedor = f.cells[5].textContent.toLowerCase();

            f.style.display = sucursal.includes(filtro) || concepto.includes(filtro) || fecha.includes(filtro) || proveedor.includes(filtro) ? "" : "none";
        })
    })

    // --Lista los items en el detalle de la consulta Historica.
    function detalleItem(d) {
        document.getElementById("detalleDocumento").textContent = d.idTransaccion;

        const tbody = document.getElementById("detalleItems");
        tbody.innerHTML = "";

        d.items.forEach(i => {
            const fila = document.createElement("tr");
            fila.innerHTML = `
                <td class="col_lm">${i.tituloLibro ?? ""}</td>
                <td class="col_xs">${i.unidades ?? ""}</td>
            `;
            tbody.appendChild(fila);
        });

        document.getElementById("modalDetalle").style.display = "flex";
    }
}


// --Resalta la consulta activa para diferenciarla del resto.
document.addEventListener('click', (e) => {
    if (e.target.matches('.btn_consulta')) {
        document.querySelectorAll('.btn_consulta').forEach(m => m.classList.remove('active'));

        e.target.classList.add('active');
    }
});


// FUNCIONAMIENTO DE LA MODAL
// --Cierre de la modal detalle de la consulta historica.
function cerrarModalInventarioDetalle() {
    document.getElementById("modalDetalle").style.display = "none";
}