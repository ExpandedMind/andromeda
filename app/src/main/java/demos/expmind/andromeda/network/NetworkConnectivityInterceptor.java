package demos.expmind.andromeda.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectivityInterceptor implements Interceptor {

    private final ConnectivityManager connectivityManager;

    @Inject
    public NetworkConnectivityInterceptor(ConnectivityManager networkConnectivityManager) {
        this.connectivityManager = networkConnectivityManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return chain.proceed(chain.request());
        }
        throw new NetworkUnavailableException();
    }
}
