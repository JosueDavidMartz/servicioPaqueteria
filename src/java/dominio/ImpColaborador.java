/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;

/**
 *
 * @author WIN 10
 */

public class ImpColaborador {


    public static List<Colaborador> obtenerTodosLosColaboradores() {
        System.out.println("impcolaborador");
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.obtenerColaborador");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
        return colaboradores;
    }
    public static Colaborador obtenerColaboradorPorNoPersonal(String noPersonal) {
        Colaborador colaborador = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("noPersonal", noPersonal);
                colaborador = conexionBD.selectOne("colaborador.colaboradorXnoPersonal", parametros);

                if (colaborador != null) {
                    System.out.println("Colaborador encontrado: " + colaborador.getNombre());
                } else {
                    System.out.println("Colaborador no encontrado para el NoPersonal: " + noPersonal);
                }

            } catch (Exception e) {
                System.err.println("Error al obtener colaborador: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            System.err.println("No se pudo establecer conexión con la base de datos.");
        }
        return colaborador;
    }
       
    public static Mensaje registrarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                // Verificar existencia del CURP o número de personal
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("curp", colaborador.getCurp());
                parametros.put("numeroPersonal", colaborador.getNumeroPersonal());
                int duplicados = conexionBD.selectOne("colaborador.verificarColaborador", parametros);

                if (duplicados > 0) {
                    msj.setError(true);
                msj.setMensaje("El CURP o el número de personal ya están registrados.");
            } else {
                // Insertar colaborador si no hay duplicados
                int filasAfectadas = conexionBD.insert("colaborador.registrarColaborador", colaborador);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("El Colaborador " + colaborador.getNombre() + " " +
                            colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno() +
                            ", fue registrado con éxito.");
                } else {
                    msj.setError(true);
                    msj.setMensaje("La información del colaborador no se pudo registrar. :c");
                }
            }
        } catch (Exception e) {
            msj.setError(true);
            msj.setMensaje("Error al registrar colaborador: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
        } else {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }

        return msj;
    }

    
    
        public static Mensaje editarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                // Verificar si el CURP está duplicado
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("curp", colaborador.getCurp());
                parametros.put("idColaborador", colaborador.getIdColaborador());
                int duplicados = conexionBD.selectOne("colaborador.verificarCURP", parametros);

                if (duplicados > 0) {
                    msj.setError(true);
                    msj.setMensaje("El CURP ya está registrado en otro colaborador.");
                } else {
                    // Proceder con la actualización si no hay duplicados
                    int filasAfectadas = conexionBD.update("colaborador.editarColaborador", colaborador);
                    conexionBD.commit();

                    if (filasAfectadas > 0) {
                        msj.setError(false);
                        msj.setMensaje("El colaborador con el ID " + colaborador.getIdColaborador() + " fue actualizado con éxito.");
                    } else {
                        msj.setError(true);
                        msj.setMensaje("No se encontró el colaborador o no se pudo actualizar.");
                    }
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error al actualizar colaborador: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("No se pudo establecer la conexión con la base de datos.");
        }

        return msj;
    }

        
       public static Mensaje eliminarColaborador (Integer idColaborador){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD!=null){
            try {
                Colaborador colaboradorExistente = conexionBD.selectOne("colaborador.idColaborador", idColaborador);
                if(colaboradorExistente!=null){
                    int numeroFilasAfectadas = conexionBD.delete("colaborador.eliminarColaborador", idColaborador);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        mensaje.setError(false);
                        mensaje.setMensaje("Cuenta eliminada con exito");
                    }else{
                        mensaje.setMensaje("Lo sentimos, no se pudo eliminar tu cuenta, intentalo mas tarde");
                    }
                }else{
                    mensaje.setMensaje("No hay ninguna cuenta registrada en la base de datos con el ID que proporcionaste");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " +e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Lo sentimos, por el momento no hay conexion a la base de datos");
        }
        return mensaje;
        
    }
       
   public static List<Colaborador> buscarColaborador(String nombre, String numeroPersonal, String rol) {
    List<Colaborador> colaboradores = null;
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();

    if (conexionBD != null) {
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            parametros.put("numeroPersonal", numeroPersonal);
            parametros.put("rol", rol);

            colaboradores = conexionBD.selectList("colaborador.buscarColaborador", parametros);

        } catch (Exception e) {
            System.err.println("Error al buscar colaboradores: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        System.err.println("No se pudo establecer conexión con la base de datos.");
    }

    return colaboradores;
}

    public static List<Colaborador> obtenerTodosLosConductores() {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("conductor.obtenerConductores");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
        return colaboradores;
    }

   
}