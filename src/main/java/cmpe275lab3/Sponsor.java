package cmpe275lab3;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

/**
 * Created by mallika on 4/8/15.
 */

public class Sponsor {

    private long sponsorId;
    private String name;
    private String description;
    private Address address;
    private static long count = 20000;

    public Sponsor(String name, String description, Address address) {
        this.sponsorId = assignSponsorId();
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public Sponsor() {}

    private long assignSponsorId() {
        count++;
        return count;
    }

    public long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public JSONObject JavaObjectToJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonAddress = new JSONObject();
        try {
            jsonObject.put("Street", address.getStreet());
            jsonObject.put("City", address.getCity());
            jsonObject.putOpt("State", address.getState());
            jsonObject.putOnce("zip", address.getZip());
        } catch (JSONException je) {
            return null;
        }
        try {
            jsonObject.put("Sponsor ID", sponsorId);
            jsonObject.put("Sponsor Name", name);
            jsonObject.putOpt("Sponsor address", jsonAddress.toString());
            jsonObject.putOnce("Sponsor Description", description);
        } catch (JSONException je) {
            return null;
        }
        return jsonObject;
    }

}
