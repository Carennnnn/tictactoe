package com.comp1601.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button mButton0;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private TextView playerXTextView;
    private TextView playerOTextView;
    private final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);
    private String[] viewBoard = new String[9];
    public static final int MAX_NUM = 9;
    private Button[] buttonBoard = new Button[MAX_NUM];
    private static String PLAYER_X_SCORE_KEY = "playerX_score";
    private static String PLAYER_O_SCORE_KEY = "playerO_score";
    private static String BUTTON_VIEW_KEY = "button_view";
    private int playerX_score = 0;
    private int playerO_score = 0;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState(Bundle)");
        savedInstanceState.putInt(PLAYER_X_SCORE_KEY,playerX_score);
        savedInstanceState.putInt(PLAYER_O_SCORE_KEY, playerO_score);
        savedInstanceState.putStringArray(BUTTON_VIEW_KEY, viewBoard);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //get state show on the screen
        if(savedInstanceState != null){
            playerX_score = savedInstanceState.getInt(PLAYER_X_SCORE_KEY,0);
            playerO_score = savedInstanceState.getInt(PLAYER_O_SCORE_KEY,0);
            viewBoard = savedInstanceState.getStringArray(BUTTON_VIEW_KEY);
        }

        mButton0 = (Button) findViewById(R.id.button0);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        mButton6 = (Button) findViewById(R.id.button6);
        mButton7 = (Button) findViewById(R.id.button7);
        mButton8 = (Button) findViewById(R.id.button8);

        playerXTextView = (TextView)findViewById(R.id.playerX_text_view);
        playerOTextView = (TextView)findViewById(R.id.playerO_text_view);

        //add all buttons to array
        buttonBoard[0] = mButton0;
        buttonBoard[1] = mButton1;
        buttonBoard[2] = mButton2;
        buttonBoard[3] = mButton3;
        buttonBoard[4] = mButton4;
        buttonBoard[5] = mButton5;
        buttonBoard[6] = mButton6;
        buttonBoard[7] = mButton7;
        buttonBoard[8] = mButton8;

        //set default background color
        for(int i = 0; i < 9; i++){
           buttonBoard[i].setBackgroundColor(Color.parseColor("#E0E0E0"));
           if(viewBoard[i] != null){
               buttonBoard[i].setText(viewBoard[i]);
           }

        }

        playerXTextView.setText("Player X: " + playerX_score);
        playerOTextView.setText("Player O: " + playerO_score);

        //store button in the hashmap
        final HashMap<Button,Integer> maps = new HashMap<>();
        for(int i = 0;i<9;i++){
            maps.put(buttonBoard[i],i);
        }

        //create an instance
        final TicTacToeGame computer = new TicTacToeGame(buttonBoard,viewBoard);

        //set all button by using for loop
        for (final Button button:buttonBoard) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(computer.isPersonTurn()) {
                        computer.play("X", maps.get(button));
                    }else if(computer.isComputerTurn()){
                        computer.play("O", maps.get(button));
                    }
                    //print board configuration on terminal
                    System.out.println(computer.boardConfiguration());

                    //print board configuration on logcat
                    Log.i(TAG,computer.boardConfiguration());

                    //get current state
                    viewBoard = computer.getViewBoard();
                    playerX_score = computer.getPlayerXScore();
                    playerO_score = computer.getPlayerOScore();

                    //if ends in a game, show toast and update
                    if(computer.willShowToast()){
                        Toast.makeText(MainActivity.this, computer.getToastString(), Toast.LENGTH_SHORT).show();
                        computer.update(playerXTextView, playerOTextView);
                    }

                }
            });

        }


    }



}