package skarlat.dev.ecoproject.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.R
import skarlat.dev.ecoproject.utils.addDisposable
import skarlat.dev.ecoproject.data.SettingsManager
import skarlat.dev.ecoproject.data.User
import skarlat.dev.ecoproject.databinding.FragmentEditProfileBinding
import java.util.*
import java.util.concurrent.TimeUnit

class EditProfileFragment : Fragment() {


    private lateinit var binding: FragmentEditProfileBinding
    private var settingsManager: SettingsManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsManager = SettingsManager(requireActivity().getSharedPreferences(Const.ECO_TIPS_PREFERENCES, Context.MODE_PRIVATE))
        binding.buttonExit.setOnClickListener { closeFragment() }
        binding.buttonEditMaterial.setOnClickListener {
//            val newUser = User()
//            newUser.email = binding.userEmail.text.toString()
//            newUser.name = binding.userName.text.toString()
//            newUser.password = binding.userPassword.text.toString()
//            updateUser(newUser)
        }
    }

    override fun onStart() {
        super.onStart()
        initUser()
    }

    private fun initUser() {
        binding.userName.setText(settingsManager!!.userName)
//        binding.userEmail.setText(User.currentUser!!.email)
        binding.userPassword.setText(getString(R.string.mask_user_password))
    }

    private val firebaseRequestQueue: Queue<Completable> = ArrayDeque(3)
    private fun updateUser(newUser: User) {
//        val currentUser = User.currentUser
        /*val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (currentUser!!.email != newUser.email) {
            changeUserMail(firebaseUser, newUser.email)
        }
        if (currentUser.name != newUser.name) {
            changeUserName(firebaseUser, newUser.name)
        }
        if (newUser.password != context!!.getString(R.string.mask_user_password)) {
          */  //changePassword(firebaseUser, newUser.password)
//        }
//        launchRequests()
    }

    private fun launchRequests() {
        var completable: Completable = firebaseRequestQueue.poll() ?: return
        while (firebaseRequestQueue.size > 0) {
            val nextCompletable = firebaseRequestQueue.poll()?.delay(10, TimeUnit.SECONDS)
            if (nextCompletable != null) {
                completable = completable.andThen(nextCompletable)
            }
        }
        // TODO Handling firebase exceptions
        val disposable = completable.subscribe({
            showSuccessfulMessageInToast("Жизнь прекрасна!")
            closeFragment()
        }) { throwable: Throwable ->
            showSuccessfulMessageInToast("Возникла ошибка")
            if (throwable is FirebaseAuthRecentLoginRequiredException) {
                showSuccessfulMessageInToast("Зайдите в свой профиль заново")
            } else {
                showSuccessfulMessageInToast("Перезапустите приложение")
            }
            closeFragment()
            throwable.printStackTrace()
        }
        addDisposable(disposable)
    }

    private fun changePassword(firebaseUser: FirebaseUser?, newPassword: String?) {
//        firebaseRequestQueue.add(
//                RxFirebaseUser.updatePassword(firebaseUser!!, newPassword!!)
//                        .doOnError { error: Throwable? -> showSuccessfulMessageInToast("Error! Change password!") }
//                        .doOnComplete { showSuccessfulMessageInToast(getString(R.string.password_changed_success)) }
//        )
    }

    private fun changeUserName(firebaseUser: FirebaseUser?, newName: String?) {
//        firebaseRequestQueue.add(
//                RxFirebaseUser
//                        .updateProfile(firebaseUser!!, UserProfileChangeRequest.Builder().setDisplayName(newName).build())
//                        .doOnError { throwable: Throwable? -> showSuccessfulMessageInToast("Error! Change user name!") }
//                        .doOnComplete {
//                            settingsManager?.updateUserName(firebaseUser.displayName ?: "")
//                            showSuccessfulMessageInToast(getString(R.string.name_changed_success))
//                        }
//        )
    }

    private fun changeUserMail(firebaseUser: FirebaseUser?, newEmail: String?) {
//        firebaseRequestQueue.add(RxFirebaseUser
//                .updateEmail(firebaseUser!!, newEmail!!)
//                .doOnError { throwable: Throwable? -> showSuccessfulMessageInToast("Error!") }
//                .doOnComplete { showSuccessfulMessageInToast(getString(R.string.mail_changed_success)) })
    }

    private fun showSuccessfulMessageInToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun closeFragment() {
        settingsManager!!.updateUserName(FirebaseAuth.getInstance().currentUser!!.displayName!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

}