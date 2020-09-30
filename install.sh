#!/bin/sh

INSTALL_DIR=/opt/HttpServer
PWD_DIR=`pwd`

if [ -d $INSTALL_DIR ] ; then
    LINE[0]="HttpServer уже установлен"
    LINE[1]="Перед установкой удалите"
    LINE[2]=""
    clear
    exit 1
fi

echo Install HttpServer

sleep 1

mkdir -p $INSTALL_DIR
mkdir -p $INSTALL_DIR/lib
chmod 0777 $INSTALL_DIR/lib
mkdir -p $INSTALL_DIR/saves
chmod 0777 $INSTALL_DIR/saves

cp -f $PWD_DIR/bin/* $INSTALL_DIR/
cp -f $PWD_DIR/lib/* $INSTALL_DIR/lib
cp -f $PWD_DIR/saves/* $INSTALL_DIR/saves

sleep 1
