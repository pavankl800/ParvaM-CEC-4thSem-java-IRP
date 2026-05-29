<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Users – CineBook</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"/>
  <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="container mt-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3><i class="bi bi-people text-warning"></i> Users</h3>
    <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#userModal" onclick="openCreate()">
      <i class="bi bi-person-plus"></i> Add User
    </button>
  </div>

  <div id="alertBox" class="d-none"></div>

  <div class="card shadow-sm border-0">
    <div class="card-body p-0">
      <table class="table table-hover mb-0">
        <thead class="table-dark">
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Wallet (₹)</th>
            <th>Registered</th>
            <th class="text-center">Actions</th>
          </tr>
        </thead>
        <tbody id="userBody">
          <tr><td colspan="7" class="text-center py-4">
            <div class="spinner-border text-warning" role="status"></div>
          </td></tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<!-- User Modal -->
<div class="modal fade" id="userModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-warning">
        <h5 class="modal-title" id="modalTitle">Add User</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <form id="userForm">
          <input type="hidden" id="userId"/>
          <div class="mb-3">
            <label class="form-label">Name <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="uName" required/>
          </div>
          <div class="mb-3">
            <label class="form-label">Email <span class="text-danger">*</span></label>
            <input type="email" class="form-control" id="uEmail" required/>
          </div>
          <div class="mb-3">
            <label class="form-label">Phone <span class="text-danger">*</span></label>
            <input type="text" class="form-control" id="uPhone" required/>
          </div>
          <div class="mb-3" id="passwordField">
            <label class="form-label">Password <span class="text-danger">*</span></label>
            <input type="password" class="form-control" id="uPassword"/>
            <div class="form-text">Leave blank when editing to keep existing password.</div>
          </div>
          <div class="mb-3">
            <label class="form-label">Wallet Balance (₹)</label>
            <input type="number" class="form-control" id="uWallet" value="0" min="0" step="0.01"/>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button class="btn btn-warning" onclick="saveUser()">
          <i class="bi bi-save"></i> Save
        </button>
      </div>
    </div>
  </div>
</div>

<!-- Wallet Top-up Modal -->
<div class="modal fade" id="walletModal" tabindex="-1">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header bg-success text-white">
        <h5 class="modal-title">Top-up Wallet</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" id="walletUserId"/>
        <label class="form-label">Amount (₹) <span class="text-danger">*</span></label>
        <input type="number" class="form-control" id="walletAmount" min="1" step="0.01" placeholder="Enter amount"/>
      </div>
      <div class="modal-footer">
        <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        <button class="btn btn-success" onclick="topUpWallet()">
          <i class="bi bi-wallet2"></i> Add Funds
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
      <div class="modal-body">Are you sure you want to delete this user?</div>
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
<script src="js/users.js"></script>
</body>
</html>
