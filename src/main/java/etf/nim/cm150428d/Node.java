package etf.nim.cm150428d;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIlos on 6.1.2018.
 */

public class Node {
    public int previousPlay;
    private int poles;
    private int[] tolkens;
    public int childrenNumber;
    private List<Node> children;
    private int func_value;
    private boolean isMaxPlayer;
    public Node(int poles, int[] tolkens,boolean is, int previous){
        previousPlay=previous;
        childrenNumber=0;
        this.poles=poles;
        this.tolkens=tolkens.clone();
        isMaxPlayer=is;
        //terminal position???-else statement?
        //live for the player to calculate!!!!
        int terminalNode=finalState();
        if(terminalNode!=0){
            func_value=terminalNode;
        }
        else{
            //unnecessary
            Player player_turn;
            if(GameActivity.player1.getTurn()) player_turn=GameActivity.player1;
            else player_turn=GameActivity.player2;
            func_value=player_turn.calculateFunction(poles,tolkens);
        }
        children=new ArrayList<>();
    }
    public void setFunc_value(int value){
        func_value=value;
    }
    public boolean isMax(){
        return isMaxPlayer;
    }
    public int getFunction(){
        return func_value;
    }
    public List<Node> createChildren(){
        /*TODO
            modify for greater take!?
         */
        if(!children.isEmpty()) return children;
        int[] testTolkens=tolkens.clone();
        for(int i=GameActivity.max_num;i>0;i--){
            boolean allowed=false;
            if((previousPlay!=0)&&(i<=2*previousPlay)) allowed=true;
            else if (previousPlay==0) allowed=true;
            for(int j=0;j<poles;j++){
                if((i<=testTolkens[j])&& allowed) {
                    testTolkens[j] -= i;
                    int state = GameActivity.checkState(poles, testTolkens);
                    if(state==1){
                        children.add(new Node(poles,testTolkens,!isMaxPlayer,i));
                        childrenNumber++;
                    }
                    testTolkens[j]=tolkens[j];
                }

            }
        }
        return children;
    }
    public int getPoles(){
        return poles;
    }
    public int[] getTolkens(){
        return tolkens;
    }
    public int finalState(){
        /*TODO
        test all posible states
        code ^^^^
        return 1,-1 or 0
         */

        int[] testTolkens=tolkens.clone();
        /*
        for(int i=1;i<GameActivity.max_num;i++){
            for(int j=0;j<poles;j++){
                if(i<=testTolkens[j]) {
                    testTolkens[j] -= i;
                    int state = GameActivity.checkState(poles, testTolkens);
                    if(state==1){
                        return 0;
                    }
                    testTolkens[j]=tolkens[j];
                }
            }
        }*/
        for(int i=0;i<poles;i++){
            if (testTolkens[i]!=0){
                return 0;
            }
        }
        if (isMaxPlayer) return Player.MIN_VALUE;
        else return Player.MAX_VALUE;
    }
    public Node getBestChild(){
        Node best=null;
        int bestValue=0;
        if(isMaxPlayer){
            //final postion-could be only and with -1 in value
            bestValue=2*Player.MIN_VALUE;
            for(int i=0;i<children.size();i++){
                Node curr=children.get(i);
                if(curr.getFunction()>bestValue){
                    bestValue=curr.getFunction();
                    best=curr;
                }
            }
            return best;
        }
        else {
            bestValue = 2*Player.MAX_VALUE;
            for (int i = 0; i < children.size(); i++) {
                Node curr = children.get(i);
                if (curr.getFunction() < bestValue) {
                    bestValue = curr.getFunction();
                    best = curr;
                }
            }
            return best;
        }
    }
}
