package dominio;

import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Colaborador;
import pojo.RespuestaPrueba;


public class ImpPruebaConexion {
    
    public static RespuestaPrueba probarConexion(int noPersonal) {
    RespuestaPrueba respuesta = new RespuestaPrueba();

    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                System.out.println("en imp");
               respuesta.setColaborador(conexionBD.selectOne("prueba.conectar", noPersonal));

                if (respuesta.getColaborador() != null) {
                    respuesta.setError(false);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No hay registros");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("No se pudo establecer la conexión con la base de datos.");
        }
        return respuesta;
    }

}
