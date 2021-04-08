package com.example.geoquiz;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 100, 200);
            toast.show();
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, R.string.incorrect_answer, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        });
    }
}