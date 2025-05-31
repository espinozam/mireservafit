const baseURL = 'http://localhost:8080/MiReservaFit';

// Cargar lista de entrenadores en tabla
window.onload = function () {
    cargarEntrenadores();

    const formulario = document.getElementById('formEntrenador');
    if (formulario) {
        formulario.onsubmit = agregarEntrenador;
    }
};

function cargarEntrenadores() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `${baseURL}/EntrenadorListServlet`, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            const contenedor = document.getElementById('listaEntrenadores');
            if (xhr.status === 200) {
                contenedor.innerHTML = xhr.responseText;
            } else {
                console.error('Error al cargar entrenadores:', xhr.statusText);
                contenedor.innerText = 'Error al cargar los entrenadores.';
            }
        }
    };

    xhr.send();
}


console.log("Formulario a enviar:");
function agregarEntrenador(event) {
    event.preventDefault();

    const nombre = document.querySelector('#formEntrenador input[name="nombre"]').value;
    const especialidad = document.querySelector('#formEntrenador input[name="especialidad"]').value;

    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${baseURL}/EntrenadorAddServlet`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                cargarEntrenadores();
                document.getElementById('formEntrenador').reset();
            } else {
                alert("Error al agregar entrenador.");
                console.error("Error:", xhr.responseText);
            }
        }
    };

    const params = `nombre=${encodeURIComponent(nombre)}&especialidad=${encodeURIComponent(especialidad)}`;
    xhr.send(params);
}




function eliminarEntrenador(id) {
    //mostrarFormularioEditar();
    if (confirm("¿Estás seguro de que deseas eliminar este entrenador?")) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", `${baseURL}/EntrenadorDeleteServlet`, true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    alert(xhr.responseText);
                    cargarEntrenadores(); // Recargar la lista de entrenadores
                } else {
                    alert("Error al eliminar entrenador.");
                    console.error("Error:", xhr.responseText);
                }
            }
        };

        const params = `id=${encodeURIComponent(id)}`;
        xhr.send(params);
    }
}

// Cargar la lista de Entrenadores al inicio
window.onload = function () {
    cargarEntrenadores();
};


function mostrarFormularioAgregar() {
    document.getElementById('formEntrenador').style.display = 'block';
}

// Muestra el formulario de edición con los datos del entrenador
function editarEntrenador(id, nombre, especialidad) {
    document.getElementById('editarId').value = id;
    document.getElementById('editarNombre').value = nombre;
    document.getElementById('editarEspecialidad').value = especialidad;
    document.getElementById('formEditarEntrenador').style.display = 'block';
}

// Guarda los cambios al hacer submit en el formulario de edición
function guardarEdicionEntrenador(event) {
    event.preventDefault();

    const id = document.getElementById('editarId').value;
    const nombre = document.getElementById('editarNombre').value;
    const especialidad = document.getElementById('editarEspecialidad').value;

    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${baseURL}/EntrenadorUpdateServlet`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                document.getElementById('formEditarEntrenador').reset();
                document.getElementById('formEditarEntrenador').style.display = 'none';
                cargarEntrenadores();
            } else {
                alert("Error al actualizar entrenador.");
                console.error("Error:", xhr.responseText);
            }
        }
    };

    const params = `id=${encodeURIComponent(id)}&nombre=${encodeURIComponent(nombre)}&especialidad=${encodeURIComponent(especialidad)}`;
    xhr.send(params);
}