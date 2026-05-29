/**
 * users.js – User CRUD page logic
 */

let userList = [];
let deleteTargetId = null;

// ------------------------------------------------------------------ Load
async function loadUsers() {
  try {
    userList = await Api.get('users');
    renderTable();
  } catch (e) {
    showAlert('Failed to load users: ' + e.message);
  }
}

function renderTable() {
  const tbody = document.getElementById('userBody');
  if (userList.length === 0) {
    tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted py-4">No users found.</td></tr>';
    return;
  }
  tbody.innerHTML = userList.map((u, i) => `
    <tr>
      <td>${i + 1}</td>
      <td class="fw-semibold">${escHtml(u.name)}</td>
      <td>${escHtml(u.email)}</td>
      <td>${escHtml(u.phone)}</td>
      <td class="text-success fw-bold">₹${(u.walletBalance || 0).toFixed(2)}</td>
      <td>${fmtDate(u.createdAt)}</td>
      <td class="text-center">
        <button class="btn btn-sm btn-outline-success btn-action me-1"
                title="Top-up Wallet"
                onclick="openWallet('${u.id}')">
          <i class="bi bi-wallet2"></i>
        </button>
        <button class="btn btn-sm btn-outline-warning btn-action me-1"
                onclick="openEdit('${u.id}')">
          <i class="bi bi-pencil"></i>
        </button>
        <button class="btn btn-sm btn-outline-danger btn-action"
                onclick="openDelete('${u.id}')">
          <i class="bi bi-trash"></i>
        </button>
      </td>
    </tr>`).join('');
}

// ------------------------------------------------------------------ Create / Edit
function openCreate() {
  document.getElementById('modalTitle').textContent = 'Add User';
  document.getElementById('userId').value    = '';
  document.getElementById('uName').value     = '';
  document.getElementById('uEmail').value    = '';
  document.getElementById('uPhone').value    = '';
  document.getElementById('uPassword').value = '';
  document.getElementById('uWallet').value   = '0';
  document.getElementById('uPassword').required = true;
}

function openEdit(id) {
  const u = userList.find(x => x.id === id);
  if (!u) return;
  document.getElementById('modalTitle').textContent = 'Edit User';
  document.getElementById('userId').value    = u.id;
  document.getElementById('uName').value     = u.name;
  document.getElementById('uEmail').value    = u.email;
  document.getElementById('uPhone').value    = u.phone;
  document.getElementById('uPassword').value = '';
  document.getElementById('uWallet').value   = u.walletBalance || 0;
  document.getElementById('uPassword').required = false;
  new bootstrap.Modal(document.getElementById('userModal')).show();
}

async function saveUser() {
  const id       = document.getElementById('userId').value;
  const name     = document.getElementById('uName').value.trim();
  const email    = document.getElementById('uEmail').value.trim();
  const phone    = document.getElementById('uPhone').value.trim();
  const password = document.getElementById('uPassword').value;
  const wallet   = parseFloat(document.getElementById('uWallet').value) || 0;

  if (!name || !email || !phone) {
    showAlert('Name, Email and Phone are required.');
    return;
  }
  if (!id && !password) {
    showAlert('Password is required for new users.');
    return;
  }

  const payload = { name, email, phone, walletBalance: wallet };
  if (password) payload.passwordHash = password;

  try {
    if (id) {
      await Api.put(`users/${id}`, payload);
      showAlert('User updated successfully.', 'success');
    } else {
      await Api.post('users', payload);
      showAlert('User created successfully.', 'success');
    }
    bootstrap.Modal.getInstance(document.getElementById('userModal')).hide();
    loadUsers();
  } catch (e) {
    showAlert('Error: ' + e.message);
  }
}

// ------------------------------------------------------------------ Wallet Top-up
function openWallet(id) {
  document.getElementById('walletUserId').value = id;
  document.getElementById('walletAmount').value = '';
  new bootstrap.Modal(document.getElementById('walletModal')).show();
}

async function topUpWallet() {
  const userId = document.getElementById('walletUserId').value;
  const amount = parseFloat(document.getElementById('walletAmount').value);

  if (!amount || amount <= 0) {
    showAlert('Please enter a valid amount.');
    return;
  }

  try {
    await Api.put(`users/${userId}/wallet`, { amount });
    showAlert('Wallet topped up successfully.', 'success');
    bootstrap.Modal.getInstance(document.getElementById('walletModal')).hide();
    loadUsers();
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
    await Api.delete(`users/${deleteTargetId}`);
    showAlert('User deleted.', 'success');
    bootstrap.Modal.getInstance(document.getElementById('deleteModal')).hide();
    loadUsers();
  } catch (e) {
    showAlert('Error: ' + e.message);
  }
  deleteTargetId = null;
}

// ------------------------------------------------------------------ Init
loadUsers();
