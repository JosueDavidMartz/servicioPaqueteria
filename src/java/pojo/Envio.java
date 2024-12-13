package pojo;


public class Envio {
    
    private Integer idEnvio;
    private String numeroGuia;
    private Float costoEnvio;
    private String comentario;
    private Integer idColaborador;
    private Integer idCliente;
    private Integer idConductor;
    private Seguimiento seguimiento ;
    private Direccion direccionOrigen;
    private Direccion direccionDestino;
   
    

    public Envio() {
        // Inicializa las subclases para evitar NullPointerException
        this.seguimiento = new Seguimiento();
        this.direccionOrigen = new Direccion();
        this.direccionDestino = new Direccion();
    }
    public Envio(Integer idEnvio, String numeroGuia, Float costoEnvio, String comentario, Integer idColaborador, Integer idCliente, Integer idConductor, Seguimiento seguimiento, Direccion direccionOrigen, Direccion direccionDestino) {
        this.idEnvio = idEnvio;
        this.numeroGuia = numeroGuia;
        this.costoEnvio = costoEnvio;
        this.comentario = comentario;
        this.idColaborador = idColaborador;
        this.idCliente = idCliente;
        this.idConductor = idConductor;
        this.seguimiento = seguimiento;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public Float getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Float costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    public Seguimiento getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(Seguimiento seguimiento) {
        this.seguimiento = seguimiento;
    }

    public Direccion getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(Direccion direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public Direccion getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(Direccion direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

   
   
    
}