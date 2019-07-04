package com.codebulls.MeritQ

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.codebulls.MeritQ.helpers.helperAPIGood
import kotlinx.android.synthetic.main.activity_new.*


class NewActivity : AppCompatActivity() {

    private var genderChoice = "Male"
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        radGroup_Gender.setOnCheckedChangeListener { _, checkedId ->
            if (R.id.rad_Male == checkedId) {
                genderChoice = "Male"
            }
            if (R.id.rad_Female == checkedId) {
                genderChoice = "Female"
            }
        }

        checkFields()

        context = this

        btnRequestAdd.setOnClickListener {
            checkFields()
            helperAPIGood(context)
                .execute(txtCreditAmount.text.toString(), txtCreditDuration.text.toString(),
                spnCreditPurpose.selectedItem.toString(), genderChoice, spnHouseType.selectedItem.toString(), spnSavingsType.selectedItem.toString(),
                spnCheckingsType.selectedItem.toString(), txtCustomerAge.text.toString(), spnCustomerJobType.selectedItem.toString(), txtCustomerEMail.text.toString())
        }

    }

    private fun checkFields() {

        var isSuccess = false

        if (txtCustomerName.text.toString().isEmpty()) {
            txtCustomerName.error = getString(R.string.error_field_required)
            isSuccess = false
        }
        if (txtCustomerAge.text.toString().isEmpty()) {
            txtCustomerAge.error = getString(R.string.error_field_required)
            isSuccess = false
        }
        if (txtCustomerSalary.text.toString().isEmpty()) {
            txtCustomerSalary.error = getString(R.string.error_field_required)
            isSuccess = false
        }
        if (txtCreditAmount.text.toString().isEmpty()) {
            txtCreditAmount.error = getString(R.string.error_field_required)
            isSuccess = false
        }
        if (txtCreditDuration.text.toString().isEmpty()) {
            txtCreditDuration.error = getString(R.string.error_field_required)
            isSuccess = false
        }
        if (txtCustomerEMail.text.toString().isEmpty()) {
            txtCustomerEMail.error = getString(R.string.error_field_required)
            isSuccess = false
        }
        if (!isSuccess) {
            return
        }
    }
}
