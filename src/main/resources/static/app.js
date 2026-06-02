async function listarBibliotecas() {
  const tbody = document.querySelector('#biblioteca-table tbody');
  tbody.innerHTML = '<tr><td colspan="5">Cargando...</td></tr>';
  try {
    const res = await fetch('/bibliotecas');
    if (!res.ok) throw new Error('Error al obtener bibliotecas');
    const data = await res.json();
    if (!Array.isArray(data) || data.length === 0) {
      tbody.innerHTML = '<tr><td colspan="5">No hay bibliotecas registradas.</td></tr>';
      return;
    }
    tbody.innerHTML = data.map(b => `
      <tr>
        <td>${b.id}</td>
        <td>${escapeHtml(b.nombre)}</td>
        <td>${escapeHtml(b.direccion)}</td>
        <td>${escapeHtml(b.telefono)}</td>
        <td>${escapeHtml(b.responsable)}</td>
      </tr>
    `).join('');
  } catch (e) {
    tbody.innerHTML = `<tr><td colspan="5">${e.message}</td></tr>`;
  }
}

function escapeHtml(s) {
  if (!s) return '';
  return s.replaceAll('&','&amp;').replaceAll('<','&lt;').replaceAll('>','&gt;').replaceAll('"','&quot;');
}

async function crearBiblioteca(event) {
  event.preventDefault();
  const nombre = document.getElementById('nombre').value.trim();
  const direccion = document.getElementById('direccion').value.trim();
  const telefono = document.getElementById('telefono').value.trim();
  const responsable = document.getElementById('responsable').value.trim();
  const msg = document.getElementById('message');
  msg.textContent = '';
  if (!nombre || !direccion || !telefono || !responsable) {
    msg.textContent = 'Completa todos los campos.';
    return;
  }
  const payload = { nombre, direccion, telefono, responsable };
  try {
    const res = await fetch('/bibliotecas', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (res.status === 201 || res.ok) {
      msg.textContent = 'Biblioteca creada correctamente.';
      document.getElementById('biblioteca-form').reset();
      await listarBibliotecas();
    } else {
      const text = await res.text();
      msg.textContent = 'Error: ' + (text || res.status);
    }
  } catch (e) {
    msg.textContent = 'Error de conexión: ' + e.message;
  }
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('biblioteca-form').addEventListener('submit', crearBiblioteca);
  listarBibliotecas();
});
