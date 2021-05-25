package kz.aknur.newchildcare.signIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import kz.aknur.newchildcare.signIn.models.AuthRequest
import kz.aknur.newchildcare.signUp.firstPage.SignUpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.FoundationActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class SignInActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SignInActivity"
    }

    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        lets()
    }

    private fun lets() {
        setLoading(true)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        forgotPasswordBtn.onClick { Toast.makeText(this@SignInActivity, "Функционал в разработке", Toast.LENGTH_SHORT).show() }
        sign_in_button.onClick { prepareLogin() }
        CreateAccount.onClick {
            startActivity(intentFor<SignUpActivity>())
        }
        observer()
        setLoading(false)
    }

    private fun setLoading(b: Boolean) {
        if (b) signInLv.visibility = View.VISIBLE else signInLv.visibility = View.GONE
    }

    private fun prepareLogin() {
        val login = inputEmail.text.toString()
        val password = inputPassword.text.toString()
        if (login.isNotEmpty() && password.isNotEmpty()){
            val tokenRequest =
                AuthRequest(
                    password,
                    login
                )
            setLoading(true)
            logIn(tokenRequest)
        } else Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show()

    }

    private fun logIn(tokenRequest: AuthRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.signIn(tokenRequest)
        }
    }

    private fun observer() {
        viewModel.isError.observe(this, Observer {
            errorDialog(getString(R.string.error_unknown_body))
        })

        viewModel.isSuccess.observe(this, Observer {
            if (!it) {
                unSuccessFulDialog()
            } else {
                finish()
                startActivity(intentFor<FoundationActivity>())
                Toast.makeText(this, "Авторизация прошла успешно!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun unSuccessFulDialog() {
        alert {
            title = getString(R.string.error_auth_wrong_data_title)
            message = getString(R.string.error_auth_wrong_data_msg)
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                setLoading(false)
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }.show()
    }

    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                setLoading(false)
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }.show()
    }

}