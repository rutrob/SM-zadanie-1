package com.example.zad1quizz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

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
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswerCorrectness(true)
        }
        falseButton.setOnClickListener {
            checkAnswerCorrectness(false)
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questions.size
            setNextQuestion()
        }
        setNextQuestion()
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

        if (userAnswer == correctAnswer) {
            arrayOfPoints[currentIndex] = 1
            resultMessageId = R.string.correct_answer
        } else {
            arrayOfPoints[currentIndex] = 0
            resultMessageId = R.string.incorrect_answer
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show()
    }

}