package shop.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"shop.API.core.application"})
@ComponentScan({"shop.API.presentation.Controllers"})
@ComponentScan({"shop.API.core.application.interfaces.services"})
@ComponentScan({"shop.API.infrastructure.persistence.DAL.implementations"})
@ComponentScan({"shop.API.core.application.mapperProfiles"})



@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
