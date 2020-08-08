@echo off
 
SETLOCAL ENABLEDELAYEDEXPANSION

cd

@REM Set user-defined variables.
@REM Uncomment the following JDK/JRE installation
@REM variable if you wish use a different JRE.
@REM set JAVA_HOME=C:\Program Files\Java\jre6

set INSTALLPATH="..\.."
set COMMONDIR=%INSTALLPATH%\Common

set COMMONLIB=%COMMONDIR%\lib
set COMMONCLASSES=%COMMONDIR%\classes
set PROPDIR=%COMMONDIR%\properties

rem
rem	End of user customization
rem

if exist "%JAVA_HOME%" goto checkJDK
goto runGen

@REM Check for JAVA.
:checkJDK
if exist "%JAVA_HOME%\bin\java.exe" goto runGen
echo Java was not found in directory %JAVA_HOME%.
echo Please edit the startgen.cmd script so that the JAVA_HOME
echo variable points to the root directory of your Java installation.
goto finish

:runGen
rem echo on

set CLASSPATH=%COMMONCLASSES%;%COMMONLIB%\*

echo *********** Starting Crypto Tool ***********
@REM echo CLASSPATH: %CLASSPATH%

"%JAVA_HOME%"\bin\java -classpath "%CLASSPATH%" -Dpinfly.props.dir="%PROPDIR%" -Dpinfly.common.dir="%COMMONDIR%" com.pinfly.common.crypto.ui.CryptoTool %1 %2
goto finish

:finish
ENDLOCAL