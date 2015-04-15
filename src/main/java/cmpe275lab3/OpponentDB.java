package cmpe275lab3;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mallika on 4/13/15.
 */
public class OpponentDB implements IOpponentDB {
    private SessionFactory sessionFactory;

    @Override
    public String addOpponent(long player1Id, long player2Id) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        IPlayerDB playerDB = new PlayerDB();
        if (playerDB.getPlayer(player1Id) == null || playerDB.getPlayer(player2Id) == null) {
            return "One of the players does not exist";
        }
        session.close();
        if (checkExistingOpponents(player1Id, player2Id) || checkExistingOpponents(player2Id, player1Id)) {
            return "";
        }
        return addOpponents(player1Id, player2Id);
    }

    @Override
    public String removeOpponent(long player1Id, long player2Id) {
        sessionFactory = DBConnection.getSessionFactory();
        IPlayerDB playerDB = new PlayerDB();
        Session session = sessionFactory.openSession();
        if (playerDB.getPlayer(player1Id) == null || playerDB.getPlayer(player2Id) == null) {
            return "One of the players does not exist.";
        }
        session.close();
        if (!checkExistingOpponents(player1Id, player2Id)) {
            if (!checkExistingOpponents(player2Id, player1Id)) {
                return "Players are Not opponents";
            } else {
                return removeOpponents(player2Id, player1Id);
            }
        } else {
            return removeOpponents(player1Id, player2Id);
        }
    }

    private String removeOpponents(long player1Id, long player2Id) {
        Session session;
        session = sessionFactory.openSession();
        Query query = session.createSQLQuery("SELECT * FROM Opponents o WHERE o.PlayerID = :p1 AND o.OpponentID = :p2")
                .addEntity(Opponent.class)
                .setParameter("p1", player1Id)
                .setParameter("p2", player2Id);
        Opponent opponent = (Opponent) query.uniqueResult();
        session.close();
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(opponent);
        transaction.commit();
        session.close();
        return "Opponents removed";
    }

    private String addOpponents(long player1Id, long player2Id) {
        Session session;
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Opponent opponent = new Opponent(player1Id, player2Id);
        session.persist(opponent);
        transaction.commit();
        session.close();
        return "Opponents added";
    }


    private boolean checkExistingOpponents(long player1Id, long player2Id) {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("SELECT * FROM Opponents o WHERE o.PlayerID = :p1")
                .addEntity(Opponent.class)
                .setParameter("p1", player1Id);
        List<Opponent> opponents = (List<Opponent>) query.list();
        session.close();
        for (Opponent opponent1 : opponents) {
            if (opponent1.getOpponentId() == player2Id) {
                return true;
            }
        }
        return false;
    }
}
