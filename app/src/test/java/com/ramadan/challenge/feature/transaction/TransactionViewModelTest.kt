package com.ramadan.challenge.feature.transaction

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ramadan.challenge.core.common.AppConst
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.core.error.Failure
import com.ramadan.challenge.domain.entity.Rates
import com.ramadan.challenge.domain.inteactor.GetExchangeRates
import com.ramadan.challenge.fake.FakeData
import com.ramadan.challenge.feature.transaction.TransactionViewModel.Companion.DEFAULT
import com.ramadan.test_utils.RxSchedulerRule
import com.ramadan.test_utils.mock
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TransactionViewModelTest {
    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var transactionViewModel: TransactionViewModel
    @Mock
    lateinit var getExchangeRates: GetExchangeRates

    @Mock
    private var currentRatesStateObserver: Observer<DataState<Rates>>  = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        transactionViewModel = TransactionViewModel(getExchangeRates).apply {
                this.ratesDataState.observeForever(currentRatesStateObserver)
            }
    }

    @Test
    fun `TransactionViewModel is ready for test`() {
        assertNotNull(transactionViewModel)
    }

    @Test
    fun `should success when getCurrentRates() returns proper data`() {
        // Given
        val data = FakeData.givenRatesData()
        `when`(getExchangeRates.execute()).thenReturn(Single.just(data))
        // When
        getExchangeRates.execute()
        transactionViewModel.changeRatesDataState(data)
        // Then
        verify(currentRatesStateObserver).onChanged(DataState.Success(data))
    }

    @Test(expected = RuntimeException::class)
    fun `should receive error state when getCurrentRates() throws Exception `() {
        // given
        `when`(getExchangeRates.execute()).thenThrow(RuntimeException())
        // when
        getExchangeRates.execute()
        // Then
        verify(currentRatesStateObserver).onChanged(DataState.Error(Failure.Unknown))
    }

   @Test
   fun `convertBinaryToDecimal should return 0 if the binary string is empty`(){
       val binaryString = ""
       val result = transactionViewModel.convertBinaryToDecimal(binaryString)
       assertEquals(0,result)
   }

    @Test
    fun `convertBinaryToDecimal should return correct decimal value if the binary string is not empty`(){
        val binaryString = "010"
        val result = transactionViewModel.convertBinaryToDecimal(binaryString)
        assertEquals(2,result)
    }

    @Test
    fun `isValidToSendMoney should return false if the first name is empty`(){
        val firstName = ""
        val lastName = "Ramadan"
        val phoneNumber= "123456789"
        val amountBinary = "010"
        val selectedCountryCode = AppConst.COUNTRY_KENYA_CODE

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(false,result)
    }

    @Test
    fun `isValidToSendMoney should return false if the last name is empty`(){
        val firstName = "Mahmoud"
        val lastName = ""
        val phoneNumber= "123456789"
        val amountBinary = "010"
        val selectedCountryCode = AppConst.COUNTRY_KENYA_CODE

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(false,result)
    }

    @Test
    fun `isValidToSendMoney should return false if the phoneNumber  is empty`(){
        val firstName = "Mahmoud"
        val lastName = "Ramadan"
        val phoneNumber= ""
        val amountBinary = "010"
        val selectedCountryCode = AppConst.COUNTRY_KENYA_CODE

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(false,result)
    }

    @Test
    fun `isValidToSendMoney should return false if the amountBinary is empty`(){
        val firstName = "Mahmoud"
        val lastName = "Ramadan"
        val phoneNumber= "123456789"
        val amountBinary = ""
        val selectedCountryCode = AppConst.COUNTRY_KENYA_CODE

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(false,result)
    }

    @Test
    fun `isValidToSendMoney should return false if the selectedCountryCode is null`(){
        val firstName = "Mahmoud"
        val lastName = "Ramadan"
        val phoneNumber= "123456789"
        val amountBinary = "010"
        val selectedCountryCode = null

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(false,result)
    }

    @Test
    fun `isValidToSendMoney should return false selectedCountryCode is correct and phone number length is not valid`(){
        val firstName = "Mahmoud"
        val lastName = "Ramadan"
        val phoneNumber= "1234567"
        val amountBinary = "010"
        val selectedCountryCode = AppConst.COUNTRY_KENYA_CODE

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(false,result)
    }

    @Test
    fun `isValidToSendMoney should return true selectedCountryCode is correct and phone number length is valid`(){
        val firstName = "Mahmoud"
        val lastName = "Ramadan"
        val phoneNumber= "123456789"
        val amountBinary = "010"
        val selectedCountryCode = AppConst.COUNTRY_KENYA_CODE

        val result = transactionViewModel.isValidToSendMoney(firstName,lastName,phoneNumber,amountBinary,selectedCountryCode)
        assertEquals(true,result)
    }
  @Test
   fun `calcReceivingMoneyBinary should return zeros string if current rate is null`(){
     val currentRate = null
      val result = transactionViewModel.calcReceivingMoneyBinary(currentRate , 2 )
      assertEquals(DEFAULT,result)
  }

    @Test
    fun `calcReceivingMoneyBinary should return valid binary value if current rate is not null`(){
        val currentRate = 410.0
        val decimal =  2
        val total = currentRate * decimal
        val expectedValue =  Integer.toBinaryString(total.toInt())
        val result = transactionViewModel.calcReceivingMoneyBinary(currentRate , 2 )
        assertEquals(expectedValue,result)
    }
}
