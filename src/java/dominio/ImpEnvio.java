package dominio;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Direccion;
import pojo.Envio;
import pojo.Mensaje;
import pojo.RespuestaEnvio;
import pojo.ResultadoObtenerEnvio;

public class ImpEnvio {

    public static RespuestaEnvio registrarEnvio(Envio envio) throws Exception {
        boolean bandera = false;
        RespuestaEnvio msj = new RespuestaEnvio();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try{
                
                conexionBD.getConnection().setAutoCommit(false);
                
                HashMap<String, Object> parametrosEnvio = new HashMap<>();
                //parametrosEnvio.put("costoEnvio", envio.getCostoEnvio());
                parametrosEnvio.put("idColaborador", envio.getIdColaborador());
                parametrosEnvio.put("idCliente", envio.getIdCliente()); 
                
               
                String guia = generarNumeroGuia(conexionBD);
                parametrosEnvio.put("numeroGuia", guia);
                
                int filasAfectadas = conexionBD.insert("envio.registrarEnvio", parametrosEnvio);                                   
                Object idEnvioObj = parametrosEnvio.get("idEnvio");
                Integer idEnvio = null;
                if (idEnvioObj instanceof BigInteger) {
                    idEnvio = ((BigInteger) idEnvioObj).intValue();
                } else {
                    idEnvio = (Integer) idEnvioObj;
                }
                
                System.out.println("ID del envío registrado: " + idEnvio);

                HashMap<String, Object> parametrosDestino = new HashMap<>();
                parametrosDestino.put("tipo", envio.getDireccionDestino().getTipo());
                parametrosDestino.put("calle", envio.getDireccionDestino().getCalle());
                parametrosDestino.put("numero", envio.getDireccionDestino().getNumero());
                parametrosDestino.put("colonia", envio.getDireccionDestino().getColonia());
                parametrosDestino.put("codigoPostal", envio.getDireccionDestino().getCodigoPostal());
                parametrosDestino.put("ciudad", envio.getDireccionDestino().getCiudad());
                parametrosDestino.put("estado", envio.getDireccionDestino().getEstado());
                parametrosDestino.put("idEnvio", idEnvio);
                
                HashMap<String, Object> parametrosOrigen = new HashMap<>();
                parametrosOrigen.put("tipo", envio.getDireccionOrigen().getTipo());
                parametrosOrigen.put("calle", envio.getDireccionOrigen().getCalle());
                parametrosOrigen.put("numero", envio.getDireccionOrigen().getNumero());
                parametrosOrigen.put("colonia", envio.getDireccionOrigen().getColonia());
                parametrosOrigen.put("codigoPostal", envio.getDireccionOrigen().getCodigoPostal());
                parametrosOrigen.put("ciudad", envio.getDireccionOrigen().getCiudad());
                parametrosOrigen.put("estado", envio.getDireccionOrigen().getEstado());                                
                parametrosOrigen.put("idEnvio", idEnvio);
                
                HashMap<String, Object> parametrosSeguimiento = new HashMap<>();
                parametrosSeguimiento.put("nombre", envio.getSeguimiento().getNombre());
                parametrosSeguimiento.put("fecha", envio.getSeguimiento().getFecha());
                parametrosSeguimiento.put("hora", envio.getSeguimiento().getHora());
                parametrosSeguimiento.put("idColaborador", envio.getSeguimiento().getIdColaborador());
                parametrosSeguimiento.put("idEnvio", idEnvio);
                
                
                
                conexionBD.insert("envio.registrarDireccion", parametrosOrigen);          

                conexionBD.insert("envio.registrarDireccion", parametrosDestino);   
                
                conexionBD.insert("envio.registrarSeguimiento", parametrosSeguimiento);
                            
                /*HashMap<String, Object> parametrosSeguimiento = new HashMap<>();
                parametrosSeguimiento.put("nombre", envio.getSeguimiento().getNombre());
                parametrosSeguimiento.put("fecha", envio.getSeguimiento().getFecha());
                parametrosSeguimiento.put("hora", envio.getSeguimiento().getHora());
                parametrosSeguimiento.put("idColaborador", envio.getSeguimiento().getidColaborador());
                
                conexionBD.insert("envio.registrarSeguimiento", parametrosSeguimiento);  */
                
                
                
                conexionBD.commit();
                
                if(filasAfectadas > 0){                   
                    msj.setError(false);
                    msj.setMensaje("El envío fue registrado con éxito.");
                    msj.setNumeroGuia(guia);
                }else{
                    msj.setError(true);
                    msj.setMensaje("El envío no pudo ser registrado");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                throw e; // Manejar o loguear el error adecuadamente.
            } finally {
                if (conexionBD != null) {
                    conexionBD.close();
                }
            }
        }else{
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible");
        }
        return msj;
    }
        
    
    private static String generarNumeroGuia(SqlSession conexionBD) {
        String numeroGuia;
        boolean existe;
        do {
            numeroGuia = UUID.randomUUID().toString().substring(0, 8); // Genera un número único
            existe = conexionBD.selectOne("envio.verificarNumeroGuia", numeroGuia) != null;
        } while (existe);
        return numeroGuia;
    }

