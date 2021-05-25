package kz.aknur.newchildcare.signUp.secondPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_second_stage.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.child.SkipOrAddChildActivity
import kz.aknur.newchildcare.signUp.secondPage.models.CityModel
import kz.aknur.newchildcare.signUp.secondPage.models.OrganizationsModel
import kz.aknur.newchildcare.signUp.secondPage.models.PersonalInformationRequest
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONException


class PersonalInformationActivity : AppCompatActivity() {

    companion object {
        const val TAG = "PersonalInformationActivity"
    }




    private lateinit var viewModel: PersonalInformationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_stage)
        lets()
    }

    private fun lets() {
            setupGender()
            viewModel = ViewModelProvider(this).get(PersonalInformationViewModel::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getCities()
            }
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getOrganizations()
             }


        isPregnantSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                weekCount.isEnabled = selectedItem == "Да"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

            observer()
        sendUserInfo.onClick { prepareUserInfo() }

    }

    private fun prepareUserInfo() {
        if (
            input_city.selectedItemPosition!=0
            && addressEt.text.isNotEmpty()
            && input_hospital.selectedItemPosition!=0
            && isPregnantSpinner.selectedItemPosition!=0
            && weekCount.text.isNotEmpty()
        ) {
            setLoading(true)
            var isPregnant = false
            if (isPregnantSpinner.selectedItemPosition == 1) {
                isPregnant = true
            }
            val user = PersonalInformationRequest(
                addressEt.text.toString(),
                input_city.selectedItemId.toInt(),
                intent.getStringExtra("EMAIL")!!,
                intent.getStringExtra("GENDER")!!,
                intent.getStringExtra("ID")!!.toInt(),
               isPregnant,
                intent.getStringExtra("NAME")!!,
                intent.getStringExtra("PHONE")!!,
               input_hospital.selectedItemId.toInt(),
                weekCount.text.toString().toInt()
                )
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.sendUserInfo(user)
           }
        } else {
            Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupGender() {
        if (intent.getStringExtra("GENDER") == "MALE") {
            val isPregnant: ArrayList<String> = ArrayList()
            isPregnant.add("Ваша супруга(партнер) беременна?")
            isPregnant.add("Да")
            isPregnant.add("Нет")
            val arrayAdapter =
                ArrayAdapter(this, R.layout.row, isPregnant)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            isPregnantSpinner.adapter = arrayAdapter

            weekCount.hint = "Укажите срок? (В неделях)"
        }
    }

    @SuppressLint("LongLogTag")
    private fun observer() {

        viewModel.listOfCity.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, it.toString())
                addCities(it)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })
        viewModel.listOfOrg.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, it.toString())
                addOrg(it)
                setLoading(false)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })
        viewModel.isError.observe(this, Observer {
            errorDialog(getString(R.string.error_unknown_body))
        })

        viewModel.leaveReg.observe(this, Observer {
            if (it) {
                finish()
                startActivity(intentFor<SkipOrAddChildActivity>().putExtra("ID", intent.getStringExtra("ID")))
            } else {
                errorDialog(getString(R.string.error_unknown_body))
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

    private fun addCities(it: List<CityModel>) {
        setSpinnerCityContent(it)
    }

    private fun addOrg(it: List<OrganizationsModel>) {
        setSpinnerOrgContent(it)
    }

    @SuppressLint("LongLogTag")
    private fun setSpinnerCityContent(
            cities: List<CityModel>
    ) {
        val answerList: ArrayList<String> = ArrayList()
        answerList.add("Ваш город")
        for (i in cities.indices) {
            try {
                answerList.add(cities[i].name.toString())
            } catch (e: JSONException) {
                Log.e(TAG, e.message.toString())
            }
        }
        setSpinnerCityAdapter(answerList)
    }

    private fun setSpinnerCityAdapter(
                cities: List<String>
        ) {
            val arrayAdapter =
                ArrayAdapter(this, R.layout.row, cities)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            input_city.adapter = arrayAdapter

        }


    @SuppressLint("LongLogTag")
    private fun setSpinnerOrgContent(
            cities: List<OrganizationsModel>
    ) {
        val answerList: ArrayList<String> = ArrayList()
        answerList.add("Ваша поликлиника")
        for (i in cities.indices) {
            try {
                answerList.add(cities[i].name.toString())
            } catch (e: JSONException) {
                Log.e(TAG, e.message.toString())
            }
        }
        setSpinnerOrgAdapter(answerList)
    }

    private fun setSpinnerOrgAdapter(
            cities: List<String>
    ) {
        val arrayAdapter =
                ArrayAdapter(this, R.layout.row, cities)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        input_hospital.adapter = arrayAdapter
    }

    private fun setLoading(b: Boolean) {
        if (b) secondLv.visibility = View.VISIBLE else secondLv.visibility = View.GONE
    }



}