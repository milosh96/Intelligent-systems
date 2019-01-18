package etf.nim.cm150428d;

/**
 * Created by MIlos on 5.1.2018.
 */

public class Human extends Player {
    @Override
    public int calculateFunction(int poles, int[] tolkens) {
        return 0;
    }

    @Override
    public void turn(int numPoles, int[] tolkens,GameActivity gameActivity) {

        /*while(true){
            //do nothing
            if(!getTurn()) break;
        }*/
        return;
    }
    public Human(boolean is){
        super(is);
    }
}
