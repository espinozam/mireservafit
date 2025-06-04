

//funcion para agregar un entrenador
function agregarEntrenador() {
    const nombre = document.getElementById('nombre').value;
    const email = document.getElementById('email').value;

    if (!nombre || !email) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${baseURL}/EntrenadorAddServlet`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                cargarEntrenadores(); // Recargar la lista de entrenadores
                document.getElementById('formEntrenador').reset();
            } else {
                alert("Error al agregar entrenador.");
                console.error("Error:", xhr.responseText);
            }
        }
    };

    const params = `nombre=${encodeURIComponent(nombre)}&email=${encodeURIComponent(email)}`;
    xhr.send(params);
}

//funcion para cargar la lista de entrenadores
// Esta función se llama al cargar la página
function cargarEntrenadores() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `${baseURL}/EntrenadorListServlet`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const entrenadores = JSON.parse(xhr.responseText);
                const listaEntrenadores = document.getElementById('listaEntrenadores');
                listaEntrenadores.innerHTML = ''; // Limpiar la lista antes de agregar nuevos elementos

                entrenadores.forEach(entrenador => {
                    const li = document.createElement('li');
                    li.textContent = `ID: ${entrenador.id}, Nombre: ${entrenador.nombre}, Email: ${entrenador.email}`;
                    listaEntrenadores.appendChild(li);
                });
            } else {
                alert("Error al cargar entrenadores.");
                console.error("Error:", xhr.responseText);
            }
        }
    };
    xhr.send();
}

function eliminarEntrenador(id) {
    mostrarFormularioEditar();
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
