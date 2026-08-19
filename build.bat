@echo off
echo ===================================================
echo Building Software Development Document Environment
echo ===================================================
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo Build failed!
    exit /b %ERRORLEVEL%
)
echo Build completed successfully.
