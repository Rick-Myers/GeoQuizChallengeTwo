package com.myerstyme.android.geoquiz;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by D on 8/27/2017.
 * This class was created while I reintroduce myself to Android. This can easily be done with
 * anonymous listeners and not needing a public checkAnswer class in the main activity.
 */

public class TrueButtonListener implements View.OnClickListener {

    QuizActivity context;

    public TrueButtonListener(Activity context){
        this.context = (QuizActivity) context;
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(context, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        context.checkAnswer(true);
    }
}
