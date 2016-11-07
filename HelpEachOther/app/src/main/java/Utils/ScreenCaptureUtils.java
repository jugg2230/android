package Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Dream on 2016/10/18.
 */
public class ScreenCaptureUtils {

    public static class ShotHandler extends Handler {

        ProgressDialog progressDialog;
        WeakReference<Activity> weakReference;

        public ShotHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    progressDialog = ProgressDialog.show(weakReference.get(), "", ((String) msg.obj));
                    break;
                case 1:
                    progressDialog.setMessage(((String) msg.obj));
                    break;
                case 2:
                    progressDialog.dismiss();
                    break;
            }
        }
    }

    public static void sendMessageForShot(ShotHandler shotHandler, int type, String s) {
        Message message = Message.obtain();
        message.obj = s;
        message.arg1 = type;
        shotHandler.sendMessage(message);
    }
}
