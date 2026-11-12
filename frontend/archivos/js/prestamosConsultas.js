// PROCESO MODULAR
// --Accede al archivo config.js para llamar al endpoint
const API_CONSULTAS_PRESTAMO = `${CONFIG.API_URL}/prestamos`;


// TABLA DE MOVIMIENTO HISTORICO.
async function prestamoHistorico() {
    const respuesta = await fetch(API_CONSULTAS_PRESTAMO);
    const datos = await respuesta.json();    

    const contTable = document.getElementById("cont_table");
    contTable.innerHTML = `
        <input id="input_buscar" class="input_buscar" type="text" placeholder="Buscar registro" title="Busqueda por sucursal, estado, identificacion, nombre o fecha.">
        <table id="tabla" class="list_table">
            <tr>
                <th class="col_xs">id</th>
                <th class="col_ms">Sucursal</th>         
                <th class="col_mm">Estado</th>
                <th class="col_ml">Identificacion</th>  
                <th class="col_lm">Cliente</th>  
                <th class="col_ml">Fecha registro</th>
                <th class="col_ml">Fecha prevista</th>
                <th class="col_ml">Fecha entrega</th>
                <th class="col_ml">Usuario</th>  
                <th class="col_ms">Detalle</th>
                <th class="col_ms">Finalizar</th>
            </tr>    
        </table>
    `;

    datos.forEach(d => {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <tr>
                <td class="col_xs" id="idTransaccion" >${d.idTransaccion ?? ""}</td>
                <td class="col_ms">${d.nombreSucursal ?? ""}</td>
                <td class="col_mm">${d.estadoPrestamo ?? ""}</td>
                <td class="col_ml">${d.identificacionCliente ?? ""}</td>
                <td class="col_lm">${d.nombreCliente ?? ""}</td>
                <td class="col_ml">${d.fechaRegistro ?? ""}</td>
                <td class="col_ml">${d.fechaDevolucionEstimada ?? ""}</td>
                <td class="col_ml">${d.fechaDevolucion ?? ""}</td>
                <td class="col_ml">${d.nombreUsuario ?? ""}</td>
                <td class="col_ms">
                    <button class="btn_editar">
                        <i class="bi bi-card-list"></i>
                    </button>
                </td>
                <td class="col_ms">
                    <button title="Finalizar préstamo" class="btn_editar">
                        <i class="bi bi-check-circle" onclick="finalizarPrestamo(${d.idTransaccion})"></i>
                    </button>
                </td>
            </tr>
        `;

        fila.querySelector(".btn_editar").addEventListener("click", () => detalleItem(d));
        tabla.appendChild(fila);    
    });


    // FILTRADO HISTORICO
    // --Busqueda de la disponibilidad por sucursal, estado, identificacion, nombre o fecha.
    document.getElementById("input_buscar").addEventListener("input", function(e){
        const filtro = e.target.value.toLowerCase();

        const fila = document.querySelectorAll("#tabla tr:not(:first-child)");
        fila.forEach(f => {
            const sucursal = f.cells[1].textContent.toLowerCase();
            const estado = f.cells[2].textContent.toLowerCase();
            const identificacion = f.cells[3].textContent.toLowerCase();
            const nombre = f.cells[4].textContent.toLowerCase();
            const fecha = f.cells[5].textContent.toLowerCase();


            f.style.display = sucursal.includes(filtro) || estado.includes(filtro) || identificacion.includes(filtro) || nombre.includes(filtro) || fecha.includes(filtro) ? "" : "none";
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


// --Proceso de cierre de prestamo.
async function finalizarPrestamo(id){
    if (!confirm("¿Finalizar el prestamo?")) return;
    
    const respuesta = await fetch (`${API_PRESTAMOS}/${id}/devolucion`, {method: "PUT"});
    console.log(id);

    if (respuesta.ok){
        prestamoHistorico();
    } else {
        const error = await respuesta.json();
        alert("Error: " + error.mensaje || "No se pudo finalizar el prestamo.");
    }
}


// FUNCIONAMIENTO DE LA MODAL
// --Cierre de la modal detalle de la consulta historica.
function cerrarModalPrestamosDetalle() {
    document.getElementById("modalDetalle").style.display = "none";
}