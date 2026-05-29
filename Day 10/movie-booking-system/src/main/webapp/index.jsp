<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Movie Booking System</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"/>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>

<%@ include file="includes/navbar.jsp" %>

<!-- Hero -->
<div class="hero-section text-white text-center py-5 mb-4">
  <div class="container">
    <h1 class="display-4 fw-bold"><i class="bi bi-film"></i> Movie Booking System</h1>
    <p class="lead">Book your favourite movies at the best theatres near you.</p>
    <a href="movies.jsp" class="btn btn-warning btn-lg me-2">
      <i class="bi bi-ticket-perforated"></i> Browse Movies
    </a>
    <a href="bookings.jsp" class="btn btn-outline-light btn-lg">
      <i class="bi bi-journal-check"></i> My Bookings
    </a>
  </div>
</div>

<!-- Quick Stats -->
<div class="container mb-5">
  <div class="row g-4 text-center" id="statsRow">
    <div class="col-md-3">
      <div class="card shadow-sm border-0 h-100">
        <div class="card-body">
          <i class="bi bi-building fs-1 text-primary"></i>
          <h3 class="mt-2" id="statTheatres">–</h3>
          <p class="text-muted mb-0">Theatres</p>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card shadow-sm border-0 h-100">
        <div class="card-body">
          <i class="bi bi-camera-reels fs-1 text-success"></i>
          <h3 class="mt-2" id="statMovies">–</h3>
          <p class="text-muted mb-0">Movies</p>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card shadow-sm border-0 h-100">
        <div class="card-body">
          <i class="bi bi-people fs-1 text-warning"></i>
          <h3 class="mt-2" id="statUsers">–</h3>
          <p class="text-muted mb-0">Users</p>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card shadow-sm border-0 h-100">
        <div class="card-body">
          <i class="bi bi-ticket-detailed fs-1 text-danger"></i>
          <h3 class="mt-2" id="statBookings">–</h3>
          <p class="text-muted mb-0">Bookings</p>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Latest Movies -->
<div class="container mb-5">
  <h4 class="mb-3"><i class="bi bi-star-fill text-warning"></i> Now Showing</h4>
  <div class="row g-3" id="latestMovies">
    <div class="col-12 text-center text-muted py-4">
      <div class="spinner-border text-primary" role="status"></div>
    </div>
  </div>
</div>

<%@ include file="includes/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/api.js"></script>
<script>
  // Load stats
  Promise.all([
    Api.get('theatres'),
    Api.get('movies'),
    Api.get('users'),
    Api.get('bookings')
  ]).then(([theatres, movies, users, bookings]) => {
    document.getElementById('statTheatres').textContent = theatres.length;
    document.getElementById('statMovies').textContent   = movies.length;
    document.getElementById('statUsers').textContent    = users.length;
    document.getElementById('statBookings').textContent = bookings.length;

    // Show latest 6 movies
    const container = document.getElementById('latestMovies');
    container.innerHTML = '';
    const latest = movies.slice(0, 6);
    if (latest.length === 0) {
      container.innerHTML = '<div class="col-12 text-center text-muted">No movies available yet.</div>';
      return;
    }
    latest.forEach(m => {
      container.innerHTML += `
        <div class="col-md-4 col-lg-2">
          <div class="card h-100 shadow-sm border-0">
            <div class="card-body text-center">
              <i class="bi bi-camera-reels fs-2 text-primary"></i>
              <h6 class="mt-2 fw-bold">${escHtml(m.title)}</h6>
              <span class="badge bg-secondary">${escHtml(m.genre)}</span>
              <p class="small text-muted mt-1 mb-1">${escHtml(m.theatreName || '')}</p>
              <p class="small text-success fw-bold">₹${m.ticketPrice}</p>
              <p class="small text-muted">${escHtml(m.availableSeats)} seats left</p>
              <a href="bookings.jsp?movieId=${m.id}" class="btn btn-sm btn-primary w-100">Book Now</a>
            </div>
          </div>
        </div>`;
    });
  }).catch(() => {
    document.getElementById('latestMovies').innerHTML =
      '<div class="col-12 text-center text-danger">Failed to load movies.</div>';
  });

  function escHtml(str) {
    if (!str) return '';
    return str.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
  }
</script>
</body>
</html>
