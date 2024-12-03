rem %~dp0 is the expanded pathname of the current script under NT
set LOCAL_WDWE_HOME=
if "%OS%"=="Windows_NT" set LOCAL_WDWE_HOME=%~dp0

rem Code from Apache Ant project
rem Slurp the command line arguments. This loop allows for an unlimited number
rem of arguments (up to the command line limit, anyway).
rem Could also do a "shift" and "%*" for all params, but apparently doesn't work 
rem with Win9x.
set WDWE_CMD_LINE_ARGS=%1
if ""%1""=="""" goto doneStart
shift
:setupArgs
if ""%1""=="""" goto doneStart
set WDWE_CMD_LINE_ARGS=%WDWE_CMD_LINE_ARGS% %1
shift
goto setupArgs
rem This label provides a place for the argument list loop to break out 
rem and for NT handling to skip to.
:doneStart
set LOGCHOICE=
rem The default commons logger for JDK1.4 is JDK1.4Logger.
rem To use a different logger, uncomment the one desired below
rem set LOGCHOICE=-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.NoOpLog
rem set LOGCHOICE=-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog
rem set LOGCHOICE=-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger

set LOGLEVEL=
rem Logging levels
rem Below option is only if you are using SimpleLog instead of the default JDK1.4 Logger.
rem To set logging levels for JDK 1.4 Logger, edit the %JAVA_HOME%\JRE\LIB\logging.properties 
rem file instead.
rem Possible SimpleLog values:  "trace", "debug", "info" (default), "warn", "error", or "fatal".
rem set LOGLEVEL=-Dorg.apache.commons.logging.simplelog.defaultlog=INFO

set LIBDIR=%LOCAL_WDWE_HOME%target\lib
setlocal enabledelayedexpansion
set LOCALCLASSPATH=
for %%i in (%LIBDIR%\*.jar) do (
	set "LOCALCLASSPATH=!LOCALCLASSPATH!%%i;"
)
%JAVA_HOME%\bin\java.exe   %LOGCHOICE% %LOGLEVEL% -cp  "%LOCALCLASSPATH%" com.wisii.component.startUp.Start %WDWE_CMD_LINE_ARGS% awt authorityid=default