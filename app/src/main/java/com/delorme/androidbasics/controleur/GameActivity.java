package com.delorme.androidbasics.controleur;

import static android.content.ContentValues.TAG;
import static com.delorme.androidbasics.model.Utils.generateQuestionBank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delorme.androidbasics.R;
import com.delorme.androidbasics.model.Question;
import com.delorme.androidbasics.model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";

    private boolean mEnableTouchEvents = true;


    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mRemainingQuestionCount = 3;
    private int mScore = 0;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mQuestionBank = savedInstanceState.getParcelable(BUNDLE_STATE_QUESTION_BANK);
        } else {
            mQuestionBank = generateQuestionBank();
        }
        setListeners();
        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mCurrentQuestion);


    }

    private void setListeners() {
        mQuestionTextView = findViewById(R.id.game_activity_textview_question);
        mAnswerButton1 = findViewById(R.id.game_activity_button_1);
        mAnswerButton2 = findViewById(R.id.game_activity_button_2);
        mAnswerButton3 = findViewById(R.id.game_activity_button_3);
        mAnswerButton4 = findViewById(R.id.game_activity_button_4);
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);
    }

    private void displayQuestion(final Question question) {
// Set the text for the question text view and the four buttons
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    @Override
    public void onClick(View v) {
        int index;

        if (v == mAnswerButton1) {
            index = 0;
        } else if (v == mAnswerButton2) {
            index = 1;
        } else if (v == mAnswerButton3) {
            index = 2;
        } else if (v == mAnswerButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }
        answerQuestion(index);
        finishTurn();
    }

    private void finishTurn() {
        mRemainingQuestionCount--;
        mEnableTouchEvents = false;
        new Handler().postDelayed(() -> {
            // If this is the last question, ends the game.
            // Else, display the next question.
            if (mRemainingQuestionCount > 0) {
                mCurrentQuestion = mQuestionBank.getNextQuestion();
                displayQuestion(mCurrentQuestion);
            } else {
                // No question left, end the game
                showDialog();
            }
            mEnableTouchEvents = true;
        }, 2_000);
    }

    private void answerQuestion(int index) {
        if (mCurrentQuestion.getAnswerIndex() == index){
            Toast.makeText(this, "Bravo vous avez trouvez la bonne réponse", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Désolé la bonne réponse était " + mCurrentQuestion.getChoiceList().get(mCurrentQuestion.getAnswerIndex()), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", (dialog, which) -> endGame())
                .create()
                .show();
    }

    private void endGame(){
//        Intent intent = new Intent();
//        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
//        setResult(RESULT_OK, intent);
//        finish();
        setResult(RESULT_OK, getIntent().putExtra(BUNDLE_EXTRA_SCORE, mScore));
        finish();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_STATE_QUESTION_BANK, mQuestionBank);
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }
}