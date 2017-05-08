package demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017/4/19.
 */
public class RunClient {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/ribbon/test/2");
//        WebTarget target = client.target("http://localhost:7501/test/2");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new User("abc", 1)));
        System.out.println(response.toString());
        System.out.println(response.readEntity(Message.class));

        response.close();
    }
}
