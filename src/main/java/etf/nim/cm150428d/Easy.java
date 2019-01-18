package etf.nim.cm150428d;

import java.util.List;

/**
 * Created by MIlos on 5.1.2018.
 */

public class Easy extends Player {
    @Override
    public int calculateFunction(int poles, int[] tolkens) {
        int value=0;
        for(int i=0;i<poles;i++){
            value^=tolkens[i];
        }
        return value;
    }

    @Override
    public void turn(int numPoles, int[] tolkens,GameActivity gameActivity) {
        //set turn
        //simple function
        //calculate minimax-private function
        //play and repaint?
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
    private int minimax(Node node,int treeDepth,boolean isMaxPlayer){
        if((treeDepth==0)||(node.finalState()!=0)){
            return node.getFunction();
        }
        if(isMaxPlayer){
            int bestValue=MIN_VALUE;
            List<Node> children = node.createChildren();
            for(int i=0;i<children.size();i++){
                Node next=children.get(i);
                int value=minimax(next,treeDepth-1,false);
                bestValue=Math.max(bestValue,value);
            }
            node.setFunc_value(bestValue);
            return bestValue;
        }
        else{
            int bestValue=MAX_VALUE;
            List<Node> children = node.createChildren();
            for(int i=0;i<children.size();i++){
                Node next=children.get(i);
                int value=minimax(next,treeDepth-1,true);
                bestValue=Math.min(bestValue,value);
            }
            node.setFunc_value(bestValue);
            return bestValue;
        }
    }
    private void constructTree(Node node){
        List<Node> children = node.createChildren();

        for (int i=0;i<children.size();i++){
            Node next=children.get(i);
            int value=minimax(next,treeDepth-1,!node.isMax());
            next.setFunc_value(value);
        }
    }
    public Easy(boolean is){
        super(is);
    }
}
