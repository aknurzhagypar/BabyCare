package kz.aknur.newchildcare.common.remote

import com.google.gson.JsonArray
import kz.aknur.newchildcare.content.calendar.model.EventsResponse
import kz.aknur.newchildcare.content.child.add.models.ChildAddRequest
import kz.aknur.newchildcare.content.child.list.ChildListResponse
import kz.aknur.newchildcare.content.home.articles.details.ArticleResponse
import kz.aknur.newchildcare.content.home.articles.list.AddNewFavArticleRequest
import kz.aknur.newchildcare.content.home.articles.list.ArticlesResponse
import kz.aknur.newchildcare.content.home.categories.models.MainCategoriesResponse
import kz.aknur.newchildcare.content.hospitals.models.HospitalsResponse
import kz.aknur.newchildcare.content.profile.models.CardCategoriesResponse
import kz.aknur.newchildcare.content.profile.models.ProfileResponse
import kz.aknur.newchildcare.content.surveys.models.SurveyGroupsResponse
import kz.aknur.newchildcare.content.surveys.models.SurveyStartResponse
import kz.aknur.newchildcare.signIn.models.AuthRequest
import kz.aknur.newchildcare.signIn.models.AuthResponse
import kz.aknur.newchildcare.signUp.firstPage.models.SignUpRequest
import kz.aknur.newchildcare.signUp.firstPage.models.SignUpResponse
import kz.aknur.newchildcare.signUp.secondPage.models.GetCitiesResponse
import kz.aknur.newchildcare.signUp.secondPage.models.GetOrganizationsResponse
import kz.aknur.newchildcare.signUp.secondPage.models.PersonalInformationRequest
import retrofit2.Response
import retrofit2.http.*


interface NetworkService {

    @POST(EndPoints.SIGN_IN)
    suspend fun signIn(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST(EndPoints.SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @GET(EndPoints.GET_CITIES)
    suspend fun getCities(
        @Header("Authorization") token: String
    ): Response<GetCitiesResponse>

    @GET(EndPoints.GET_ORGANIZATIONS)
    suspend fun getOrganizations(
        @Header("Authorization") token: String
    ): Response<GetOrganizationsResponse>

    @PUT(EndPoints.POST_P_INFO)
    suspend fun sendPersonalInfo(
        @Header("Authorization") token: String,
        @Body personalInformationRequest: PersonalInformationRequest
    ): Response<Any?>

    @GET(EndPoints.GET_MAIN_CATEGORIES)
    suspend fun getMainCategories(
        @Header("Authorization") token: String
    ): Response<MainCategoriesResponse>

    @POST(EndPoints.ADD_NEW_CHILD)
    suspend fun addNewChild(
        @Header("Authorization") token: String,
        @Body childAddRequest: ChildAddRequest
    ): Response<Any?>

    @GET(EndPoints.GET_PROFILE_INFO)
    suspend fun getProfileInfo(
        @Header("Authorization") token: String
    ): Response<ProfileResponse>

    @GET(EndPoints.GET_ORGANIZATIONS)
    suspend fun getHospitals(
        @Header("Authorization"
        ) token: String): Response<HospitalsResponse>

    @GET(EndPoints.GET_SURVEY_GROUPS)
    suspend fun getSurveyGroups(
        @Header("Authorization"
        ) token: String): Response<SurveyGroupsResponse>

    @GET(EndPoints.START_SURVEY)
    suspend fun startSurvey(
        @Path("month") month: Int,
        @Path("groupId") groupId: Int,
        @Header("Authorization"
        ) token: String): Response<SurveyStartResponse>

    @POST(EndPoints.SEND_ANSWER)
    suspend fun sendAnswer(
        @Path("surveyId") surveyId: Int,
        @Body answerList: JsonArray,
        @Header("Authorization"
        ) token: String): Response<Any?>



    @POST(EndPoints.ADD_NEW_FAV_ARTICLE)
    suspend fun addNewFavArticle(
        @Body addNewFavArticleRequest: AddNewFavArticleRequest,
        @Header("Authorization"
        ) token: String): Response<Any?>

    @GET(EndPoints.GET_ARTICLES_BY_ID)
    suspend fun getArticles(
        @Path("categoryId") categoryId: Int,
        @Header("Authorization") token: String
    ): Response<ArticlesResponse>

    @GET(EndPoints.GET_ARTICLES_BY_ARTICLE_ID)
    suspend fun getArticle(
        @Path("articleId") articleId: String,
        @Header("Authorization") token: String
    ): Response<ArticleResponse>

    @GET(EndPoints.GET_FAVOURITE_ARTICLES)
    suspend fun getFavouriteArticles(
        @Header("Authorization") token: String
    ): Response<ArticlesResponse>

    @GET(EndPoints.GET_MY_CHILD_LIST)
    suspend fun getMyChild(
        @Header("Authorization") token: String
    ): Response<ChildListResponse>

    @GET(EndPoints.GET_CARD_CATEGORIES)
    suspend fun getCardCategories(
        @Header("Authorization") token: String
    ): Response<CardCategoriesResponse>

    @DELETE(EndPoints.DELETE_FAV_ARTICLE)
    suspend fun removeFromFav(
        @Path("articleId") articleId: String,
        @Header("Authorization") token: String
    ): Response<Any>?

    @GET(EndPoints.GET_EVENTS)
    suspend fun getEvents(
        @Header("Authorization") token: String
    ): Response<EventsResponse>
}