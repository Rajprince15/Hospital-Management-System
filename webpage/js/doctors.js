// Dynamic Doctor Loading
document.addEventListener('DOMContentLoaded', async function() {
    const doctorContainer = document.querySelector('.doc-row');
    
    if (!doctorContainer) return;

    // Show loading state
    doctorContainer.innerHTML = '<div style="text-align: center; padding: 40px; width: 100%;"><p>Loading doctors...</p></div>';

    try {
        // Fetch doctors from backend
        const result = await API.getAllDoctors();
        
        if (result.success && result.data && result.data.length > 0) {
            renderDoctors(result.data);
        } else {
            // Fallback to static doctors if API fails
            console.warn('Using fallback static doctors');
            renderStaticDoctors();
        }
    } catch (error) {
        console.error('Error loading doctors:', error);
        renderStaticDoctors();
    }
});

function renderDoctors(doctors) {
    const doctorContainer = document.querySelector('.doc-row');
    doctorContainer.innerHTML = '';

    // Doctor specialty to image mapping (using quality images)
    const specialtyImages = {
        'Cardiologist': 'https://media.istockphoto.com/id/1420317532/photo/a-female-doctor-smiling-in-her-uniform.jpg?s=612x612&w=0&k=20&c=a0yvQ54VWHtIsCNSWElH0CKWgpjy7-qsLwLDogXoFnM=',
        'Neurosurgeon': 'https://img.koreatimes.co.kr/upload/newsV2/images/ParkSH450.jpg/dims/resize/740/optimize',
        'Gynecologist': 'https://i.pinimg.com/originals/9b/c5/94/9bc594e0840c26648b41df2e577d68a7.jpg',
        'Gynaecologist': 'https://i.pinimg.com/originals/9b/c5/94/9bc594e0840c26648b41df2e577d68a7.jpg',
        'Dermatologist': 'https://static.vecteezy.com/system/resources/thumbnails/049/462/386/small_2x/confident-korean-female-doctor-in-white-coat-with-stethoscope-posing-against-soft-background-photo.jpeg',
        'Pediatrician': 'https://i.pinimg.com/736x/9f/98/79/9f9879d1a30245fdbc5cd5e24c533cdd.jpg',
        'Orthopedist': 'https://i.pinimg.com/474x/98/74/74/987474b11a0b1ede9779337693e794df.jpg',
        'default': 'https://media.istockphoto.com/id/1420317532/photo/a-female-doctor-smiling-in-her-uniform.jpg?s=612x612&w=0&k=20&c=a0yvQ54VWHtIsCNSWElH0CKWgpjy7-qsLwLDogXoFnM='
    };

    doctors.forEach(doctor => {
        const imageUrl = specialtyImages[doctor.specialty] || specialtyImages['default'];
        
        const doctorCard = `
            <div class="doctor-card" data-doctor-id="${doctor.id}">
                <div class="doctor-image">
                    <img src="${imageUrl}" alt="${doctor.name}" />
                </div>
                <div class="specialization-tag">${doctor.specialty}</div>
                <h4>${doctor.name}</h4>
                <p class="qualification">MD, FACC</p>
                <div class="expertise">
                    <span>${doctor.specialty}</span>
                </div>
                <div class="availability">
                    <i class="fas fa-calendar-check"></i>
                    Available: Mon-Fri
                </div>
                <a href="Appoi/Appoi.html?doctorId=${doctor.id}"><button class="btn">Book Appointment</button></a>
            </div>
        `;
        
        doctorContainer.innerHTML += doctorCard;
    });
}

