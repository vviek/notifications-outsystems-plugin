package org.notification;


import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;


public class NotificationPlugin extends CordovaPlugin {


    @Override
    public boolean execute(String action, JSONArray args,
                           final CallbackContext callbackContext) {

        if (action.equalsIgnoreCase("register")) {

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                return;
                            }
                            JSONArray jsonArrayObj=new JSONArray();
                            jsonArrayObj.put(task.getResult().getToken());
                            callbackContext.success(jsonArrayObj);
                         
                        }
                    });


            return true;
        }


        return false;
    }
}