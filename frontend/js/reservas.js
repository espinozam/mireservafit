const baseURL = 'http://localhost:8080/MiReservaFit';

window.onload = cargarReservas;

function cargarReservas() {
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${baseURL}/ReservaListServlet`, true);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      document.getElementById('listaReservas').innerHTML = xhr.responseText;
      // Asignar eventos a los botones de editar si existen
      document.querySelectorAll('.btn-editar-reserva').forEach(btn => {
        btn.onclick = function () {
          editarReserva(
            btn.dataset.id,
            btn.dataset.fecha,
            btn.dataset.hora,
            btn.dataset.clienteId,
            btn.dataset.entrenadorId
          );
        };
      });
      // Asignar eventos a los botones de eliminar
      document.querySelectorAll('.btn-eliminar-reserva').forEach(btn => {
        btn.onclick = function () {
          if (confirm("¿Seguro que deseas eliminar esta reserva?")) {
            eliminarReserva(btn.dataset.id);
          }
        };
      });
    }
  };

  xhr.send();
}

function agregarReserva(event) {
  console.log("Agregando reserva...");
  
  event.preventDefault(); // Prevenir el envío del formulario para manejarlo con AJAX

  const form = document.getElementById('formReserva');
  const fecha = form.querySelector('input[name="fecha"]').value;
  const hora = form.querySelector('select[name="hora"]').value;
  const clienteId = form.querySelector('select[name="cliente_id"]').value;
  const entrenadorId = form.querySelector('select[name="entrenador_id"]').value;
  const reservaId = form.dataset.editing || "";

  const xhr = new XMLHttpRequest();
  let url = `${baseURL}/ReservaAddServlet`;
  let params = `fecha=${encodeURIComponent(fecha)}&hora=${encodeURIComponent(hora)}&cliente_id=${clienteId}&entrenador_id=${entrenadorId}`;

  // Si estamos editando, usamos el servlet de actualización
  if (reservaId) {
    url = `${baseURL}/ReservaUpdateServlet`;
    params += `&id=${encodeURIComponent(reservaId)}`;
  }

  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        alert(xhr.responseText);
        const resp = xhr.responseText.trim();
        if (
          resp !== "El cliente no existe." &&
          resp !== "El entrenador no existe." &&
          resp !== "No se puede reservar en una fecha y hora pasada." &&
          !resp.includes("Ya existe una reserva")
        ) {
          cargarReservas();
          form.reset();
          form.style.display = 'none';
          form.dataset.editing = "";
        }
      } else {
        alert("Error al guardar la reserva.");
      }
    }
  };

  xhr.send(params);
}

// Llenar el formulario para editar una reserva
function editarReserva(id, fecha, hora, clienteId, entrenadorId) {
  const form = document.getElementById('formReserva');
  form.reset();
  form.dataset.editing = id;
  form.style.display = 'block';

  // Cargar selects de clientes y entrenadores
  cargarClientes();
  cargarEntrenadores();

  // Asignar fecha, cliente y entrenador después de cargar selects
  setTimeout(() => {
    form.querySelector('input[name="fecha"]').value = fecha;
    form.querySelector('select[name="cliente_id"]').value = clienteId;
    form.querySelector('select[name="entrenador_id"]').value = entrenadorId;

    // Cargar horas disponibles para ese día y entrenador
    cargarHorasDisponibles();

    // Asignar la hora después de cargar las opciones de hora
    setTimeout(() => {
      form.querySelector('select[name="hora"]').value = hora;
    }, 300);
  }, 300);
}

function eliminarReserva(id) {
  const xhr = new XMLHttpRequest();
  xhr.open("POST", `${baseURL}/ReservaDeleteServlet`, true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        alert(xhr.responseText);
        cargarReservas();
      } else {
        alert("Error al eliminar la reserva.");
      }
    }
  };

  xhr.send(`id=${encodeURIComponent(id)}`);
}

function cargarHorasDisponibles() {
  const fecha = document.getElementById('fecha').value;
  const entrenadorId = document.getElementById('entrenador_id').value;
  const horaSel = document.getElementById('hora');
  limpiarHoras();

  if (!fecha || !entrenadorId) return;

  // AJAX clásico para cargar las opciones HTML
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${baseURL}/HorasDisponiblesServlet?fecha=${fecha}&entrenador_id=${entrenadorId}`, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      horaSel.innerHTML = xhr.responseText;
    }
  };
  xhr.send();
}


// Simulación: reemplaza por AJAX a tu backend para obtener clientes y entrenadores reales
const clientes = [
  { id: 1, nombre: "Carlos" },
  { id: 2, nombre: "Marta" }
];
const entrenadores = [
  { id: 1, nombre: "Ana" },
  { id: 2, nombre: "Luis" }
];

// Cargar clientes y entrenadores desde el backend al abrir el formulario
function mostrarFormularioReserva() {
  document.getElementById('formReserva').reset();
  document.getElementById('formReserva').style.display = 'block';
  cargarClientes();
  cargarEntrenadores();
  limpiarHoras();
}

function cargarClientes() {
  const clienteSel = document.getElementById('cliente_id');
  clienteSel.innerHTML = '<option value="">Seleccione cliente</option>';
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${baseURL}/ClientesSelectServlet`, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      clienteSel.innerHTML += xhr.responseText;
    }
  };
  xhr.send();
}

function cargarEntrenadores() {
  const entrenadorSel = document.getElementById('entrenador_id');
  entrenadorSel.innerHTML = '<option value="">Seleccione entrenador</option>';
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${baseURL}/EntrenadoresSelectServlet`, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      entrenadorSel.innerHTML += xhr.responseText;
    }
  };
  xhr.send();
}

function limpiarHoras() {
  const horaSel = document.getElementById('hora');
  horaSel.innerHTML = '<option value="">Seleccione hora disponible</option>';
}

document.getElementById('fecha').addEventListener('change', cargarHorasDisponibles);
document.getElementById('entrenador_id').addEventListener('change', cargarHorasDisponibles);

function cargarHorasDisponibles() {
  const fecha = document.getElementById('fecha').value;
  const entrenadorId = document.getElementById('entrenador_id').value;
  const horaSel = document.getElementById('hora');
  limpiarHoras();

  if (!fecha || !entrenadorId) return;

  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${baseURL}/HorasDisponiblesServlet?fecha=${fecha}&entrenador_id=${entrenadorId}`, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      horaSel.innerHTML = xhr.responseText;
    }
  };
  xhr.send();
}