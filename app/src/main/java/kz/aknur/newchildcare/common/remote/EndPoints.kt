package kz.aknur.newchildcare.common.remote

object EndPoints {
    const val SIGN_IN = "auth/login" //Авторизация
    const val SIGN_UP = "auth/signup" //Регистрация
    const val GET_CITIES = "cities" //Список городов
    const val GET_ORGANIZATIONS = "organizations" //Список больниц
    const val POST_P_INFO = "users/dto"
    const val GET_MAIN_CATEGORIES = "categories/main"
    const val ADD_NEW_CHILD = "children"
    const val GET_PROFILE_INFO = "profile"
    const val GET_SURVEY_GROUPS = "groups"
    const val START_SURVEY = "survey/start/{month}/{groupId}"
    const val SEND_ANSWER ="survey/{surveyId}"
    const val GET_ARTICLES_BY_ID = "articles/catId/{categoryId}"
    const val GET_ARTICLES_BY_ARTICLE_ID = "articles/{articleId}"
    const val GET_FAVOURITE_ARTICLES = "users/fav"
    const val GET_MY_CHILD_LIST = "children/my"
    const val GET_CARD_CATEGORIES = "categories/type/CARD"
    const val ADD_NEW_FAV_ARTICLE = "users/fav2"
    const val DELETE_FAV_ARTICLE = "users/fav/{articleId}"
    const val GET_EVENTS = "events/my"

}