    public static Mensaje actualizarEnvio(Envio envio) {
        boolean bandera = false;
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try{       
                
                conexionBD.getConnection().setAutoCommit(false);
                
                HashMap<String, Object> parametrosEnvio = new HashMap<>();
                //parametrosEnvio.put("costoEnvio", envio.getCostoEnvio());
                parametrosEnvio.put("idColaborador", envio.getIdColaborador());
                parametrosEnvio.put("idCliente", envio.getIdCliente()); 
                parametrosEnvio.put("costoEnvio", envio.getCostoEnvio());    
                parametrosEnvio.put("idEnvio", envio.getIdEnvio());
                parametrosEnvio.put("idConductor", envio.getIdConductor());
                
                HashMap<String, Object> parametrosDestino = new HashMap<>();
                parametrosDestino.put("tipo", envio.getDireccionOrigen().getTipo());
                parametrosDestino.put("calle", envio.getDireccionOrigen().getCalle());
                parametrosDestino.put("numero", envio.getDireccionOrigen().getNumero());
                parametrosDestino.put("colonia", envio.getDireccionOrigen().getColonia());
                parametrosDestino.put("codigoPostal", envio.getDireccionOrigen().getCodigoPostal());
                parametrosDestino.put("ciudad", envio.getDireccionOrigen().getCiudad());
                parametrosDestino.put("estado", envio.getDireccionOrigen().getEstado());
                parametrosDestino.put("idEnvio", envio.getIdEnvio());
                
                HashMap<String, Object> parametrosOrigen = new HashMap<>();
                parametrosOrigen.put("tipo", envio.getDireccionDestino().getTipo());
                parametrosOrigen.put("calle", envio.getDireccionDestino().getCalle());
                parametrosOrigen.put("numero", envio.getDireccionDestino().getNumero());
                parametrosOrigen.put("colonia", envio.getDireccionDestino().getColonia());
                parametrosOrigen.put("codigoPostal", envio.getDireccionDestino().getCodigoPostal());
                parametrosOrigen.put("ciudad", envio.getDireccionDestino().getCiudad());
                parametrosOrigen.put("estado", envio.getDireccionDestino().getEstado());                                
                parametrosOrigen.put("idEnvio", envio.getIdEnvio());
                
                HashMap<String, Object> parametrosSeguimiento = new HashMap<>();
                parametrosSeguimiento.put("nombreSeguimiento", envio.getSeguimiento().getNombre());
                parametrosSeguimiento.put("idEnvio", envio.getIdEnvio());
                
               /* String consultarEstatus = conexionBD.selectOne("envio.consultarSeguimiento", envio.getIdEnvio()); 
                System.out.println("actual: "+consultarEstatus);
                System.out.println("nuevo: "+envio.getSeguimiento().getNombre());
                if(consultarEstatus!=null){
                    System.out.println("entre 1");
                    if(!consultarEstatus.equals(envio.getSeguimiento().getNombre())){
                        String fechaActual = LocalDate.now().toString(); // Formato: "YYYY-MM-DD"
                        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                         System.out.println("entre 2");
                        
                        Map<String, Object> parametros = new HashMap<>();
                        parametros.put("nombre", envio.getSeguimiento().getNombre()); // El nombre del estatus
                        parametros.put("fecha", fechaActual); // Fecha actual
                        parametros.put("hora", horaActual);  // Hora actual
                        parametros.put("idColaborador", envio.getIdColaborador()); // Id del colaborador
                        parametros.put("idEnvio", envio.getIdEnvio()); // Id del envío

                        // Llamada al mapper
                        conexionBD.insert("envio.registrarSeguimiento", parametros);

                    }
                }*/
             /*   String consultarEstatus = conexionBD.selectOne("envio.consultarSeguimiento", envio.getIdEnvio());
                int filasAfectadasSeguimiento = 0;
            if (consultarEstatus != null && !consultarEstatus.equals(envio.getSeguimiento().getNombre())) {
                // Registrar nuevo seguimiento si el estatus cambia
                String fechaActual = LocalDate.now().toString(); // "YYYY-MM-DD"
                String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                HashMap<String, Object> parametrosSeguimiento = new HashMap<>();
                parametrosSeguimiento.put("nombre", envio.getSeguimiento().getNombre());
                parametrosSeguimiento.put("fecha", fechaActual);
                parametrosSeguimiento.put("hora", horaActual);
                parametrosSeguimiento.put("idColaborador", envio.getIdColaborador());
                parametrosSeguimiento.put("idEnvio", envio.getIdEnvio());

                // Insertar nuevo registro en seguimiento
                filasAfectadasSeguimiento = conexionBD.insert("envio.registrarSeguimiento", parametrosSeguimiento);
            }*/

                
                int filasAfectadasEnvio = conexionBD.insert("envio.actualizarEnvio", parametrosEnvio); 
                      
                int filasAfectadasOrigen = conexionBD.insert("envio.actualizarDireccion", parametrosOrigen);          

                int filasAfectadasDestino = conexionBD.insert("envio.actualizarDireccion", parametrosDestino);                                                    
                
                //int filasAfectadasSeguimiento = conexionBD.insert("envio.actualizarSeguimiento", parametrosSeguimiento); 
                
                
                
                
                
                conexionBD.commit();
                
                if(filasAfectadasEnvio > 0 && filasAfectadasOrigen > 0 && filasAfectadasDestino > 0 ){                   
                    msj.setError(false);
                    msj.setMensaje("El envío fue actualizado con éxito.");
                }else{
                    msj.setError(true);
                    msj.setMensaje("El envío no pudo ser actualizado");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                
            } finally {
                if (conexionBD != null) {
                    conexionBD.close();
                }
            }
        }else{
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible");
        }
        return msj;
    }
   /* public static Mensaje actualizarEnvio(Envio envio) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD == null) {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible");
            return msj;
        }

        try {
            conexionBD.getConnection().setAutoCommit(false);

            // Crear parámetros para actualizar envío
            HashMap<String, Object> parametrosEnvio = new HashMap<>();
            parametrosEnvio.put("idColaborador", envio.getIdColaborador());
            parametrosEnvio.put("idCliente", envio.getIdCliente());
            parametrosEnvio.put("costoEnvio", envio.getCostoEnvio());
            parametrosEnvio.put("comentario", envio.getComentario());

            // Crear parámetros para actualizar direcciones (origen y destino)
            HashMap<String, Object> parametrosOrigen = new HashMap<>();
            parametrosOrigen.put("tipo", envio.getDireccionOrigen().getTipo());
            parametrosOrigen.put("calle", envio.getDireccionOrigen().getCalle());
            parametrosOrigen.put("numero", envio.getDireccionOrigen().getNumero());
            parametrosOrigen.put("colonia", envio.getDireccionOrigen().getColonia());
            parametrosOrigen.put("codigoPostal", envio.getDireccionOrigen().getCodigoPostal());
            parametrosOrigen.put("ciudad", envio.getDireccionOrigen().getCiudad());
            parametrosOrigen.put("estado", envio.getDireccionOrigen().getEstado());
            parametrosOrigen.put("idEnvio", envio.getIdEnvio());

            HashMap<String, Object> parametrosDestino = new HashMap<>();
            parametrosDestino.put("tipo", envio.getDireccionDestino().getTipo());
            parametrosDestino.put("calle", envio.getDireccionDestino().getCalle());
            parametrosDestino.put("numero", envio.getDireccionDestino().getNumero());
            parametrosDestino.put("colonia", envio.getDireccionDestino().getColonia());
            parametrosDestino.put("codigoPostal", envio.getDireccionDestino().getCodigoPostal());
            parametrosDestino.put("ciudad", envio.getDireccionDestino().getCiudad());
            parametrosDestino.put("estado", envio.getDireccionDestino().getEstado());
            parametrosDestino.put("idEnvio", envio.getIdEnvio());

            // Realizar actualizaciones en la base de datos
            int filasAfectadasEnvio = conexionBD.update("envio.actualizarEnvio", parametrosEnvio);
            int filasAfectadasOrigen = conexionBD.update("envio.actualizarDireccion", parametrosOrigen);
            int filasAfectadasDestino = conexionBD.update("envio.actualizarDireccion", parametrosDestino);

            // Validar resultados
            if (filasAfectadasEnvio > 0 && filasAfectadasOrigen > 0 && filasAfectadasDestino > 0) {
                conexionBD.commit();
                msj.setError(false);
                msj.setMensaje("El envío fue actualizado con éxito.");
            } else {
                conexionBD.rollback();
                msj.setError(true);
                msj.setMensaje("El envío no pudo ser actualizado. Verifica los datos proporcionados.");
            }
        } catch (SQLException sqlEx) {
            conexionBD.rollback();
            msj.setError(true);
            msj.setMensaje("Error de base de datos: " + sqlEx.getMessage());
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            conexionBD.rollback();
            msj.setError(true);
            msj.setMensaje("Error interno del servidor: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (conexionBD != null) {
                    conexionBD.close();
                }
            } catch (Exception closeEx) {
                msj.setError(true);
                msj.setMensaje("Error al cerrar la conexión: " + closeEx.getMessage());
                closeEx.printStackTrace();
            }
        }

        return msj;
    }

*/
    public static ResultadoObtenerEnvio consultarEnvio(String numeroGuia) {
        ResultadoObtenerEnvio resultado = new ResultadoObtenerEnvio();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        
        
        if (conexionBD != null) {
            try {           
                // Consultamos el idColaborador actual de la unidad
               resultado = conexionBD.selectOne("envio.consultarEnvio", numeroGuia);
               System.out.println("Resultado de la consulta: " + resultado);
               
               if (resultado == null) {
                    // Si no se encontró el envío, inicializas un nuevo objeto para manejar el caso
                    resultado = new ResultadoObtenerEnvio();
                    resultado.setError(true);
                    resultado.setMensaje("No se encontró el envio");
                } else {
                    // Si se encontró el envío
                    resultado.setError(false);
                    resultado.setMensaje("Se encontró el envio");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                resultado.setError(true);
                resultado.setMensaje("Error aobtener envio " + e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        } else {
         
            resultado.setError(true);
            resultado.setMensaje("Por el momento el servicio no está disponible.");
        }

        return resultado;
    }   

    public static List<ResultadoObtenerEnvio> obtenerEnvios() {
        List<ResultadoObtenerEnvio> resultado = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                // Consultamos los envíos
                resultado = conexionBD.selectList("envio.obtenerEnvios");

                if (resultado == null || resultado.isEmpty()) {
                    // Crear un objeto para agregar el mensaje de error
                    ResultadoObtenerEnvio mensajeError = new ResultadoObtenerEnvio();
                    mensajeError.setMensaje("No se encontraron envíos.");
                    resultado = new ArrayList<>();
                    resultado.add(mensajeError); // Agregamos el mensaje a la lista
                }
            } catch (Exception e) {
                // Crear un objeto para el mensaje de error
                ResultadoObtenerEnvio mensajeError = new ResultadoObtenerEnvio();
                mensajeError.setMensaje("Error al obtener envíos: " + e.getMessage());
                resultado = new ArrayList<>();
                resultado.add(mensajeError); // Agregamos el mensaje a la lista
                conexionBD.rollback();
            } finally {
                conexionBD.close();
            }
        } else {
            // Crear un objeto para el mensaje de error
            ResultadoObtenerEnvio mensajeError = new ResultadoObtenerEnvio();
            mensajeError.setMensaje("Por el momento el servicio no está disponible.");
            resultado = new ArrayList<>();
            resultado.add(mensajeError); // Agregamos el mensaje a la lista
        }

        return resultado;
    }

   /* public static Mensaje asignarSeguimiento(Envio envio) {
       Mensaje msj = new Mensaje();
       SqlSession conexionBD = MyBatisUtil.obtenerConexion();
       
       if(conexionBD!=null){
           try {
               String fechaActual = LocalDate.now().toString(); // "YYYY-MM-DD"
               String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    
               String consultarEstatus = conexionBD.selectOne("envio.consultarSeguimiento", envio.getIdEnvio());
               int filasAfectadasSeguimiento = 0;
            if (consultarEstatus != null && !consultarEstatus.equals(envio.getSeguimiento().getNombre())) {
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("idEnvio", envio.getIdEnvio());
                parametros.put("nombre", envio.getSeguimiento().getNombre());
                parametros.put("fecha", fechaActual);
                parametros.put("hora", horaActual);
                parametros.put("idColaborador", envio.getIdColaborador());


                // Insertar nuevo registro en seguimiento
                filasAfectadasSeguimiento = conexionBD.insert("envio.registrarSeguimiento", parametros);
            }
               
               if(filasAfectadasSeguimiento > 0){
                   conexionBD.commit();
                   msj.setError(false);
                   msj.setMensaje("Se registró un nuevo estatus");
               }else{
                   msj.setError(true);
                   msj.setMensaje("No se pudo actualizar el estatus");
               }
               
           } catch (Exception e) {
               msj.setError(true);
               msj.setMensaje("Error: "+e);
               conexionBD.rollback();
           }finally {
                conexionBD.close();
            }
       }else{
           msj.setError(true);
           msj.setMensaje("Error en la conexion");
       }
       return msj;
    }*/
    public static Mensaje asignarSeguimiento(Envio envio) {
    Mensaje msj = new Mensaje();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    
    if (conexionBD != null) {
        try {
            // Obtener la fecha y hora actual
            String fechaActual = LocalDate.now().toString(); // "YYYY-MM-DD"
            String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            
            // Consultar el último estatus registrado
            String consultarEstatus = conexionBD.selectOne("envio.consultarSeguimiento", envio.getIdEnvio());
            System.out.println("Estatus actual: " + consultarEstatus);
            // Verificar si el estatus actual es diferente al que se desea asignar
            if (consultarEstatus == null || !consultarEstatus.equals(envio.getSeguimiento().getNombre())) {
                
                // Preparar los parámetros para insertar el nuevo seguimiento
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("idEnvio", envio.getIdEnvio());
                parametros.put("nombre", envio.getSeguimiento().getNombre());
                parametros.put("fecha", fechaActual);
                parametros.put("hora", horaActual);
                parametros.put("idColaborador", envio.getIdColaborador());
                
                // Insertar el nuevo estatus en la base de datos
                int filasAfectadasSeguimiento = conexionBD.insert("envio.registrarSeguimiento", parametros);
                
                // Verificar si la inserción fue exitosa
                if (filasAfectadasSeguimiento > 0) {
                    conexionBD.commit();
                    msj.setError(false);
                    msj.setMensaje("Se registró un nuevo estatus.");
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo registrar el estatus.");
                }
            } else {
                msj.setError(true);
                msj.setMensaje("El estatus que se intenta asignar ya es el mismo que el actual.");
            }
            
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje("Error: " + e);
            conexionBD.rollback();
        } finally {
            conexionBD.close();
        }
    } else {
        msj.setError(true);
        msj.setMensaje("Error en la conexión.");
    }
    
    return msj;
}


    
}
