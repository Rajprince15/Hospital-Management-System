const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});

// Get elements
const nameInput = document.getElementById('signupName');
const uidInput = document.getElementById('signupUid');
const passwordInput = document.getElementById('signupPassword');
const nameError = document.getElementById('nameError');
const uidError = document.getElementById('uidError');
const passwordError = document.getElementById('passwordError');

// Validation functions
function validateName() {
    if (nameInput.value.length < 2) {
        nameInput.classList.add('invalid');
        nameError.textContent = 'Name must be at least 2 characters long';
        return false;
    }
    nameInput.classList.remove('invalid');
    nameError.textContent = '';
    return true;
}

function validateUID() {
    const uidPattern = /^[A-Z]{3}[0-9]{3}$/;
    if (!uidPattern.test(uidInput.value)) {
        uidInput.classList.add('invalid');
        uidError.textContent = 'UID must be 3 uppercase letters followed by 3 numbers';
        return false;
    }
    uidInput.classList.remove('invalid');
    uidError.textContent = '';
    return true;
}

function validatePassword() {
    if (passwordInput.value.length < 6) {
        passwordInput.classList.add('invalid');
        passwordError.textContent = 'Password must be at least 6 characters long';
        return false;
    }
    passwordInput.classList.remove('invalid');
    passwordError.textContent = '';
    return true;
}

// Add event listeners
nameInput.addEventListener('input', validateName);
uidInput.addEventListener('input', validateUID);
passwordInput.addEventListener('input', validatePassword);

signupForm.addEventListener('submit', (e) => {
    e.preventDefault();
    
    const isNameValid = validateName();
    const isUIDValid = validateUID();
    const isPasswordValid = validatePassword();

    if (isNameValid && isUIDValid && isPasswordValid) {
        // Form is valid, proceed with submission
        console.log('Form submitted successfully');
    }
});

