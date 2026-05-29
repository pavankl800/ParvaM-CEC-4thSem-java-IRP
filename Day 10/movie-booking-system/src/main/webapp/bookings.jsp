<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Bookings – CineBook</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"/>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3><i class="bi bi-ticket-detailed text-danger"></i> Bookings</h3>
    <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#bookingModal" onclick="openCreate()">
      <i class="bi bi-plus-circle"></i> New Booking
    </button>
  </div>

  <!-- Filter by User -->
  <div class="row mb-3">
    <div class="col-md-4">
      <select class="form-select" id="filterUser" onchange="loadBookings()">
        <option value="">All Users</option>
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
            <th>User</th>
            <th>Movie</th>
            <th>Theatre</th>
            <th>Show Time</th>
            <th>Seats</th>
            <th>Amount (₹)</th>
            <th>Status</th>
            <th>Booked At</th>
            <th class="text-center">Actions</th>
          </tr>
        </thead>
        <tbody id="bookingBody">
          <tr><td colspan="10" class="text-center py-4">
            <div class="spinner-border text-danger" role="status"></div>
          </td></tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<!-- New Booking Modal -->
<div class="modal fade" id="bookingModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-danger text-white">
        <h5 class="modal-title">New Booking</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form id="bookingForm">
          <div class="mb-3">
            <label class="form-label">User <span class="text-danger">*</span></label>
            <select class="form-select" id="bUserId" required onchange="updateWalletInfo()">
              <option value="">Select User</option>
            </select>
            <div class="form-text" id="walletInfo"></div>
          </div>
          <div class="mb-3">
            <label class="form-label">Movie <span class="text-danger">*</span></label>
            <select class="form-select" id="bMovieId" required onchange="updateMovieInfo()">
              <option value="">Select Movie</option>
            </select>
            <div class="form-text" id="movieInfo"></div>
          </div>
          <div class="mb-3">
            <label class="form-label">Seats to Book <span class="text-danger">*</span></label>
            <input type="number" class="form-control" id="bSeats" min="1" value="1"
                   oninput="updateTotal()"/>
          </div>
          <div class="alert alert-info py-2" id="totalInfo">Total: ₹0.00</div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button class="btn btn-danger" onclick="saveBooking()">
          <i class="bi bi-ticket-perforated"></i> Confirm Booking
        </button>
      </div>
    </div>
  </div>
</div>

<!-- Cancel Confirm Modal -->
<div class="modal fade" id="cancelModal" tabindex="-1">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header bg-warning">
        <h5 class="modal-title">Cancel Booking</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to cancel this booking?</p>
        <p class="text-success fw-bold" id="refundMsg"></p>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">No</button>
        <button class="btn btn-warning" onclick="confirmCancel()">Yes, Cancel &amp; Refund</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="includes/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/api.js"></script>
<script src="js/bookings.js"></script>
</body>
</html>