function renderStaticDoctors() {
    const doctorContainer = document.querySelector('.doc-row');
    
    const staticDoctors = `
        <div class="doctor-card">
            <div class="doctor-image">
                <img src="https://media.istockphoto.com/id/1420317532/photo/a-female-doctor-smiling-in-her-uniform.jpg?s=612x612&w=0&k=20&c=a0yvQ54VWHtIsCNSWElH0CKWgpjy7-qsLwLDogXoFnM=" alt="Dr. Anna Yamada" />
            </div>
            <div class="specialization-tag">Cardiologist</div>
            <h4>Dr. Anna Yamada</h4>
            <p class="qualification">MD, FACC</p>
            <div class="expertise">
                <span>Heart Surgery</span>
                <span>Cardiac Care</span>
            </div>
            <div class="availability">
                <i class="fas fa-calendar-check"></i>
                Available: Mon-Fri
            </div>
            <a href="Appoi/Appoi.html"><button class="btn">Book Appointment</button></a>
        </div>
        <div class="doctor-card">
            <div class="doctor-image">
                <img src="https://img.koreatimes.co.kr/upload/newsV2/images/ParkSH450.jpg/dims/resize/740/optimize" alt="Dr. Tsunade" />
            </div>
            <div class="specialization-tag">Neurosurgeon</div>
            <h4>Dr. Tsunade</h4>
            <p class="qualification">MD, FACC</p>
            <div class="expertise">
                <span>Brain Surgeon</span>
                <span>Neurosurgery</span>
            </div>
            <div class="availability">
                <i class="fas fa-calendar-check"></i>
                Available: Wed-Fri
            </div>
            <a href="Appoi/Appoi.html"><button class="btn">Book Appointment</button></a>
        </div>
        <div class="doctor-card">
            <div class="doctor-image">
                <img src="https://i.pinimg.com/originals/9b/c5/94/9bc594e0840c26648b41df2e577d68a7.jpg" alt="Dr. Marien Kitagaewa" />
            </div>
            <div class="specialization-tag">Gynaecologist</div>
            <h4>Dr. Marien Kitagaewa</h4>
            <p class="qualification">MD, FACC</p>
            <div class="expertise">
                <span>Gynae and Obstetrics</span>
                <span>Gynaecology</span>
            </div>
            <div class="availability">
                <i class="fas fa-calendar-check"></i>
                Available: Mon-Thu
            </div>
            <a href="Appoi/Appoi.html"><button class="btn">Book Appointment</button></a>
        </div>
        <div class="doctor-card">
            <div class="doctor-image">
                <img src="https://static.vecteezy.com/system/resources/thumbnails/049/462/386/small_2x/confident-korean-female-doctor-in-white-coat-with-stethoscope-posing-against-soft-background-photo.jpeg" alt="Dr. Boa Hancock" />
            </div>
            <div class="specialization-tag">Dermatologist</div>
            <h4>Dr. Boa Hancock</h4>
            <p class="qualification">MD, FACC</p>
            <div class="expertise">
                <span>Skin Surgery</span>
                <span>Skin specialist</span>
            </div>
            <div class="availability">
                <i class="fas fa-calendar-check"></i>
                Available: Mon-Fri
            </div>
            <a href="Appoi/Appoi.html"><button class="btn">Book Appointment</button></a>
        </div>
        <div class="doctor-card">
            <div class="doctor-image">
                <img src="https://i.pinimg.com/736x/9f/98/79/9f9879d1a30245fdbc5cd5e24c533cdd.jpg" alt="Dr. Nico Robin" />
            </div>
            <div class="specialization-tag">Pediatrician</div>
            <h4>Dr. Nico Robin</h4>
            <p class="qualification">MD, FAAP</p>
            <div class="expertise">
                <span>Child Care</span>
                <span>Pediatric Medicine</span>
            </div>
            <div class="availability">
                <i class="fas fa-calendar-check"></i>
                Available: Tue-Sat
            </div>
            <a href="Appoi/Appoi.html"><button class="btn">Book Appointment</button></a>
        </div>
        <div class="doctor-card">
            <div class="doctor-image">
                <img src="https://i.pinimg.com/474x/98/74/74/987474b11a0b1ede9779337693e794df.jpg" alt="Dr. Rias Gremory" />
            </div>
            <div class="specialization-tag">Orthopedist</div>
            <h4>Dr. Rias Gremory</h4>
            <p class="qualification">MD, FAAOS</p>
            <div class="expertise">
                <span>Joint Surgery</span>
                <span>Sports Medicine</span>
            </div>
            <div class="availability">
                <i class="fas fa-calendar-check"></i>
                Available: Mon-Wed
            </div>
            <a href="Appoi/Appoi.html"><button class="btn">Book Appointment</button></a>
        </div>
    `;
    
    doctorContainer.innerHTML = staticDoctors;
}
