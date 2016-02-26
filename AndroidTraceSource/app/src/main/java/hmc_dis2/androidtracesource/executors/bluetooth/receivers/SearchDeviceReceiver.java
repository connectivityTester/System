package hmc_dis2.androidtracesource.executors.bluetooth.receivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.Semaphore;

import hmc_dis2.androidtracesource.executors.bluetooth.BluetoothCommandExecutor;


public class SearchDeviceReceiver extends BroadcastReceiver {

    private Semaphore semaphore;
    private BluetoothAdapter bluetoothAdapter;
    private String deviceToSearch = null;
    private BluetoothCommandExecutor executor;

    public SearchDeviceReceiver(Semaphore semaphore, BluetoothCommandExecutor executor){
        this.semaphore = semaphore;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.executor = executor;
    }

    public void setDeviceToSearch(String name){
        this.deviceToSearch = name;
        Log.i("SearchDeviceReceiver::", "setDeviceToSearch, device \"" + this.deviceToSearch + "\" will be searching" );
        try {
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case BluetoothDevice.ACTION_FOUND 				:
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver was received action: " + BluetoothDevice.ACTION_FOUND.toString());
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver, found device name: " + device.getName());
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver, device to search: " + this.deviceToSearch);

                if(device.getName()!= null && this.deviceToSearch.contentEquals(device.getName())){
                    this.executor.setFoundDevice(device);
                    this.bluetoothAdapter.cancelDiscovery();
                    this.semaphore.release();
                }
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED	:
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver was received action: " + BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED	:
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver was received action: " + BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                this.executor.setFoundDevice(null);
                this.semaphore.release();
                break;
        }
    }
}
