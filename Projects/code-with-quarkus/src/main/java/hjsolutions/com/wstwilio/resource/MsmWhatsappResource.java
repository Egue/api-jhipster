package hjsolutions.com.wstwilio.resource;

import hjsolutions.com.wstwilio.services.TwilioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/msm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MsmWhatsappResource {

    @Inject
    TwilioService twilioService;

    @Path("/whatsapp")
    @GET
    public Response whatsap(@QueryParam("phone") String phone , @QueryParam("text") String text){

        twilioService.sendMsm(phone , text);
        return  Response.ok().build();
    }

}
