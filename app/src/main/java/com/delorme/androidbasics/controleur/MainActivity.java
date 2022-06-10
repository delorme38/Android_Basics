package com.delorme.androidbasics.controleur;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.delorme.androidbasics.R;
import com.delorme.androidbasics.model.User;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";

    private ActivityResultLauncher<Intent> gameActivityLauncher;

    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        mUser = new User();
        mPlayButton.setEnabled(false);

        playerCheck();

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });
        gameActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.hasExtra(GameActivity.BUNDLE_EXTRA_SCORE)) {
                                Log.d(TAG, "onActivityResult: " + data.hasExtra(GameActivity.BUNDLE_EXTRA_SCORE));
                                int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
                                mUser.setScore(score);
                                saveToSharedPref();
                                playerCheck();
                            }
                        }
                    }
                });

        mPlayButton.setOnClickListener(v -> {
            mUser.setFirstName(mNameEditText.getText().toString());
            saveToSharedPref();
            openGameAvtivityForResult();
        });
    }

    public void openGameAvtivityForResult(){
        Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
        gameActivityLauncher.launch(gameActivityIntent);
    }
    private void playerCheck() {
        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        int score = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, 0);
        if (firstName != null) {

            mUser.setFirstName(firstName);
            mUser.setScore(score);
            mGreetingTextView.setText(
                    MessageFormat.format("Welcome back, {0}!\n Your last score was {1} wil you do better this time?", firstName, score)
            );
            mNameEditText.setText(firstName);
            mPlayButton.setEnabled(true);
        }
    }

    private void saveToSharedPref() {
        getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_USER_INFO_NAME, mUser.getFirstName())
                .putInt(SHARED_PREF_USER_INFO_SCORE, mUser.getScore())
                .apply();
    }
}