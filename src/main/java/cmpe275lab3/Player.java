package cmpe275lab3;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 4/8/15.
 */


public class Player {

    private long playerId;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private Address address;
    private Long sponsorId;
    private List<Long> opponents;
    private static long count = 1000;
    private Sponsor sponsor;
    public Player(String firstName, String lastName, String email, String description, Address address, Long sponsorId) {
        this.playerId = assignPlayerId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.address = address;
        this.sponsorId = sponsorId;
        opponents = new ArrayList<Long>();
    }

    public Player() {}

    private long assignPlayerId() {
        count++;
        return count;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getSponsorId() {
        return sponsorId;
    }

    public Sponsor getSponsor() {
        SponsorDB sponsorDB = new SponsorDB();
        return sponsorDB.getSponsorfromSponsorId(sponsorId);
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public List<Long> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Long> opponents) {
        this.opponents = opponents;
    }

    public void appendOpponents(List<Opponent> opponents, String column) {
        if (this.opponents == null) {
            this.opponents = new ArrayList<Long>();
        }
        if (column.equals("player")) {
            if (opponents != null) {
                for (Opponent opponent : opponents) {
                    this.opponents.add(opponent.getOpponentId());
                }
            }
        } else if (column.equals("opponent")) {
            if (opponents != null) {
                for (Opponent opponent : opponents) {
                    this.opponents.add(opponent.getPlayerId());
                }
            }
        }
    }

    public JSONObject JavaObjectToJSON() {
        JSONObject jsonObject = new JSONObject();
        SponsorDB sponsorDB = new SponsorDB();
        try {
            jsonObject.put("Player Id", playerId);
            jsonObject.put("Player First Name", firstName);
            jsonObject.put("Player Last Name", lastName);
            jsonObject.put("Player email", email);
            jsonObject.putOpt("Player address", address);
            jsonObject.putOpt("Player Description", description);
            Sponsor sponsor = sponsorDB.getSponsorfromSponsorId(sponsorId);
            if (sponsor != null) {
                jsonObject.putOpt("Sponsor Name", sponsor.getName());
                jsonObject.putOpt("Sponsor Id", sponsor.getSponsorId());
                jsonObject.putOpt("Sponsor description", sponsor.getDescription());
                jsonObject.putOpt("Sponsor Address", sponsor.getAddress());
            }
            if (opponents != null) {
                for (int i = 0; i < opponents.size(); i++) {
                    String opponent = "Opponents" + (i+1);
                    jsonObject.putOpt(opponent, opponents.get(i));
                }
            }
        } catch (JSONException je) {
            return null;
        }
        return jsonObject;
    }
}
