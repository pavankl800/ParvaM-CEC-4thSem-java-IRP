<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Movies – CineBook</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"/>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3><i class="bi bi-camera-reels text-success"></i> Movies</h3>
    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#movieModal" onclick="openCreate()">
      <i class="bi bi-plus-circle"></i> Add Movie
    </button>
  </div>

  <!-- Filter by Theatre -->
  <div class="row mb-3">
    <div class="col-md-4">
      <select class="form-select" id="filterTheatre" onchange="loadMovies()">
        <option value="">All Theatres</option>
      </select>
    </div>
  </div>

  <div id="alertBox" class="d-none"></div>

  <div class="card shadow-sm border-0">
    <div class="card-body p-0">
      <table class="table table-hover mb-0">
        <thead class="table-dark">
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Theatre</th>
            <th>Genre</th>
            <th>Language</th>
            <th>Duration</th>
            <th>Show Time</th>
            <th>Price (₹)</th>
            <th>Seats</th>
            <th class="text-center">Actions</th>
          </tr>
        </thead>
        <tbody id="movieBody">
          <tr><td colspan="10" class="text-center py-4">
            <div class="spinner-border text-success" role="status"></div>
          </td></tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<!-- Movie Modal -->
<div class="modal fade" id="movieModal" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title" id="modalTitle">Add Movie</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form id="movieForm">
          <input type="hidden" id="movieId"/>
          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Theatre <span class="text-danger">*</span></label>
              <select class="form-select" id="mTheatreId" required>
                <option value="">Select Theatre</option>
              </select>
            </div>
            <div class="col-md-6">
              <label class="form-label">Title <span class="text-danger">*</span></label>
              <input type="text" class="form-control" id="mTitle" required/>
            </div>
            <div class="col-md-4">
              <label class="form-label">Genre <span class="text-danger">*</span></label>
              <input type="text" class="form-control" id="mGenre" placeholder="Action, Drama…" required/>
            </div>
            <div class="col-md-4">
              <label class="form-label">Language</label>
              <input type="text" class="form-control" id="mLanguage" value="English"/>
            </div>
            <div class="col-md-4">
              <label class="form-label">Duration (min) <span class="text-danger">*</span></label>
              <input type="number" class="form-control" id="mDuration" min="1" required/>
            </div>
            <div class="col-md-4">
              <label class="form-label">Show Time <span class="text-danger">*</span></label>
              <input type="datetime-local" class="form-control" id="mShowTime" required/>
            </div>
            <div class="col-md-4">
              <label class="form-label">Ticket Price (₹) <span class="text-danger">*</span></label>
              <input type="number" class="form-control" id="mPrice" min="1" step="0.01" required/>
            </div>
            <div class="col-md-4">
              <label class="form-label">Available Seats <span class="text-danger">*</span></label>
              <input type="number" class="form-control" id="mSeats" min="0" required/>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button class="btn btn-success" onclick="saveMovie()">
          <i class="bi bi-save"></i> Save
        </button>
      </div>
    </div>
  </div>
</div>

<!-- Delete Confirm Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title">Confirm Delete</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">Are you sure you want to delete this movie?</div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button class="btn btn-danger" onclick="confirmDelete()">Delete</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="includes/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/api.js"></script>
<script src="js/movies.js"></script>
</body>
</html>
