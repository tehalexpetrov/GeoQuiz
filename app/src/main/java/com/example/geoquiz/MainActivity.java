package com.example.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private int tryCount = 0; //Счетчик правильных ответов
    private int falseCount = 0; //Счетчик не правильных ответов

    public static  final String TAG = "MainActivity"; //Логирование
    public static final String KEY_INDEX = "index";

    private Questions[] mQuestionBank = new Questions[]{
            new Questions(R.string.question_Australia, true),
            new Questions(R.string.question_Egipt, true),
            new Questions(R.string.question_Africa, true),
    };

    private boolean[] mQuestionsAnswered = new boolean[mQuestionBank.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called"); //Логирование onCreate
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mQuestionsAnswered = savedInstanceState.getBooleanArray(KEY_INDEX);
        }


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
            checkAnswer(true);// Проверка на правильность ответа на вопрос
        });

//Кнопка. Неверный ответ
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            checkAnswer(false);
            mFalseButton.setEnabled(!mQuestionsAnswered[mCurrentIndex]);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG, "saveInstanceState");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putBooleanArray(KEY_INDEX, mQuestionsAnswered);
    }

    private void updateQuestion() {
        AtomicInteger question = new AtomicInteger(mQuestionBank[mCurrentIndex].getTextResId());
        mQuestionTextView.setText(question.get());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        mQuestionsAnswered[mCurrentIndex] = true;
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);

        mTrueButton.setEnabled(!mQuestionsAnswered[mCurrentIndex]);
        mFalseButton.setEnabled(!mQuestionsAnswered[mCurrentIndex]);

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_answer;
        } else {
            mFalseButton.setEnabled(false);
            messageResId = R.string.incorrect_answer;
        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }
}