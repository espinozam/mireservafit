const baseURL = "http://localhost:8080/MiReservaFit";

function loginUsuario() {
  const tipo = document.querySelector('input[name="tipoUsuario"]:checked').value;
  const usuario = document.getElementById('usuario').value;
  const password = document.getElementById('password').value;

  if (tipo && usuario && password) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${baseURL}/LoginServlet`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        console.log("Respuesta del backend:", xhr.responseText); // <-- Añade esto
        if (xhr.status === 200 && xhr.responseText.trim() === "ok") {
          // Guarda el email y tipo de usuario en localStorage
          localStorage.setItem("usuarioEmail", usuario);
          localStorage.setItem("tipoUsuario", tipo);
          
          window.location.href = tipo === 'cliente' ? 'clienteArea.html' : 'entrenadorArea.html';
        } else {
          alert("Usuario o contraseña incorrectos. Respuesta: " + xhr.responseText.trim());
        }
      }
    };
    xhr.send("tipo=" + encodeURIComponent(tipo) + "&usuario=" + encodeURIComponent(usuario) + "&password=" + encodeURIComponent(password));
  } else {
    alert('Completa todos los campos.');
  }
  return false;
}

function registrarUsuario() {
  const tipo = document.querySelector('input[name="tipoRegistro"]:checked').value;
  const usuario = document.getElementById('nuevoUsuario').value;
  const password = document.getElementById('nuevoPassword').value;
  if (tipo && usuario && password) {
    alert('Registro exitoso como ' + tipo + '. Ahora puedes iniciar sesión.');
  } else {
    alert('Completa todos los campos para registrarte.');
  }
  return false;
}