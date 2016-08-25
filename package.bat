@echo off

rem
rem Script to generate distribution package
rem

set PRGDIR=%~dp0

echo 'making distribution package ...'
if not exist "%PRGDIR%\dist" rd /s /q "%PRGDIR%\dist"

mkdir "%PRGDIR%\dist"

rem add  --refresh-dependencies if you want to ignore dependency caches
"%PRGDIR%\gradlew.bat" clean build -x test

echo 'copying executable ...'
cp "%PRGDIR%\build\libs\quick-mvc-*.war" "%PRGDIR%\dist\app.war"

echo 'copying startup scripts ...'
cp "%PRGDIR%\scripts\*" "%PRGDIR%\dist"

echo 'copying README.md ...'
cp "%PRGDIR%\README.md" "%PRGDIR%\dist"

echo 'done!'