package etf.nim.cm150428d;

import java.util.List;

/**
 * Created by MIlos on 5.1.2018.
 */

public class Pro extends Player {
    @Override
    public int calculateFunction(int poles, int[] tolkens) {
        //TODO something better?
        int value=0;
        for(int i=0;i<poles;i++){
            value^=tolkens[i];
        }
        return value;
    }

    @Override
    public void turn(int numPoles, int[] tolkens,GameActivity gameActivity) {
        if (GameActivity.gameFinished) {
            return;
        }
        root = new Node(numPoles, tolkens, isMaxPlayer,opponent.previousMove);
        //tree depth TODO
        constructTree(root);
        Node next=root.getBestChild();
        makeMove(next);
        if(next.finalState()!=0){
            GameActivity.declareVictor(gameActivity.getApplicationContext());
        }
        else{
            gameActivity.switchColors();
            endTurn();
            if (opponent instanceof Human) {
                opponent.setTurn(gameActivity);
            }
            else opponent.ready();
        }
    }
    private void constructTree(Node node){
        List<Node> children = node.createChildren();
        int alpha=Integer.MIN_VALUE;
        int beta=Integer.MAX_VALUE;
        for (int i=0;i<children.size();i++){
            Node next=children.get(i);
            int value=minimax(next,treeDepth-1,!node.isMax(),alpha,beta);
            next.setFunc_value(value);
        }
    }
    private int minimax(Node node,int treeDepth,boolean isMaxPlayer,int alpha, int beta){
        if((treeDepth==0)||(node.finalState()!=0)){
            return node.getFunction();
        }
        if(isMaxPlayer){
            int bestValue=MIN_VALUE;
            List<Node> children = node.createChildren();
            for(int i=0;i<children.size();i++){
                Node next=children.get(i);
                int value=minimax(next,treeDepth-1,false,alpha,beta);
                //NO MODIFICATION
                bestValue=Math.max(bestValue,value);
                alpha=Math.max(alpha,bestValue);
                if(beta<=alpha){
                    break;
                }
            }
            node.setFunc_value(bestValue);
            return bestValue;
        }
        else{
            int bestValue=MAX_VALUE;
            List<Node> children = node.createChildren();
            for(int i=0;i<children.size();i++){
                Node next=children.get(i);
                int value=minimax(next,treeDepth-1,true,alpha,beta);
                if(bestValue!=MAX_VALUE){
                    /*
                    modification-if value better than worst found->avoid Nim-Zero-Sum
                     */
                    if(value!=0){
                        bestValue = Math.min(bestValue, value);
                        beta = Math.min(beta, bestValue);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                else {
                    bestValue = Math.min(bestValue, value);
                    beta = Math.min(beta, bestValue);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            node.setFunc_value(bestValue);
            return bestValue;
        }
    }
    public Pro(boolean is){
        super(is);
    }
}
