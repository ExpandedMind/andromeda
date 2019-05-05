package demos.expmind.andromeda

import com.facebook.stetho.Stetho
import okhttp3.mockwebserver.MockWebServer

/**
 * Custom application that starts a mock http server meant to handle any network traffic app generates
 */
class MockServerAndromedaApp : AndromedaApp() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
        initializeMockWebServer()
    }

    private fun initializeMockWebServer() {
        val mockServer = MockWebServer()
        mockServer.setDispatcher(AndromedaDispatcher())
        val t = Thread {
            mockServer.start(8080)
        }.start()
    }
}