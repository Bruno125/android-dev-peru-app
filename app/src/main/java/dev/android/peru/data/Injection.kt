package dev.android.peru.data

object Injection {

    val questionnaireRepo by lazy { FirebaseQuestionnaireRepo() }

}