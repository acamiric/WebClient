package eccomerce.orderapp;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import eccomerce.itemorder.ItemOrder;
import lombok.Builder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan("eccomerce.itemorder")
public class OrderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderAppApplication.class, args);
		ItemOrder io=ItemOrder.builder().creditCardNo(123L).build();
		System.out.println(io);
		
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/order/**"))
				.apis(RequestHandlerSelectors.basePackage("eccomerce.orderapp"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Item Order API",
				"Item Ordering Using Kafka and Spring API",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Aleksandar Miric", "www.random.com",
						"aleksandar.miric@eng.it"),
				"Api Licence", "www.random.com",
				Collections.emptyList()

		);
	}

}
