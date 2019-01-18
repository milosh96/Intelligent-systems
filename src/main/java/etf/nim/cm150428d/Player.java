package etf.nim.cm150428d;
/**
 * Created by MIlos on 4.1.2018.
 */

public abstract class Player{
    public static final int MAX_VALUE=10000;
    public static final int MIN_VALUE=-10000;
    protected boolean myTurn;
    protected boolean isMaxPlayer;
    public int previousMove;
    protected Player opponent;
    //TODO potpis funkcije
    public abstract int calculateFunction(int poles,int[] tolkens);
    protected Node root;
    protected int treeDepth;
    protected boolean ready;
    public abstract void turn(int numPoles,int[] tolkens,GameActivity gameActivity);
    public Player(boolean is) {
        myTurn = false;
        isMaxPlayer=is;
       // somwhere else root=new Node(GameActivity.poles,GameActivity.tolkens,isMaxPlayer);
    }
    public void setTurn(GameActivity gameActivity){
        ready=false;
        myTurn=true;
        turn(GameActivity.poles,GameActivity.tolkens,gameActivity);
    }
    public Player getOpponent(){
        return opponent;
    }
    public void ready(){
        ready=true;
    }
    public boolean getReady(){
        return ready;
    }
    public void setTreeDepth(int depth){
        treeDepth=depth;
    }
    public void endTurn(){
        myTurn=false;
    }
    public boolean getTurn(){
        return myTurn;
    }
    public int checkEnd(int numPoles, int[]tolkens){
        //TODO
        for(int i=0;i<numPoles;i++){
            if (tolkens[i]!=0){
                return 0;
            }
        }
        return 1;
    }
    protected void makeMove(Node next){
        int[] old=GameActivity.tolkens;
        int[] newTolkens=next.getTolkens();
        int poles=next.getPoles();
        int index=0;
        for(index=0;index<poles;index++){
            if(old[index]!=newTolkens[index]){
                break;
            }
        }
        this.previousMove=old[index]-newTolkens[index];
        GameActivity.repaint(index,newTolkens[index],old[index]);
        GameActivity.tolkens[index]=newTolkens[index];
    }
    public void setOpponent(Player player){
        opponent=player;
    }
}
