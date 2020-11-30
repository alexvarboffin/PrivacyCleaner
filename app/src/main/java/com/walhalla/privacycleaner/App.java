package com.walhalla.privacycleaner;

import androidx.multidex.MultiDexApplication;

//import com.google.android.gms.ads.MobileAds;
//import com.yandex.metrica.YandexMetrica;
//import com.yandex.metrica.YandexMetricaConfig;


public class App extends MultiDexApplication {

//    public static final String API_KEY = "73fcdd5d-44c7-4cbc-9e29-30421b8b4ec8";

    @Override
    public void onCreate() {
        super.onCreate();

//        MobileAds.initialize(this, getString(R.string.app_unit_id));
//
//        // Создание расширенной конфигурации библиотеки.
//        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(API_KEY).build();
//        // Инициализация AppMetrica SDK.
//        YandexMetrica.activate(getApplicationContext(), config);
//        // Отслеживание активности пользователей.
//        YandexMetrica.enableActivityAutoTracking(this);
    }

}
