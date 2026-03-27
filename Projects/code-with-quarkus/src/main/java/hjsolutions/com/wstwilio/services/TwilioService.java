package hjsolutions.com.wstwilio.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TwilioService {
    @ConfigProperty(name="twilio.account-sid")
    String account;

    @ConfigProperty(name="twilio.auth-token")
    String token;

     public  void sendMsm(String to , String text){
         Twilio.init(account , token);
         Message message = Message.creator(
             new PhoneNumber("whatsapp:+57"+to),
             new PhoneNumber("whatsapp:+14155238886"),
             text
         ).create();

         System.out.println(message.getSid());
     }
}
