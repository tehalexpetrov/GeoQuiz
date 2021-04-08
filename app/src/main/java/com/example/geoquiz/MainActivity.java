package com.example.geoquiz;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;

    private Questions[] mQuestionBank = new Questions[]{
            new Questions(R.string.question_Australia, true),
            new Questions(R.string.question_Egipt, true),
            new Questions(R.string.question_Africa, true),
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        AtomicInteger question = new AtomicInteger(mQuestionBank[mCurrentIndex].getTextResId());
        mQuestionTextView.setText(question.get());

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, R.string.incorrect_answer, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            question.set(mQuestionBank[mCurrentIndex].getTextResId());
            mQuestionTextView.setText(question.get());
        });
    }
}