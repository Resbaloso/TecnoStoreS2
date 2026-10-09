package model.entities;

public class Venta{
    private Integer id;
    private Integer usuarios_fk;
    private java.time.OffsetDateTime fecha;
    private Double total;  

    public Venta(Integer id, Integer usuarios_fk, java.time.OffsetDateTime fecha, Double total) {
        this.id = id;
        this.usuarios_fk = usuarios_fk;
        this.fecha = fecha;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUsuarios_fk() {
        return usuarios_fk;
    }

    public void setUsuarios_fk(Integer usuarios_fk) {
        this.usuarios_fk = usuarios_fk;
    }

    public java.time.OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(java.time.OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
    return """
                Id:                        %s
                Usuario_fk:                %s
                Username:                  %s
                Total:                     %s
                """.formatted(id, usuarios_fk, fecha, total);    
    }

}
