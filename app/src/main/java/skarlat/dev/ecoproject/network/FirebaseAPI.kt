
package skarlat.dev.ecoproject.network

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import skarlat.dev.ecoproject.Const
import timber.log.Timber

class FirebaseAPI {

    companion object {
        private const val usersCountFieldName = "countUsers"


        fun getUsersDocument(callback: (QuerySnapshot) -> Unit) {
            /*Firebase
                    .firestore
                    .collection(Const.ECO_TIPS_COLLECTION)
                    .get(Source.SERVER)
                    .addOnSuccessListener(callback)
                    .addOnCompleteListener {
                        Timber.tag(Const.TAG).v("FirebaseAPI.getUsersDocument successful: ${it.isSuccessful}")
                    }*/
        }

        @JvmStatic
        fun getUsersCount(callback: (Long) -> Unit) {
            getUsersDocument {
                callback(extractCountOfUsers(it))
            }
        }

        private fun extractCountOfUsers(querySnapshot: QuerySnapshot): Long {
            /*val usersCountData = querySnapshot.documents[0].get(usersCountFieldName)
            if (usersCountData is Long) {
                return usersCountData
            }*/
            return 0
        }

        fun increaseCountOfUsers() {
            /*getUsersDocument { querySnapshot ->
                val newCount = extractCountOfUsers(querySnapshot).inc()
                querySnapshot
                        .documents[0]
                        .reference
                        .update(hashMapOf(usersCountFieldName to newCount as Any))
                        .addOnCompleteListener { Timber.tag(Const.TAG).v("FirebaseAPI.increaseCountOfUsers successful: ${it.isSuccessful}") }
            }*/
        }
    }
}