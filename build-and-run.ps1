# Hospital Management System - Build and Run Script
# For IntelliJ Terminal (PowerShell)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Hospital Management System - Quick Start" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# Step 1: Build
Write-Host "Step 1: Building backend (skipping tests)..." -ForegroundColor Yellow
mvn clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nBuild failed! Check errors above." -ForegroundColor Red
    exit 1
}

Write-Host "`n✅ Build successful!" -ForegroundColor Green
Write-Host "WAR file created: target\hospital-management-system.war`n" -ForegroundColor Green

# Step 2: Instructions
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Next Steps:" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

Write-Host "1. Deploy WAR file to Tomcat:" -ForegroundColor White
Write-Host "   Copy target\hospital-management-system.war to Tomcat webapps folder`n" -ForegroundColor Gray

Write-Host "2. Start Tomcat on port 2525 (if not running)`n" -ForegroundColor White

Write-Host "3. Start frontend server:" -ForegroundColor White
Write-Host "   node serve-webpage.js`n" -ForegroundColor Gray

Write-Host "4. Open in browser:" -ForegroundColor White
Write-Host "   http://localhost:3000/Main.html`n" -ForegroundColor Gray

Write-Host "Press any key to continue..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
