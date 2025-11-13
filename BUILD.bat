@echo off
echo Building Hospital Management System...
mvn clean package -DskipTests
echo.
echo Done! WAR file is in: target\hospital-management-system.war
pause
