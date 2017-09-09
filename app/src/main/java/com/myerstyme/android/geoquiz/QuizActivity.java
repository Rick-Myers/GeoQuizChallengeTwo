package com.myerstyme.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "SavedIndex";
    private static final String KEY_CHEATED = "SavedCheated";
    private static final String KEY_CHEAT_WATCH = "SavedCheatWatch";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private boolean[] mCheatWatch = new boolean[mQuestionBank.length];

    private int mCurrentIndex = 0;

    private void updateQuestion() {
        int nextQuestion = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(nextQuestion);
    }

    public void checkAnswer(boolean userPressedTrue) {
        int messageResId = 0;

        //if (mIsCheater) {
        if (mCheatWatch[mCurrentIndex]) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
        }
        mIsCheater = CheatActivity.wasAnswerShown(data);
        if (mIsCheater == true) {
            mCheatWatch[mCurrentIndex] = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate(Bundle) called");

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean mAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, mAnswer);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        //Attempting to reload any persistent data
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATED, false);

            //check if null
            boolean[] tempArray = savedInstanceState.getBooleanArray(KEY_CHEAT_WATCH);
            if (tempArray != null) {
                mCheatWatch = Arrays.copyOf(tempArray, tempArray.length);
            }
        }
        updateQuestion();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATED, mIsCheater);
        savedInstanceState.putBooleanArray(KEY_CHEAT_WATCH, mCheatWatch);
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState(Bundle) called");

    }
}
