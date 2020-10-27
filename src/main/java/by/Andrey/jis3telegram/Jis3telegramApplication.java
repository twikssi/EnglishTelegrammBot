package by.Andrey.jis3telegram;

import by.Andrey.jis3telegram.enums.Emoji;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Jis3telegramApplication {

	public static void main(String[] args) {
			ApiContextInitializer.init();
			SpringApplication.run(Jis3telegramApplication.class, args);
	}
}
