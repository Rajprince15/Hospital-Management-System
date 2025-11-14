// Appointment Form Handler
document.addEventListener('DOMContentLoaded', function() {
    const appointmentForm = document.getElementById('appointmentForm');

    if (!appointmentForm) return;

    const departmentSelect = document.getElementById('department');
    const doctorSelect = document.getElementById('doctorId');

    let allDoctors = [];

    // Load all doctors on page load
    loadAllDoctors();

    // Filter doctors when department changes
    if (departmentSelect) {
        departmentSelect.addEventListener('change', function() {
            const selectedDepartment = this.value;
            filterDoctorsByDepartment(selectedDepartment);
        });
    }

    // Form submission
    appointmentForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        // Get form values
        const patientId = document.getElementById('patientId').value;
        const email = document.getElementById('email').value;
        const date = document.getElementById('appointmentDate').value;
        const department = document.getElementById('department').value;
        const doctorId = document.getElementById('doctorId').value;

        // Validate inputs
        if (!patientId || !email || !date || !department || !doctorId) {
            showMessage('Please fill in all fields', 'error');
            return;
        }

        // Validate patient ID exists
        const patientExists = await validatePatientId(patientId);
        if (!patientExists) {
            showMessage('Patient ID not found. Please check your ID or register as a new patient.', 'error');
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
                doctorId: parseInt(doctorId),
                appointmentDate: date,
                status: 'Scheduled'
            };

            console.log('Booking appointment:', appointmentData);

            // Call API
            const result = await API.scheduleAppointment(appointmentData);

            if (result.success) {
                showMessage('Appointment booked successfully! Redirecting...', 'success');

                // Reset form
                appointmentForm.reset();
                doctorSelect.innerHTML = '<option value="">Select Doctor*</option>';

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

    // Load all doctors from API
    async function loadAllDoctors() {
        try {
            const result = await API.getAllDoctors();

            if (result.success && result.data) {
                allDoctors = result.data;
                console.log('Loaded doctors:', allDoctors);
            } else {
                console.error('Failed to load doctors');
            }
        } catch (error) {
            console.error('Error loading doctors:', error);
        }
    }

    // Filter doctors based on selected department
    function filterDoctorsByDepartment(department) {
        // Clear current options
        doctorSelect.innerHTML = '<option value="">Select Doctor*</option>';

        if (!department) return;

        // Filter doctors by specialty matching department
        const filteredDoctors = allDoctors.filter(doctor => {
            return doctor.specialty && doctor.specialty.toLowerCase().includes(department.toLowerCase());
        });

        // Populate doctor dropdown
        if (filteredDoctors.length > 0) {
            filteredDoctors.forEach(doctor => {
                const option = document.createElement('option');
                option.value = doctor.id;
                option.textContent = `${doctor.name} - ${doctor.specialty}`;
                doctorSelect.appendChild(option);
            });
        } else {
            // If no exact match, show all doctors for that department
            const option = document.createElement('option');
            option.value = '';
            option.textContent = 'No doctors available for this department';
            option.disabled = true;
            doctorSelect.appendChild(option);
        }
    }

    // Validate if patient ID exists
    async function validatePatientId(patientId) {
        try {
            const result = await API.getPatientById(patientId);
            return result.success && result.data;
        } catch (error) {
            console.error('Error validating patient:', error);
            return false;
        }
    }
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
