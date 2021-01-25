import com.clls.domain.Information;
import com.clls.domain.PageBean;
import com.clls.service.InformationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class TestInformationService {
    @Autowired
    private InformationService informationService;

    @Test
    public void testFindInformationByPage() {
        PageBean<Information> pb = informationService.findByPage("1");//,"5");
        List<Information> infoList = pb.getList();
        for (Information information : infoList) {
            System.out.println(information);
        }
        System.out.println(pb);
    }

    @Test
    public void testFindUserIdById() {
        int userId = informationService.findUserIdById(500);
        System.out.println(userId);
    }

    @Test
    public void testDeleteOneInformation() {
        informationService.deleteOneInformation(100);
    }

    @Test
    public void testModifyState() {
        int id = 3;
        int state = 2;
        informationService.modifyState(id, state);
    }

    @Test
    public void testFindState() {
        int id = 50;
        System.out.println(informationService.findState(id));
    }

    @Test
    public void testSaveInformation() {
        Information information = new Information();
        information.setUserId(1);
        information.setBounty(100);
        information.setDate(new Date());
        information.setContent("测试。。。");
        informationService.saveInformation(information);
//        information.s
    }

    @Test
    public void testFindServantId() {
        int infoId = 144;
        System.out.println(informationService.findServantId(infoId));
    }

    @Test
    public void testFindByPage() {
        informationService.findByPage("1");
    }

    @Test
    public void testCancelAnOrderByInfoId(){
        int infoId=444;
        informationService.cancelAnOrderByInfoId(infoId);
    }
}
