package com.czech.housegot.viewModels

import androidx.lifecycle.SavedStateHandle
import com.czech.housegot.models.Houses
import com.czech.housegot.repositories.character.CharacterRepository
import com.czech.housegot.repositories.detail.DetailsRepository
import com.czech.housegot.ui.screens.detail.DetailsViewModel
import com.czech.housegot.utils.states.DataState
import com.czech.housegot.utils.states.DetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HouseDetailViewModelTest {

    @Mock
    private lateinit var detailsRepository: DetailsRepository

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var detailsViewModel: DetailsViewModel

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)

        val savedStateHandle = SavedStateHandle()
        savedStateHandle["house_id"] = 1
        detailsViewModel = DetailsViewModel(savedStateHandle, detailsRepository, characterRepository, testCoroutineDispatcher)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetDetails() = runTest(UnconfinedTestDispatcher()) {
        val detail = Houses(
            id = 1,
            url = "",
            name = "",
            region = "",
            coatOfArms = "",
            titles = listOf(),
            words = "",
            seats = listOf(),
            currentLord = "",
            heir = "",
            overlord = "",
            founded = "",
            founder = "",
            diedOut = "",
            ancestralWeapons = listOf(),
            cadetBranches = listOf(),
            swornMembers = listOf(),
            page = 1
        )

        val response = DataState.data(data = detail)

        val houseId = 1

        val channel = Channel<DataState<Houses>>()

        val flow = channel.consumeAsFlow()

        `when`(detailsRepository.getHouseDetails(houseId)).thenReturn(flow)

        val job = launch {
            channel.send(response)
        }

        detailsViewModel.getDetails(houseId)

        Assert.assertEquals(true, detailsViewModel.detailsState.value == DetailsState.Success(detail))
        Assert.assertEquals(false, detailsViewModel.detailsState.value == DetailsState.Loading)
        Assert.assertEquals(false, detailsViewModel.detailsState.value == DetailsState.Error(""))
        job.cancel()
        
    }

}