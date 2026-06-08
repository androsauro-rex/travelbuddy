// ============================================================
//  login.js  —  Login reale collegato al progetto AUTH
//  RICHIEDE: api.js incluso PRIMA di questo file
//    <script src="script/api.js"></script>
//    <script src="script/login.js"></script>
// ============================================================

const email = document.getElementById("email");
const pass = document.getElementById("password");
const button = document.getElementById("btn");
const form = document.querySelector(".login-form");

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// ================= VALIDAZIONE =================

function checkInputs() {
  const emailOk = emailRegex.test(email.value);
  const passOk = pass.value.length >= 6;
  button.disabled = !(emailOk && passOk);
}

form.addEventListener("input", checkInputs);

// ================= LOGIN REALE (chiama il backend AUTH) =================

form.addEventListener("submit", async (e) => {
  e.preventDefault();
  button.disabled = true;

  const emailValue = email.value.trim();
  const passValue = pass.value.trim();

  // controllo veloce lato client prima di disturbare il server
  if (!emailRegex.test(emailValue) || passValue.length < 6) {
    showToast("Credenziali non valide ❌");
    button.disabled = false;
    return;
  }

  try {
    // 1) chiamo AUTH: POST /api/v1/public/login con email e password
    //    apiLogin vive in api.js e restituisce { token, id, nickname, ruolo }
    const risposta = await apiLogin(emailValue, passValue);

    // 2) salvo token + dati utente nel localStorage
    salvaAuth(risposta);

    // 3) tutto ok -> vado alla dashboard
    showToast("Login effettuato 🎉");
    setTimeout(() => {
      window.location.href = "dashboard.html";
    }, 800);

  } catch (err) {
    // credenziali sbagliate, server spento, ecc.
    showToast("Login fallito: " + err.message + " ❌");
    button.disabled = false;
  }
});

// ================= TOAST =================

function showToast(message) {
  const toast = document.createElement("div");
  toast.textContent = message;
  toast.style.cssText = `
    position: fixed;
    bottom: 2rem;
    right: 2rem;
    background: #1a4d5c;
    color: white;
    padding: 1rem 1.5rem;
    border: 3px solid #000;
    box-shadow: 4px 4px 0 #000;
    font-family: 'Outfit', sans-serif;
    font-weight: 700;
    z-index: 9999;
    animation: slideIn 0.3s ease;
  `;
  document.body.appendChild(toast);
  setTimeout(() => {
    toast.style.animation = "slideOut 0.3s ease forwards";
    setTimeout(() => toast.remove(), 300);
  }, 2500);
}

// ================= ANIMAZIONI =================

const style = document.createElement("style");
style.textContent = `
@keyframes slideIn {
  from { transform: translateX(300px); opacity: 0; }
  to { transform: translateX(0); opacity: 1; }
}
@keyframes slideOut {
  from { transform: translateX(0); opacity: 1; }
  to { transform: translateX(300px); opacity: 0; }
}
`;
document.head.appendChild(style);