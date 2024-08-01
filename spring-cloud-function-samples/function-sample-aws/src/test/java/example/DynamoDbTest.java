package example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest
public class DynamoDbTest {

	@Autowired
	private Function<String, String> dynamoSample;

	@Test
	void test01() {
		System.out.println(dynamoSample.apply("hello"));
	}
}
