package mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MyBatisUtil {
    
    public static final String RESOURCE = "mybatis/mybatis-conf.xml";
    public static final String ENVIRONMENT = "development";
    
    public static SqlSession obtenerConexion(){
        SqlSession session = null;
        try{
            Reader reader = Resources.getResourceAsReader(RESOURCE);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            session = sqlMapper.openSession();
        }catch(IOException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        return session;
    }
}