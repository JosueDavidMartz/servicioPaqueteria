package pojo;

public class Seguimiento {
    
    private Integer idSeguimiento;
    private String nombre;
    private String fecha;
    private String hora;
    private Integer idColaborador;
    private Integer idEnvio;

    public Seguimiento() {
    }

    public Seguimiento(Integer idSeguimiento, String nombre, String fecha, String hora, Integer idColaborador, Integer idEnvio) {
        this.idSeguimiento = idSeguimiento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.idColaborador = idColaborador;
        this.idEnvio = idEnvio;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    
    
    
}
