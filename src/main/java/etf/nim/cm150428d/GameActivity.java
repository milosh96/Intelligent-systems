package etf.nim.cm150428d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity{
    public static final int max_num=10;
    public static int poles;
    public static int[] tolkens;
    public static Player player1;
    public static Player player2;
    public static boolean gameFinished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameFinished=false;
        setContentView(R.layout.activity_game);
        Intent intent=getIntent();
        //TODO
        /*
        TextView text = (TextView) findViewById(R.id.player1_text);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        text.setPadding(width/2,0,0,0);*/
        initialize(intent);

        paint();
        if (player1 instanceof Human) {
            player1.setTurn(this);
        }
        else player1.ready();
    }
    private void initialize(Intent intent){
        poles=StartActivity.getNumPoles();
        tolkens=StartActivity.getTolkens();
        //initialize player modes

        String sPlayer1 = intent.getStringExtra("player1");
        String sPlayer2 = intent.getStringExtra("player2");

        switch (sPlayer1){
            case "Human":
                player1=new Human(true);
                break;
            case "Machine easy":
                player1=new Easy(true);
                player1.setTreeDepth(intent.getIntExtra("treeDepth",1));
                break;
            case "Machine intermediate":
                player1=new Inter(true);
                player1.setTreeDepth(intent.getIntExtra("treeDepth",1));
                break;
            case "Machine professional":
                player1=new Pro(true);
                player1.setTreeDepth(intent.getIntExtra("treeDepth",1));
                break;
        }

        switch (sPlayer2){
            case "Human":
                player2=new Human(false);
                break;
            case "Machine easy":
                player2=new Easy(false);
                player2.setTreeDepth(intent.getIntExtra("treeDepth",1));
                break;
            case "Machine intermediate":
                player2=new Inter(false);
                player2.setTreeDepth(intent.getIntExtra("treeDepth",1));
                break;
            case "Machine professional":
                player2=new Pro(false);
                player2.setTreeDepth(intent.getIntExtra("treeDepth",1));
                break;
        }
        //draw the screen-activity
        player1.setOpponent(player2);
        player2.setOpponent(player1);

    }
    public static int checkMove(int pole, int number, int numPoles,int[] tolkens,Player p1,Player p2){
        if (number<=0) return 0;
        if (number>10) return 0;
        if(number>tolkens[pole]) return 0;
        int oldnumber=tolkens[pole];
        int previousPlay=p2.previousMove;
        if (previousPlay!=0) {
            if (number > previousPlay*2) return 0;
        }

        tolkens[pole]-=number;
        int res=GameActivity.checkState(numPoles,tolkens);
        if (res==0) return 0;
        //successful
        GameActivity.tolkens[pole]=tolkens[pole];
        GameActivity.repaint(pole,tolkens[pole],oldnumber);
        p1.previousMove=number;
        return 1;
    }
    public static void declareVictor(Context context){
        gameFinished=true;
        if (player1.getTurn()){
            Toast.makeText(context,"Player 1 has won!",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context,"Player 2 has won!",Toast.LENGTH_LONG).show();
        }
    }
    public static void myWait(){
        for(int i=0;i<1000;i++){
            for (int j=0;j<5000;j++){
                int k=i+j;
            }
        }
    }
    public static void repaint(int pole, int from, int to){
        //TODO
        /*
            set visibility to false
            poles je matricno
            from i to smanjiti za 1? --odradjeno gore
         */
        for (int i=from;i<to;i++){
            MyImage image = MyImage.getMatrix(pole, i);
            image.setVisibility(View.INVISIBLE);
        }
        //bonus-invisible for button---
    }
    public void switchColors(){
        TextView player1_txt = (TextView) findViewById(R.id.player1_text);
        TextView player2_txt = (TextView) findViewById(R.id.player2_text);
        /*not working?
        if(player1.getTurn()){
            player1_txt.setTextColor(Color.parseColor("@android:color/holo_green_dark"));
            player2_txt.setTextColor(Color.parseColor("@android:color/holo_red_dark"));
        }
        else{
            player2_txt.setTextColor(Color.parseColor("@android:color/holo_green_dark"));
            player1_txt.setTextColor(Color.parseColor("@android:color/holo_red_dark"));
        }*/
        if(player1.getTurn()){
            player2_txt.setTextColor(Color.GREEN);
            player1_txt.setTextColor(Color.RED);

        }
        else{
            player1_txt.setTextColor(Color.GREEN);
            player2_txt.setTextColor(Color.RED);
        }
    }
    public static int checkState(int numPoles, int[] tolkens){
        /*
        go through the array    x

         */
        boolean exists=false;
        for(int i=1;i<=max_num;i++){
            for (int j=0;j<numPoles;j++){
                if (tolkens[j]==i) {
                    if(!exists) exists=true;
                    else return 0;
                }
            }
            exists=false;
        }
        return 1;
    }

    private void illegalMove(){
        Toast.makeText(getApplicationContext(),"That is not a legal move! Choose again.",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LinearLayout mainL=(LinearLayout)findViewById(R.id.myCanvas);
        for (int i=0;i<GameActivity.poles;i++){
            mainL.removeView(findViewById(i));
        }
        MyImage.emptyList();
    }

    private void paint(){
        LinearLayout mainL=(LinearLayout)findViewById(R.id.myCanvas);
        //TODO check if it cleans the layout
        //mainL.removeAllViews();

        for (int i=0;i<poles;i++){
            LinearLayout layout=new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
            layout.setLayoutParams(params);

            int index=tolkens[i]-1;
            for(int j=0;j<tolkens[i];j++){
                MyImage image=new MyImage(this);
                image.setImageResource(R.drawable.tolken);
                image.setLayoutParams(params);
                layout.addView(image);
                image.setVisibility(View.VISIBLE);
                //fix to delete from top to bottom-row diffrent
                image.setMatrix(i,index);
                index--;
            }
            Button btn=new Button(this);
            btn.setText("Pick");
            btn.setVisibility(View.VISIBLE);
            params.gravity= Gravity.BOTTOM;
            btn.setLayoutParams(params);
            layout.addView(btn);
            btn.setId(i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*TODO
                        check if Human player turn  x
                        enter num of tolk x
                        check valid
                        change visibility
                        endTurn

                     */
                    Player myPlayer,opponent;
                    if ((player1 instanceof Human)&&(player1.getTurn())) {
                        myPlayer=player1;
                        opponent=player2;
                    }
                    else if((player2 instanceof Human)&&(player2.getTurn()) ){
                        myPlayer=player2;
                        opponent=player1;
                    }
                    else return;

                    Button btn=(Button) view;
                    int id=btn.getId();
                    if (GameActivity.tolkens[id]==0) {
                        illegalMove();
                        return;
                    }
                    EditText del_num= (EditText)findViewById(R.id.del_num);
                    int number=Integer.parseInt(del_num.getText().toString());
                    int[] tolkensNew=GameActivity.tolkens.clone();
                    int move = checkMove(id, number, GameActivity.poles, tolkensNew, myPlayer, opponent);
                    if (move==0) illegalMove();
                    else {

                        int isFinal=myPlayer.checkEnd(GameActivity.poles, GameActivity.tolkens);
                        if (isFinal==1) {
                            declareVictor(getApplicationContext());
                        }
                        else {
                            switchColors();
                            myPlayer.endTurn();
                            if (opponent instanceof Human) {
                                opponent.setTurn(GameActivity.this);
                            }
                            else opponent.ready();
                        }
                    }

                }
            });
            mainL.addView(layout);

        }

    }

    public void clickMachine(View view) {
        if(player1.getReady()){
            player1.setTurn(GameActivity.this);
        }
        else if(player2.getReady()){
            player2.setTurn(GameActivity.this);
        }
    }
}
