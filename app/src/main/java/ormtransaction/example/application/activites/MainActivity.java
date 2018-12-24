package ormtransaction.example.application.activites;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ormtransaction.example.R;
import ormtransaction.example.application.core.MainApplication;
import ormtransaction.example.application.orm.ORMDBHelpers;

public class MainActivity extends AppCompatActivity {

    private Context context;
    View view;
    LinearLayout appbar;
    TextView result,processingTime;

    //task
    Handler task = new Handler();
    int task_delay = 1*45;
    Runnable task_runnable;
    int task_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MainTheme);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.main_activity);
        context = this;
        view = findViewById(android.R.id.content);

        changeStatusBarColor();
        setAppBarHeight();

        appbar = (LinearLayout)findViewById(R.id.appbar);
        if(getStatusBarHeight() !=0){

        }

        processingTime = (TextView)findViewById(R.id.processingTime);
        processingTime.setText(MainApplication.startTime + " / " + MainApplication.endTime);

        result = (TextView)findViewById(R.id.result);
        task_count = 0;
        try {

            task.postDelayed(new Runnable() {
                public void run() {
                    //do something
                    task_count++;
                    if(task_count == 12){

                        String getCount = ORMDBHelpers.getCount().toString();

                        if(getCount.length() == 1){
                        }else if(getCount.length() == 5){
                            getCount = getCount.substring(0, 2) + "K";
                        }else if(getCount.length() == 6){
                            getCount = getCount.substring(0, 3) + "K";
                        }if(getCount.length() == 7){
                            getCount = getCount.substring(0, 3) + "M";
                        }else {

                        }

                        result.setText(getCount);

                    }else{

                        Random r = new Random();
                        int i1 = r.nextInt(25 - 10) + 10;

                        result.setText(i1 + "");
                        task_runnable=this;
                        task.postDelayed(task_runnable, task_delay);
                    }
                }
            }, task_delay);


        }catch (Exception error) {


        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void setAppBarHeight() {
        LinearLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight() + dpToPx(48 + 56)));
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int dpToPx(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }


}
