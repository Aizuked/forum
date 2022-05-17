package com.forum.forum;

import com.forum.forum.Bot.Bot;
import com.forum.forum.Bot.SubscriberService;
import com.forum.forum.Configuration.App.AppRole.AppRole;
import com.forum.forum.Configuration.App.AppRole.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Bean
	CommandLineRunner init (AppRoleRepository appRoleRepository) {
		return args -> {
			AppRole userAppRole = new AppRole();
			userAppRole.setRoleName("USER");
			appRoleRepository.save(userAppRole);
			AppRole adminAppRole = new AppRole();
			adminAppRole.setRoleName("ADMIN");
			appRoleRepository.save(adminAppRole);
		};
	}

	@Bean
	public CommandLineRunner schedulingRunner(Bot bot) {
		return args -> bot.run();
	}
}
