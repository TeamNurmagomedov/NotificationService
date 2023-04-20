package miniyoutube.com.notificationservice.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationService {
    Email from = new Email("miniyt123456@gmail.com");

    public void sendNotification(String type, String post, String by, String toEmail) throws IOException {
        String subject = "MiniYouTube Notification";
        Email to = new Email(toEmail.trim());
        Content content;
        if (type.equals("Like")) {
            content = new Content("text/plain", "Your post: " + "'" + post + "'" + "\n" + "was liked by: " + by);
        } else if (type.equals("Dislike")) {
            content = new Content("text/plain", "Your post: " + "'" + post + "'" + "\n" + "was disliked by: " + by);
        } else {
            throw new RuntimeException("Invalid type");
        }
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(""); // NEED TO ADD API KEY HERE
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

    public void sendCommentNotification(String post, String by, String toEmail, String comment) throws IOException {
        String subject = "MiniYouTube Notification";
        Email to = new Email(toEmail.trim());
        Content content = new Content("text/plain", "Your post: " + "'" + post + "'" + "\n" + "was commented by: " + by + "\n" + "Comment: " + comment);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(""); // NEED TO ADD API KEY HERE
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

    public void testingFunction(String message) throws IOException {
        String[] parts = message.split("/");
        String function = parts[0];
        String post = parts[1];
        String by = parts[2];
        String toEmail = parts[3];
        if (function.equals("Like")) {
            sendNotification("Like", post, by, toEmail);
        } else if (function.equals("Dislike")) {
            sendNotification("Dislike", post, by, toEmail);
        } else if (function.equals("Comment")) {
            String comment = parts[4];
            sendCommentNotification(post, by, toEmail, comment);
        } else {
            throw new RuntimeException("Invalid function");
        }
    }

}
