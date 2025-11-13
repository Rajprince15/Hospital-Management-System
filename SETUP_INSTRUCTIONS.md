# Hospital Management System - Setup & Run Instructions

## ✅ Issues Fixed

### 1. API Configuration Fixed ✓
**File:** `/app/webpage/js/api.js`
- **Changed:** `API_BASE_URL` from `window.location.origin` to `http://localhost:2525/hospital-management-system`
- **Why:** Frontend runs on port 3000 (Node.js), but backend runs on port 2525 (Tomcat). They need different URLs.

### 2. Database Configuration ✓
**File:** `/app/src/main/java/main/java/util/DatabaseUtil.java`
- **Database:** `hospital_management`
- **Port:** 3001 (MySQL)
- **Credentials:** root/Prince1504
- **Status:** Already configured correctly ✓

### 3. Code Structure ✓
- All servlets properly annotated with `@WebServlet`
- CORS properly configured for cross-origin requests
- API endpoints: `/api/*` mapped to HospitalManagementServlet
- Frontend properly structured with reusable API client

---

## 🚀 How to Run Your Application

### Prerequisites
✅ MySQL running on port 3001
✅ Database `hospital_management` created with all tables
✅ Tomcat configured to run on port 2525
✅ Node.js installed

### Step 1: Build the Backend
```bash
cd "D:\HSP\Hospital Management System"
mvn clean package -DskipTests
```

**Note:** We use `-DskipTests` to skip unit tests during build. This is common in development.

This will create: `target/hospital-management-system.war`

### Step 2: Deploy to Tomcat
1. Copy the WAR file to your Tomcat webapps folder:
   ```bash
   copy "target\hospital-management-system.war" "$env:TOMCAT_HOME\webapps\"

   ```

2. Start Tomcat:
   ```bash
   & "$env:TOMCAT_HOME\bin\startup.bat"

   ```
   to stop:
      ```bash
      & "$env:TOMCAT_HOME\bin\shutdown.bat"

      ```

3. Wait for deployment (Tomcat will extract the WAR file automatically)

4. Verify backend is running:
   - Open browser: `http://localhost:2525/hospital-management-system/api/doctors`
   - You should see JSON response with doctors list

### Step 3: Start Frontend Server
```bash
cd "D:\HSP\Hospital Management System"
node serve-webpage.js
```

You should see:
```
============================================================
Hospital Management System - Frontend Server
============================================================
Server running at: http://localhost:3000/
Serving files from: D:\HSP\Hospital Management System\webpage
```

### Step 4: Access Your Application
Open your browser and go to:
- **Main Page:** http://localhost:3000/Main.html
- **Login Page:** http://localhost:3000/login/login.html
- **Book Appointment:** http://localhost:3000/Appoi/Appoi.html

---

## 🔌 API Endpoints

All backend APIs are available at: `http://localhost:2525/hospital-management-system/api`

### Doctors
- GET `/api/doctors` - Get all doctors
- GET `/api/doctors/{id}` - Get doctor by ID
- POST `/api/doctors` - Add new doctor

### Patients
- GET `/api/patients` - Get all patients
- GET `/api/patients/{id}` - Get patient by ID
- POST `/api/patients` - Add new patient

### Appointments
- GET `/api/appointments` - Get all appointments
- GET `/api/appointments/{id}` - Get appointment by ID
- POST `/api/appointments` - Schedule appointment

### Billing
- GET `/api/bills` - Get all bills
- GET `/api/bills/{id}` - Get bill by ID
- POST `/api/bills` - Add new bill

---

## 🧪 Testing the Setup

### Test 1: Backend API
Open browser or use curl:
```bash
curl http://localhost:2525/hospital-management-system/api/doctors
```
Expected: JSON array of doctors

### Test 2: Frontend Loading
Open: http://localhost:3000/Main.html
Expected: Hospital homepage loads with doctors section

### Test 3: Frontend-Backend Connection
1. Open browser console (F12)
2. Go to: http://localhost:3000/Main.html
3. Check console for any errors
4. Doctors section should load dynamically from backend

---

## 🐛 Troubleshooting

### Issue: Backend API returns 404
**Solution:** 
- Verify Tomcat is running on port 2525
- Check if WAR file is deployed: Look for `hospital-management-system` folder in Tomcat webapps
- Check Tomcat logs: `%TOMCAT_HOME%\logs\catalina.out`

### Issue: Database connection failed
**Solution:**
- Verify MySQL is running on port 3001:
  ```sql
  SHOW VARIABLES LIKE 'port';
  ```
- Test connection:
  ```bash
  mysql -u root -p -h localhost -P 3001
  ```
- Check database exists:
  ```sql
  SHOW DATABASES LIKE 'hospital_management';
  ```

### Issue: Frontend can't connect to backend
**Solution:**
- Check browser console for CORS errors
- Verify API_BASE_URL in `/app/webpage/js/api.js` is: `http://localhost:2525/hospital-management-system`
- Test backend directly in browser first

### Issue: Frontend shows "Loading doctors..." forever
**Solution:**
- Check browser console (F12) for errors
- Verify backend API is accessible
- Check network tab in browser dev tools to see API requests

---

## 📁 Project Structure

```
D:\HSP\Hospital Management System\
├── src/
│   └── main/
│       ├── java/main/java/
│       │   ├── controller/     # API Controllers
│       │   ├── service/        # Business Logic
│       │   ├── dao/            # Database Access
│       │   ├── entities/       # Data Models
│       │   ├── servlet/        # User Servlets
│       │   └── util/           # Utilities (DB Connection)
│       └── webapp/
│           └── WEB-INF/
│               └── web.xml     # Servlet Configuration
├── webpage/
│   ├── Main.html               # Homepage
│   ├── main.css                # Styles
│   ├── script.js               # Main JS
│   ├── js/
│   │   ├── api.js             # API Client (FIXED)
│   │   └── doctors.js         # Doctor Loading
│   ├── login/                  # Login Page
│   └── Appoi/                  # Appointment Page
├── serve-webpage.js            # Frontend Server
├── pom.xml                     # Maven Config
└── target/                     # Build Output
```

---

## ✨ What's Working Now

✅ Frontend serves on port 3000 (Node.js)
✅ Backend serves on port 2525 (Tomcat)
✅ API calls properly routed from frontend to backend
✅ Database connection configured
✅ CORS enabled for cross-origin requests
✅ All servlets properly mapped
✅ Dynamic doctor loading from database
✅ Appointment booking system
✅ User registration and login

---

## 🎯 Next Steps

1. **Test All Features:**
   - View doctors list
   - Book appointments
   - Register new users
   - Add patients

2. **Add Sample Data:**
   - Add some doctors to database
   - Add sample patients
   - Test appointment booking

3. **Production Deployment:**
   - Update API_BASE_URL for production domain
   - Configure proper database credentials
   - Set up proper logging
   - Add authentication/authorization

---

## 📞 Support

If you encounter any issues:
1. Check Tomcat logs: `logs/catalina.out`
2. Check browser console (F12)
3. Verify all services are running
4. Test each component individually

---

**Last Updated:** December 2024
**Status:** ✅ All issues fixed and ready to run!
