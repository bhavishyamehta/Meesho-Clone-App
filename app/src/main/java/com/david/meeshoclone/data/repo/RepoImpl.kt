package com.david.meeshoclone.data.repo

import android.net.Uri
import com.david.meeshoclone.common.ADD_TO_CART
import com.david.meeshoclone.common.ADD_TO_FAV
import com.david.meeshoclone.common.PRODUCT_COLLECTION
import com.david.meeshoclone.common.ResultState
import com.david.meeshoclone.common.USER_COLLECTION
import com.david.meeshoclone.domain.models.BannerDataModels
import com.david.meeshoclone.domain.models.CartDataModels
import com.david.meeshoclone.domain.models.CategoryDataModels
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.domain.models.UserData
import com.david.meeshoclone.domain.models.UserDataParent
import com.david.meeshoclone.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : Repo {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result.user?.uid ?: ""
                        firebaseFirestore.collection(USER_COLLECTION)
                            .document(userId).set(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(ResultState.Success("User Registered Successfully and added to firestore"))
                                } else {
                                    if (it.exception != null) {
                                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                                    } else {
                                        trySend(ResultState.Error("Something went wrong"))
                                    }
                                }
                            }
                        trySend(ResultState.Success("User Registered Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        } else {
                            trySend(ResultState.Error("Something went wrong"))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Logged In Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        } else {
                            trySend(ResultState.Error("Something went wrong"))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun getUserById(uid: String): Flow<ResultState<UserDataParent>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(uid).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val data = it.result.toObject(UserData::class.java)
                if (data != null) {
                    val userDataParent = UserDataParent(it.result.id, userData = data)
                    trySend(ResultState.Success(userDataParent))
                } else {
                    trySend(element = ResultState.Error("User not found"))
                }

            } else {
                if (it.exception != null) {
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                } else {
                    trySend(ResultState.Error("Something went wrong"))
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId)
                .update(userDataParent.userData.toMap()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Data Updated Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        } else {
                            trySend(ResultState.Error("Something went wrong"))
                        }
                    }
                }
            awaitClose {
                close()
            }
        }

    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseStorage.reference.child("userProfileImage/${System.currentTimeMillis()}+ ${firebaseAuth.currentUser?.uid}")
            .putFile(uri ?: Uri.EMPTY).addOnCompleteListener {
                it.result.storage.downloadUrl.addOnSuccessListener { imageUri ->
                    trySend(ResultState.Success(imageUri.toString()))
                }
                if (it.exception != null) {
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }
            }
        awaitClose {
            close()
        }
    }

    override fun getCategoriesInLimit(): Flow<ResultState<List<CategoryDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("categories").limit(7).get()
                .addOnSuccessListener { querySnapshot ->
                    val categories = querySnapshot.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(CategoryDataModels::class.java)
                    }
                    trySend(ResultState.Success(categories))
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getProductsInLimit(): Flow<ResultState<List<ProductsDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("products").limit(7).get()
            .addOnSuccessListener { querySnapshot ->
                val products = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(ProductsDataModels::class.java)?.apply {
                        productId = documentSnapshot.id
                    }
                }
                trySend(ResultState.Success(products))
            }.addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").get().addOnSuccessListener {
            val categories = it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(CategoryDataModels::class.java)
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getAllProducts(): Flow<ResultState<List<ProductsDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("products").get()
            .addOnSuccessListener { querySnapshot ->
                val products = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(ProductsDataModels::class.java)?.apply {
                        productId = documentSnapshot.id
                    }
                }
                trySend(ResultState.Success(products))
            }.addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getProductById(productId: String): Flow<ResultState<ProductsDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val product = documentSnapshot.toObject(ProductsDataModels::class.java)
                    if (product != null) {
                        trySend(ResultState.Success(product))
                    } else {
                        trySend(ResultState.Error("Product not found"))
                    }
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.toString()))
                }
            awaitClose {
                close()
            }

        }

    override fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
                .collection("user_cart").add(cartDataModels)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Product added to Cart"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun addToFav(productsDataModels: ProductsDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
                .collection("user_fav").add(productsDataModels)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Product added to favourites"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getAllFav(): Flow<ResultState<List<ProductsDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
            .collection("user_fav").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                val fav = queryDocumentSnapshots.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(ProductsDataModels::class.java)
                }
                trySend(ResultState.Success(fav))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getCart(): Flow<ResultState<List<CartDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
            .collection("user_cart").get()
            .addOnSuccessListener {
                val cart = it.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(CartDataModels::class.java)?.apply {
                        cartId = documentSnapshot.id
                    }
                }
                trySend(ResultState.Success(cart))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
        awaitClose {
            close()
        }
    }

    override fun getCheckOut(productId: String): Flow<ResultState<ProductsDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("products").document(productId).get()
                .addOnSuccessListener {
                    val product = it.toObject(ProductsDataModels::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("banner").get().addOnSuccessListener {
            val banner = it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(BannerDataModels::class.java)
            }
            trySend(ResultState.Success(banner))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getSpecificCategoryItem(categoryName: String): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("products").whereEqualTo("category", categoryName).get()
                .addOnSuccessListener {
                    val products = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(ProductsDataModels::class.java)?.apply {
                            productId = documentSnapshot.id
                        }
                    }
                    trySend(ResultState.Success(products))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }

        }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
                .collection("user_fav").get()
                .addOnSuccessListener {
                    val fav = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(ProductsDataModels::class.java)
                    }
                    trySend(ResultState.Success(fav))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }
}