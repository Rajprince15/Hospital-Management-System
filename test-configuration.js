#!/usr/bin/env node

/**
 * Configuration Test Script
 * Tests if all configurations are correct before running the application
 */

const fs = require('fs');
const path = require('path');

console.log('═══════════════════════════════════════════════════════════');
console.log('  Hospital Management System - Configuration Test');
console.log('═══════════════════════════════════════════════════════════\n');

let allTestsPassed = true;

// Test 1: Check if api.js has correct backend URL
console.log('Test 1: Checking API Configuration...');
const apiJsPath = path.join(__dirname, 'webpage', 'js', 'api.js');
try {
    const apiContent = fs.readFileSync(apiJsPath, 'utf8');
    if (apiContent.includes("'http://localhost:2525/hospital-management-system'")) {
        console.log('✅ API_BASE_URL is correctly set to: http://localhost:2525/hospital-management-system\n');
    } else if (apiContent.includes('window.location.origin')) {
        console.log('❌ FAILED: API_BASE_URL still using window.location.origin');
        console.log('   Expected: http://localhost:2525/hospital-management-system\n');
        allTestsPassed = false;
    } else {
        console.log('⚠️  WARNING: API_BASE_URL has unexpected value');
        console.log('   Please verify it points to: http://localhost:2525/hospital-management-system\n');
    }
} catch (error) {
    console.log('❌ FAILED: Could not read api.js file');
    console.log('   Error:', error.message, '\n');
    allTestsPassed = false;
}

// Test 2: Check if all required frontend files exist
console.log('Test 2: Checking Frontend Files...');
const frontendFiles = [
    'webpage/Main.html',
    'webpage/main.css',
    'webpage/script.js',
    'webpage/js/api.js',
    'webpage/js/doctors.js',
    'webpage/Appoi/Appoi.html',
    'webpage/Appoi/appointment.js',
    'webpage/login/login.html'
];

let missingFiles = [];
frontendFiles.forEach(file => {
    const filePath = path.join(__dirname, file);
    if (!fs.existsSync(filePath)) {
        missingFiles.push(file);
    }
});

if (missingFiles.length === 0) {
    console.log(`✅ All ${frontendFiles.length} frontend files found\n`);
} else {
    console.log('❌ FAILED: Missing frontend files:');
    missingFiles.forEach(file => console.log('   -', file));
    console.log('');
    allTestsPassed = false;
}

// Test 3: Check if serve-webpage.js exists and is configured
console.log('Test 3: Checking Frontend Server Configuration...');
const serverPath = path.join(__dirname, 'serve-webpage.js');
try {
    const serverContent = fs.readFileSync(serverPath, 'utf8');
    if (serverContent.includes('const PORT = 3000')) {
        console.log('✅ Frontend server configured to run on port 3000\n');
    } else {
        console.log('⚠️  WARNING: Frontend server port might not be 3000\n');
    }
} catch (error) {
    console.log('❌ FAILED: Could not read serve-webpage.js');
    console.log('   Error:', error.message, '\n');
    allTestsPassed = false;
}

// Test 4: Check if pom.xml exists
console.log('Test 4: Checking Maven Configuration...');
const pomPath = path.join(__dirname, 'pom.xml');
if (fs.existsSync(pomPath)) {
    const pomContent = fs.readFileSync(pomPath, 'utf8');
    if (pomContent.includes('<finalName>hospital-management-system</finalName>')) {
        console.log('✅ Maven project configured correctly\n');
        console.log('   WAR file will be: hospital-management-system.war\n');
    } else {
        console.log('⚠️  WARNING: Could not verify WAR file name\n');
    }
} else {
    console.log('❌ FAILED: pom.xml not found\n');
    allTestsPassed = false;
}

// Test 5: Check Java source structure
console.log('Test 5: Checking Java Source Files...');
const javaSourcePath = path.join(__dirname, 'src', 'main', 'java', 'main', 'java');
if (fs.existsSync(javaSourcePath)) {
    const controllers = ['controller/HospitalManagementServlet.java'];
    let foundControllers = 0;
    
    controllers.forEach(controller => {
        const controllerPath = path.join(javaSourcePath, controller);
        if (fs.existsSync(controllerPath)) {
            foundControllers++;
        }
    });
    
    if (foundControllers === controllers.length) {
        console.log('✅ Main servlet found\n');
    } else {
        console.log('⚠️  WARNING: Some Java files might be missing\n');
    }
} else {
    console.log('❌ FAILED: Java source directory not found\n');
    allTestsPassed = false;
}

// Test 6: Check Database Configuration
console.log('Test 6: Checking Database Configuration...');
const dbUtilPath = path.join(__dirname, 'src', 'main', 'java', 'main', 'java', 'util', 'DatabaseUtil.java');
if (fs.existsSync(dbUtilPath)) {
    const dbContent = fs.readFileSync(dbUtilPath, 'utf8');
    if (dbContent.includes('localhost:3001') && dbContent.includes('hospital_management')) {
        console.log('✅ Database configured:');
        console.log('   Host: localhost:3001');
        console.log('   Database: hospital_management\n');
    } else {
        console.log('⚠️  WARNING: Database configuration might be different\n');
    }
} else {
    console.log('⚠️  WARNING: Could not find DatabaseUtil.java\n');
}

// Summary
console.log('═══════════════════════════════════════════════════════════');
if (allTestsPassed) {
    console.log('✅ All tests passed! Configuration looks good.');
    console.log('\nNext steps:');
    console.log('1. Build backend: mvn clean package');
    console.log('2. Deploy WAR to Tomcat (port 2525)');
    console.log('3. Start frontend: node serve-webpage.js');
    console.log('4. Access: http://localhost:3000/Main.html');
} else {
    console.log('❌ Some tests failed. Please fix the issues above.');
    console.log('\nRefer to SETUP_INSTRUCTIONS.md for detailed setup guide.');
}
console.log('═══════════════════════════════════════════════════════════\n');

process.exit(allTestsPassed ? 0 : 1);
