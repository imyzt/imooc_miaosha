package top.imyzt.study.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author imyzt
 */
@SpringBootApplication
@MapperScan("top.imyzt.study.miaosha.mapper")
public class MiaoshaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiaoshaApplication.class, args);
	}

}
