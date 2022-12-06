package com.example.acronyms.viewmodel

import com.example.acronyms.data.AcronymMeaning
import com.example.acronyms.data.AcronymMeaningResponse
import com.example.acronyms.service.AcronymsServiceImpl
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AcronymsViewModelTest : TestCase() {

    @get:Rule
    val mockCoroutineRule = MockCoroutineRule()

    private lateinit var state: MutableMap<String, Any>

    @Mock
    private lateinit var service: AcronymsServiceImpl

    @InjectMocks
    private lateinit var viewModel: AcronymsViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        state = mutableMapOf()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Verify Initial State of View Model`() {
        viewModel = AcronymsViewModel(service)
        val currentState = viewModel.uiState.value
        assertFalse(currentState.showLoading)
        assertNull(currentState.acronymInput)
        assertNull(currentState.acronymMeaningList)
        assertFalse(currentState.isError)
    }


    @Test
    fun `When AcronymMeanings API response is Empty, State of View Model is Valid`() {
        viewModel = AcronymsViewModel(service)

        mockCoroutineRule.runBlockingTest {
            whenever(service.getAcronymMeanings("HMM")).thenReturn(Response.success(listOf()))
        }

        viewModel.getAcronymMeanings("HMM")
        val currentState = viewModel.uiState.value
        assertFalse(currentState.showLoading)
        assert(currentState.acronymInput == "HMM")
        assertTrue(currentState.acronymMeaningList?.isEmpty() == true)
        assertFalse(currentState.isError)
    }

    @Test
    fun `When AcronymMeanings API response is Valid but meanings list is null, State of View Model is Valid`() {
        viewModel = AcronymsViewModel(service)

        mockCoroutineRule.runBlockingTest {
            whenever(service.getAcronymMeanings("HMM")).thenReturn(Response.success(listOf(
                AcronymMeaningResponse("HMM", null)
            )))
        }

        viewModel.getAcronymMeanings("HMM")
        val currentState = viewModel.uiState.value
        assertFalse(currentState.showLoading)
        assert(currentState.acronymInput == "HMM")
        assertNull(currentState.acronymMeaningList)
        assertTrue(currentState.isError)
    }

    @Test
    fun `When AcronymMeanings API response is Valid but meanings list is empty, State of View Model is Valid`() {
        viewModel = AcronymsViewModel(service)

        mockCoroutineRule.runBlockingTest {
            whenever(service.getAcronymMeanings("HMM")).thenReturn(Response.success(listOf(
                AcronymMeaningResponse("HMM", arrayListOf())
            )))
        }

        viewModel.getAcronymMeanings("HMM")
        val currentState = viewModel.uiState.value
        assertFalse(currentState.showLoading)
        assert(currentState.acronymInput == "HMM")
        assertTrue(currentState.acronymMeaningList?.isEmpty() == true)
        assertFalse(currentState.isError)
    }

    @Test
    fun `When AcronymMeanings API response is Valid but meanings list is non-empty, State of View Model is Valid`() {
        viewModel = AcronymsViewModel(service)

        mockCoroutineRule.runBlockingTest {
            whenever(service.getAcronymMeanings("HMM")).thenReturn(Response.success(listOf(
                AcronymMeaningResponse("HMM", arrayListOf(AcronymMeaning("heavy meromyosin", 267)))
            )))
        }

        viewModel.getAcronymMeanings("HMM")
        val currentState = viewModel.uiState.value
        assertFalse(currentState.showLoading)
        assert(currentState.acronymInput == "HMM")
        assertTrue(currentState.acronymMeaningList?.isNotEmpty() == true)
        assertFalse(currentState.isError)
    }

    @Test
    fun `When AcronymMeanings API response has Error, State of View Model is Valid`() {
        viewModel = AcronymsViewModel(service)

        mockCoroutineRule.runBlockingTest {
            whenever(service.getAcronymMeanings("HMM")).thenReturn(Response.error(500, ResponseBody.create(null, "")))

            //thenReturn(Response.error(500, error("Mock Error")))
        }

        viewModel.getAcronymMeanings("HMM")
        val currentState = viewModel.uiState.value
        assertFalse(currentState.showLoading)
        assert(currentState.acronymInput == "HMM")
        assertNull(currentState.acronymMeaningList)
        assertTrue(currentState.isError)
    }

}