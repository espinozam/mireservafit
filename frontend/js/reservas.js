const baseURL = 'http://localhost:8080/MiReservaFit';

window.onload = cargarReservas;

function mostrarFormularioReserva() {
  document.getElementById('formReserva').reset();
  document.getElementById('formReserva').dataset.editing = "";
  document.getElementById('formReserva').style.display = 'block';
}

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
  event.preventDefault();

  const form = document.getElementById('formReserva');
  const fecha = form.querySelector('input[name="fecha"]').value;
  const hora = form.querySelector('input[name="hora"]').value;
  const clienteId = form.querySelector('input[name="cliente_id"]').value;
  const entrenadorId = form.querySelector('input[name="entrenador_id"]').value;
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
  form.querySelector('input[name="fecha"]').value = fecha;
  form.querySelector('input[name="hora"]').value = hora;
  form.querySelector('input[name="cliente_id"]').value = clienteId;
  form.querySelector('input[name="entrenador_id"]').value = entrenadorId;
  form.dataset.editing = id;
  form.style.display = 'block';
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
