package dao;

import com.lagou.RunBoot;
import com.lagou.entity.Order;
import com.lagou.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestShardingDatabase {

    @Resource
    private OrderRepository orderRepository;

    @Test
    public void testAdd(){
        for (int i = 1; i <= 20; i++) {
            Order order = new Order();
            order.setId(i);
            order.setIs_del(false);
            order.setUser_id(i);
            order.setCompany_id(i);
            order.setPublish_user_id(i);
            order.setPosition_id(i);
            order.setResume_type(0);
            order.setStatus("WAIT");
            order.setCreate_time(new Date());
            order.setUpdate_time(new Date());
            orderRepository.save(order);
        }
    }

    @Test
    public void testFind(){
        List<Order> list = orderRepository.findAll();
        for (Order order : list) {
            System.out.println(order.getId());
        }
    }
}
