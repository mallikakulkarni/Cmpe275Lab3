package cmpe275lab3;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by mallika on 4/8/15.
 */
@Component
@Repository
public class SponsorDB implements ISponsorDB{
    private SessionFactory sessionFactory;



    @Override
    public String createSponsor(String name, String description, Address address) {
        Sponsor sponsor = new Sponsor(name, description, address);
        JSONObject sponsorJSON = sponsor.JavaObjectToJSON();
        if (sponsorJSON == null) {
            return "Could not create Sponsor object";
        }
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(sponsor);
        transaction.commit();
        session.close();
        return sponsorJSON.toString();
    }

    @Override
    public String getSponsor(long sponsorId) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Sponsor sponsor = null;
        sponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
        session.close();
        if (sponsor != null) {
            JSONObject sponsorJSON = sponsor.JavaObjectToJSON();
            return sponsorJSON.toString();
        }

        return "Could not find sponsor";
    }

    @Override
    public String updateSponsor(long sponsorId, String name, String description, Address address) {
        if (!doesSponsorExist(sponsorId)) {
            return "Sponsor does not exist";
        }
        Sponsor sponsor = new Sponsor(name, description, address);
        sponsor.setSponsorId(sponsorId);
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(sponsor);
        transaction.commit();
        Sponsor newSponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
        session.close();
        if (newSponsor != null) {
            JSONObject sponsorJSON = sponsor.JavaObjectToJSON();
            return sponsorJSON.toString();
        }
        return "Oops! Something went wrong";
    }

    @Override
    public String deleteSponsor(long sponsorId) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        if (!doesSponsorExist(sponsorId)) {
            return "Sponsor doesnot exist in database";
        }
        Sponsor sponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
        JSONObject jsonSponsor = sponsor.JavaObjectToJSON();
        Transaction transaction = session.beginTransaction();
        session.delete(sponsor);
        transaction.commit();
        session.close();
        return jsonSponsor.toString();
    }

    @Override
    public boolean doesSponsorExist(long sponsorId) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Sponsor sponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
        if (sponsor != null) {
            return true;
        }
        return false;
    }

    public Sponsor getSponsorfromSponsorId(Long sponsorId) {
        sessionFactory = DBConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        Sponsor sponsor = null;
        sponsor = (Sponsor) session.get(Sponsor.class, sponsorId);
        session.close();
        return sponsor;
    }

}
