const usuarioEmail = localStorage.getItem("usuarioEmail");

// Mostrar info del entrenador y botón de cerrar sesión
document.getElementById("infoEntrenador").innerHTML = `
  <p>Bienvenido, ${usuarioEmail}</p>
  <button id="cerrarSesionBtn">Cerrar sesión</button>
`;

document.getElementById("cerrarSesionBtn").onclick = function() {
  localStorage.removeItem("usuarioEmail");
  localStorage.removeItem("tipoUsuario");
  window.location.href = "index.html";
};

// Cuando marques el id del entrenador (por ejemplo, tras login o al cargar la página)
function marcarEntrenadorId() {
  const xhr = new XMLHttpRequest();
  xhr.open('GET', `${baseURL}/EntrenadorIdServlet?email=${encodeURIComponent(usuarioEmail)}`, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      const id = xhr.responseText.trim();
      const select = document.getElementById('entrenador_id');
      if (select) {
        select.innerHTML = `<option value="${id}" selected></option>`;
        select.value = id;
      }
    }
  };
  xhr.send();
}

// Cargar reservas asignadas al entrenador usando ReservaListServlet
function cargarReservasEntrenador() {
  const xhr = new XMLHttpRequest();
  xhr.open('GET', `${baseURL}/ReservaListServlet?emailEntrenador=${encodeURIComponent(usuarioEmail)}`, true);

  xhr.onreadystatechange = function () {
    const contenedor = document.getElementById('reservasEntrenador');
    if (contenedor && xhr.readyState === 4) {
      if (xhr.status === 200) {
        contenedor.innerHTML = xhr.responseText;
      } else {
        contenedor.innerText = 'Error al cargar las reservas.';
      }
    }
  };

  xhr.send();
}

// Sobrescribe agregarReserva para el área de entrenador
function agregarReserva(event) {
  event.preventDefault();
  // Asegura que el id del entrenador esté actualizado
  marcarEntrenadorId();

  const form = document.getElementById('formReserva');
  const fecha = form.querySelector('input[name="fecha"]').value;
  const hora = form.querySelector('select[name="hora"]').value;
  const clienteId = form.querySelector('select[name="cliente_id"]').value;
  const entrenadorId = form.querySelector('select[name="entrenador_id"]').value;
  const reservaId = form.dataset.editing || "";

  let url = `${baseURL}/ReservaAddServlet`;
  let params = `fecha=${encodeURIComponent(fecha)}&hora=${encodeURIComponent(hora)}&cliente_id=${clienteId}&entrenador_id=${entrenadorId}`;

  // Si estamos editando, usamos el servlet de actualización
  if (reservaId) {
    url = `${baseURL}/ReservaUpdateServlet`;
    params += `&id=${encodeURIComponent(reservaId)}`;
  }

  const xhr = new XMLHttpRequest();
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      alert(xhr.responseText);
      form.reset();
      // Vuelve a asignar el id del entrenador después de resetear
      marcarEntrenadorId();
      cargarReservasEntrenador();
      form.dataset.editing = "";
    }
  };

  xhr.send(params);
}

// Sobrescribe eliminarReserva en el área de entrenador
function eliminarReserva(id) {
  if (!confirm("¿Estás seguro de que deseas eliminar esta reserva?")) {
    return;
  }
  const xhr = new XMLHttpRequest();
  xhr.open("POST", `${baseURL}/ReservaDeleteServlet`, true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        alert(xhr.responseText);
        cargarReservasEntrenador(); // Recarga solo las reservas del entrenador
      } else {
        alert("Error al eliminar la reserva.");
      }
    }
  };

  xhr.send(`id=${encodeURIComponent(id)}`);
}

// Sobrescribe editarReserva solo para el área de entrenador
function editarReserva(id, fecha, hora, clienteId, entrenadorId) {
  const form = document.getElementById('formReserva');
  form.reset();
  form.dataset.editing = id;
  form.style.display = 'block';
  
  // Solo cargar clientes, el entrenador es el actual y ya está en el select
  cargarClientes();

  setTimeout(() => {
    form.querySelector('input[name="fecha"]').value = fecha;
    form.querySelector('select[name="cliente_id"]').value = clienteId;
    form.querySelector('select[name="entrenador_id"]').value = entrenadorId;

    setTimeout(() => {
      cargarHorasDisponibles();
      form.querySelector('select[name="hora"]').value = hora;
    }, 100);
  }, 100);
}

// Llama a la función al cargar la página
cargarReservasEntrenador();
mostrarFormularioReserva();
marcarEntrenadorId();