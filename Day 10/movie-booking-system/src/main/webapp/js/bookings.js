/**
 * bookings.js – Booking page logic (create + cancel with wallet refund)
 */

let bookingList  = [];
let userList     = [];
let movieList    = [];
let cancelTargetId = null;

// ------------------------------------------------------------------ Init
async function init() {
  try {
    [userList, movieList] = await Promise.all([
      Api.get('users'),
      Api.get('movies')
    ]);
    populateDropdowns();
    await loadBookings();

    // Pre-select movie from query param (e.g. from index.jsp "Book Now")
    const params  = new URLSearchParams(window.location.search);
    const movieId = params.get('movieId');
    if (movieId) {
      document.getElementById('bMovieId').value = movieId;
      updateMovieInfo();
      new bootstrap.Modal(document.getElementById('bookingModal')).show();
    }
  } catch (e) {
    showAlert('Failed to initialise: ' + e.message);
  }
}

function populateDropdowns() {
  const userSel  = document.getElementById('bUserId');
  const movieSel = document.getElementById('bMovieId');
  const filterSel = document.getElementById('filterUser');

  const userOpts = userList.map(u =>
    `<option value="${u.id}" data-wallet="${u.walletBalance}">${escHtml(u.name)} (₹${(u.walletBalance||0).toFixed(2)})</option>`
  ).join('');

  const movieOpts = movieList.map(m =>
    `<option value="${m.id}" data-price="${m.ticketPrice}" data-seats="${m.availableSeats}">
      ${escHtml(m.title)} – ${escHtml(m.theatreName||'')} (₹${m.ticketPrice}, ${m.availableSeats} seats)
    </option>`
  ).join('');

  userSel.innerHTML  = '<option value="">Select User</option>'  + userOpts;
  movieSel.innerHTML = '<option value="">Select Movie</option>' + movieOpts;
  filterSel.innerHTML = '<option value="">All Users</option>'   + userList.map(u =>
    `<option value="${u.id}">${escHtml(u.name)}</option>`).join('');
}

// ------------------------------------------------------------------ Load Bookings
async function loadBookings() {
  const userId = document.getElementById('filterUser').value;
  const path   = userId ? `bookings?userId=${userId}` : 'bookings';
  try {
    bookingList = await Api.get(path);
    renderTable();
  } catch (e) {
    showAlert('Failed to load bookings: ' + e.message);
  }
}

function renderTable() {
  const tbody = document.getElementById('bookingBody');
  if (bookingList.length === 0) {
    tbody.innerHTML = '<tr><td colspan="10" class="text-center text-muted py-4">No bookings found.</td></tr>';
    return;
  }
  tbody.innerHTML = bookingList.map((b, i) => {
    const statusBadge = b.status === 'CONFIRMED'
      ? '<span class="badge-confirmed">CONFIRMED</span>'
      : '<span class="badge-cancelled">CANCELLED</span>';

    const cancelBtn = b.status === 'CONFIRMED'
      ? `<button class="btn btn-sm btn-outline-warning btn-action"
                 onclick="openCancel('${b.id}', ${b.totalAmount})"
                 title="Cancel & Refund">
           <i class="bi bi-x-circle"></i>
         </button>`
      : '<span class="text-muted small">–</span>';

    return `
      <tr>
        <td>${i + 1}</td>
        <td>${escHtml(b.userName)}</td>
        <td class="fw-semibold">${escHtml(b.movieTitle)}</td>
        <td>${escHtml(b.theatreName)}</td>
        <td>${fmtDate(b.showTime)}</td>
        <td>${b.seatsBooked}</td>
        <td class="text-success fw-bold">₹${b.totalAmount.toFixed(2)}</td>
        <td>${statusBadge}</td>
        <td>${fmtDate(b.bookedAt)}</td>
        <td class="text-center">${cancelBtn}</td>
      </tr>`;
  }).join('');
}

// ------------------------------------------------------------------ New Booking
function openCreate() {
  document.getElementById('bUserId').value  = '';
  document.getElementById('bMovieId').value = '';
  document.getElementById('bSeats').value   = '1';
  document.getElementById('walletInfo').textContent = '';
  document.getElementById('movieInfo').textContent  = '';
  document.getElementById('totalInfo').textContent  = 'Total: ₹0.00';
}

function updateWalletInfo() {
  const sel = document.getElementById('bUserId');
  const opt = sel.options[sel.selectedIndex];
  const wallet = opt ? opt.dataset.wallet : null;
  document.getElementById('walletInfo').textContent =
    wallet ? `Wallet Balance: ₹${parseFloat(wallet).toFixed(2)}` : '';
  updateTotal();
}

function updateMovieInfo() {
  const sel = document.getElementById('bMovieId');
  const opt = sel.options[sel.selectedIndex];
  if (opt && opt.dataset.price) {
    document.getElementById('movieInfo').textContent =
      `Price: ₹${opt.dataset.price} | Available Seats: ${opt.dataset.seats}`;
  } else {
    document.getElementById('movieInfo').textContent = '';
  }
  updateTotal();
}

function updateTotal() {
  const movieSel = document.getElementById('bMovieId');
  const opt      = movieSel.options[movieSel.selectedIndex];
  const price    = opt ? parseFloat(opt.dataset.price) || 0 : 0;
  const seats    = parseInt(document.getElementById('bSeats').value) || 0;
  document.getElementById('totalInfo').textContent = `Total: ₹${(price * seats).toFixed(2)}`;
}

async function saveBooking() {
  const userId     = document.getElementById('bUserId').value;
  const movieId    = document.getElementById('bMovieId').value;
  const seatsBooked = parseInt(document.getElementById('bSeats').value);

  if (!userId || !movieId || !seatsBooked || seatsBooked < 1) {
    showAlert('Please select a user, movie and enter valid seat count.');
    return;
  }

  try {
    const booking = await Api.post('bookings', { userId, movieId, seatsBooked });
    showAlert(`Booking confirmed! ID: ${booking.id}`, 'success');
    bootstrap.Modal.getInstance(document.getElementById('bookingModal')).hide();
    // Refresh dropdowns (wallet balance changed)
    userList  = await Api.get('users');
    movieList = await Api.get('movies');
    populateDropdowns();
    loadBookings();
  } catch (e) {
    showAlert('Booking failed: ' + e.message);
  }
}

// ------------------------------------------------------------------ Cancel
function openCancel(id, amount) {
  cancelTargetId = id;
  document.getElementById('refundMsg').textContent =
    `₹${parseFloat(amount).toFixed(2)} will be refunded to the user's wallet.`;
  new bootstrap.Modal(document.getElementById('cancelModal')).show();
}

async function confirmCancel() {
  if (!cancelTargetId) return;
  try {
    await Api.put(`bookings/${cancelTargetId}/cancel`, {});
    showAlert('Booking cancelled and amount refunded to wallet.', 'success');
    bootstrap.Modal.getInstance(document.getElementById('cancelModal')).hide();
    // Refresh
    userList  = await Api.get('users');
    movieList = await Api.get('movies');
    populateDropdowns();
    loadBookings();
  } catch (e) {
    showAlert('Cancellation failed: ' + e.message);
  }
  cancelTargetId = null;
}

// ------------------------------------------------------------------ Init
init();
