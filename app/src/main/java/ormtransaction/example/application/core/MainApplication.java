package ormtransaction.example.application.core;

import android.app.Application;
import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ormtransaction.example.application.orm.ORMAppDatabase;
import ormtransaction.example.application.orm.ORMDBClass;

public class MainApplication extends Application {

    private MainApplication instance;

    public static int RowProcessedCount = 0;
    public static int addRowsAmount = 5000;

    public static boolean useTransactions = false;
    public static String  startTime;
    public static String  endTime;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

    @Override
    public void onCreate() {
        super.onCreate();


        DatabaseConfig appDatabaseConfig = new DatabaseConfig.Builder(ORMAppDatabase.class)
                .addModelClasses(
                        ORMDBClass.class
                )
                .disableMigrationsChecking()
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabaseConfig)
                .build());


    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

}