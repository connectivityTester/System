package connectivity.androiddevicesource.executors.bluetooth.receivers;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.Semaphore;

/**
 * Created by Администратор on 15.12.2015.
 */
public class PairingRequestReceiver extends BroadcastReceiver {

    private Semaphore semafore;
    private boolean isRequestReceived;

    public PairingRequestReceiver(Semaphore semaphore){
        this.isRequestReceived = false;
        this.semafore = semaphore;
    }

    public boolean getIsRequestReceived() {
        return this.isRequestReceived;
    }

    public void setIsRequestReceived(boolean flag)
    {
        this.isRequestReceived = flag;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BluetoothDevice.ACTION_PAIRING_REQUEST :
                Log.i("PairingRequestReceiver::", "Function onReceive, ACTION_PAIRING_REQUEST was not received");
                this.isRequestReceived = true;
                this.semafore.release();
                break;
        }
    }
}
