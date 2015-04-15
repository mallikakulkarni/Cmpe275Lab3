package cmpe275lab3;

import org.json.JSONString;

/**
 * Created by mallika on 4/8/15.
 */
public interface IOpponentDB {
    public String addOpponent(long player1Id, long player2Id);
    public String removeOpponent(long player1Id, long player2Id);
}
