package com.example.skibslogapp.viewControl;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.skibslogapp.Model.DB.LogData;
import com.example.skibslogapp.Model.LogInstans;

public class AddItem {

    public void setLog(LogData log) {
        this.log = log;
    }

    private LogData log;

    public ProgressDialog load(){
        final ProgressDialog loading = ProgressDialog.show(log,"The Item is being added","Please wait");
        return loading;
    }
    public void response(String response, ProgressDialog loading){
        loading.dismiss();
        Toast.makeText(log,response,Toast.LENGTH_LONG).show();//might be a problem here

    }

    public AddItem(LogData log){
        this.setLog(log);
    }
}
