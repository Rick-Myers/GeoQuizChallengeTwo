package com.myerstyme.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private Button mCheatButton;
    private TextView mAnswerTextView;
    private TextView mApiTextView;
    private boolean mAnswerIsTrue;
    private boolean mCheatedIsTrue;
    private static final String EXTRA_ANSWER_IS_TRUE = "com.myerstyme.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.myerstyme.android.geoquiz.answer_shown";
    private static final String INDEX_CHEATED_IS_TRUE = "CheatIsTrue";
    private static final String INDEX_ANSWER_IS_TRUE = "AnswerIsTrue";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mCheatButton = (Button) findViewById(R.id.showAnswerButton);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mApiTextView = (TextView) findViewById(R.id.apiTextView);

        String apiLevel = "API Level " + Build.VERSION.SDK_INT;
        mApiTextView.setText(apiLevel);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswerTextView(mAnswerIsTrue);
                setAnswerShownResult(true);
                mCheatedIsTrue = true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putBoolean(INDEX_CHEATED_IS_TRUE, mCheatedIsTrue);

        //If the user cheated, save that state. If not, ignore as view will clear onCreate.
        if (mCheatedIsTrue == true) {
            saveInstanceState.putBoolean(INDEX_ANSWER_IS_TRUE, mAnswerIsTrue);
        }

        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mCheatedIsTrue = savedInstanceState.getBoolean(INDEX_CHEATED_IS_TRUE, false);

        //If the user has cheated, continue showing the answer.
        if (mCheatedIsTrue == true) {
            setAnswerTextView(mAnswerIsTrue);
        }
    }

    private void setAnswerTextView(boolean answerIsTrue) {
        if (answerIsTrue)
            mAnswerTextView.setText(R.string.true_button);
        else
            mAnswerTextView.setText(R.string.false_button);
    }


    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
