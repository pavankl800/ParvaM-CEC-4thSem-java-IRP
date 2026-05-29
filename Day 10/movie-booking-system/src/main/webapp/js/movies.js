/**
 * movies.js – Movie CRUD page logic
 */

let movieList    = [];
let theatreList  = [];
let deleteTargetId = null;

// ------------------------------------------------------------------ Load
async function init() {
  try {
    theatreList = await Api.get('theatres');
    populateTheatreDropdowns();
    await loadMovies();
  } catch (e) {
    showAlert('Failed to initialise: ' + e.message);
  }
}

function populateTheatreDropdowns() {
  const filterSel  = document.getElementById('filterTheatre');
  const modalSel   = document.getElementById('mTheatreId');

  const opts = theatreList.map(t =>
    `<option value="${t.id}">${escHtml(t.name)}</option>`).join('');

  filterSel.innerHTML = '<option value="">All Theatres</option>' + opts;
  modalSel.innerHTML  = '<option value="">Select Theatre</option>' + opts;
}

async function loadMovies() {
  const theatreId = document.getElementById('filterTheatre').value;
  const path = theatreId ? `movies?theatreId=${theatreId}` : 'movies';
  try {
    movieList = await Api.get(path);
    renderTable();
  } catch (e) {
    showAlert('Failed to load movies: ' + e.message);
  }
}

function renderTable() {
  const tbody = document.getElementById('movieBody');
  if (movieList.length === 0) {
    tbody.innerHTML = '<tr><td colspan="10" class="text-center text-muted py-4">No movies found.</td></tr>';
    return;
  }
  tbody.innerHTML = movieList.map((m, i) => `
    <tr>
      <td>${i + 1}</td>
      <td class="fw-semibold">${escHtml(m.title)}</td>
      <td>${escHtml(m.theatreName || '')}</td>
      <td><span class="badge bg-secondary">${escHtml(m.genre)}</span></td>
      <td>${escHtml(m.language)}</td>
      <td>${m.durationMin} min</td>
      <td>${fmtDate(m.showTime)}</td>
      <td class="text-success fw-bold">₹${m.ticketPrice}</td>
      <td>
        <span class="badge ${m.availableSeats > 0 ? 'bg-success' : 'bg-danger'}">
          ${m.availableSeats}
        </span>
      </td>
      <td class="text-center">
        <button class="btn btn-sm btn-outline-success btn-action me-1"
                onclick="openEdit('${m.id}')">
          <i class="bi bi-pencil"></i>
        </button>
        <button class="btn btn-sm btn-outline-danger btn-action"
                onclick="openDelete('${m.id}')">
          <i class="bi bi-trash"></i>
        </button>
      </td>
    </tr>`).join('');
}

// ------------------------------------------------------------------ Create / Edit
function openCreate() {
  document.getElementById('modalTitle').textContent = 'Add Movie';
  document.getElementById('movieId').value    = '';
  document.getElementById('mTheatreId').value = '';
  document.getElementById('mTitle').value     = '';
  document.getElementById('mGenre').value     = '';
  document.getElementById('mLanguage').value  = 'English';
  document.getElementById('mDuration').value  = '';
  document.getElementById('mShowTime').value  = '';
  document.getElementById('mPrice').value     = '';
  document.getElementById('mSeats').value     = '';
}

function openEdit(id) {
  const m = movieList.find(x => x.id === id);
  if (!m) return;
  document.getElementById('modalTitle').textContent = 'Edit Movie';
  document.getElementById('movieId').value    = m.id;
  document.getElementById('mTheatreId').value = m.theatreId;
  document.getElementById('mTitle').value     = m.title;
  document.getElementById('mGenre').value     = m.genre;
  document.getElementById('mLanguage').value  = m.language;
  document.getElementById('mDuration').value  = m.durationMin;
  // Convert "2025-06-01 18:30:00" → "2025-06-01T18:30"
  document.getElementById('mShowTime').value  = (m.showTime || '').replace(' ', 'T').substring(0, 16);
  document.getElementById('mPrice').value     = m.ticketPrice;
  document.getElementById('mSeats').value     = m.availableSeats;
  new bootstrap.Modal(document.getElementById('movieModal')).show();
}

async function saveMovie() {
  const id          = document.getElementById('movieId').value;
  const theatreId   = document.getElementById('mTheatreId').value;
  const title       = document.getElementById('mTitle').value.trim();
  const genre       = document.getElementById('mGenre').value.trim();
  const language    = document.getElementById('mLanguage').value.trim() || 'English';
  const durationMin = parseInt(document.getElementById('mDuration').value);
  const showTimeRaw = document.getElementById('mShowTime').value;
  const ticketPrice = parseFloat(document.getElementById('mPrice').value);
  const availSeats  = parseInt(document.getElementById('mSeats').value);

  if (!theatreId || !title || !genre || !showTimeRaw || isNaN(durationMin) ||
      isNaN(ticketPrice) || isNaN(availSeats)) {
    showAlert('Please fill all required fields.');
    return;
  }

  // Convert datetime-local "2025-06-01T18:30" → "2025-06-01T18:30:00"
  const showTime = showTimeRaw.length === 16 ? showTimeRaw + ':00' : showTimeRaw;

  const payload = { theatreId, title, genre, language, durationMin, showTime, ticketPrice, availableSeats: availSeats };

  try {
    if (id) {
      await Api.put(`movies/${id}`, payload);
      showAlert('Movie updated successfully.', 'success');
    } else {
      await Api.post('movies', payload);
      showAlert('Movie created successfully.', 'success');
    }
    bootstrap.Modal.getInstance(document.getElementById('movieModal')).hide();
    loadMovies();
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
    await Api.delete(`movies/${deleteTargetId}`);
    showAlert('Movie deleted.', 'success');
    bootstrap.Modal.getInstance(document.getElementById('deleteModal')).hide();
    loadMovies();
  } catch (e) {
    showAlert('Error: ' + e.message);
  }
  deleteTargetId = null;
}

// ------------------------------------------------------------------ Init
init();
