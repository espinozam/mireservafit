const baseURL = 'http://localhost:8080/MiReservaFit';

window.onload = cargarReservas;
function mostrarFormularioReserva() {
  document.getElementById('formReserva').style.display = 'block';
}

function cargarReservas() {
  const xhr = new XMLHttpRequest();
  xhr.open("GET", `${baseURL}/ReservaListServlet`, true);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      document.getElementById('listaReservas').innerHTML = xhr.responseText;
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

  const xhr = new XMLHttpRequest();
  xhr.open("POST", `${baseURL}/ReservaAddServlet`, true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        alert(xhr.responseText);
        // No ocultar el formulario si hay error de cliente o entrenador no existe o solapamiento
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
        }
      } else {
        alert("Error al guardar la reserva.");
      }
    }
  };

  const params = `fecha=${encodeURIComponent(fecha)}&hora=${encodeURIComponent(hora)}&cliente_id=${clienteId}&entrenador_id=${entrenadorId}`;
  xhr.send(params);
}

// Cargar reservas al iniciar
window.onload = cargarReservas;
