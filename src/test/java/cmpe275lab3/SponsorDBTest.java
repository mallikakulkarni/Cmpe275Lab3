package cmpe275lab3;

import com.sun.tools.javac.code.Type;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.internal.SessionFactoryImpl;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SponsorDBTest {
    @Test
    public void testCreateSponsor() {
        ISponsorDB sponsorDB = new SponsorDB();
        Address address = new Address("Kylie", "Santa Clara", "California", "99999");
        String string = sponsorDB.createSponsor("Mallika", "Student at SJSU", address);
        assertNotNull(string);
        System.out.println(string);
    }

    @Test
    public void testGetSponsor() {
        ISponsorDB sponsorDB = new SponsorDB();
        String string = sponsorDB.getSponsor(20001);
        assertNotNull(string);
        System.out.println("Get Method: "+string);
    }

    @Test
    public void testUpdateSponsor() {
        ISponsorDB sponsorDB = new SponsorDB();
        Address address = new Address("Kylie", "Santa Clara", "California", "99999");
        String string = sponsorDB.updateSponsor(20001, "Dinesh", "Software Engineer", address);
        assertNotNull(string);
        System.out.println("Update Method: "+string);
    }

    @Test
    public void testDeleteSponsor() {
        ISponsorDB sponsorDB = new SponsorDB();
        String output = sponsorDB.deleteSponsor(1);
        assertEquals(output, "Sponsor deleted");
    }

}