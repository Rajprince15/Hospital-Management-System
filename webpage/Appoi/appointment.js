// Appointment Form Handler
document.addEventListener('DOMContentLoaded', function() {
    const appointmentForm = document.querySelector('form');
    
    if (!appointmentForm) return;

    // Get doctor ID from URL if present
    const urlParams = new URLSearchParams(window.location.search);
    const doctorId = urlParams.get('doctorId');

    appointmentForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        // Get form values
        const patientId = appointmentForm.querySelector('input[type="text"]').value;
        const email = appointmentForm.querySelector('input[type="email"]').value;
        const date = appointmentForm.querySelector('input[type="date"]').value;
        const department = appointmentForm.querySelector('select').value;

        // Validate inputs
        if (!patientId || !email || !date || department === 'Select Department*') {
            showMessage('Please fill in all fields', 'error');
            return;
        }

        // Show loading state
        const submitButton = appointmentForm.querySelector('button[type="submit"]');
        const originalText = submitButton.textContent;
        submitButton.disabled = true;
        submitButton.textContent = 'Booking...';

        try {
            // Prepare appointment data
            const appointmentData = {
                patientId: parseInt(patientId),
                doctorId: doctorId ? parseInt(doctorId) : 1, // Default to doctor 1 if not specified
                appointmentDate: date,
                status: 'SCHEDULED'
            };

            // Call API
            const result = await API.scheduleAppointment(appointmentData);

            if (result.success) {
                showMessage('Appointment booked successfully! Redirecting...', 'success');
                
                // Reset form
                appointmentForm.reset();
                
                // Redirect after 2 seconds
                setTimeout(() => {
                    window.location.href = '../Main.html';
                }, 2000);
            } else {
                showMessage('Failed to book appointment. Please try again.', 'error');
            }
        } catch (error) {
            console.error('Appointment Error:', error);
            showMessage('An error occurred. Please try again.', 'error');
        } finally {
            // Reset button
            submitButton.disabled = false;
            submitButton.textContent = originalText;
        }
    });

    // Load doctors dynamically into select if needed
    loadDoctorOptions();
});

async function loadDoctorOptions() {
    try {
        const result = await API.getAllDoctors();
        
        if (result.success && result.data) {
            console.log('Doctors loaded:', result.data);
            // You can enhance this to populate a doctor dropdown if needed
        }
    } catch (error) {
        console.error('Error loading doctors:', error);
    }
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
