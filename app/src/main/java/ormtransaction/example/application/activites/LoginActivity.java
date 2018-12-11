package ormtransaction.example.application.activites;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ormtransaction.example.R;
import ormtransaction.example.application.core.MainApplication;
import ormtransaction.example.application.orm.ORMDBHelpers;

public class LoginActivity extends AppCompatActivity {

    LinearLayout parentLayout;
    TextView feed;

    //task Settings
    boolean stop = false;
    boolean start = false;
    Handler taskEverySecondHandler = new Handler();
    int taskEverySecondDelay = 1*1000; //1 second=1000 millisecond, 2*1000=2seconds
    Runnable taskEverySecondRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.MainTheme);

        getWindow().getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.login_activity);
        parentLayout = (LinearLayout)findViewById(R.id.content);
        feed = (TextView)findViewById(R.id.feed);

        changeStatusBarColor();

        doAsyncTask myAsyncTask = new doAsyncTask();
        myAsyncTask.execute();

    }

    @Override
    public void onResume() {
        //start handler as view become visible
        taskEverySecondHandler.postDelayed( taskEverySecondRunnable = new Runnable() {
            public void run() {
                //do something
                runTaskEverySecond();
                taskEverySecondHandler.postDelayed(taskEverySecondRunnable, taskEverySecondDelay);
            }
        }, taskEverySecondDelay);

        super.onResume();
    }

    @Override
    public void onPause() {
        taskEverySecondHandler.removeCallbacks(taskEverySecondRunnable); //stop handler when view not visible
        super.onPause();
    }

    private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorTrans));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    public void runTaskEverySecond(){
        if(!stop) {
            feed.setText("Synced " + MainApplication.RowProcessedCount + " of " + MainApplication.addRowsAmount);
        }
    }

    private void goToMainActivity(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public Boolean beginORMTransaction() {

        boolean isCompleted = false;
        List<String> buildPassedData = new ArrayList<String>();
        int addRowsAmount = MainApplication.addRowsAmount;
        int wordLength = 25;

        //region foreach
        for (int i1 = 0; i1 < addRowsAmount; i1++) {

            //https://www.baeldung.com/java-random-string
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            buildPassedData.add(generatedString);

            if(addRowsAmount == i1){

                isCompleted = true;
                break;

            }

        }
        //endregion foreach

        //region pass data to Helper
        boolean getResult = ORMDBHelpers.setRowData(buildPassedData);
        if(getResult){

            isCompleted = true;

        }
        //endregion pass data to Helper

        return isCompleted;

    }

    public class doAsyncTask extends AsyncTask<Boolean, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Boolean... params) {

            Boolean result = false;

            if(beginORMTransaction()){
                result = true;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {


            if(result){

                goToMainActivity();

            }else {

                goToMainActivity();

            }

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Boolean... values) {


        }
    }

}
