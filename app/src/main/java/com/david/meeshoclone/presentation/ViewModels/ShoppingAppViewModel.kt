package com.david.meeshoclone.presentation.ViewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.meeshoclone.common.HomeScreenState
import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.domain.models.CartDataModels
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.domain.models.UserData
import com.david.meeshoclone.domain.models.UserDataParent
import com.david.meeshoclone.domain.usecase.AddToCartUseCase
import com.david.meeshoclone.domain.usecase.AddToFavUseCase
import com.david.meeshoclone.domain.usecase.CreateUserUseCase
import com.david.meeshoclone.domain.usecase.GetAllCategoryUseCase
import com.david.meeshoclone.domain.usecase.GetAllFavUseCase
import com.david.meeshoclone.domain.usecase.GetAllProductUseCase
import com.david.meeshoclone.domain.usecase.GetAllSuggestedProductUseCases
import com.david.meeshoclone.domain.usecase.GetBannerUseCase
import com.david.meeshoclone.domain.usecase.GetCartUseCase
import com.david.meeshoclone.domain.usecase.GetCategoryInLimit
import com.david.meeshoclone.domain.usecase.GetCheckOutUseCase
import com.david.meeshoclone.domain.usecase.GetProductById
import com.david.meeshoclone.domain.usecase.GetProductsInLimitUseCase
import com.david.meeshoclone.domain.usecase.GetSpecificCategoryItems
import com.david.meeshoclone.domain.usecase.GetUserUseCase
import com.david.meeshoclone.domain.usecase.LoginUserUseCase
import com.david.meeshoclone.domain.usecase.UpdateUserdataUseCase
import com.david.meeshoclone.domain.usecase.UserProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUserCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserDataUseCase: UpdateUserdataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getCategoriesInLimitUseCase: GetCategoryInLimit,
    private val getProductsInLimitUseCase: GetProductsInLimitUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getProductById: GetProductById,
    private val addToFavUseCase: AddToFavUseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getCheckOutUseCase: GetCheckOutUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getSpecificCategoryItems: GetSpecificCategoryItems,
    private val getAllSuggestedProductUseCases: GetAllSuggestedProductUseCases,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val getCartUseCase: GetCartUseCase,

    ) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginUpScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpdateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _uploadUserProfileImageState = MutableStateFlow(UploadUserProfileImageState())
    val uploadUserProfileImageState = _uploadUserProfileImageState.asStateFlow()

    private val _addToCartScreenState = MutableStateFlow(AddToCartScreenState())
    val addToCartScreenState = _addToCartScreenState.asStateFlow()

    private val _getProductById = MutableStateFlow(GetProductByIdState())
    val getProductByIdState = _getProductById.asStateFlow()

    private val _addToFav = MutableStateFlow(AddToFavState())
    val addToFav = _addToFav.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getCheckoutState = MutableStateFlow(GetCheckOutState())
    val getCheckoutState = _getCheckoutState.asStateFlow()

    private val _getSpecificCategoryItemsState = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategoryItemsState = _getSpecificCategoryItemsState.asStateFlow()

    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()


    fun getSpecificCategoryItems(categoryName: String) {
        viewModelScope.launch {
            getSpecificCategoryItems.getSpecificCategoryItems(categoryName).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                ErrorMsg = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = true,
                            )
                    }

                    is ResultState.Success -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                UserData = it.data
                            )
                    }
                }
            }
        }
    }

    fun getCheckOut(productId: String) {
        viewModelScope.launch {
            getCheckOutUseCase.getCheckOutUseCase(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase.getAllCategoriesUseCase().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getCart() {
        viewModelScope.launch {
            getCartUseCase.getCart().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductUseCase.getAllProducts().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllFav() {
        viewModelScope.launch {
            getAllFavUseCase.getAllFav().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun addToFav(productsDataModels: ProductsDataModels) {
        viewModelScope.launch {
            addToFavUseCase.addToFav(productsDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToFav.value = _addToFav.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToFav.value = _addToFav.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _addToFav.value = _addToFav.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductById.getProductById(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getProductById.value = _getProductById.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getProductById.value = _getProductById.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _getProductById.value = _getProductById.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun addToCart(CartDataModels: CartDataModels) {
        viewModelScope.launch {
            addToCartUseCase.addToCart(CartDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToCartScreenState.value = _addToCartScreenState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToCartScreenState.value = _addToCartScreenState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _addToCartScreenState.value = _addToCartScreenState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    init {
        loadHomeScreenData()
    }

    fun loadHomeScreenData() {
        viewModelScope.launch {
            combine(
                getCategoriesInLimitUseCase.getCategoryInLimited(),
                getProductsInLimitUseCase.getProductsInLimit(),
                getBannerUseCase.getBannerUseCase()

            ) { categoriesResult, productResult, bannerResult ->

                when {
                    categoriesResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = categoriesResult.message)
                    }

                    productResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = productResult.message)
                    }

                    bannerResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = bannerResult.message)
                    }

                    categoriesResult is ResultState.Success && productResult is ResultState.Success && bannerResult is ResultState.Success -> {
                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productResult.data,
                            banner = bannerResult.data
                        )
                    }

                    else -> {
                        HomeScreenState(isLoading = true)
                    }

                }
            }.collect { state ->
                _homeScreenState.value = state
            }
        }
    }

    fun uploadUserProfileImage(uri: Uri) {
        viewModelScope.launch {
            userProfileImageUseCase.userProfile(uri).collect {
                when (it) {
                    is ResultState.Error -> {
                        _uploadUserProfileImageState.value =
                            _uploadUserProfileImageState.value.copy(
                                isLoading = false,
                                ErrorMsg = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _uploadUserProfileImageState.value =
                            _uploadUserProfileImageState.value.copy(
                                isLoading = true,
                            )
                    }

                    is ResultState.Success -> {
                        _uploadUserProfileImageState.value =
                            _uploadUserProfileImageState.value.copy(
                                isLoading = false,
                                UserData = it.data
                            )
                    }
                }
            }
        }
    }

    fun updateUserData(userDataParent: UserDataParent) {
        viewModelScope.launch {
            updateUserDataUseCase.updateUserData(userDataParent).collect {
                when (it) {
                    is ResultState.Error -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun createUser(userData: UserData) {
        viewModelScope.launch {
            createUserUseCase.createUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun loginUser(userData: UserData) {
        viewModelScope.launch {
            loginUserUserCase.loginUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getUserById(uid: String) {
        viewModelScope.launch {
            getUserUseCase.getUserById(uid).collect {
                when (it) {
                    is ResultState.Error -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            ErrorMsg = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = true,
                        )
                    }

                    is ResultState.Success -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            UserData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllSuggestedProduct() {
        viewModelScope.launch {
            getAllSuggestedProductUseCases.getAllSuggestedProducts().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                ErrorMsg = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = true,
                            )
                    }

                    is ResultState.Success -> {
                        _getAllSuggestedProductsState.value =
                            _getAllSuggestedProductsState.value.copy(
                                isLoading = false,
                                UserData = it.data
                            )
                    }
                }
            }
        }
    }


}


data class ProfileScreenState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: UserDataParent? = null
)

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: String? = null
)

data class LoginUpScreenState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: String? = null
)

data class UpdateScreenState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: String? = null
)

data class UploadUserProfileImageState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: String? = null
)

data class AddToCartScreenState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: String? = null
)

data class GetProductByIdState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: ProductsDataModels? = null
)

data class AddToFavState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: String? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: List<ProductsDataModels?> = emptyList()
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: List<ProductsDataModels?> = emptyList()
)

data class GetCartState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: List<CartDataModels?> = emptyList()
)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: List<CategoryDataModels?> = emptyList()
)

data class GetCheckOutState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: ProductsDataModels? = null
)

data class GetSpecificCategoryItemsState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: List<ProductsDataModels?> = emptyList()
)

data class GetAllSuggestedProductsState(
    val isLoading: Boolean = false,
    val ErrorMsg: String? = null,
    val UserData: List<ProductsDataModels?> = emptyList()
)


