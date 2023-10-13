package com.example.zad1quizz

class Question(var questionId: Int, private var trueAnswer: Boolean) {

    fun isTrueAnswer(): Boolean {
        return this.trueAnswer
    }

}