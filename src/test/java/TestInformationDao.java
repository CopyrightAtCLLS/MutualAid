import com.clls.dao.InformationDao;
import com.clls.domain.Information;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class TestInformationDao {
//    private InputStream in;
//    private SqlSession sqlSession;
    @Autowired
    private InformationDao informationDao;
    @Before
    public void init() throws Exception {
//        //1、读取配置文件，生成字节输入流
//        in= Resources.getResourceAsStream("SqlMapConfig.xml");
//        //2、获取SqlSessionFactory
//        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
//        //3、获取SqlSession对象
//        sqlSession=factory.openSession();
//        //4、获取dao的代理对象
//        informationDao=sqlSession.getMapper(InformationDao.class);

//        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        informationDao= (InformationDao) ac.getBean("informationDao");
    }

    @After
    public void destroy() throws Exception{
        //5、释放资源
//        sqlSession.close();
//        in.close();
    }

    @Test
    public void testFindAll() throws IOException {
        List<Information> infos=informationDao.findAll();
        for (Information info : infos) {
            System.out.println(info);
        }
    }

    @Test
    public void testSaveInformation() throws IOException {
        Information info=new Information();
        info.setDate(new Date());
        info.setUserId(2);
        System.out.println(new Date());
        int temp=0;
        for(int i=0;i<25;i++) {
            info.setBounty(i);
            if(i%5==0)
                temp++;
            info.setContent("NEED HELP PLZ "+temp);
            informationDao.saveInformation(info);
        }
//        sqlSession.commit();
    }
    @Test
    public void testFindOne() throws IOException {
//        System.out.println(new String("哈哈").length());
        Information info=informationDao.findOne(1);
        System.out.println(info);
    }
    @Test
    public void testFindTotalCount(){
        System.out.println(informationDao.findTotalCount());
    }

    @Test
    public void testFindByPage(){
        List<Information> infos=informationDao.findByPage(0,5);
        for (Information information : infos) {
            System.out.println(information);
        }
    }

    @Test
    public void testDeleteOneInformation(){
        informationDao.deleteOneInformation(100);
    }

    @Test
    public void testFindUserIdById(){
        int userIdById = informationDao.findUserIdById(1);
        System.out.println(userIdById);
    }

    @Test
    public void testModifyState(){
        int id=2;
        int state=2;
        informationDao.modifyState(id,state);
    }

    @Test
    public void testFindState(){
        int state=informationDao.findState(50);
        System.out.println(state);
    }

    @Test
    public void testfindInfoOfUserByPage(){
        List<Information> list=informationDao.findInfoOfUserByPage(0, 8, 1);
        for (Information information : list) {
            System.out.println(information);
        }
    }

    @Test
    public void testCompleteOneOrder(){
        informationDao.completeOrderByInfoId(310);
    }

    @Test
    public void testfindAllTakenOrderByPage(){
        List<Information> infos=informationDao.findAllTakenOrderByPage(0,5,8);
        for (Information info : infos) {
            System.out.println(info);
        }
    }

    @Test
    public void testfindTotalCountOfTakenOrder(){
        int UserId=8;
        int total=informationDao.findTotalCountOfTakenOrder(UserId);
        System.out.println(total);
    }

    @Test
    public void testCancelAnOrderByInfoId(){
        int infoId=444;
        informationDao.cancelAnOrderByInfoId(infoId);
        informationDao.deleteTakenOrderByInfoId(infoId);
    }
}
