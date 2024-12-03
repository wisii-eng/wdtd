set LOCAL_WDTD_HOME=
if "%OS%"=="Windows_NT" set LOCAL_WDTD_HOME=%~dp0
set LIBDIR=%LOCAL_WDTD_HOME%lib
setlocal enabledelayedexpansion
set LOCALCLASSPATH=
for %%i in (%LIBDIR%\*.jar) do (
	set "LOCALCLASSPATH=!LOCALCLASSPATH!%%i;"
)
jre8\bin\java.exe -splash:graphics\splash.jpg -Xmx512m    -cp "%LOCALCLASSPATH%"  com.wisii.wisedoc.Main
