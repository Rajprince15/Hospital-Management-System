// Authentication Handler
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const signupForm = document.getElementById('signupForm');
    const loginLink = document.querySelector('.login-link');
    const signupLink = document.querySelector('.signup-link');
    const formsContainer = document.querySelector('.container.forms');

    // Form switching - Fixed to use CSS classes properly
    if (signupLink) {
        signupLink.addEventListener('click', (e) => {
            e.preventDefault();
            formsContainer.classList.add('show-signup');
        });
    }

    if (loginLink) {
        loginLink.addEventListener('click', (e) => {
            e.preventDefault();
            formsContainer.classList.remove('show-signup');
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
            submitButton.innerHTML = '<i class="bx bx-loader-alt bx-spin"></i> Logging in...';

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
                submitButton.innerHTML = originalText;
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
            const originalText = submitButton.innerHTML;
            submitButton.disabled = true;
            submitButton.innerHTML = '<i class="bx bx-loader-alt bx-spin"></i> Creating account...';

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
                        formsContainer.classList.remove('show-signup');
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
                submitButton.innerHTML = originalText;
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

    const icon = type === 'success' ? '<i class="bx bx-check-circle"></i>' : '<i class="bx bx-error-circle"></i>';

    messageBox.innerHTML = `
        <div style="display: flex; align-items: center; gap: 10px;">
            ${icon}
            <span>${message}</span>
        </div>
    `;

    messageBox.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 16px 24px;
        border-radius: 12px;
        z-index: 9999;
        font-weight: 500;
        box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
        animation: slideIn 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
        max-width: 450px;
        font-size: 14px;
        ${type === 'success' ?
            'background: linear-gradient(135deg, #10b981 0%, #059669 100%); color: white;' :
            'background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%); color: white;'}
    `;

    // Add animation
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from {
                transform: translateX(120%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }
        @keyframes slideOut {
            from {
                transform: translateX(0) scale(1);
                opacity: 1;
            }
            to {
                transform: translateX(120%) scale(0.9);
                opacity: 0;
            }
        }
        .message-box i {
            font-size: 24px;
        }
    `;
    if (!document.getElementById('message-animations')) {
        style.id = 'message-animations';
        document.head.appendChild(style);
    }

    document.body.appendChild(messageBox);

    // Auto remove after 5 seconds
    setTimeout(() => {
        messageBox.style.animation = 'slideOut 0.3s ease-out forwards';
        setTimeout(() => messageBox.remove(), 300);
    }, 5000);
}
