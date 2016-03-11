package com.example.stepanenko.dicecup;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stepanenko.dicecup.Model.Board;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int MIN_DICES = 1;
    private final int MAX_DICES = 6;
    private final String TAG = "Tag";

    ImageButton buttonGoToHistory;
    ImageButton buttonRoll;
    Spinner spinnerDicesAmount;
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonGoToHistory = (ImageButton) findViewById(R.id.buttonHistory);
        buttonGoToHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHistory();
            }
        });

        buttonRoll = (ImageButton) findViewById(R.id.buttonRoll);
        buttonRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonRollClick();
            }
        });

        spinnerDicesAmount = (Spinner) findViewById(R.id.spinnerNumberOfDices);
        setUpSpinner();
        if (savedInstanceState == null) {
            board = new Board(MIN_DICES);
            setUpGameBoard(board);
        }
        Log.d(TAG, "onCreate");

}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("board", board);
        Log.d(TAG, "onSave");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        board = (Board) savedInstanceState.getSerializable("board");
        setUpGameBoard(board);
        updateGameBoard(board);
        Log.d(TAG, "onRestore");
    }

    private void onClickHistory() {
        Intent intent = new Intent(this,HistoryListActivity.class);
        intent.putExtra("map", board.getHistory());
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if  (data==null) return;
        boolean deleted = data.getBooleanExtra("isDeleted",false);
        if (deleted){
            board.clearHistory();
        }
    }

    private void onButtonRollClick() {
        board.roll();
        updateGameBoard(board);
    }

    private void setUpGameBoard(Board board){
        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
        gameLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;

        for (int i=0; i < board.getNumberOfDices(); i++){
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            gameLayout.addView(imageView,params);
        }
    }

    private void updateGameBoard(Board board) {
        for(int i = 0; i < board.getNumberOfDices(); i++){
            ImageView imageView = (ImageView) findViewById(i);
            imageView.setImageResource( getImageDiceId( board.getDice(i).getFaceValue()));
            imageView.startAnimation(getRandomAnimation());
        }
    }

    private Animation getRandomAnimation()
    {
        Random r = new Random();
        int randomNumber = r.nextInt(6)+1;
        Animation animation = null;
        switch (randomNumber){
            case 1: animation = AnimationUtils.loadAnimation(this,R.anim.mycombo); break;
            case 2: animation = AnimationUtils.loadAnimation(this,R.anim.myalpha);break;
            case 3: animation = AnimationUtils.loadAnimation(this,R.anim.myrotate);break;
            case 4: animation = AnimationUtils.loadAnimation(this,R.anim.myscale);break;
            case 5: animation = AnimationUtils.loadAnimation(this,R.anim.mytrans);break;
            case 6: animation = AnimationUtils.loadAnimation(this,R.anim.mytrans2);break;
        }
        return animation;
    }


    private void setUpSpinner() {
        int arraySize = MAX_DICES - MIN_DICES + 1;
        Integer[] values = new Integer[arraySize];
        for (int i=0; i< arraySize; i++){
            values[i] = MIN_DICES + i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDicesAmount.setPrompt(getString(R.string.title));
        spinnerDicesAmount.setAdapter(adapter);
        spinnerDicesAmount.setSelection(0, true);

        spinnerDicesAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int amount = Integer.parseInt(spinnerDicesAmount.getSelectedItem().toString());
                board.setNumberOfDices(amount);
                setUpGameBoard(board);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private int getImageDiceId(int faceValue) {
        switch (faceValue){
            case 1: { return R.drawable.die_face_1;}
            case 2:{ return R.drawable.die_face_2;}
            case 3:{ return R.drawable.die_face_3;}
            case 4:{ return R.drawable.die_face_4;}
            case 5:{ return R.drawable.die_face_5;}
            case 6:{ return R.drawable.die_face_6;}
            default: {return 0;}
        }
    }
}
