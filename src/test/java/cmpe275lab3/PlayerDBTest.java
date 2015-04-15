package cmpe275lab3;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PlayerDBTest {

    @Test
    public void testCreatePlayer() throws Exception {
        IPlayerDB playerDB = new PlayerDB();
        Long sponsorId = Long.valueOf(20001);
        String string = playerDB.createPlayer("M", "K", "m@k.com", null, null, sponsorId);
        assertNotNull(string);
        System.out.println(string);
    }

    @Test
    public void testGetPlayer() throws Exception {
        IPlayerDB playerDB = new PlayerDB();
        String playerJSON = playerDB.getPlayer(4);
        assertNotNull(playerJSON);
        System.out.println("Get Method: "+playerJSON);
    }

    @Test
    public void testUpdatePlayer() throws Exception {
        IPlayerDB playerDB = new PlayerDB();
        Address address = new Address("Kylie", "Santa Clara", "California", "99999");
        String string = playerDB.updatePlayer(1001, "Dinesh", "J", "d@j.com", "Software Engineer", address, Long.valueOf(20001));
        assertNotNull(string);
        System.out.println("Update Method: "+string);
    }

    @Test
    public void testDeletePlayer() throws Exception {
        IPlayerDB playerDB = new PlayerDB();
        String output = playerDB.deletePlayer(1);
        assertEquals(output, "Player deleted");
    }
}