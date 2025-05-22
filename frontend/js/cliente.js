const baseURL = 'http://localhost:8080/MiReservaFit';

// Cargar lista de clientes en tabla
window.onload = function () {
    cargarClientes();

    const formulario = document.getElementById('formCliente');
    if (formulario) {
        formulario.onsubmit = agregarCliente;
    }
};

function cargarClientes() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `${baseURL}/ClienteListServlet`, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            const contenedor = document.getElementById('listaClientes');
            if (xhr.status === 200) {
                contenedor.innerHTML = xhr.responseText;
            } else {
                console.error('Error al cargar clientes:', xhr.statusText);
                contenedor.innerText = 'Error al cargar los clientes.';
            }
        }
    };

    xhr.send();
}


console.log("Formulario a enviar:");
function agregarCliente(event) {
    event.preventDefault();

    const nombre = document.querySelector('#formCliente input[name="nombre"]').value;
    const email = document.querySelector('#formCliente input[name="email"]').value;

    alert("Enviando:", nombre, email);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${baseURL}/ClienteAddServlet`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                cargarClientes();
                document.getElementById('formCliente').reset();
            } else {
                alert("Error al agregar cliente.");
                console.error("Error:", xhr.responseText);
            }
        }
    };

    const params = `nombre=${encodeURIComponent(nombre)}&email=${encodeURIComponent(email)}`;
    xhr.send(params);
}




function eliminarCliente(id) {
    //mostrarFormularioEditar();
    if (confirm("¿Estás seguro de que deseas eliminar este cliente?")) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", `${baseURL}/ClienteDeleteServlet`, true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    alert(xhr.responseText);
                    cargarClientes(); // Recargar la lista de clientes
                } else {
                    alert("Error al eliminar cliente.");
                    console.error("Error:", xhr.responseText);
                }
            }
        };

        const params = `id=${encodeURIComponent(id)}`;
        xhr.send(params);
    }
}

// Cargar la lista de clientes al inicio
window.onload = function () {
    cargarClientes();
};


function mostrarFormularioAgregar() {
    document.getElementById('formCliente').style.display = 'block';
}

// Muestra el formulario de edición con los datos del cliente
function editarCliente(id, nombre, email) {
    document.getElementById('editarId').value = id;
    document.getElementById('editarNombre').value = nombre;
    document.getElementById('editarEmail').value = email;
    document.getElementById('formEditarCliente').style.display = 'block';
}

// Guarda los cambios al hacer submit en el formulario de edición
function guardarEdicionCliente(event) {
    event.preventDefault();

    const id = document.getElementById('editarId').value;
    const nombre = document.getElementById('editarNombre').value;
    const email = document.getElementById('editarEmail').value;

    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${baseURL}/ClienteUpdateServlet`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                document.getElementById('formEditarCliente').reset();
                document.getElementById('formEditarCliente').style.display = 'none';
                cargarClientes();
            } else {
                alert("Error al actualizar cliente.");
                console.error("Error:", xhr.responseText);
            }
        }
    };

    const params = `id=${encodeURIComponent(id)}&nombre=${encodeURIComponent(nombre)}&email=${encodeURIComponent(email)}`;
    xhr.send(params);
}


