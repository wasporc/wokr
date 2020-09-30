@ECHO OFF
set IP="%1"
set PORT="%2"
psql -U postgres -h %IP% -p %PORT% -f test_sql.sql