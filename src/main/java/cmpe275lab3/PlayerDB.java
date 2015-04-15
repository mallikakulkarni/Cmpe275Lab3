package cmpe275lab3;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONObject;
import org.json.JSONString;

import java.util.List;

/**
 * Created by mallika on 4/13/15.
 */
public class PlayerDB implements IPlayerDB {
    private SessionFactory sessionFactory;
    @Override
    public String createPlayer(String firstName, String lastName, String email, String description, Address address, Long sponsorId) {
        ISponsorDB sponsorDB = new SponsorDB();
        sessionFactory = DBConnection.getSessionFactory();
        Session session;
        Sponsor sponsor = null;
        Player existPlayer = getExistingPlayer(email);
        if (existPlayer != null) {
            return "Player exists in database";
        }
        if (sponsorId != null) {
            if (sponsorDB.doesSponsorExist(sponsorId)) {
                session = sessionFactory.openSession();
                sponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
                session.close();
            }
        }
        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setEmail(email);
        player.setDescription(description);
        player.setAddress(address);
        player.setSponsorId(sponsor.getSponsorId());
        JSONObject playerJSON = player.JavaObjectToJSON();
        if (playerJSON == null) {
            return "Player could not be created!";
        }
        session = sessionFactory.openSession();
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(player);
        transaction.commit();
        session.close();
        return playerJSON.toString();
    }

    private Player getExistingPlayer(String email) {
        Session session;
        session = sessionFactory.openSession();
        Query query = session.createSQLQuery("SELECT * FROM Players WHERE Email = :p")
                .addEntity(Player.class)
                .setParameter("p", email);
        List<Player> existPlayer = query.list();
        session.close();
        if (existPlayer.size() == 1) {
            return existPlayer.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String getPlayer(long playerId) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Player player = null;
        player = (Player) session.get(Player.class, playerId);
        session.close();
        if (player != null) {
            List<Opponent> opponents = getOpponents(playerId, "player");
            player.appendOpponents(opponents, "player");
            opponents = getOpponents(playerId, "opponent");
            player.appendOpponents(opponents, "opponent");
            JSONObject sponsorJSON = player.JavaObjectToJSON();
            return sponsorJSON.toString();
        }

        return "Player not found in database";
    }

    private List<Opponent> getOpponents(long playerId, String column) {
        Session session;
        session = sessionFactory.openSession();
        Query query = null;
        if (column.equals("player")) {
            query = session.createSQLQuery("SELECT * FROM Opponents WHERE PlayerID = :p")
                    .addEntity(Opponent.class)
                    .setParameter("p", playerId);
        } else if (column.equals("opponent")) {
            query = session.createSQLQuery("SELECT * FROM Opponents WHERE OpponentID = :p")
                    .addEntity(Opponent.class)
                    .setParameter("p", playerId);
        }
        List<Opponent> opponents = query.list();
        session.close();
        if (opponents != null) {
            return opponents;
        }
        return null;
    }

    @Override
    public String updatePlayer(long playerId, String firstName, String lastName, String email, String description, Address address, Long sponsorId) {
        if (getPlayer(playerId) == null) {
            return "Player not found";
        }
        ISponsorDB sponsorDB = new SponsorDB();
        sessionFactory = DBConnection.getSessionFactory();
        Session session;
        Sponsor sponsor = null;
        if (sponsorId != null) {
            if (sponsorDB.doesSponsorExist(sponsorId)) {
                session = sessionFactory.openSession();
                sponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
                session.close();
            }
        }
        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setEmail(email);
        player.setDescription(description);
        player.setAddress(address);
        player.setSponsorId(sponsor.getSponsorId());
        player.setPlayerId(playerId);
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(player);
        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        session.close();
        JSONObject playerJSON = player.JavaObjectToJSON();
        return playerJSON.toString();
    }

    @Override
    public String deletePlayer(long playerId) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        if (getPlayer(playerId) == null) {
            return "Player does not exist in database";
        }
        Player player = (Player) session.get(Player.class, playerId);
        JSONObject jsonplayer = player.JavaObjectToJSON();
        Transaction transaction = session.beginTransaction();
        session.delete(player);
        transaction.commit();
        session.close();
        return jsonplayer.toString();
    }
}
