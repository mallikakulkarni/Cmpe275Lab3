package cmpe275lab3;

import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.xml.ws.Response;
import java.util.Arrays;

/**
 * Created by mallika on 4/8/15.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
public class Application {
    IPlayerDB playerDB = new PlayerDB();
    IOpponentDB opponentDB = new OpponentDB();
    ISponsorDB sponsorDB = new SponsorDB();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/player", method= RequestMethod.POST)
    public String createPlayer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam(required=false, defaultValue="null") String description, @RequestParam(required=false, defaultValue="null") String street, @RequestParam(required=false, defaultValue="null") String city, @RequestParam(required=false, defaultValue="null") String state, @RequestParam(required=false, defaultValue="null") String zip, @RequestParam(required=false, defaultValue="-1") Long sponsorId) {
        if (description == "null") {
            description = null;
        }
        if (street == "null") {
            street = null;
        }
        if (city == "null") {
            city = null;
        }
        if (state == "null") {
            state = null;
        }
        if (zip == "null") {
            zip = null;
        }
        Address address = new Address(street, city, state, zip);
        if (sponsorId == -1) {
            sponsorId = null;
        }
        String string = playerDB.createPlayer(firstName, lastName, email, description, address, sponsorId);
        return string;
    }

    @RequestMapping(value = "/player/{playerId}", method = RequestMethod.GET)
    public String getPlayer(@PathVariable("playerId") Long playerId) {
        String output = playerDB.getPlayer(playerId);
        if (output.equals("Player not found in database")) {
            throw new ResourceNotFoundException();
        }
        return output;
    }

    @RequestMapping(value  = "/player/{playerId}", method= RequestMethod.PUT)
    public String updatePlayer(@PathVariable("PlayerId") Long playerId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam(required=false, defaultValue="null") String description, @RequestParam(required=false, defaultValue="null") String street, @RequestParam(required=false, defaultValue="null") String city, @RequestParam(required=false, defaultValue="null") String state, @RequestParam(required=false, defaultValue="null") String zip, @RequestParam(required=false, defaultValue="-1") Long sponsorId) {
        if (description == "null") {
            description = null;
        }
        if (street == "null") {
            street = null;
        }
        if (city == "null") {
            city = null;
        }
        if (state == "null") {
            state = null;
        }
        if (zip == "null") {
            zip = null;
        }
        Address address = new Address(street, city, state, zip);
        if (sponsorId == -1) {
            sponsorId = null;
        }
        String string = playerDB.updatePlayer(playerId, firstName, lastName, email, description, address, sponsorId);
        if (string.equals("Player not found")) {
            throw new ResourceNotFoundException();
        }
        return string;
    }

    @RequestMapping(value  = "/player/{playerId}", method= RequestMethod.DELETE)
    public String deletePlayer(@PathVariable("PlayerId") Long playerId) {
        String string = playerDB.deletePlayer(playerId);
        if (string.equals("Player does not exist in database")) {
            throw new ResourceNotFoundException();
        }
        return string;
    }

    @RequestMapping(value = "/sponsor", method= RequestMethod.POST)
    public String createSponsor(@RequestParam String name, @RequestParam(required=false, defaultValue="null") String description, @RequestParam(required=false, defaultValue="null") String street, @RequestParam(required=false, defaultValue="null") String city, @RequestParam(required=false, defaultValue="null") String state, @RequestParam(required=false, defaultValue="null") String zip) {
        if (description == "null") {
            description = null;
        }
        if (street == "null") {
            street = null;
        }
        if (city == "null") {
            city = null;
        }
        if (state == "null") {
            state = null;
        }
        if (zip == "null") {
            zip = null;
        }
        Address address = new Address(street, city, state, zip);
        String string = sponsorDB.createSponsor(name, description, address);
        return string;
    }

    @RequestMapping(value = "/sponsor/{sponsorId}", method= RequestMethod.GET)
    public String getSponsor(@PathVariable("sponsorId") Long sponsorId) {
        String string = sponsorDB.getSponsor(sponsorId);
        if (string.equals("Could not find sponsor")) {
            throw new RuntimeException();
        }
        return string;
    }

    @RequestMapping(value = "/sponsor/{sponsorId}", method= RequestMethod.PUT)
    public String updateSponsor(@PathVariable("sponsorId") Long sponsorId, @RequestParam String name, @RequestParam(required=false, defaultValue="null") String description, @RequestParam(required=false, defaultValue="null") String street, @RequestParam(required=false, defaultValue="null") String city, @RequestParam(required=false, defaultValue="null") String state, @RequestParam(required=false, defaultValue="null") String zip) {
        if (description == "null") {
            description = null;
        }
        if (street == "null") {
            street = null;
        }
        if (city == "null") {
            city = null;
        }
        if (state == "null") {
            state = null;
        }
        if (zip == "null") {
            zip = null;
        }
        Address address = new Address(street, city, state, zip);
        String string = sponsorDB.updateSponsor(sponsorId, name, description, address);
        if (string.equals("Sponsor does not exist")) {
            throw new ResourceNotFoundException();
        }
        return string;
    }

    @RequestMapping(value = "/sponsor/{sponsorId}", method= RequestMethod.DELETE)
    public String deleteSponsor(@PathVariable("sponsorId") Long sponsorId) {
        String string = sponsorDB.deleteSponsor(sponsorId);
        if (string.equals("Sponsor doesnot exist in database")) {
            throw new ResourceNotFoundException();
        }
        return string;
    }

    @RequestMapping(value = "/opponents/{id1}/{id2}", method= RequestMethod.POST)
    public String addOpponents(@PathVariable("id1") Long playerId, @PathVariable("id2") Long opponentId) {
        String string = opponentDB.addOpponent(playerId, opponentId);
        if (string.equals("One of the players does not exist. Invalid input")) {
            throw new ResourceNotFoundException();
        }
        return string;
    }

    @RequestMapping(value = "/opponents/{id1}/{id2}", method= RequestMethod.DELETE)
    public String removeOpponents(@PathVariable("id1") Long playerId, @PathVariable("id2") Long opponentId) {
        String string = opponentDB.addOpponent(playerId, opponentId);
        if (string.equals("One of the players does not exist. Invalid input") || string.equals("Players are Not opponents")) {
            throw new ResourceNotFoundException();
        }
        return string;
    }
}
