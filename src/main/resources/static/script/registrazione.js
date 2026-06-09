// ================= DOM ELEMENTS =================

const form = document.querySelector('.registration-form');
const nameInput = document.getElementById('nome');
const surnameInput = document.getElementById('cognome');
const nicknameInput = document.getElementById('nickname');
const ageInput = document.getElementById('eta');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const submitBtn = document.querySelector('.btn-register');

// Indirizzo del progetto AUTH (la "biglietteria": registrazione + login)
const AUTH_REGISTER_URL = "http://localhost:8081/api/v1/auth/register";

// ================= VALIDATION STATE =================

let validationState = {
  nome: false,
  cognome: false,
  nickname: false,
  email: false,
  eta: false,
  password: false,
};

// ================= EVENT LISTENERS =================

document.addEventListener('DOMContentLoaded', () => {
  nameInput.addEventListener('input', validateName);
  surnameInput.addEventListener('input', validateSurname);
  nicknameInput.addEventListener('input', validateNickname);
  ageInput.addEventListener('input', validateAge);
  emailInput.addEventListener('input', validateEmail);
  passwordInput.addEventListener('input', validatePassword);

  form.addEventListener('submit', handleFormSubmit);

  updateButtonState();
});

// ================= VALIDATION FUNCTIONS =================

function validateName() {
  const value = nameInput.value.trim();
  if (value.length > 0 && value.length <= 128) {
    validationState.nome = true;
    removeError(nameInput);
  } else {
    validationState.nome = false;
    showError(nameInput, value.length === 0 ? 'Il nome è obbligatorio' : 'Il nome deve avere massimo 128 caratteri');
  }
  updateButtonState();
}

function validateSurname() {
  const value = surnameInput.value.trim();
  if (value.length > 0 && value.length <= 128) {
    validationState.cognome = true;
    removeError(surnameInput);
  } else {
    validationState.cognome = false;
    showError(surnameInput, value.length === 0 ? 'Il cognome è obbligatorio' : 'Il cognome deve avere massimo 128 caratteri');
  }
  updateButtonState();
}

function validateNickname() {
  const value = nicknameInput.value.trim();
  if (value.length >= 3 && value.length <= 50) {
    validationState.nickname = true;
    removeError(nicknameInput);
  } else {
    validationState.nickname = false;
    if (value.length === 0) showError(nicknameInput, 'Il nickname è obbligatorio');
    else if (value.length < 3) showError(nicknameInput, 'Il nickname deve avere almeno 3 caratteri');
    else showError(nicknameInput, 'Il nickname deve avere massimo 50 caratteri');
  }
  updateButtonState();
}

function validateAge() {
  const value = parseInt(ageInput.value);
  if (!isNaN(value) && value >= 18 && value <= 120) {
    validationState.eta = true;
    removeError(ageInput);
  } else {
    validationState.eta = false;
    showError(ageInput, (ageInput.value === '' || isNaN(value)) ? "L'età è obbligatoria" : 'Devi avere almeno 18 anni');
  }
  updateButtonState();
}

function validateEmail() {
  const value = emailInput.value.trim();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (emailRegex.test(value)) {
    validationState.email = true;
    removeError(emailInput);
  } else {
    validationState.email = false;
    showError(emailInput, value === '' ? "L'email è obbligatoria" : 'Inserisci un email valido (es: user@domain.com)');
  }
  updateButtonState();
}

function validatePassword() {
  const value = passwordInput.value;
  if (value.length >= 6 && value.length <= 20) {
    validationState.password = true;
    removeError(passwordInput);
  } else {
    validationState.password = false;
    if (value === '') showError(passwordInput, 'La password è obbligatoria');
    else if (value.length < 6) showError(passwordInput, 'La password deve avere almeno 6 caratteri');
    else showError(passwordInput, 'La password deve avere massimo 20 caratteri');
  }
  updateButtonState();
}

// ================= UPDATE BUTTON STATE =================

function updateButtonState() {
  const isFormValid = Object.values(validationState).every(value => value === true);
  if (isFormValid) {
    submitBtn.disabled = false;
    submitBtn.style.opacity = '1';
    submitBtn.style.cursor = 'pointer';
  } else {
    submitBtn.disabled = true;
    submitBtn.style.opacity = '0.5';
    submitBtn.style.cursor = 'not-allowed';
  }
}

// ================= ERROR HANDLING =================

