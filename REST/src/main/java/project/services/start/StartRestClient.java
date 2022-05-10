package project.services.start;

import org.springframework.web.client.RestClientException;
import project.model.Flight;
import project.services.start.rest.ServiceException;
import rest.client.FlightsClient;

import java.time.LocalDateTime;

public class StartRestClient {
    private final static FlightsClient flightsClient=new FlightsClient();
    public static void main(String[] args) {
        //  RestTemplate restTemplate=new RestTemplate();
        Flight flight=new Flight("destination1","airport1", LocalDateTime.now(), 2, 2);
        try{
            //  User result= restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class);

            //  System.out.println("Result received "+result);
      /*  System.out.println("Updating  user ..."+userT);
        userT.setName("New name 2");
        restTemplate.put("http://localhost:8080/chat/users/test124", userT);

*/
            // System.out.println(restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
            //System.out.println( restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));

            show(()-> System.out.println(flightsClient.create(flight)));
            show(()->{
                Flight[] res=flightsClient.getAll();
                for(Flight f:res){
                    System.out.println(f.getDestination()+": "+f.getAirport());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

        //show(()-> System.out.println(flight.getById("ana")));
    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
