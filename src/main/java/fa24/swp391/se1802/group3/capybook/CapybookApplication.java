package fa24.swp391.se1802.group3.capybook;

import fa24.swp391.se1802.group3.capybook.utils.EmailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CapybookApplication {
	public static void main(String[] args) {
		SpringApplication.run(CapybookApplication.class, args);
	}
}
