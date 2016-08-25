@echo off

set _PRG_DIR=%~dp0
set _EXEC_NAME=app.war

if not exist "%_PRG_DIR%setenv.bat" goto SKIPENV
call "%_PRG_DIR%setenv.bat"
:SKIPENV

set HOME_DIR=%_PRG_DIR%data
set _JAVA_EXEC=%JRE_HOME%\bin\java.exe
set _ARGS=--server.address=%TCP_LISTEN% --server.port=%TCP_PORT% EXTRA_ARGS

echo checking executable existance ...
if not exist "%_PRG_DIR%%_EXEC_NAME%" echo Executable %_PRG_DIR%%_EXEC_NAME% not found! goto END

echo found executable %_PRG_DIR%%_EXEC_NAME%

echo checking jre ...
if not exist "%_JAVA_EXEC%" echo JRE %_JAVA_EXEC% not found! goto END

echo found jre %_JAVA_EXEC%

echo startting application in foreground ...

"%_JAVA_EXEC%" -jar %_PRG_DIR%%_EXEC_NAME% %_ARGS%

:END
pause