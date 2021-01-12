package com.comp1601.tictactoe;
import android.graphics.Color;
import android.widget.Button;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;


public class TicTacToeGame extends MainActivity{
    private int position;
    private String person;
    private String anotherPlayer;
    private int playerXScore = 0;
    private int playerOScore = 0;
    private TextView playerXTextView;
    private TextView playerOTextView;
    private int nextButtonPosition;
    private Button[] buttonBoard;
    private int count;
    private int score = 0;
    private int countBefore3;
    private String[] viewBoard;
    private String[] boardString = new String[9];
    private int roundNumber = 1;
    private String winner = "";
    private boolean showToast = false;
    private String toastString;
    private Button[] winnerButton = new Button[3];
    private int computerFirstStep;
    private String X_string_score;
    private String O_string_score;
    private ArrayList<Integer> emptyPosition = new ArrayList<Integer>();
    public static final double InvalidResult = -1.0;

    public TicTacToeGame(){

    }

    public TicTacToeGame(Button[] aButtonBoard, String[] aViewBoard) {

        buttonBoard = aButtonBoard;
        viewBoard = aViewBoard;

    }

//main activity here
    public void play(String player, int aPosition) {


        person = player;
        position = aPosition;
        showToast = false;

        String textOfButton = buttonBoard[position].getText().toString();

//if no text on clicked button, take an action
        if (textOfButton.equals("")) {
            //person turn
            if (isPersonTurn()) {
                anotherPlayer = "O";
                buttonBoard[position].setText(person);
                updateViewBoard(position, person);
                nextButtonPosition = nextButtonPosition();
                if(nextButtonPosition != InvalidResult) {
                    buttonBoard[nextButtonPosition].setText(anotherPlayer);
                    viewBoard[nextButtonPosition] = anotherPlayer;}
                if (isWinning()) {
                        showToast = true;
                        toastString = toastString();

                }else if (endsInADraw()){
                    showToast = true;
                    toastString = "Ends in a draw!";
                }

            //computer turn
            }else if (isComputerTurn()) {

                anotherPlayer = "X";
                buttonBoard[position].setText(person);
                updateViewBoard(position, person);
                nextButtonPosition = nextButtonPosition();
                if(nextButtonPosition != InvalidResult) {
                    buttonBoard[nextButtonPosition].setText(anotherPlayer);
                    viewBoard[nextButtonPosition] = anotherPlayer;}
                if (isWinning()) {
                    showToast = true;
                    toastString = toastString();

                } else if (endsInADraw()) {
                    showToast = true;
                    toastString = "Ends in a draw!";
                    System.out.println("This is end in a draw!");

                }
            }

        }
    }

    //test whether next step is valid
    public int nextStep(int nextPosition){
       nextButtonPosition = nextPosition;
        if(nextButtonPosition != InvalidResult) {
            nextButtonPosition = nextButtonPosition();
            buttonBoard[nextPosition].setText(anotherPlayer);
            viewBoard[nextButtonPosition] = anotherPlayer;
            return nextButtonPosition;
        }return (int)InvalidResult;
    }

//update view board position to a person
    public void updateViewBoard(int position, String person) {
        viewBoard[position] = person;
    }


//to check if someone is winning
    public boolean isWinning() {

        for (int i = 0; i < 9; i += 3) {
            if (viewBoard[i] != null && viewBoard[i].equals(viewBoard[i + 1]) && viewBoard[i].equals(viewBoard[i + 2])) {
                winner = viewBoard[i];
                winnerButton[0] = buttonBoard[i];
                winnerButton[1] = buttonBoard[i+1];
                winnerButton[2] = buttonBoard[i+2];
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (viewBoard[i] != null && viewBoard[i].equals(viewBoard[i + 3]) && viewBoard[i].equals(viewBoard[i + 6])) {
                winner = viewBoard[i];
                winnerButton[0] = buttonBoard[i];
                winnerButton[1] = buttonBoard[i+3];
                winnerButton[2] = buttonBoard[i+6];
                return true;
            }
        }

        if (viewBoard[0] != null && viewBoard[0].equals(viewBoard[4]) && viewBoard[0].equals(viewBoard[8])) {
            winner = viewBoard[0];
            winnerButton[0] = buttonBoard[0];
            winnerButton[1] = buttonBoard[4];
            winnerButton[2] = buttonBoard[8];
            return true;
        }

        if (viewBoard[2] != null && viewBoard[2].equals(viewBoard[4]) && viewBoard[2].equals(viewBoard[6])) {
            winner = viewBoard[2];
            winnerButton[0] = buttonBoard[2];
            winnerButton[1] = buttonBoard[4];
            winnerButton[2] = buttonBoard[6];
            return true;
        }

        return false;
    }

    //set all text on board to none
    public void drawBoard() {
        for (int i = 0; i < 9; i++) {
            buttonBoard[i].setText("");
            viewBoard[i] = null;
        }
    }

//return toast string that should show on screen
    public String toastString() {

        if (winner.equals("X")) {
            winner = "";
            playerXScore++;
            X_string_score = String.valueOf(playerXScore);

            System.out.println("X wins" + winner);
            return "Player X wins!";

        }

        else if (winner.equals("O")) {
            winner = "";
            playerOScore++;
            O_string_score = String.valueOf(playerOScore);
            System.out.println("O wins"+ winner);
            return "Player O wins!";
        }

        return "";
    }

    //determine if one game is end or not
    public boolean willShowToast(){
        return showToast;
    }

    //return toast string
    public String getToastString(){
        return toastString;
    }


//highlight winner's button to a different color
    public void highlightWinnerButton(){

        for(Button b: winnerButton){
            b.setBackgroundColor(Color.parseColor("#ff80aa"));
        }

    }

    //update background to default color
    public void updateBackGroundColor(){
        for(int i = 0; i < 9; i++){
            buttonBoard[i].setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
        showToast=false;
    }

//determine if two are in a tie
    public boolean endsInADraw(){
        if(boardIsFull() && !isWinning()){
            return true;
        }return false;
    }

    //to see if the board has all played
    public boolean boardIsFull(){
        count = 0;
        for(int i = 0; i < 9; i++){
            if(viewBoard[i] != null){
                count++;
            }
        }if (count == 9){
            return true;
        }
        return false;
    }

    //update the state
    public void update(TextView XTextView, TextView OTextView) {
        playerXTextView = XTextView;
        playerOTextView = OTextView;
        if (isWinning()) {
            highlightWinnerButton();
            updatePlayerScore();
        }
        delay();
        }

        //delay for 2.5 seconds so that one can see what happening when the game comes to an end
    public void delay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawBoard();
                updateBackGroundColor();
                roundNumber++;
                System.out.println("This is round " + roundNumber);
                if(isComputerTurn()) {
                    computerFirstStep = randomPosition();
                    buttonBoard[computerFirstStep].setText("X");
                    viewBoard[computerFirstStep] = "X";
                }
                    for(int i = 0; i < 9; i++){
                        viewBoard[i] = null;
                    }

            }
        },2500);
    }

