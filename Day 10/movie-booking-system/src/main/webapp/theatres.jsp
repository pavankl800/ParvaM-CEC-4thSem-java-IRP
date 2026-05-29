<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Theatres – CineBook</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"/>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3><i class="bi bi-building text-primary"></i> Theatres</h3>
    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#theatreModal" onclick="openCreate()">
      <i class="bi bi-plus-circle"></i> Add Theatre
    </button>
  </div>

  <!-- Alert -->
  <div id="alertBox" class="d-none"></div>

  <!-- Table -->
  <div class="card shadow-sm border-0">
    <div class="card-body p-0">
      <table class="table table-hover mb-0" id="theatreTable">
        <thead class="table-dark">
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Location</th>
            <th>Total Seats</th>
            <th>Created</th>
            <th class="text-center">Actions</th>
          </tr>
        </thead>
        <tbody id="theatreBody">
          <tr><td colspan="6" class="text-center py-4">
            <div class="spinner-border text-primary" role="status"></div>
          </td></tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="theatreModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title" id="modalTitle">Add Theatre</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form id="theatreForm">
          <input type="hidden" id="theatreId"/>
          <div class="mb-3">
            <label class="form-label">Name <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="tName" required/>
          </div>
          <div class="mb-3">
            <label class="form-label">Location <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="tLocation" required/>
          </div>
          <div class="mb-3">
            <label class="form-label">Total Seats</label>
            <input type="number" class="form-control" id="tSeats" value="100" min="1"/>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button class="btn btn-primary" onclick="saveTheatre()">
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
      <div class="modal-body">Are you sure you want to delete this theatre?</div>
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
<script src="js/theatres.js"></script>
</body>
</html>
