package cmpe275lab3;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OpponentDBTest {

    @Test
    public void testAddOpponent() {
        IOpponentDB opponentDB = new OpponentDB();
        String output = opponentDB.addOpponent(1, 5);
        assertEquals(output, "One of the players does not exist");
        output = opponentDB.addOpponent(1, 2);
        assertEquals(output, "Opponents added");
        output = opponentDB.addOpponent(2, 1);
        assertEquals(output, "Players are already opponents");
        output = opponentDB.addOpponent(1, 3);
        assertEquals(output, "Opponents added");
        output = opponentDB.addOpponent(3, 1);
        assertEquals(output, "Players are already opponents");
        output = opponentDB.addOpponent(1, 4);
        assertEquals(output, "Opponents added");
        output = opponentDB.addOpponent(4, 1);
        assertEquals(output, "Players are already opponents");
    }

    @Test
    public void testRemoveOpponent() {
        IOpponentDB opponentDB = new OpponentDB();
        String output = opponentDB.removeOpponent(1, 5);
        assertEquals(output, "One of the players does not exist. Invalid input");
        output = opponentDB.removeOpponent(2, 3);
        assertEquals(output, "Players are Not opponents");
        output = opponentDB.removeOpponent(1, 3);
        assertEquals(output, "Opponents removed");
        output = opponentDB.removeOpponent(2, 1);
        assertEquals(output, "Opponents removed");
    }

}