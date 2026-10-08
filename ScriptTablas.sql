drop database if exists tecnostore_s2;
create database tecnostore_s2;
use tecnostore_s2;
create table celulares(id int not null unique auto_increment, sku varchar(45) not null, modelo varchar(45) not null, marca varchar(45) not null, stock int not null, precio decimal not null, sistema_operativo varchar(45) not null, gama enum("baja","media","alta") not null, primary key(id));
create table personas(id int not null unique auto_increment, nombre varchar(45) not null, identificacion varchar(45), correo varchar(45), telefono int, primary key(id));
create table usuarios(id int not null unique auto_increment, personas_fk int not null, username varchar(45) not null, contraseña varchar(45) not null, rol enum("admin", "usuario") not null, primary key(id), foreign key(personas_fk) references personas(id));