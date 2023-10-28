package com.example.zad1quizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

const val QUIZ_TAG = "MainActivity"
const val KEY_CURRENT_INDEX = "currentIndex"
const val KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer"
const val REQUEST_CODE_PROMPT = 0

class MainActivity : ComponentActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var promptButton: Button
    private lateinit var questionTextView: TextView
    private var answerWasShown: Boolean? = null

    private var questionCounter: Int = -1
    private var currentIndex: Int = 0

    private val questions = listOf(
        Question(R.string.q_activity, true),
        Question(R.string.q_find_resources, false),
        Question(R.string.q_listener, true),
        Question(R.string.q_resources, true),
        Question(R.string.q_version, false)
    )

    private val arrayOfPoints = IntArray(questions.size) {0}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onCreate")
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX)
        }

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        promptButton = findViewById(R.id.prompt_button)

        trueButton.setOnClickListener {
            checkAnswerCorrectness(true)
        }
        falseButton.setOnClickListener {
            checkAnswerCorrectness(false)
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questions.size
            answerWasShown = false
            setNextQuestion()
        }
        promptButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PromptActivity::class.java)
            val correctAnswer = questions[currentIndex].isTrueAnswer()
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer)
            startActivityForResult(intent, REQUEST_CODE_PROMPT)

        }
        setNextQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) { return }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) { return }
            answerWasShown = data.getBooleanExtra(KEY_EXTRA_ANSWER_SHOWN, false)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState")
        outState.putInt(KEY_CURRENT_INDEX, currentIndex)
    }

    private fun setNextQuestion() {
        questionCounter++
        if (questionCounter == questions.size) {
            Toast.makeText(this, getString(R.string.result_info, arrayOfPoints.sum()), Toast.LENGTH_LONG).show()
            questionCounter = 0
            arrayOfPoints.fill(0)
        }
        questionTextView.setText(questions[currentIndex].questionId)
    }

    private fun checkAnswerCorrectness(userAnswer: Boolean) {
        var correctAnswer = questions[currentIndex].isTrueAnswer()
        var resultMessageId: Int = 0

        if (answerWasShown == true) {
            resultMessageId = R.string.answer_was_shown;
            arrayOfPoints[currentIndex] = 0
        } else {

            if (userAnswer == correctAnswer) {
                arrayOfPoints[currentIndex] = 1
                resultMessageId = R.string.correct_answer
            } else {
                arrayOfPoints[currentIndex] = 0
                resultMessageId = R.string.incorrect_answer
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show()
    }

}