// Login Form Validation
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    validateLoginForm();
});

// Signup Form Validation
document.getElementById('signupForm').addEventListener('submit', function(e) {
    e.preventDefault();
    validateSignupForm();
});

function validateLoginForm() {
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;
    
    document.getElementById('loginEmailError').textContent = '';
    document.getElementById('loginPasswordError').textContent = '';
    
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email) {
        document.getElementById('loginEmailError').textContent = 'Email is required';
    } else if (!emailPattern.test(email)) {
        document.getElementById('loginEmailError').textContent = 'Please enter a valid email';
    }
    
    if (!password) {
        document.getElementById('loginPasswordError').textContent = 'Password is required';
    } else if (password.length < 6) {
        document.getElementById('loginPasswordError').textContent = 'Password must be at least 6 characters';
    }
}

function validateSignupForm() {
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    
    document.getElementById('signupEmailError').textContent = '';
    document.getElementById('signupPasswordError').textContent = '';
    document.getElementById('confirmPasswordError').textContent = '';
    
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email) {
        document.getElementById('signupEmailError').textContent = 'Email is required';
    } else if (!emailPattern.test(email)) {
        document.getElementById('signupEmailError').textContent = 'Please enter a valid email';
    }
    
    if (!password) {
        document.getElementById('signupPasswordError').textContent = 'Password is required';
    } else if (password.length < 6) {
        document.getElementById('signupPasswordError').textContent = 'Password must be at least 6 characters';
    }
    
    if (!confirmPassword) {
        document.getElementById('confirmPasswordError').textContent = 'Please confirm your password';
    } else if (password !== confirmPassword) {
        document.getElementById('confirmPasswordError').textContent = 'Passwords do not match';
    }
}

const forms = document.querySelector(".forms"),
  pwShowHide = document.querySelectorAll(".eye-icon"),
  links = document.querySelectorAll(".link");

// Add click event listener to each eye icon for toggling password visibility
pwShowHide.forEach(eyeIcon => {
  eyeIcon.addEventListener("click", () => {
    let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password");

    pwFields.forEach(password => {
      if (password.type === "password") { // If password is hidden
        password.type = "text"; // Show password
        eyeIcon.classList.replace("bx-hide", "bx-show"); // Change icon to show state
        return;
      }
      password.type = "password"; // Hide password
      eyeIcon.classList.replace("bx-show", "bx-hide"); // Change icon to hide state
    });

  });
});

// Add click event listener to each link to toggle between forms
links.forEach(link => {
  link.addEventListener("click", e => {
    e.preventDefault(); // Prevent default link behavior
    forms.classList.toggle("show-signup");
  });
});