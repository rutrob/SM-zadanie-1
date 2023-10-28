package com.example.zad1quizz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val KEY_EXTRA_ANSWER_SHOWN = "pb.edu.pl.wi.quiz.answerShown"
class PromptActivity : AppCompatActivity() {

    private var correctAnswer: Boolean? = null
    private lateinit var answerTextView: TextView
    private lateinit var showCorrectAnswerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prompt)
        answerTextView = findViewById(R.id.answer_text_view)
        correctAnswer = intent.getBooleanExtra(KEY_EXTRA_ANSWER, true)
        showCorrectAnswerButton = findViewById(R.id.show_correct_answer)

        showCorrectAnswerButton.setOnClickListener {
            var answer = if (correctAnswer == true) R.string.button_true else R.string.button_false
            answerTextView.setText(answer)
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(answerWasShown: Boolean) {
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown)
        setResult(RESULT_OK, resultIntent)
    }
}