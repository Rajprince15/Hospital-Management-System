// Wait for DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Initialize Bootstrap tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    });

    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Animated counter for statistics
    const counters = document.querySelectorAll('.counter');
    const speed = 200;

    const animateCounter = () => {
        counters.forEach(counter => {
            const target = +counter.getAttribute('data-target');
            const count = +counter.innerText;
            const increment = target / speed;

            if (count < target) {
                counter.innerText = Math.ceil(count + increment);
                setTimeout(animateCounter, 1);
            } else {
                counter.innerText = target;
            }
        });
    }

    // Intersection Observer for counter animation
    const observerOptions = {
        threshold: 0.7
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                animateCounter();
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    counters.forEach(counter => observer.observe(counter));

    // Appointment Form Validation
    const appointmentForm = document.getElementById('appointmentForm');
    if (appointmentForm) {
        appointmentForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            if (validateForm()) {
                showSuccessMessage('Appointment request submitted successfully!');
                appointmentForm.reset();
            }
        });
    }
    
    function validateForm() {
        let isValid = true;
        const requiredFields = appointmentForm.querySelectorAll('[required]');
        
        requiredFields.forEach(field => {
            if (!field.value.trim()) {
                isValid = false;
                showError(field, 'This field is required');
            } else {
                removeError(field);
            }
        });

        return isValid;
    }

    // Doctor Search and Filter
    const doctorSearch = document.getElementById('doctorSearch');
    if (doctorSearch) {
        doctorSearch.addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const doctors = document.querySelectorAll('.doctor-card');

            doctors.forEach(doctor => {
                const doctorName = doctor.querySelector('h4').textContent.toLowerCase();
                const doctorSpecialty = doctor.querySelector('.speciality').textContent.toLowerCase();

                if (doctorName.includes(searchTerm) || doctorSpecialty.includes(searchTerm)) {
                    doctor.style.display = 'block';
                } else {
                    doctor.style.display = 'none';
                }
            });
        });
    }

    // Department Filter
    const departmentFilter = document.getElementById('departmentFilter');
    if (departmentFilter) {
        departmentFilter.addEventListener('change', function(e) {
            const selectedDepartment = e.target.value.toLowerCase();
            const doctors = document.querySelectorAll('.doctor-card');

            doctors.forEach(doctor => {
                const doctorDepartment = doctor.dataset.department.toLowerCase();
                if (selectedDepartment === 'all' || doctorDepartment === selectedDepartment) {
                    doctor.style.display = 'block';
                } else {
                    doctor.style.display = 'none';
                }
            });
        });
    }

    // Lazy Loading Images
    const images = document.querySelectorAll('img[data-src]');
    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.removeAttribute('data-src');
                observer.unobserve(img);
            }
        });
    });

    images.forEach(img => imageObserver.observe(img));

    // Show/Hide Navigation on Scroll
    let lastScroll = 0;
    const navbar = document.querySelector('.navbar');

    window.addEventListener('scroll', () => {
        const currentScroll = window.pageYOffset;

        if (currentScroll <= 0) {
            navbar.classList.remove('scroll-up');
            return;
        }

        if (currentScroll > lastScroll && !navbar.classList.contains('scroll-down')) {
            navbar.classList.remove('scroll-up');
            navbar.classList.add('scroll-down');
        } else if (currentScroll < lastScroll && navbar.classList.contains('scroll-down')) {
            navbar.classList.remove('scroll-down');
            navbar.classList.add('scroll-up');
        }
        lastScroll = currentScroll;
    });

    // Helper Functions
    function showSuccessMessage(message) {
        const alert = document.createElement('div');
        alert.className = 'alert alert-success alert-dismissible fade show';
        alert.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        appointmentForm.insertAdjacentElement('beforebegin', alert);
    }

    function showError(field, message) {
        const existingError = field.nextElementSibling;
        if (existingError?.classList.contains('invalid-feedback')) {
            existingError.textContent = message;
        } else {
            const error = document.createElement('div');
            error.className = 'invalid-feedback';
            error.textContent = message;
            field.parentNode.insertBefore(error, field.nextSibling);
        }
        field.classList.add('is-invalid');
    }

    function removeError(field) {
        field.classList.remove('is-invalid');
        const error = field.nextElementSibling;
        if (error?.classList.contains('invalid-feedback')) {
            error.remove();
        }
    }
});
