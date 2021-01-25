import com.clls.dao.UserDao;
import com.clls.domain.Information;
import com.clls.domain.User;
import com.clls.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class TestUserDao {
//    private InputStream in;
//    private SqlSession sqlSession;
    @Autowired
    private UserDao userDao;
    @Before
    public void init() throws Exception {
//        //1、读取配置文件，生成字节输入流
//        in= Resources.getResourceAsStream("SqlMapConfig.xml");
//        //2、获取SqlSessionFactory
//        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
//        //3、获取SqlSession对象
//        sqlSession=factory.openSession();
//        //4、获取dao的代理对象
//        userDao=sqlSession.getMapper(UserDao.class);

//        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        userDao= (UserDao) ac.getBean("userDao");
    }

    @After
    public void destroy() throws Exception{
//        sqlSession.commit();
//        //5、释放资源
//        sqlSession.close();
//        in.close();
    }

    @Test
    public void testFindAll() throws IOException {
        List<User> users=userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSaveUser() throws IOException {
        User user=new User();
        user.setUsername("testUser");
        user.setPassword("1234");
        user.setStuNum("401");
        user.setTel("");
        userDao.saveUser(user);
    }

    @Test
    public void testFindOne() throws IOException {
        String username;
        username="testUser";
        User user;
        user=userDao.findOneByUsername(username);
        System.out.println(user);
    }

    @Test
    public void testFindInfosByUserId(){
        User user=new User();
        user.setId(1);
        List<Information> infos=userDao.findInfosByUserId(user);
        for (Information information : infos) {
            System.out.println(information);
        }

    }

    @Test
    public void testReceiveOrder(){
        int userId=1;
        int infoId=1;
        userDao.receiveOrder(userId,infoId);
    }

    @Test
    public void testFindOneByUserId(){
        int userId=1;
        System.out.println(userDao.findOneByUserId(userId));;
    }

    @Test
    public void testFindServantIdByInfoId(){
        int infoId=445;
        System.out.println(userDao.findServantIdByInfoId(infoId));
    }
}
