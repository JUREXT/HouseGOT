package com.czech.housegot.data.viewModels


import androidx.lifecycle.SavedStateHandle
import com.czech.housegot.models.Houses
import com.czech.housegot.repositories.CharacterRepository
import com.czech.housegot.repositories.DetailsRepository
import com.czech.housegot.ui.screens.DetailsViewModel
import com.czech.housegot.utils.DataState
import com.czech.housegot.utils.DetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HouseDetailViewModelTest {

    @Mock
    private lateinit var detailsRepository: DetailsRepository

    @Mock
    private lateinit var characterRepository: CharacterRepository

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
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

        val detailsViewModel = DetailsViewModel(savedStateHandle, detailsRepository, characterRepository)

        val response = DataState.data(data = detail)

        val houseId = 1

        val channel = Channel<DataState<Houses>>()

        val flow = channel.consumeAsFlow()

        Mockito.`when`(detailsRepository.getHouseDetails(houseId)).thenReturn(flow)

        val job = launch {
            channel.send(response)
        }

        detailsViewModel.getDetails(1)

        Assert.assertEquals(true, detailsViewModel.detailsState.value == DetailsState.Success(detail))
        Assert.assertEquals(false, detailsViewModel.detailsState.value == DetailsState.Loading)
        Assert.assertEquals(false, detailsViewModel.detailsState.value == DetailsState.Error(""))
        job.cancel()
        
    }

}