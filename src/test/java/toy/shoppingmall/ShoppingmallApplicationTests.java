package toy.shoppingmall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.Member;
import toy.shoppingmall.domain.Order;

import javax.persistence.EntityManager;

@SpringBootTest
class ShoppingmallApplicationTests {

	@Test
	void contextLoads() {
	}

}
