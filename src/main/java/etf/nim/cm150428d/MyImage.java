package etf.nim.cm150428d;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIlos on 5.1.2018.
 */

public class MyImage extends android.support.v7.widget.AppCompatImageView {
    private int i;
    private int j;
    private static List<MyImage> myList=new ArrayList<>();
    public MyImage(Context context) {
        super(context);
        if(myList==null){
            myList=new ArrayList<>();
        }
        myList.add(this);
    }
    public void setMatrix(int i,int j){
        this.i=i;
        this.j=j;
    }
    public static MyImage getMatrix(int i, int j){
        for (int k=0;k<myList.size();k++){
            MyImage im=myList.get(k);
            if((i==im.getI())&&(j==im.getJ())){
                myList.remove(k);
                return im;
            }
        }
        return null;
    }
    public static void emptyList(){
        myList=null;
    }
    public int getI(){
        return i;
    }
    public int getJ(){
        return j;
    }
}
