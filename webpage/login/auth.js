// Authentication Handler
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const signupForm = document.getElementById('signupForm');
    const loginLink = document.querySelector('.login-link');
    const signupLink = document.querySelector('.signup-link');
    const loginFormContainer = document.querySelector('.form.login');
    const signupFormContainer = document.querySelector('.form.signup');

    // Form switching
    if (signupLink) {
        signupLink.addEventListener('click', (e) => {
            e.preventDefault();
            loginFormContainer.style.display = 'none';
            signupFormContainer.style.display = 'block';
        });
    }

    if (loginLink) {
        loginLink.addEventListener('click', (e) => {
            e.preventDefault();
            signupFormContainer.style.display = 'none';
            loginFormContainer.style.display = 'block';
        });
    }

    // Password toggle
    const eyeIcons = document.querySelectorAll('.eye-icon');
    eyeIcons.forEach(icon => {
        icon.addEventListener('click', () => {
            const passwordField = icon.previousElementSibling;
            
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                icon.classList.replace('bx-hide', 'bx-show');
            } else {
                passwordField.type = 'password';
                icon.classList.replace('bx-show', 'bx-hide');
            }
        });
    });

    // Login Form Handler
    if (loginForm) {
        loginForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            // Clear previous errors
            clearErrors();

            const email = document.getElementById('loginEmail').value.trim();
            const password = document.getElementById('loginPassword').value;

            // Validation
            let isValid = true;

            if (!email) {
                showError('loginEmailError', 'ID/Email is required');
                isValid = false;
            }

            if (!password) {
                showError('loginPasswordError', 'Password is required');
                isValid = false;
            }

            if (!isValid) return;

            // Show loading
            const submitButton = loginForm.querySelector('button[type="submit"]');
            const originalText = submitButton.textContent;
            submitButton.disabled = true;
            submitButton.textContent = 'Logging in...';

            try {
                // Call API
                const result = await API.login(email, password);

                if (result.success) {
                    showMessage('Login successful! Redirecting...', 'success');
                    
                    setTimeout(() => {
                        window.location.href = '../Main.html';
                    }, 1000);
                } else {
                    showError('loginPasswordError', 'Invalid credentials. Please try again.');
                }
            } catch (error) {
                console.error('Login Error:', error);
                showError('loginPasswordError', 'An error occurred. Please try again.');
            } finally {
                submitButton.disabled = false;
                submitButton.textContent = originalText;
            }
        });
    }

    // Signup Form Handler
    if (signupForm) {
        signupForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            // Clear previous errors
            clearErrors();

            const fullName = document.getElementById('signupFullName').value.trim();
            const email = document.getElementById('signupEmail').value.trim();
            const phone = document.getElementById('signupPhone').value.trim();
            const address = document.getElementById('signupAddress').value.trim();
            const password = document.getElementById('signupPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            // Validation
            let isValid = true;

            if (!fullName) {
                showError('signupFullNameError', 'Full name is required');
                isValid = false;
            }

            if (!email) {
                showError('signupEmailError', 'Email is required');
                isValid = false;
            } else if (!isValidEmail(email)) {
                showError('signupEmailError', 'Please enter a valid email');
                isValid = false;
            }

            if (!phone) {
                showError('signupPhoneError', 'Phone number is required');
                isValid = false;
            }

            if (!address) {
                showError('signupAddressError', 'Address is required');
                isValid = false;
            }

            if (!password) {
                showError('signupPasswordError', 'Password is required');
                isValid = false;
            } else if (password.length < 6) {
                showError('signupPasswordError', 'Password must be at least 6 characters');
                isValid = false;
            }

            if (!confirmPassword) {
                showError('confirmPasswordError', 'Please confirm your password');
                isValid = false;
            } else if (password !== confirmPassword) {
                showError('confirmPasswordError', 'Passwords do not match');
                isValid = false;
            }

            if (!isValid) return;

            // Show loading
            const submitButton = signupForm.querySelector('button');
            const originalText = submitButton.textContent;
            submitButton.disabled = true;
            submitButton.textContent = 'Creating account...';

            try {
                // Prepare registration data
                const userData = {
                    username: email,
                    password: password,
                    email: email,
                    fullName: fullName,
                    phoneNumber: phone,
                    address: address
                };

                // Call API
                const result = await API.registerPatient(userData);

                if (result.success) {
                    showMessage(`Account created successfully! Your Patient ID is: ${result.patientId}. Please save this for booking appointments.`, 'success');

                    // Switch to login form
                    setTimeout(() => {
                        signupFormContainer.style.display = 'none';
                        loginFormContainer.style.display = 'block';
                        signupForm.reset();
                    }, 4000);
                } else {
                    showError('signupEmailError', result.error || 'Registration failed. Email may already exist.');
                }
            } catch (error) {
                console.error('Registration Error:', error);
                showError('signupEmailError', 'An error occurred. Please try again.');
            } finally {
                submitButton.disabled = false;
                submitButton.textContent = originalText;
            }
        });
    }
});

function showError(elementId, message) {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.color = '#ef4444';
        errorElement.style.fontSize = '12px';
        errorElement.style.marginTop = '5px';
    }
}

function clearErrors() {
    const errorElements = document.querySelectorAll('.error');
    errorElements.forEach(el => el.textContent = '');
}

function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function showMessage(message, type) {
    // Remove any existing messages
    const existingMessage = document.querySelector('.message-box');
    if (existingMessage) {
        existingMessage.remove();
    }

    // Create message element
    const messageBox = document.createElement('div');
    messageBox.className = `message-box ${type}`;
    messageBox.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 25px;
        border-radius: 5px;
        z-index: 9999;
        font-weight: 500;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        animation: slideIn 0.3s ease-out;
        max-width: 400px;
        ${type === 'success' ? 'background-color: #10b981; color: white;' : 'background-color: #ef4444; color: white;'}
    `;
    messageBox.textContent = message;

    // Add animation
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
    `;
    document.head.appendChild(style);

    document.body.appendChild(messageBox);

    // Auto remove after 5 seconds
    setTimeout(() => {
        messageBox.style.animation = 'slideIn 0.3s ease-out reverse';
        setTimeout(() => messageBox.remove(), 300);
    }, 5000);
}
