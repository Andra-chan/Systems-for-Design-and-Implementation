package project.services.start.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Flight;
import project.repository.IFlightRepository;
import project.repository.RepositoryException;
import project.repository.database.FlightDbRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.io.FileReader;

//@CrossOrigin
@RestController
@RequestMapping("/flights")
public class FlightController {

    private static final String template = "Hello, %s!";

    //@Autowired
    private FlightDbRepository flightDbRepository;

    public FlightController() {
        Properties clientProps = new Properties();
        try {
            clientProps.load(FlightDbRepository.class.getResourceAsStream("/db.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find db.properties " + e);
            return;
        }
        flightDbRepository = new FlightDbRepository(clientProps);
    }

    @RequestMapping("/greeting")
    public  String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }


    @RequestMapping( method= RequestMethod.GET)
    public Iterable<Flight> getAll(){
        System.out.println("Get all users ...");
        return flightDbRepository.findAll();
    }

    /*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("Get by id "+id);
        User user=userRepository.findBy(id);
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(user, HttpStatus.OK);
    }

     */

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Flight create(@PathVariable Integer id, @RequestBody Flight flight){
        flightDbRepository.save(flight);
        flight.setId(id);
        return flight;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Flight update(@PathVariable Integer id, @RequestBody Flight flight) {
        System.out.println("Updating user ...");
        flight.setId(id);
        //flightDbRepository.idUpdate(id, flight);
        flightDbRepository.update(flight);
        return flight;

    }
    // @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        System.out.println("Deleting user ... "+ flightDbRepository.findOne(id));
        try {
            flightDbRepository.delete(id);
            return new ResponseEntity<Flight>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

/*
    @RequestMapping("/{user}/name")
    public String name(@PathVariable String user){
        User result=userRepository.findBy(user);
        System.out.println("Result ..."+result);

        return result.getName();
    }

 */



    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
