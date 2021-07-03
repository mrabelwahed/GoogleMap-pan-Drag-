package com.ramadan.challenge.feature.transaction

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ramadan.challenge.R
import com.ramadan.challenge.core.common.AppConst.COUNTRY_KENYA_CODE
import com.ramadan.challenge.core.common.AppConst.COUNTRY_NIGERIA_CODE
import com.ramadan.challenge.core.common.AppConst.COUNTRY_TANZANIA_CODE
import com.ramadan.challenge.core.common.AppConst.COUNTRY_UGANDA_CODE
import com.ramadan.challenge.core.common.AppConst.KENYA
import com.ramadan.challenge.core.common.AppConst.NIGERIA
import com.ramadan.challenge.core.common.AppConst.TANZANIA
import com.ramadan.challenge.core.common.AppConst.UGANDA
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.error.Failure
import com.ramadan.challenge.databinding.ActivityTransactionBinding
import com.ramadan.challenge.domain.entity.Rates
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private val transactionViewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRatesInfo()
        observeCurrentRatesState()
        handleBinaryActions()
        handleCountryCodePicker()
        handleSendMoneyButton()

        binding.ccpLoadFullNumber.registerCarrierNumberEditText(binding.editTextLoadCarrierNumber)
    }

    private fun handleSendMoneyButton() {
        binding.sendBtn.setOnClickListener {
            val firstName = binding.firstNameFiled.text.toString()
            val lastName = binding.lastNameFiled.text.toString()
            val amountBinary = binding.amountNameFiled.text.toString()
            val phoneNumber = binding.editTextLoadCarrierNumber.text.toString()
            if (transactionViewModel.isValidToSendMoney(
                    firstName,
                    lastName,
                    phoneNumber,
                    amountBinary,
                    transactionViewModel.selectedCountryNameCode
                )
            ) {
                MaterialAlertDialogBuilder(this)
                    .setMessage(getString(R.string.send_alert))
                    .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                        dialog.dismiss()
                        clearAllFields()
                        Snackbar.make(
                            binding.root,
                            getString(R.string.send_money_notification),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.send_money_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearAllFields() {
        binding.firstNameFiled.text?.clear()
        binding.lastNameFiled.text?.clear()
        binding.editTextLoadCarrierNumber.text?.clear()
        binding.amountNameFiled.text?.clear()
        binding.recipientAmountValueTV.text = TransactionViewModel.DEFAULT
    }

    private fun handleCountryCodePicker() {
        binding.ccpLoadFullNumber.setOnCountryChangeListener {
            transactionViewModel.selectedCountryNameCode = binding.ccpLoadFullNumber.selectedCountryNameCode
            when (transactionViewModel.selectedCountryNameCode) {
                COUNTRY_KENYA_CODE -> binding.exchangeRateTV.text = getString(R.string.exchange_rate_info).plus(transactionViewModel.rates?.kES)
                COUNTRY_TANZANIA_CODE -> binding.exchangeRateTV.text = getString(R.string.exchange_rate_info).plus(transactionViewModel.rates?.tZS)
                COUNTRY_NIGERIA_CODE -> binding.exchangeRateTV.text = getString(R.string.exchange_rate_info).plus(transactionViewModel.rates?.nGN)
                COUNTRY_UGANDA_CODE -> binding.exchangeRateTV.text = getString(R.string.exchange_rate_info).plus(transactionViewModel.rates?.uGX)
            }
            calcRecipientMoney()
        }
    }


    private fun handleBinaryActions() {
        binding.zeroBtn.setOnClickListener {
            binding.amountNameFiled.setText(binding.amountNameFiled.text.toString().plus(ZERO))
            calcRecipientMoney()
        }
        binding.oneBtn.setOnClickListener {
            binding.amountNameFiled.setText(binding.amountNameFiled.text.toString().plus( ONE))
            calcRecipientMoney()
        }
    }

    private fun getRatesInfo() {
        transactionViewModel.resetRatesState()
        handleLoading(true)
        transactionViewModel.getCurrentRates()
    }

    private fun observeCurrentRatesState() {
        transactionViewModel.ratesDataState.observe(
            this,
            Observer {
                when (it) {
                    is DataState.Success -> {
                        handleLoading(false)
                        setRatesData(it.data)
                    }
                    is DataState.Error -> {
                        handleLoading(false)
                        if (it.error is Failure.NetworkConnection)
                            displayError(getString(R.string.no_internet_connection))
                        else
                            displayError(getString(R.string.general_error))
                    }
                }
            }
        )
    }

    private fun setRatesData(data: Rates) {
        transactionViewModel.rates = data
    }

    private fun handleLoading(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        message?.let { Snackbar.make(binding.mainView, it, Snackbar.LENGTH_SHORT).show() }
    }

    private fun calcRecipientMoney() {
        val binaryString = binding.amountNameFiled.text.toString()
        val decimal = transactionViewModel.convertBinaryToDecimal(binaryString)
        transactionViewModel.selectedCountryNameCode?.let {
            when (transactionViewModel.selectedCountryNameCode) {
                COUNTRY_KENYA_CODE -> {
                    binding.recipientAmountValueTV.text = transactionViewModel.calcReceivingMoneyBinary(transactionViewModel.rates?.kES, decimal).plus(KENYA)
                }
                COUNTRY_TANZANIA_CODE -> {
                    binding.recipientAmountValueTV.text = transactionViewModel.calcReceivingMoneyBinary(transactionViewModel.rates?.tZS, decimal).plus(TANZANIA)
                }
                COUNTRY_NIGERIA_CODE -> {
                    binding.recipientAmountValueTV.text = transactionViewModel.calcReceivingMoneyBinary(transactionViewModel.rates?.nGN, decimal).plus(NIGERIA)
                }
                COUNTRY_UGANDA_CODE -> {
                    binding.recipientAmountValueTV.text =
                        transactionViewModel.calcReceivingMoneyBinary(transactionViewModel.rates?.uGX, decimal).plus(UGANDA)
                }
                else -> return
            }
        }
    }

    companion object {
        const val ZERO = "0"
        const val ONE = "1"
    }
}
