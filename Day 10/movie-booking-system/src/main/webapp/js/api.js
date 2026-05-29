/**
 * api.js – Thin wrapper around fetch() for the CineBook REST API.
 * Base URL is derived from the current page's context path.
 */
const Api = (() => {
  const base = `${window.location.origin}${window.location.pathname.replace(/\/[^/]*$/, '')}/api`;

  async function request(method, path, body) {
    const opts = {
      method,
      headers: { 'Content-Type': 'application/json' }
    };
    if (body !== undefined) opts.body = JSON.stringify(body);

    const res = await fetch(`${base}/${path}`, opts);

    // 204 No Content
    if (res.status === 204) return null;

    const data = await res.json();
    if (!res.ok) {
      const msg = data.error || `HTTP ${res.status}`;
      throw new Error(msg);
    }
    return data;
  }

  return {
    get:    (path)        => request('GET',    path),
    post:   (path, body)  => request('POST',   path, body),
    put:    (path, body)  => request('PUT',    path, body),
    delete: (path)        => request('DELETE', path)
  };
})();

/** Escape HTML to prevent XSS */
function escHtml(str) {
  if (str === null || str === undefined) return '';
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

/** Show a Bootstrap alert inside #alertBox */
function showAlert(message, type = 'danger') {
  const box = document.getElementById('alertBox');
  if (!box) return;
  box.className = `alert alert-${type} alert-dismissible fade show`;
  box.innerHTML = `${escHtml(message)}
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>`;
  box.classList.remove('d-none');
  setTimeout(() => { box.classList.add('d-none'); }, 5000);
}

/** Format a datetime string for display */
function fmtDate(str) {
  if (!str) return '–';
  try {
    return new Date(str).toLocaleString('en-IN', {
      day: '2-digit', month: 'short', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  } catch { return str; }
}
