@ECHO OFF
set INSTALL_DIR="c:\opt\HttpServer"
set PWD_DIR=%cd%

if Exist "%INSTALL_DIR%" (exit 1)

echo "Install HttpServer"
mkdir %INSTALL_DIR%
mkdir %INSTALL_DIR%\lib
mkdir %INSTALL_DIR%\saves

copy %PWD_DIR%\lib\* %INSTALL_DIR%\lib
copy %PWD_DIR%\saves\* %INSTALL_DIR%\saves
copy %PWD_DIR%\bin\* %INSTALL_DIR%\

