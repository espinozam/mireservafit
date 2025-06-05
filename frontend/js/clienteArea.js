const baseURL = "http://localhost:8080/MiReservaFit";
const usuarioEmail = localStorage.getItem("usuarioEmail");

// Muestra el email en el panel
document.getElementById("infoCliente").innerHTML = `
    <p>Bienvenido, ${usuarioEmail}</p>
    <button id="cerrarSesionBtn">Cerrar sesión</button>
`;

// Botón cerrar sesión
document.getElementById("cerrarSesionBtn").onclick = function() {
    localStorage.removeItem("usuarioEmail");
    localStorage.removeItem("tipoUsuario");
    window.location.href = "index.html";
};

function cargarReservasCliente() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `${baseURL}/ReservaClienteServlet?email=${encodeURIComponent(usuarioEmail)}`, true);

    xhr.onreadystatechange = function () {
    const contenedor = document.getElementById('reservasCliente');
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
        contenedor.innerHTML = xhr.responseText;
        } else {
        contenedor.innerText = 'Error al cargar las reservas.';
        }
    }
    };

    xhr.send();
}

// Llama a la función al cargar la página
cargarReservasCliente();