package kr.dont.iacservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.Gravity;
import android.widget.Toast;

public class RemoteService extends Service {

    private Messenger mMessenger;


    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (null == this.mMessenger) {
            synchronized (RemoteService.class) {
                if (null == this.mMessenger) {
                    this.mMessenger = new Messenger(new IncomingHandler());
                }
            }
        }

        return this.mMessenger.getBinder();
    }


    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            int what = message.what;

            showToast("IACService: Remote Service successfully invoked - (" + what + ")");

            Message replyMessage = Message.obtain(null, 2, 0, 0);
            try {
                Messenger replyTo = message.replyTo;
                replyTo.send(replyMessage);
            } catch (RemoteException ex) {
                showToast("IACService: Invocation Failed!!");
            }


            ////////
            // just test for send to client some messages

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message replyMessage2 = Message.obtain(null, 3, 0, 0);
            try {
                Messenger replyTo = message.replyTo;
                replyTo.send(replyMessage2);
            } catch (RemoteException ex) {
                showToast("IACService: Invocation Failed!!");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message replyMessage3 = Message.obtain(null, 4, 0, 0);
            try {
                Messenger replyTo = message.replyTo;
                replyTo.send(replyMessage3);
            } catch (RemoteException ex) {
                showToast("IACService: Invocation Failed!!");
            }
        }


        private void showToast(String text) {
            Toast toast = Toast.makeText(RemoteService.this.getApplicationContext(), text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }
}
