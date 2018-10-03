package demos.expmind.andromeda

/**
 * Global configuration parameters.
 * The potential security risk of having all API keys and IP addresses exposed in a static class
 * will be mitigated once we find a better approach, then this class will be removed.
 */
object Configurations {

    /*
     * When you prepare to release your app to your users, follow these steps again and create a new
     * OAuth 2.0 client ID for your production app. For production apps, use your own private key
     * to sign the production app's .apk file. For more information, see Signing your applications.
     * https://developer.android.com/studio/publish/app-signing.html?hl=es-419
     */
    val YOUTUBE_DEVELOPER_KEY = "AIzaSyDdKp0mZCBOmj1_3fkq5DepM2z1Qhob6aw"

    val MUSIXMATCH_DEVELOPER_KEY = "5b2ca4be09048f5ca57b85732f78765e"

    val MUSIXMATCH_URL = "https://api.musixmatch.com/ws/1.1/"
}