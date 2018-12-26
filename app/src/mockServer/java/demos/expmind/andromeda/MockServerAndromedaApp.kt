package demos.expmind.andromeda

import com.facebook.stetho.Stetho

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
    }
}