package lt.sutemos.kodai.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import lt.sutemos.kodai.R;

/**
 * Permission request helper
 *
 * Created by Vilius Bilinkevicius on 19.1.11
 */

public class Permissions {

    public static void checkEXTWritePermission(Activity activity){
        if (activity == null) return;
        if (ContextCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(activity.getApplicationContext(), R.string.msg_no_ext_storage, Toast.LENGTH_LONG).show();
            } else  {
                ActivityCompat.requestPermissions(activity, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        Util.REQUEST_WRITE_PERMISSIONS);
            }
        }
    }

    public static void checkEXTReadPermission(Activity activity) {
        if (activity == null) return;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(activity.getApplicationContext(), R.string.msg_no_ext_storage, Toast.LENGTH_LONG).show();
            } else  {
                ActivityCompat.requestPermissions(activity, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        Util.REQUEST_READ_PERMISSIONS);
            }
        }
    }

}
