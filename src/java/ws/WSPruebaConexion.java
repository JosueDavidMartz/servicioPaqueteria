package ws;

import dominio.ImpPruebaConexion;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Colaborador;
import pojo.RespuestaPrueba;

@Path("prueba")
public class WSPruebaConexion {
    
    @GET
    @Path("conectar/{noPersonal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPrueba conectar(@PathParam("noPersonal") int noPersonal) {
        
        return ImpPruebaConexion.probarConexion(noPersonal);
        
    }
    
    
}
