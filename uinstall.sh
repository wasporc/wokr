#!/bin/sh

INSTALL_DIR=/opt/HttpServer

if [ -d $INSTALL_DIR ] ; then
    rm -r $INSTALL_DIR
fi

echo Uninstall HttpServer

sleep 1

psql -U postgres -c "drop database test"