/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

/**
 *
 * @author Usuario
 */
public class Celulares {
    private int id;
    private String sku;
    private String modelo;
    private int marcas_fk;
    private int stock;
    private double precio;
    private int sistemas_operativos_fk;
    private String gama;

    public Celulares(int id, String sku, String modelo, int marcas_fk, int stock, double precio, int sistemas_operativos_fk, String gama) {
        this.id = id;
        this.sku = sku;
        this.modelo = modelo;
        this.marcas_fk = marcas_fk;
        this.stock = stock;
        this.precio = precio;
        this.sistemas_operativos_fk = sistemas_operativos_fk;
        this.gama = gama;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getMarcas_fk() {
        return marcas_fk;
    }

    public void setMarcas_fk(int marcas_fk) {
        this.marcas_fk = marcas_fk;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getSistemas_operativos_fk() {
        return sistemas_operativos_fk;
    }

    public void setSistemas_operativos_fk(int sistemas_operativos_fk) {
        this.sistemas_operativos_fk = sistemas_operativos_fk;
    }

    public String getGama() {
        return gama;
    }

    public void setGama(String gama) {
        this.gama = gama;
    }

    @Override
    public String toString() {
        return """
            Id:                        %s
            SKU:                       %s
            Modelo:                    %s
            Marcas_fk:                 %s
            Stock:                     %s
            Precios:                   %s
            Sistemas_operativos_fk:    %s
            Gama:                      %s
            """.formatted(id, sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama);
    }
}