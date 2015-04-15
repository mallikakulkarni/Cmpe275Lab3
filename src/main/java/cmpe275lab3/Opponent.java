package cmpe275lab3;

/**
 * Created by mallika on 4/13/15.
 */
public class Opponent {
    private long playerId;
    private long opponentId;
    private int rowId;
    private static int count = 0;

    public Opponent(long playerId, long opponentId) {
        this.rowId = assignRowId();

        this.playerId = playerId;
        this.opponentId = opponentId;
    }

    public Opponent() {
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
    private int assignRowId() {
        count++;
        return count;
    }

    public long getPlayerId() {

        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(long opponentId) {
        this.opponentId = opponentId;
    }
}
