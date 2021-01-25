import com.clls.domain.User;
import com.clls.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class TestUserService{

    @Autowired
    UserService userService;
    @Test
    public void testFindAll(){
        userService.findAll();
    }

    @Test
    public void testSave(){
        User user=new User();
        user.setStuNum("404");
        user.setTel("");
        user.setUsername("test");
        user.setPassword("");
//        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        UserService userService= (UserService)ac.getBean("userService");
        userService.saveUser(user);
    }

    @Test
    public void testReceiveOrder(){
        int userId=1;
        int infoId=1000;
        userService.receiveOrder(userId,infoId);
    }
}
