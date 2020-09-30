#!/bin/sh

psql -U postgres -h $1 -p $2 -f test_sql.sql