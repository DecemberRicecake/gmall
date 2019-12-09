import com.gmall.common.ServerResponse;
import com.gmall.controller.frontend.UserController;
import com.gmall.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserTest {

    @Autowired
    private UserController c;

    @Test
    public void insertTest(){
        User u = new User();
        u.setUsername("华华");
        u.setPassword("123456");
        ServerResponse<String> s = c.register(u);
        System.out.println(s.getMsg());
    }
}
