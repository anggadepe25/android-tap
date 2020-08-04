package com.angga.telkomselapprenticeprogam.activities.edit_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.angga.telkomselapprenticeprogam.R
import com.angga.telkomselapprenticeprogam.extensions.gone
import com.angga.telkomselapprenticeprogam.extensions.visible
import com.angga.telkomselapprenticeprogam.utilities.Constants
import kotlinx.android.synthetic.main.activity_edit_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class activity_edit_password : AppCompatActivity() {

    private val editPasswordViewModel : EditPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        observer()
        updatePassword()
    }

    private fun updatePassword() {
        btn_simpan_password.setOnClickListener {
            val token = Constants.getToken(this@activity_edit_password)
            val password = et_password_new.text.toString().trim()
            val passCOnfirm = et_password_konfirm.text.toString().trim()
            if (editPasswordViewModel.validate(password, passCOnfirm)){
                editPasswordViewModel.updatePassword(token, password)
            }
        }
    }

    private fun observer() = editPasswordViewModel.listenToState().observer(this, Observer { handleUiState(it) })

    private fun handleUiState(it: EditPasswordState?) {
        when(it){
            is EditPasswordState.Loading -> handleLoading(it.state)
            is EditPasswordState.ShowToast-> toast(it.message)
            is EditPasswordState.Success -> handleSuccess()
            is EditPasswordState.Reset -> handleReset()
            is EditPasswordState.Validate -> handleValidate(it)
        }
    }

    private fun handleValidate(validate: EditPasswordState.Validate) {
        validate.password?.let { setErrorPassword(it) }
        validate.confirmPassword?.let { setErrorConfirmPassword(it) }
    }

    private fun handleReset() {
        setErrorPassword(null)
        setErrorConfirmPassword(null)
    }

    private fun handleSuccess() {
        toast("berhasil update password")
        finish()
    }

    private fun handleLoading(state: Boolean) {
        if (state){
            loading.visible()
        }else{
            loading.gone()
        }
    }


    private fun setErrorPassword(err : String?){ til_password_new.error = err }
    private fun setErrorConfirmPassword(err : String?){ til_password_konfirm.error = err }
    private fun toast(message : String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
