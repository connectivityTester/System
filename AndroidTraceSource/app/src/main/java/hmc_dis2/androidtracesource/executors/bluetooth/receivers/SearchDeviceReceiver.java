package hmc_dis2.androidtracesource.executors.bluetooth.receivers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.Semaphore;

import hmc_dis2.androidtracesource.executors.BluetoothCommandExecutor;


public class SearchDeviceReceiver extends BroadcastReceiver {

    private Semaphore semaphore;
    private BluetoothAdapter bluetoothAdapter;
    private String deviceNameToSearch = null;
    private BluetoothCommandExecutor executor;
    private String deviceAddressToSearch = null;

    public SearchDeviceReceiver(Semaphore semaphore, BluetoothCommandExecutor executor){
        this.semaphore = semaphore;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.executor = executor;
    }

    public void setDeviceToSearchByName(String name){
        this.deviceNameToSearch = name;
        this.deviceAddressToSearch = null;
        Log.i("SearchDeviceReceiver::", "setDeviceToSearchByName, device \"" + this.deviceNameToSearch + "\" will be searching" );
        try {
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDeviceToSearchByAddress(String address){
        this.deviceNameToSearch = null;
        this.deviceAddressToSearch = address;
        Log.i("SearchDeviceReceiver::", "setDeviceToSearch, device \"" + this.deviceAddressToSearch + "\" will be searching" );
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
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver, found device name: " + device.getAddress());
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver, device to search: " + this.deviceAddressToSearch);
                if(device.getAddress().equals(this.deviceAddressToSearch)){
                    this.executor.setFoundDevice(device);
                    this.bluetoothAdapter.cancelDiscovery();
                    this.semaphore.release();
                }
//                if(device.getName()!= null && this.deviceNameToSearch != null && this.deviceNameToSearch.contentEquals(device.getName())){
//                    this.executor.setFoundDevice(device);
//                    this.bluetoothAdapter.cancelDiscovery();
//                    this.semaphore.release();
//                }
//                else if(this.deviceAddressToSearch != null && this.deviceAddressToSearch.contentEquals(device.getAddress())){
//                    this.executor.setFoundDevice(device);
//                    this.bluetoothAdapter.cancelDiscovery();
//                    this.semaphore.release();
//                }
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_STARTED	:
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver was received action: " + BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                this.executor.setFoundDevice(null);
                break;
            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                Log.i("SearchDeviceReceiver::", "searchDeviceReceiver was received action: " + BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

                this.semaphore.release();
                break;
        }
    }
}