//return a random position in arraylist that contains only the button that doesn't have text
    public int randomPosition() {
        if(isComputerTurn()) {
            viewBoard[computerFirstStep] = "X";
        }
        for (int i = 0; i < 9; i++) {
            if (viewBoard[i] == null) {
                emptyPosition.add(i);
            }
        }

        System.out.println("EmptyPosition size " + emptyPosition.size());
        if(emptyPosition.size()!= 0) {
            int max = emptyPosition.size() - 1;
            int randomIndex = (int) (Math.random() * (max + 1));
            int ranPos = emptyPosition.get(randomIndex);
            emptyPosition.clear();
            return ranPos;
        }return (int)InvalidResult;
    }

//to see if it is computer turn
    public boolean isComputerTurn(){
        if (roundNumber % 2 == 0){
            return true;
        }
        return false;
    }

    //to see if it is person turn
    public boolean isPersonTurn(){
        if(!isComputerTurn()){
            return true;
        }return false;
    }

    //update score for each player
    public void updatePlayerScore(){
        playerXTextView.setText("Player X: " + playerXScore);
        playerOTextView.setText("Player O: " + playerOScore);
    }

//return a string that represents current board configuration
    public String boardConfiguration(){
        for(int i = 0; i < 9; i++){
            if(viewBoard[i] != null){
                boardString[i] = viewBoard[i];
            }else{
                boardString[i] = " ";
            }
        }

        return " " + "\n" + boardString[0] + "|" + boardString[1] + "|" + boardString[2] + "\n" +
                boardString[3] + "|" + boardString[4] + "|" + boardString[5] + "\n"+
                boardString[6] + "|" + boardString[7] + "|" + boardString[8];

    }

//get X score
    public int getPlayerXScore(){
        return playerXScore;
    }

//get O score
    public int getPlayerOScore(){
        return playerOScore;
    }

    //get view board
    public String[] getViewBoard(){
        return viewBoard;
    }

    //return next button position
    //it block player from going for anywhere have two chess on a line
    public int nextButtonPosition() {
        countBefore3 = 0;
        //if there are two "X" on the row, computer should put "O" on the line to prevent player succeed


        //check first row
        for (int i = 0; i < 3; i++) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }

        if (countBefore3 == 2) {
            for (int i = 0; i < 3; i++) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;


        //check second row
        for (int i = 3; i < 6; i++) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }

        if (countBefore3 == 2) {
            for (int i = 3; i < 6; i++) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;


        //check third row
        for (int i = 6; i < 9; i++) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }

        if (countBefore3 == 2) {
            for (int i = 6; i < 9; i++) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;

        //if there are two "X" on the column, computer should put next step on the column

        //check first column
        for (int i = 0; i < 9; i += 3) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }
        if (countBefore3 == 2) {
            for (int i = 0; i < 9; i += 3) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;


        //check second column
        for (int i = 1; i < 9; i += 3) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }

        if (countBefore3 == 2) {
            for (int i = 1; i < 9; i += 3) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;


        //check third column
        for (int i = 2; i < 9; i += 3) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }
        if (countBefore3 == 2) {
            for (int i = 2; i < 9; i += 3) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;


        //check first diagonal
        for (int i = 0; i < 9; i += 4) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }
        if (countBefore3 == 2) {
            for (int i = 0; i < 9; i += 4) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;

        //check second diagonal
        for (int i = 2; i < 7; i += 2) {
            if (viewBoard[i] != null && viewBoard[i].equals(person)) {
                countBefore3++;
            }
        }

        if (countBefore3 == 2) {
            for (int i = 2; i < 7; i += 2) {
                if (viewBoard[i] == null) {
                    return i;
                }
            }
        }
        countBefore3 = 0;

        //if none of the above cases, return a random position that is not occupied

        int ran = randomPosition();
        return ran;

    }


}