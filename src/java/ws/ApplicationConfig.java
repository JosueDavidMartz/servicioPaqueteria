package ws;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("apipacket")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) { //Es un diccionario
        resources.add(ws.WSCliente.class);
        resources.add(ws.WSColaborador.class);
        resources.add(ws.WSConductores.class);
        resources.add(ws.WSEnvios.class);
        resources.add(ws.WSLogin.class);
        resources.add(ws.WSPaquetes.class);
        resources.add(ws.WSPruebaConexion.class);
        resources.add(ws.WSUnidades.class);
      
    }
    
}
