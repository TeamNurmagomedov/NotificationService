package miniyoutube.com.notificationservice;

import miniyoutube.com.notificationservice.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaListeners {

    private final NotificationService notificationService;

    @Autowired
    public KafkaListeners(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "events-topic",
            groupId = "group_id"
    )
    private void listener(String message) throws IOException {
        notificationService.testingFunction(message);
    }

}
