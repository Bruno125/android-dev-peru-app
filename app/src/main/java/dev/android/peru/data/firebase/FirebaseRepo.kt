package dev.android.peru.data.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseRepo {

    val db: FirebaseFirestore; get() = FirebaseFirestore.getInstance()

    fun FirebaseRepo.readDocument(path: String,
                                  onSuccess: (DocumentSnapshot?) -> Unit,
                                  onFailure: (Exception)->Unit = {},
                                  onCanceled: ()->Unit = {} ) {
        db.document(path).get()
                .addOnSuccessListener {
                    onSuccess(it)
                }
                .addOnCanceledListener {
                    onCanceled.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it)
                }
    }

}

inline fun <reified T> FirebaseRepo.parseDocument(path: String,
                                                  crossinline onSuccess: (T?) -> Unit,
                                                  crossinline onFailure: (Exception)->Unit = {},
                                                  crossinline onCanceled: ()->Unit = {} ) {
    readDocument(path,
            onSuccess = { onSuccess(it?.toObject(T::class.java)) },
            onFailure = { onFailure(it) },
            onCanceled = { onCanceled() } )
}