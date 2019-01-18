package etf.nim.cm150428d;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class StartActivity extends AppCompatActivity {
    private static int numPoles;

    public static int[] getTolkens() {
        return tolkens;
    }

    private static int[] tolkens;

    public static int getNumPoles(){
        return numPoles;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void leftClick(View view) {
        //TODO
        /*
        -get player modes       x
        -get tolken numbers
        -throw exception(toast) if 2 equal num
        -create intent
        -jump to gameActivity
        -create onRestart/Resume.. for return
         */
        tolkens=new int[10];
        numPoles=0;
        RadioGroup rGroup1 = (RadioGroup)findViewById(R.id.player1);
        RadioGroup rGroup2 = (RadioGroup)findViewById(R.id.player2);

        RadioButton rButton1 = (RadioButton)findViewById(rGroup1.getCheckedRadioButtonId());
        RadioButton rButton2 = (RadioButton)findViewById(rGroup2.getCheckedRadioButtonId());

        EditText text1=(EditText)findViewById(R.id.column1);
        EditText text2=(EditText)findViewById(R.id.column2);
        EditText text3=(EditText)findViewById(R.id.column3);
        EditText text4=(EditText)findViewById(R.id.column4);
        EditText text5=(EditText)findViewById(R.id.column5);
        EditText text6=(EditText)findViewById(R.id.column6);
        EditText text7=(EditText)findViewById(R.id.column7);
        EditText text8=(EditText)findViewById(R.id.column8);
        EditText text9=(EditText)findViewById(R.id.column9);
        EditText text10=(EditText)findViewById(R.id.column10);

        String s1=text1.getText().toString();
        String s2=text2.getText().toString();
        String s3=text3.getText().toString();
        String s4=text4.getText().toString();
        String s5=text5.getText().toString();
        String s6=text6.getText().toString();
        String s7=text7.getText().toString();
        String s8=text8.getText().toString();
        String s9=text9.getText().toString();
        String s10=text10.getText().toString();


        while(true){
            if(s1.equals("")) break;
            else {
                if(tolkens[0]==0) {
                    numPoles++;
                    tolkens[0] = Integer.parseInt(s1);
                }
            }
            if(s2.equals("")) break;
            else {
                if (tolkens[1] == 0) {
                    numPoles++;
                    tolkens[1] = Integer.parseInt(s2);
                }
            }
            if(s3.equals("")) break;
            else {
                if(tolkens[2]==0) {
                    numPoles++;
                    tolkens[2] = Integer.parseInt(s3);
                }
            }
            if(s4.equals("")) break;
            else {
                if(tolkens[3]==0) {
                    numPoles++;
                    tolkens[3] = Integer.parseInt(s4);
                }
            }
            if(s5.equals("")) break;
            else {
                if(tolkens[4]==0) {
                    numPoles++;
                    tolkens[4] = Integer.parseInt(s5);
                }
            }
            if(s6.equals("")) break;
            else {
                if(tolkens[5]==0) {
                    numPoles++;
                    tolkens[5] = Integer.parseInt(s6);
                }
            }
            if(s7.equals("")) break;
            else {
                if(tolkens[6]==0) {
                    numPoles++;
                    tolkens[6] = Integer.parseInt(s7);
                }
            }
            if(s8.equals("")) break;
            else {
                if(tolkens[7]==0) {
                    numPoles++;
                    tolkens[7] = Integer.parseInt(s8);
                }
            }
            if(s9.equals("")) break;
            else {
                if(tolkens[8]==0) {
                    numPoles++;
                    tolkens[8] = Integer.parseInt(s9);
                }
            }
            if(s10.equals("")) break;
            else {
                if(tolkens[9]==0) {
                    numPoles++;
                    tolkens[9] = Integer.parseInt(s10);
                }
            }


            break;
        }

        if (numPoles!=0) {

            //check with the rules of the game-----  TODO
            int state=GameActivity.checkState(numPoles,tolkens);
            if(state==0) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            Intent intent = new Intent(this, GameActivity.class);
            EditText depth_text = (EditText) findViewById(R.id.treeDepth);
            int treeDepth=0;
            if(!depth_text.getText().toString().equals("")) {
                treeDepth = Integer.parseInt(depth_text.getText().toString());
            }
            if(treeDepth<0) treeDepth=0;
            intent.putExtra("player1", rButton1.getText().toString());
            intent.putExtra("player2", rButton2.getText().toString());
            intent.putExtra("numPoles", numPoles);
            intent.putExtra("tolkens", tolkens);
            intent.putExtra("treeDepth",treeDepth);
            startActivity(intent);
        }
    }


    //UNNECESSARY?
    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
