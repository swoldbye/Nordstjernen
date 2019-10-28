//package com.example.skibslogapp.viewControl;
//
//import android.app.ProgressDialog;
//import android.widget.Toast;
//
//import com.example.skibslogapp.Model.DB.LogData_frag;
//import com.example.skibslogapp.Model.LogInstans;
//
//public class AddItem {
//
//    private LogData_frag log = new LogData_frag();
//
//    public void setLog(LogData_frag log) {
//        this.log = log;
//    }
//
//    public ProgressDialog load(LogData_frag log){
//        final ProgressDialog loading = ProgressDialog.show(log, "The Item is being added", "Please wait");
//        return loading;
//    }
//    public void response(String response, ProgressDialog loading){
//        loading.dismiss();
//        Toast.makeText(log,response,Toast.LENGTH_LONG).show();//might be a problem here
//
//    }
//
//    public AddItem(LogData_frag log){
//        this.setLog(log);
//    }
//}
