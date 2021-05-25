package kz.aknur.newchildcare.content.child

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_skip_or_add_child.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.child.add.ChildAddActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class SkipOrAddChildActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skip_or_add_child)
        skipChildBtn.onClick {
            letsAuthorize()
        }
        addChildBtn.onClick {
            letsAdd()
        }
    }

    private fun letsAdd() {
        finish()
        startActivity(intentFor<ChildAddActivity>().putExtra("ID", intent.getStringExtra("ID")))
    }

    private fun letsAuthorize() {
        Toast.makeText(this, "Вы зарегистрированы, пожалуйста авторизуйтесь!", Toast.LENGTH_SHORT).show()
        finish()
    }
}