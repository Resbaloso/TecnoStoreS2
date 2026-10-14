drop database if exists tecnostore_s2;
create database tecnostore_s2;
use tecnostore_s2;

create table marcas(id int auto_increment, nombre varchar(45) not null unique, primary key(id));

create table sistemas_operativos(id int auto_increment, nombre varchar(45) not null unique, primary key(id)); 

create table celulares(id int auto_increment, sku varchar(45) not null unique, modelo varchar(45) not null unique, 
marcas_fk int not null, stock int not null, precio decimal(10,2) not null, sistemas_operativos_fk int not null, 
gama enum("baja","media","alta") not null, primary key(id), foreign key(marcas_fk) references marcas(id), foreign key(sistemas_operativos_fk) references sistemas_operativos(id));

create table personas(id int auto_increment, nombre varchar(45) not null, identificacion varchar(45) not null unique, 
correo varchar(45) not null unique, telefono varchar(45), primary key(id));

create table roles(id int auto_increment, rol varchar(45) not null, primary key(id));

create table usuarios(id int auto_increment, personas_fk int not null, username varchar(45) not null, 
contraseña varchar(45) not null, roles_fk int not null,
primary key(id), foreign key(personas_fk) references personas(id), foreign key(roles_fk) references roles(id));

create table ventas(id int auto_increment, usuarios_fk int not null,
fecha datetime  default current_timestamp, total decimal(10,2), primary key(id),
foreign key(usuarios_fk) references usuarios(id));

create table detalles_ventas(id int auto_increment, ventas_fk int not null, celulares_fk int not null,
cantidad int not null, precio decimal(10,2) not null, subtotal decimal(10,2) not null, primary key(id),
foreign key(ventas_fk) references ventas(id), foreign key(celulares_fk) references celulares(id));

insert into roles(id, rol) values (1, 'Admin');
insert into roles(id, rol) values (2, 'Cliente');

insert into personas (nombre, identificacion, correo, telefono) values ('Admin', '1000000001', 'admin@tecnostore.com', '3016767676');
insert into usuarios (personas_fk, username, contraseña, roles_fk) values (1, 'admin', 'Admincito36', 1);

insert into marcas (nombre) values ("Samsung");
insert into marcas (nombre) values ("Apple");
insert into marcas (nombre) values ("Motorola");

insert into sistemas_operativos (nombre) values ("Android");
insert into sistemas_operativos (nombre) values ("IOS");

insert into celulares (sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama) values ("SM-A3756-M-128","Samsung A37 5g 128gb",1,25,1200000.00,1,"media");
insert into celulares (sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama) values ("IP-18PROMAX-A-256","Iphone 18 Pro Max 256gb",2,25,6200000.00,2,"alta");
insert into celulares (sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama) values ("MR-EDGE60FUSION-M-256","Motorola Edge 60 Fusion 256gb",3,25,1200000.00,1,"media");