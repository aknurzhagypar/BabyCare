package kz.aknur.newchildcare.content.child.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_child_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.child.add.models.ChildAddRequest
import kz.aknur.newchildcare.content.child.list.ChildListActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick


class ChildAddActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ChildAddActivity"
    }




    private lateinit var viewModel: ChildAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_add)
        lets()
    }

    private fun lets() {
        viewModel = ViewModelProvider(this).get(ChildAddViewModel::class.java)
        observer()
        sendChildInfo.onClick { prepareChildInfo() }
    }

    private fun prepareChildInfo() {
        if (
            chMaleSpinner.selectedItemPosition!=0
            && childNameEt.text.isNotEmpty()
            && chDate.text.isNotEmpty()
            && chD.text.isNotEmpty()
        ) {
            setLoading(true)
            var gender = "FEMALE"
            if (chMaleSpinner.selectedItemPosition == 1) {
                gender = "MALE"
            }
            val child = ChildAddRequest(
                chDate.text.toString(),
                chD.text.toString(),
                gender,
                0,
                childNameEt.text.toString(),
                0
            )
            Log.d(TAG, child.toString())
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.addNewChild(child)
            }
        } else {
            Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_LONG).show()
        }
    }


    @SuppressLint("LongLogTag")
    private fun observer() {
        viewModel.isError.observe(this, Observer {
            errorDialog(getString(R.string.error_unknown_body))
        })

        viewModel.isSuccess.observe(this, Observer {
            if(it){
                finish()
                startActivity(intentFor<ChildListActivity>())
            }
        })

    }

    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                dialog.dismiss()
                lets()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }?.show()
    }


    private fun setLoading(b: Boolean) {
        if (b) childAddLv.visibility = View.VISIBLE else childAddLv.visibility = View.GONE
    }


}