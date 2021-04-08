package com.example.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;

    public static  final String TAG = "MainActivity"; //Логирование

    private Questions[] mQuestionBank = new Questions[]{
            new Questions(R.string.question_Australia, true),
            new Questions(R.string.question_Egipt, true),
            new Questions(R.string.question_Africa, true),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called"); //Логирование onCreate
        setContentView(R.layout.activity_main);

        //Поле вывода вопроса. А так же возможность выбрать другой вопрос
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });
//Кнопка. Верный ответ
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> {
            checkAnswer(true); // Проверка на правильность ответа на вопрос
        });

//Кнопка. Неверный ответ
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            checkAnswer(false);
        });
//Добавил кнопку вперед
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });

//        Добавил кнопку назад и остановил цикл если вопросов нет
        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(v -> {
            do{
                if(mCurrentIndex != 0){
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                } else {
                    mCurrentIndex = (mCurrentIndex) % mQuestionBank.length;
                    updateQuestion();
                }
            }while(!true);
        });
    }

    private void updateQuestion() {
        AtomicInteger question = new AtomicInteger(mQuestionBank[mCurrentIndex].getTextResId());
        mQuestionTextView.setText(question.get());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_answer;
        } else {
            messageResId = R.string.incorrect_answer;
        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}