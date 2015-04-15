package cmpe275lab3;

import org.json.JSONObject;
import org.json.JSONString;

/**
 * Created by mallika on 4/8/15.
 */
public interface IPlayerDB {
    public String createPlayer(String firstname, String lastName, String email, String description, Address address, Long sponsorId);
    public String getPlayer(long playerId);
    public String updatePlayer(long playerId, String firstname, String lastName, String email, String description, Address address, Long sponsorId);
    public String deletePlayer(long playerId);
}
