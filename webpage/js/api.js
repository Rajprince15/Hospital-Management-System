// API Configuration
const API_BASE_URL = window.location.origin;

// API Helper Functions
const API = {
    // Generic fetch wrapper
    async request(endpoint, options = {}) {
        const url = `${API_BASE_URL}${endpoint}`;
        const config = {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        };

        try {
            const response = await fetch(url, config);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const data = await response.json();
            return { success: true, data };
        } catch (error) {
            console.error('API Error:', error);
            return { success: false, error: error.message };
        }
    },

    // Doctor APIs
    async getAllDoctors() {
        return this.request('/api/doctors');
    },

    async getDoctorById(id) {
        return this.request(`/api/doctors/${id}`);
    },

    async addDoctor(doctor) {
        return this.request('/api/doctors', {
            method: 'POST',
            body: JSON.stringify(doctor)
        });
    },

    // Patient APIs
    async getAllPatients() {
        return this.request('/api/patients');
    },

    async getPatientById(id) {
        return this.request(`/api/patients/${id}`);
    },

    async addPatient(patient) {
        return this.request('/api/patients', {
            method: 'POST',
            body: JSON.stringify(patient)
        });
    },

    // Appointment APIs
    async getAllAppointments() {
        return this.request('/api/appointments');
    },

    async getAppointmentById(id) {
        return this.request(`/api/appointments/${id}`);
    },

    async scheduleAppointment(appointment) {
        return this.request('/api/appointments', {
            method: 'POST',
            body: JSON.stringify(appointment)
        });
    },

    // Billing APIs
    async getAllBills() {
        return this.request('/api/bills');
    },

    async getBillById(id) {
        return this.request(`/api/bills/${id}`);
    },

    async addBill(bill) {
        return this.request('/api/bills', {
            method: 'POST',
            body: JSON.stringify(bill)
        });
    },

    // Auth APIs (Form-based, not JSON)
    async login(username, password) {
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        try {
            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                body: formData
            });

            if (response.redirected) {
                window.location.href = response.url;
                return { success: true };
            }

            return { success: false, error: 'Login failed' };
        } catch (error) {
            console.error('Login Error:', error);
            return { success: false, error: error.message };
        }
    },

    async register(userData) {
        const formData = new FormData();
        Object.keys(userData).forEach(key => {
            formData.append(key, userData[key]);
        });

        try {
            const response = await fetch(`${API_BASE_URL}/user/register`, {
                method: 'POST',
                body: formData
            });

            if (response.redirected || response.ok) {
                return { success: true };
            }

            return { success: false, error: 'Registration failed' };
        } catch (error) {
            console.error('Registration Error:', error);
            return { success: false, error: error.message };
        }
    },

    async logout() {
        try {
            const response = await fetch(`${API_BASE_URL}/auth/logout`, {
                method: 'POST'
            });
            
            if (response.ok || response.redirected) {
                return { success: true };
            }
            
            return { success: false };
        } catch (error) {
            console.error('Logout Error:', error);
            return { success: false, error: error.message };
        }
    }
};

// Export for use in other scripts
window.API = API;
