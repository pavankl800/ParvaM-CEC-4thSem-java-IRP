/**
 * theatres.js – Theatre CRUD page logic
 */

let theatreList = [];
let deleteTargetId = null;

// ------------------------------------------------------------------ Load
async function loadTheatres() {
  try {
    theatreList = await Api.get('theatres');
    renderTable();
  } catch (e) {
    showAlert('Failed to load theatres: ' + e.message);
  }
}

function renderTable() {
  const tbody = document.getElementById('theatreBody');
  if (theatreList.length === 0) {
    tbody.innerHTML = '<tr><td colspan="6" class="text-center text-muted py-4">No theatres found.</td></tr>';
    return;
  }
  tbody.innerHTML = theatreList.map((t, i) => `
    <tr>
      <td>${i + 1}</td>
      <td class="fw-semibold">${escHtml(t.name)}</td>
      <td>${escHtml(t.location)}</td>
      <td>${t.totalSeats}</td>
      <td>${fmtDate(t.createdAt)}</td>
      <td class="text-center">
        <button class="btn btn-sm btn-outline-primary btn-action me-1"
                onclick="openEdit('${t.id}')">
          <i class="bi bi-pencil"></i>
        </button>
        <button class="btn btn-sm btn-outline-danger btn-action"
                onclick="openDelete('${t.id}')">
          <i class="bi bi-trash"></i>
        </button>
      </td>
    </tr>`).join('');
}

// ------------------------------------------------------------------ Create / Edit
function openCreate() {
  document.getElementById('modalTitle').textContent = 'Add Theatre';
  document.getElementById('theatreId').value   = '';
  document.getElementById('tName').value       = '';
  document.getElementById('tLocation').value   = '';
  document.getElementById('tSeats').value      = '100';
}

function openEdit(id) {
  const t = theatreList.find(x => x.id === id);
  if (!t) return;
  document.getElementById('modalTitle').textContent = 'Edit Theatre';
  document.getElementById('theatreId').value   = t.id;
  document.getElementById('tName').value       = t.name;
  document.getElementById('tLocation').value   = t.location;
  document.getElementById('tSeats').value      = t.totalSeats;
  new bootstrap.Modal(document.getElementById('theatreModal')).show();
}

async function saveTheatre() {
  const id       = document.getElementById('theatreId').value;
  const name     = document.getElementById('tName').value.trim();
  const location = document.getElementById('tLocation').value.trim();
  const seats    = parseInt(document.getElementById('tSeats').value) || 100;

  if (!name || !location) {
    showAlert('Name and Location are required.');
    return;
  }

  const payload = { name, location, totalSeats: seats };

  try {
    if (id) {
      await Api.put(`theatres/${id}`, payload);
      showAlert('Theatre updated successfully.', 'success');
    } else {
      await Api.post('theatres', payload);
      showAlert('Theatre created successfully.', 'success');
    }
    bootstrap.Modal.getInstance(document.getElementById('theatreModal')).hide();
    loadTheatres();
  } catch (e) {
    showAlert('Error: ' + e.message);
  }
}

// ------------------------------------------------------------------ Delete
function openDelete(id) {
  deleteTargetId = id;
  new bootstrap.Modal(document.getElementById('deleteModal')).show();
}

async function confirmDelete() {
  if (!deleteTargetId) return;
  try {
    await Api.delete(`theatres/${deleteTargetId}`);
    showAlert('Theatre deleted.', 'success');
    bootstrap.Modal.getInstance(document.getElementById('deleteModal')).hide();
    loadTheatres();
  } catch (e) {
    showAlert('Error: ' + e.message);
  }
  deleteTargetId = null;
}

// ------------------------------------------------------------------ Init
loadTheatres();
