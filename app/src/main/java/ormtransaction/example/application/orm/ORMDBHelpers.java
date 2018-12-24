package ormtransaction.example.application.orm;

import android.util.Log;
import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.query.Select;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ormtransaction.example.application.core.MainApplication;

public class ORMDBHelpers {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static Date date = new Date();

    //region CRUD DB
    public static Boolean setRowData(List<String> getPassedData) {

        String message = "";
        Boolean result = true;


            Log.e("setData()", "getPassedData().initializing()");

            //region if statement
            if((getPassedData == null || getPassedData.isEmpty())){

                Log.e("setData()", "getPassedData().empty()");

            }else{


                //region try db processing
                try {

                    if(MainApplication.useTransactions) {
                        ReActiveAndroid.getDatabase(ORMAppDatabase.class).beginTransaction();
                    }

                    int getPassedDataIndex = 0;
                    for(String item : getPassedData ) {

                        getPassedDataIndex++;

                        String key = "";
                        key = item;

                        if(!key.isEmpty()){

                            if(checkByKey(key)){

                                //update
                                ORMDBClass updateRowInORMDBClass = Select.from(ORMDBClass.class).where("key = ?", key).fetchSingle();
                                updateRowInORMDBClass.setKey(key);
                                updateRowInORMDBClass.setHash(key);

                                updateRowInORMDBClass.setUpdatedAt(dateFormat.format(date));
                                if(updateRowInORMDBClass.save() > 0){
                                    result = true;
                                    MainApplication.RowProcessedCount++;
                                }

                            }else{


                                //add
                                ORMDBClass createRowInORMDBClass = new ORMDBClass();
                                createRowInORMDBClass.setKey(key);
                                createRowInORMDBClass.setHash(key);

                                createRowInORMDBClass.setCreatedAt(dateFormat.format(date));
                                if(createRowInORMDBClass.save() > 0){
                                    result = true;
                                    MainApplication.RowProcessedCount++;
                                }

                            }

                        }else{

                            result = false;

                        }

                    }

                } catch (Exception error){
                    Log.e("setData()", "getPassedData().fatalError() " + error.toString());
                }
                //endregion try db processing

                if(MainApplication.useTransactions) {
                    ReActiveAndroid.getDatabase(ORMAppDatabase.class).getWritableDatabase().setTransactionSuccessful();
                    ReActiveAndroid.getDatabase(ORMAppDatabase.class).endTransaction();
                }

            }
            //endregion if statement

        Log.e("setData()", "getPassedData().result(" + result + ")");
        return result;

    }

    public static Boolean createRow(
            String key,
            String hash) {

        Boolean result = false;

        //region if statement
        if(!key.isEmpty()) {

            try {

                ORMDBClass createRowInORMDBClass = new ORMDBClass();
                createRowInORMDBClass.setKey(key);
                createRowInORMDBClass.setHash(hash);

                createRowInORMDBClass.setCreatedAt(dateFormat.format(date));
                if(createRowInORMDBClass.save() > 0){
                    result = true;
                }

            }catch (Exception error) {


            }

        }
        //endregion if statement

        return result;

    }

    public static Boolean updateRow(
            Long id,
            String key,
            String hash) {

        Boolean result = false;

        //region if statement
        if(!key.isEmpty()) {

            try {

                ORMDBClass updateRowInORMDBClass = Select.from(ORMDBClass.class).where("key = ?", key).fetchSingle();
                updateRowInORMDBClass.setKey(key);
                updateRowInORMDBClass.setHash(hash);

                updateRowInORMDBClass.setUpdatedAt(dateFormat.format(date));
                if(updateRowInORMDBClass.save() > 0){
                    result = true;
                }

            }catch (Exception error) {


            }

        }
        //endregion if statement

        return result;

    }

    public static Boolean checkByKey(String key) {

        Boolean result = false;

        try {

            Boolean found = Select.from(ORMDBClass.class).where("key = ?", key).count() == 1;
            if(found){
                result = true;
            }

        } catch (Exception error){



        }

        return result;

    }

    public static Boolean checkKey(Long id) {

        Boolean result = false;

        try {

            Boolean found = Select.from(ORMDBClass.class).where("id = ?", id).count() == 1;
            if(found){
                result = true;
            }

        } catch (Exception error){



        }

        return result;

    }

    public static Boolean checkIdViaForEach(Long id) {

        Boolean result = false;

        try {

            Boolean found = false;

            List<ORMDBClass> ORMDBClass = Select.from(ORMDBClass.class).fetch();
            for (ORMDBClass ORMDBClassItem : ORMDBClass) {

                if(ORMDBClassItem.getId() == id){

                    found = true;
                    break;

                }

            }

            if(found){
                result = true;
            }

        } catch (Exception error){



        }

        return result;

    }

    public static Integer getCount() {

        int result = 0;

        try {

            Integer found = Select.from(ORMDBClass.class).count();
            result = found;

        } catch (Exception error){



        }

        Log.e("getData()", "ORMDBClass().count(" + result + ")");
        return result;

    }

    //endregion CRUD DB


}