function showError(input, message) {
  removeError(input);
  const errorEl = document.createElement('span');
  errorEl.className = 'error-message';
  errorEl.textContent = '❌ ' + message;
  errorEl.style.cssText = `
    color: #ff4d4d;
    font-size: 0.85rem;
    font-weight: 600;
    margin-top: 0.3rem;
    display: block;
  `;
  input.style.borderColor = '#ff4d4d';
  input.style.boxShadow = '3px 3px 0 #ff4d4d';
  input.parentElement.appendChild(errorEl);
}

function removeError(input) {
  const errorEl = input.parentElement.querySelector('.error-message');
  if (errorEl) errorEl.remove();
  input.style.borderColor = '';
  input.style.boxShadow = '';
}

// ================= FORM SUBMISSION =================

async function handleFormSubmit(e) {
  e.preventDefault();

  validateName();
  validateSurname();
  validateNickname();
  validateAge();
  validateEmail();
  validatePassword();

  const isFormValid = Object.values(validationState).every(value => value === true);
  if (!isFormValid) {
    showNotification('Per favore, compila tutti i campi correttamente', 'error');
    return;
  }

  // Campi nel formato che si aspetta l'entity User di AUTH:
  // nome, cognome, nickname, email, eta, password
  const userData = {
    nome:     nameInput.value.trim(),
    cognome:  surnameInput.value.trim(),
    nickname: nicknameInput.value.trim(),
    email:    emailInput.value.trim(),
    eta:      parseInt(ageInput.value),
    password: passwordInput.value,
  };

  submitBtn.disabled = true;
  submitBtn.textContent = '⏳ Registrazione in corso...';

  try {
    // Registrazione verso AUTH (porta 8081): qui l'utente viene
    // creato E gli viene assegnato ROLE_USER (saveUserWithRoles).
    const response = await fetch(AUTH_REGISTER_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userData)
    });

    if (response.ok) {
      showNotification('Registrazione completata! Benvenuto su TravelBuddy!', 'success');
      setTimeout(() => {
        window.location.href = 'login.html';
      }, 2000);

    } else if (response.status === 409) {
      const errorData = await response.json().catch(() => ({}));
      showNotification(errorData.message || 'Email o nickname già in uso', 'error');
      resetButton();

    } else if (response.status === 400) {
      const errorData = await response.json().catch(() => ({}));
      showNotification('Dati non validi. Controlla i campi.', 'error');
      console.error('Errori validazione:', errorData);
      resetButton();

    } else {
      // mostra il messaggio del server se c'è
      const txt = await response.text().catch(() => '');
      showNotification(txt || 'Errore durante la registrazione', 'error');
      resetButton();
    }

  } catch (error) {
    console.error('Errore di rete:', error);
    showNotification('Impossibile contattare il server. Riprova più tardi.', 'error');
    resetButton();
  }
}

// ================= NOTIFICATION SYSTEM =================

function showNotification(message, type = 'info') {
  const notification = document.createElement('div');
  let bgColor = '#1a4d5c';
  if (type === 'error') bgColor = '#ff4d4d';
  if (type === 'success') bgColor = '#4caf50';
  if (type === 'warning') bgColor = '#ff9800';

  notification.style.cssText = `
    position: fixed;
    bottom: 2rem;
    right: 2rem;
    background-color: ${bgColor};
    color: white;
    padding: 1rem 1.5rem;
    border: 3px solid #000;
    border-radius: 0;
    box-shadow: 4px 4px 0 #000;
    font-weight: 600;
    font-family: 'Outfit', sans-serif;
    z-index: 10000;
    animation: slideIn 0.3s ease;
    max-width: 400px;
  `;
  notification.textContent = message;
  document.body.appendChild(notification);

  setTimeout(() => {
    notification.style.animation = 'slideOut 0.3s ease forwards';
    setTimeout(() => notification.remove(), 300);
  }, 4000);
}

const style = document.createElement('style');
style.textContent = `
  @keyframes slideIn {
    from { transform: translateX(400px); opacity: 0; }
    to   { transform: translateX(0);     opacity: 1; }
  }
  @keyframes slideOut {
    from { transform: translateX(0);     opacity: 1; }
    to   { transform: translateX(400px); opacity: 0; }
  }
`;
document.head.appendChild(style);

// ================= RESET BUTTON =================

function resetButton() {
  submitBtn.disabled = false;
  submitBtn.textContent = 'Registrati';
  updateButtonState();
}