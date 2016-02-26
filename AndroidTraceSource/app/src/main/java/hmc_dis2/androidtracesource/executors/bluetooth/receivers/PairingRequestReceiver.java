package hmc_dis2.androidtracesource.executors.bluetooth.receivers;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.Semaphore;

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
        if(flag){
            try {
                this.semafore.acquire();
                Log.i("PairingRequestRec::", "Func IsRequestReceived, semafore was acquired");
            } catch (InterruptedException e) {
                Log.i("PairingRequestRec::", "Func IsRequestReceived, can not acquire semafore");
            }
        }
        else{
            this.semafore.release();
            Log.i("PairingRequestRec::", "Func IsRequestReceived, semafore was released");
        }
        this.isRequestReceived = flag;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BluetoothDevice.ACTION_PAIRING_REQUEST :
                Log.i("PairingRequestRec::", "Function onReceive, ACTION_PAIRING_REQUEST was received");
                this.isRequestReceived = true;
                this.semafore.release();
                break;
        }
    }
}
