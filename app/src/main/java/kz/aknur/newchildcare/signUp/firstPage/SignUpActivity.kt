package kz.aknur.newchildcare.signUp.firstPage

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_sign_up.*
import kz.aknur.newchildcare.signUp.firstPage.models.SignUpRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.signUp.secondPage.PersonalInformationActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SignUpActivity"
    }

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        lets()
    }

    private fun lets() {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        sign_up_button.onClick { signUp() }
        singIn.onClick {
            finish()
        }
        observer()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun signUp() {
        val name = inputName.text.toString()
        val phone = inputphone.text.toString()
        val email = inputEmail_su.text.toString()
        val password = inputPassword_su.text.toString()
        val confPassword = confirmPassword.text.toString()
        if (name.isNotEmpty()
            && phone.isNotEmpty()
            && email.isNotEmpty()
            && password.isNotEmpty()
            && confPassword.isNotEmpty()
        ){
            setLoading(true)
            var gender = "FEMALE"
            if (maleFemaleSpinner.selectedItemPosition == 1) { gender = "MALE" }
            if (password == confPassword){
                val signUpRequest =
                    SignUpRequest(gender, email,name, password, phone)
                CoroutineScope(Dispatchers.IO).launch {
                    setLoading(true)
                    viewModel.signUp(signUpRequest)
                }
            } else {
                setLoading(false)
                Toast.makeText(this, "Введенные вами пароли не совпадают!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show()
        }


    }


    private fun observer() {
        viewModel.isError.observe(this, Observer {
            errorDialog(it)
        })

        viewModel.isSuccess.observe(this, Observer {
            setLoading(false)
                var gender = "FEMALE"
                if (maleFemaleSpinner.selectedItemPosition == 1) {gender = "MALE" }
                finish()
                startActivity(intentFor<PersonalInformationActivity>()
                    .putExtra("GENDER", gender)
                    .putExtra("EMAIL", inputEmail_su.text.toString())
                    .putExtra("ID", it)
                    .putExtra("NAME", inputName.text.toString())
                    .putExtra("PHONE", inputphone.text.toString())
                )
        })
    }

    private fun unSuccessFulDialog() {
        alert {
            title = getString(R.string.error_su_wrong_data_title)
            message = getString(R.string.error_su_wrong_data_msg)
            isCancelable = true
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }.show()
    }

    private fun setLoading(b: Boolean) {
        if (b) signUpLv.visibility = View.VISIBLE else signUpLv.visibility = View.GONE
    }

    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                dialog.dismiss()
                setLoading(false)
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }.show()
    }

}