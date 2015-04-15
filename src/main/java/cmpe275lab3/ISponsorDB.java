package cmpe275lab3;

import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * Created by mallika on 4/8/15.
 */
public interface ISponsorDB {
    public String createSponsor(String name, String description, Address address);
    public String getSponsor(long sponsorId);
    public String updateSponsor(long sponsorId, String name, String description, Address address);
    public String deleteSponsor(long sponsorId);
    public boolean doesSponsorExist(long sponsorId);
}
