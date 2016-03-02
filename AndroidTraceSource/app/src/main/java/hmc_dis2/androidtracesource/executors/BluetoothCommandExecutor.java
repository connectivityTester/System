package hmc_dis2.androidtracesource.executors;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;

import hmc_dis2.androidtracesource.commands.Command;
import hmc_dis2.androidtracesource.commands.CommandResult;
import hmc_dis2.androidtracesource.executors.bluetooth.receivers.BluetoothProfileManager;
import hmc_dis2.androidtracesource.executors.bluetooth.receivers.PairingRequestReceiver;
import hmc_dis2.androidtracesource.executors.bluetooth.receivers.SearchDeviceReceiver;
import hmc_dis2.androidtracesource.types.CommandResultTypes;
import hmc_dis2.androidtracesource.types.ProfileTypes;

public class BluetoothCommandExecutor implements iExecutor {

    private boolean isA2DPAvailable;
    private boolean isHFPAvailable;
    private BluetoothProfileManager bluetoothProfileManager;
    private BluetoothAdapter bluetoothAdapter;
    private SearchDeviceReceiver searchDeviceReceiver;
    private PairingRequestReceiver pairingRequestReceiver;
    private Activity activity;
    private volatile Semaphore searchSemaphore;
    private volatile Semaphore pairingRequestSemaphore;
    private volatile BluetoothDevice foundDevice;

    public BluetoothCommandExecutor(Activity activity) {
        Log.i("BluetoothCommandExec::", "Constructor started");
        this.activity = activity;
        this.foundDevice = null;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.searchSemaphore = new Semaphore(1);
        this.searchDeviceReceiver = new SearchDeviceReceiver(this.searchSemaphore, this);
        this.pairingRequestSemaphore = new Semaphore(1);
        this.pairingRequestReceiver = new PairingRequestReceiver(this.pairingRequestSemaphore);
        this.bluetoothProfileManager = new BluetoothProfileManager();
        this.isHFPAvailable = this.bluetoothAdapter.getProfileProxy(this.activity.getApplicationContext(), this.bluetoothProfileManager, BluetoothProfile.HEADSET);
        if (this.isHFPAvailable) {
            Log.i("BluetoothCommandExec::", "Constructor, BluetoothProfile.HEADSET was gotten");
        } else {
            Log.i("BluetoothCommandExec::", "Constructor, BluetoothProfile.HEADSET was not gotten");
        }
        this.isA2DPAvailable = this.bluetoothAdapter.getProfileProxy(this.activity.getApplicationContext(), this.bluetoothProfileManager, BluetoothProfile.A2DP);
        if (this.isA2DPAvailable) {
            Log.i("BluetoothCommandExec::", "Constructor, BluetoothProfile.A2DP was gotten");
        } else {
            Log.i("BluetoothCommandExec::", "Constructor, BluetoothProfile.A2DP was not gotten");
        }
        Log.i("BluetoothCommandExec::", "Constructor finished");
    }

    public CommandResult executeCommand(Command command) {
        Log.i("BluetoothCommandExec::", "Function EXECUTECOMMAND started");
        CommandResult result = null;
        Log.i("BluetoothCommandExec::", "Function EXECUTECOMMAND, command: " + command.toString());
        switch (command.getCommandType()) {
            case PAIR_BY_NAME:
                result = this.pairToTargetByName(command.getParameterValue("device"));
                break;
            case PAIR_BY_ADDRESS:
                result = this.pairToTargetByAddress(command.getParameterValue("device"));
                break;
            case CONFIRM_CONNECTION:
                result = this.confirmConnection(command.getParameterValue("confirm"));
                break;
            case BT_ON:
                result = this.turnOnBluetooth();
                break;
            case BT_OFF:
                result = this.turnOffBluetooth();
                break;
            case SEARCH_DEVICE:
                result = this.searchDeviceByName(command.getParameterValue("device"));
                break;
            case CONNECT_TARGET:
                result = this.connectTarget(command.getParameterValue("device"));
                break;
            case DISCONNECT_DEVICE:
                result = this.disconnectTarget(command.getParameterValue("device"));
                break;
            case UNPAIR_DEVICE:
                result = this.unpairDevice(command.getParameterValue("device"));
                break;
            case UNPAIP_ALL_DEVICES:
                result = this.unpairAllPairedDevices();
                break;
            case ACTIVATE_PROFILE:
                result = this.activateProfile(command.getParameterValue("profile"),command.getParameterValue("device"));
                break;
            case DEACTIVATE_PROFILE:
                result = this.deactiveProfile(command.getParameterValue("profile"));
                break;
            case UNKNOWN_COMMAND:
                result = new CommandResult(CommandResultTypes.NOK, "UNKNOWN_COMMAND was reseived");
                break;
        }
        Log.i("BluetoothCommandExec::", "Function executeCommand, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function executeCommand, Reason::" + result.getErrorReason());
        }
        result.setDeviceSourceId(command.getDeviceSourceId());
        return result;
    }




    //tested and work,
    // but can throw exception like "Can not connect socket to target!!!",
    // for current impl, it is correct and connection is made.
    private CommandResult connectTarget(String targetName) {
        Log.i("BluetoothCommandExec::", "Function connectTarget started");
        Log.i("BluetoothCommandExec::", "Function connectTarget, target to connect: " + targetName);

        CommandResult result = this.turnOnBluetooth();
        BluetoothDevice activeDeviceConnection = this.getActiveConnection();
        if (activeDeviceConnection != null) {
            Log.i("BluetoothCommandExec::", "Function connectTarget, this is already active connection exists!");
            result = new CommandResult(CommandResultTypes.NOK, "this is already active connection exists");
        } else {
            boolean deviceWasBoundBefore = false;
            for (BluetoothDevice boundDevice : this.bluetoothAdapter.getBondedDevices()) {
                Log.i("BluetoothCommandExec::", "Function connectTarget, bounded device: " + boundDevice.getName());
                if (boundDevice.getName().equals(targetName)) {
                    deviceWasBoundBefore = true;
                    BluetoothSocket socket = null;
                    try {
                        socket = boundDevice.createInsecureRfcommSocketToServiceRecord(UUID.randomUUID());
                    } catch (IOException e) {
                        result = new CommandResult(CommandResultTypes.NOK, null);
                        Log.i("BluetoothCommandExec::", "Function connectTarget, Can not create socket to target!!!");
                    }
                    try {
                        socket.connect();
                    } catch (IOException e) {
                        result = new CommandResult(CommandResultTypes.OK, null);
                        Log.i("BluetoothCommandExec::", "Function connectTarget, Can not connect socket to target!!!");
                    }
                    break;
                }
            }
            if(deviceWasBoundBefore == false){
                result = new CommandResult(CommandResultTypes.NOK, "Device was not paired yet");
            }
        }
        Log.i("BluetoothCommandExec::", "Function connectDevice, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function connectDevice, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult disconnectTarget(String deviceName) {
        Log.i("BluetoothCommandExec::", "Function disconnectDevice started");
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        BluetoothDevice activeDeviceConnection = this.getActiveConnection();
        if (activeDeviceConnection == null) {
            Log.i("BluetoothCommandExec::", "Function disconnectTarget, this is no device to disconnect!!");
            result = new CommandResult(CommandResultTypes.NOK, "this is no device to disconnect");
        } else if (activeDeviceConnection.getName().equals(deviceName)) {
            boolean hfpConnectionResult = this.bluetoothProfileManager.deactivateProfile(ProfileTypes.HFP, activeDeviceConnection);
            boolean a2dpConnectionResult = this.bluetoothProfileManager.deactivateProfile(ProfileTypes.A2DP, activeDeviceConnection);
            if (hfpConnectionResult && a2dpConnectionResult) {
                result = new CommandResult(CommandResultTypes.OK, null);
                Log.i("BluetoothCommandExec::", "Function connectTarget, Both profiles were disconnected!");
            } else if (hfpConnectionResult) {
                result = new CommandResult(CommandResultTypes.OK, null);
                Log.i("BluetoothCommandExec::", "Function connectTarget, Only HFP profiles was disconnected!");
            } else if (a2dpConnectionResult) {
                result = new CommandResult(CommandResultTypes.OK, null);
                Log.i("BluetoothCommandExec::", "Function connectTarget, Only A2DP profiles was disconnected!");
            } else {
                result = new CommandResult(CommandResultTypes.NOK, "No one profile was disconnected");
                Log.i("BluetoothCommandExec::", "Function connectTarget, No one profile was disconnected!!!!");
            }
        }
        Log.i("BluetoothCommandExec::", "Function disconnectDevice, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function disconnectDevice, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult pairToTargetByName(String targetName) {
        Log.i("BluetoothCommandExec::", "Function pairToTarget started");
        CommandResult result = this.searchDeviceByName(targetName);
        if (result.getType() == CommandResultTypes.OK ) {
            if(this.foundDevice != null) {
                this.pairingRequestReceiver.setIsRequestReceived(false);
                this.activity.registerReceiver(this.pairingRequestReceiver, new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST));
                if (!foundDevice.createBond()) {
                    Log.i("BluetoothCommandExec::", "Function pairToTarget, bond was not successful!");
                    result = new CommandResult(CommandResultTypes.NOK, "Bond was not successful");
                }
                else {
                    Log.i("BluetoothCommandExec::", "Function pairToTarget, bond was successful!!!");
                    result = new CommandResult(CommandResultTypes.OK, "Bond was created successful");
                }
            }
        }
        Log.i("BluetoothCommandExec::", "Function pairToTarget, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function pairToTarget, Reason::" + result.getErrorReason());
        }
        return result;
    }

    private CommandResult pairToTargetByAddress(String targetBTAddress) {
        Log.i("BluetoothCommandExec::", "Function pairToTarget started");
        CommandResult result = this.searchDeviceByAddress(targetBTAddress, true);
        Log.i("BluetoothCommandExec::", "Function pairToTarget, searchDeviceByAddress was finished with result::" + result.getType().toString());
        if (result.getType() == CommandResultTypes.OK ) {
            if(this.foundDevice != null) {
                this.pairingRequestReceiver.setIsRequestReceived(false);
                this.activity.registerReceiver(this.pairingRequestReceiver, new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST));
                if (!foundDevice.createBond()) {
                    Log.i("BluetoothCommandExec::", "Function pairToTarget, bond was not successful!");
                    result = new CommandResult(CommandResultTypes.NOK, "Bond was not successful");
                }
                else {
                    Log.i("BluetoothCommandExec::", "Function pairToTarget, bond was successful!!!");
                    result = new CommandResult(CommandResultTypes.OK, "Bond was created successful");
                }
            }
        }
        Log.i("BluetoothCommandExec::", "Function pairToTarget, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function pairToTarget, Reason::" + result.getErrorReason());
        }
        return result;
    }


    //tested and work
    private CommandResult confirmConnection(String confirm){
        Log.i("BluetoothCommandExec::", "Function confirmConnection was started");
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        if(this.foundDevice != null){
            if(this.pairingRequestReceiver.getIsRequestReceived()){
                Log.i("BluetoothCommandExec::", "Function confirmConnection, pairing request was not received yet");
                try {
                    this.pairingRequestSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("BluetoothCommandExec::", "Function confirmConnection, pairing request was received just now");
            }
            Log.i("BluetoothCommandExec::", "Function confirmConnection, pairing request has been already received");
            switch (confirm){
                case "true" :
                    this.foundDevice.setPairingConfirmation(true);
                    result = new CommandResult(CommandResultTypes.OK, "Confirmation was accepted");
                    break;
                case "false":
                    this.foundDevice.setPairingConfirmation(false);
                    result = new CommandResult(CommandResultTypes.OK, "Confirmation was rejected");
                    break;
                default     :
                    this.foundDevice.setPairingConfirmation(false);
                    result = new CommandResult(CommandResultTypes.NOK, "Unknown parameter was received (parameter: " + confirm + ")");
                    break;
            }
        }
        else{
            result = new CommandResult(CommandResultTypes.NOK, "Device was not found");
        }
        this.pairingRequestReceiver.setIsRequestReceived(false);
        this.activity.unregisterReceiver(this.pairingRequestReceiver);
        Log.i("BluetoothCommandExec::", "Function confirmConnection, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function confirmConnection, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult turnOnBluetooth() {
        Log.i("BluetoothCommandExec::", "Function turnOnBluetooth started");
        while (!this.bluetoothAdapter.isEnabled()) {
            this.bluetoothAdapter.enable();
        }
        this.bluetoothAdapter.cancelDiscovery();
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        Log.i("BluetoothCommandExec::", "Function turnOnBluetooth, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function turnOnBluetooth, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult turnOffBluetooth() {
        Log.i("BluetoothCommandExec::", "Function turnOffBluetooth started");
        while (this.bluetoothAdapter.isEnabled()) {
            this.bluetoothAdapter.disable();
        }
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        Log.i("BluetoothCommandExec::", "Function turnOffBluetooth, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function turnOffBluetooth, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult searchDeviceByAddress(String address, boolean needToRetry) {
        this.turnOnBluetooth();
        Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress started");
        Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, device to search: " + address);
        this.activity.registerReceiver(this.searchDeviceReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        this.activity.registerReceiver(this.searchDeviceReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        this.activity.registerReceiver(this.searchDeviceReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

        this.searchDeviceReceiver.setDeviceToSearchByAddress(address);
        this.bluetoothAdapter.cancelDiscovery();
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        if (!this.bluetoothAdapter.startDiscovery()) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, discovery was not started");
            if (!this.bluetoothAdapter.isEnabled()) {
                Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, trying to turn on bluetooth...");
                if (this.bluetoothAdapter.enable()) {
                    Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, bluetooth was turned on");
                    Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, starting discovery again...");
                    if (this.bluetoothAdapter.startDiscovery()) {
                        Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, discovery was started successfully");
                    } else {
                        Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, discovery was not started successfully");
                        result = new CommandResult(CommandResultTypes.NOK, "can not start discovery");
                    }
                }
            } else {
                Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, discovery was not enable bluetooth");
                result = new CommandResult(CommandResultTypes.NOK, "can not enable bluetooth");
            }
        }
        if (result.getType() == CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, waiting for device...");
            try {
                this.searchSemaphore.acquire();
            } catch (InterruptedException e) {
                Log.i("BluetoothCommandExec: ", "Problems with searchSemaphore: " + e.getLocalizedMessage());
            }
        }
        this.searchSemaphore.release();
        if ( this.foundDevice == null) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, device is not in range!!!");
            result = new CommandResult(CommandResultTypes.NOK, "Device is not in range");
            if(needToRetry) {
                result = this.searchDeviceByAddress(address, false);
            }
        }
        else{
            result = new CommandResult(CommandResultTypes.OK, "Device " + this.foundDevice.getName() + " was found");
        }
        Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, Reason::" + result.getErrorReason());
        }
        if(needToRetry) {
            this.activity.unregisterReceiver(this.searchDeviceReceiver);
        }
        Log.i("BluetoothCommandExec::", "Function searchDeviceByAddress, searchDeviceReceiver is unregistered");
        return result;
    }
    //tested and work
    private CommandResult searchDeviceByName(String deviceName) {
        this.turnOnBluetooth();
        Log.i("BluetoothCommandExec::", "Function searchDeviceByName started");
        Log.i("BluetoothCommandExec::", "Function searchDeviceByName, device to search: " + deviceName);
        this.activity.registerReceiver(this.searchDeviceReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        this.activity.registerReceiver(this.searchDeviceReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        this.activity.registerReceiver(this.searchDeviceReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));

        this.searchDeviceReceiver.setDeviceToSearchByName(deviceName);
        this.bluetoothAdapter.cancelDiscovery();
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        if (!this.bluetoothAdapter.startDiscovery()) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByName, discovery was not started");
            if (!this.bluetoothAdapter.isEnabled()) {
                Log.i("BluetoothCommandExec::", "Function searchDeviceByName, trying to turn on bluetooth...");
                if (this.bluetoothAdapter.enable()) {
                    Log.i("BluetoothCommandExec::", "Function searchDeviceByName, bluetooth was turned on");
                    Log.i("BluetoothCommandExec::", "Function searchDeviceByName, starting discovery again...");
                    if (this.bluetoothAdapter.startDiscovery()) {
                        Log.i("BluetoothCommandExec::", "Function searchDeviceByName, discovery was started successfully");
                    } else {
                        Log.i("BluetoothCommandExec::", "Function searchDeviceByName, discovery was not started successfully");
                        result = new CommandResult(CommandResultTypes.NOK, "can not start discovery");
                    }
                }
            } else {
                Log.i("BluetoothCommandExec::", "Function searchDeviceByName, discovery was not enable bluetooth");
                result = new CommandResult(CommandResultTypes.NOK, "can not enable bluetooth");
            }
        }
        if (result.getType() == CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByName, waiting for device...");
            try {
                this.searchSemaphore.acquire();
            } catch (InterruptedException e) {
                Log.i("BluetoothCommandExec: ", "Problems with searchSemaphore: " + e.getLocalizedMessage());
            }
        }
        this.searchSemaphore.release();
        if ( this.foundDevice == null ) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByName, device is not in range!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            result = new CommandResult(CommandResultTypes.NOK, "Device is not in range");
            result = this.searchDeviceByName(deviceName);
        }
        else{
            result = new CommandResult(CommandResultTypes.OK, "Device " + this.foundDevice.getName() + " was found");
        }
        Log.i("BluetoothCommandExec::", "Function searchDeviceByName, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function searchDeviceByName, Reason::" + result.getErrorReason());
        }
        this.activity.unregisterReceiver(this.searchDeviceReceiver);
        return result;
    }
    //tested and work
    private CommandResult unpairAllPairedDevices(){
        Log.i("BluetoothCommandExec::", "Function unpairAllPairedDevices started");

        CommandResult result = new CommandResult(CommandResultTypes.OK, "Devices were unpaired");
        for (BluetoothDevice bondDevice : this.bluetoothAdapter.getBondedDevices()) {
            try {
                Method method = bondDevice.getClass().getMethod("removeBond", (Class[]) null);
                method.invoke(bondDevice, (Object[]) null);
                break;
            } catch (Exception e) {
                Log.i("BluetoothCommandExec::", "problem with unpairing device::" + e.getLocalizedMessage());
            }
        }

        Log.i("BluetoothCommandExec::", "Function unpairAllPairedDevices, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function unpairAllPairedDevices, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult unpairDevice(String deviceName) {
        Log.i("BluetoothCommandExec::", "Function unpairDevice started");

        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        if (this.bluetoothAdapter.getBondedDevices().size() > 0) {
            boolean deviceWasPairedBefore = false;
            for (BluetoothDevice bondDevice : this.bluetoothAdapter.getBondedDevices()) {
                if (bondDevice.getName().equals(deviceName)) {
                    deviceWasPairedBefore = true;
                    try {
                        Method method = bondDevice.getClass().getMethod("removeBond", (Class[]) null);
                        method.invoke(bondDevice, (Object[]) null);
                        break;
                    } catch (Exception e) {
                        Log.i("BluetoothCommandExec::", "problem with unpairing device::" + e.getLocalizedMessage());
                        result = new CommandResult(CommandResultTypes.NOK, "problem with unpairing device");
                    }
                }
            }
            if (!deviceWasPairedBefore) {
                Log.i("BluetoothCommandExec::", "requested device was not paired before!");
                result = new CommandResult(CommandResultTypes.NOK, "requested device was not paired before");
            }
        } else {
            Log.i("BluetoothCommandExec::", "list of paired devices is empty!");
            result = new CommandResult(CommandResultTypes.NOK, "Phone does not have paired devices");
        }
        Log.i("BluetoothCommandExec::", "Function unpairDevice, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function unpairDevice, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult activateProfile(String profileName, String targetName) {
        Log.i("BluetoothCommandExec::", "Function activateProfile started");
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        ProfileTypes profileType = this.defineProfile(profileName);
        if (profileType != ProfileTypes.UNKNOWN_PROFILE) {
            BluetoothDevice activeDeviceConnection = this.getActiveConnection();
            if (activeDeviceConnection != null) {
                switch (profileType) {
                    case HFP:
                        if (this.isHFPAvailable) {
                            Log.i("BluetoothCommandExec::", "Function activateProfile, active connection with: " + activeDeviceConnection.getName());
                            if (this.bluetoothProfileManager.isProfileActive(profileType, activeDeviceConnection)) {
                                result = new CommandResult(CommandResultTypes.NOK, "HFP Profile is already active");
                                Log.i("BluetoothCommandExec::", "Function activateProfile, HFP profile is already active!");
                            } else {
                                if (!this.bluetoothProfileManager.activateProfile(profileType, activeDeviceConnection)) {
                                    Log.i("BluetoothCommandExec::", "Function activateProfile, HFP profile was not activated");
                                    result = new CommandResult(CommandResultTypes.NOK, "HFP profile was not activated");
                                } else {
                                    result = new CommandResult(CommandResultTypes.NOK, "HFP profile was activated");
                                    Log.i("BluetoothCommandExec::", "Function activateProfile, HFP profile was activated successfully!");
                                }
                            }
                        } else {
                            Log.i("BluetoothCommandExec::", "Function activateProfile, HFP profile was not gotten");
                            result = new CommandResult(CommandResultTypes.NOK, "HFP profile was not gotten");
                        }
                        break;
                    case A2DP:
                        if (this.isA2DPAvailable) {
                            Log.i("BluetoothCommandExec::", "Function activateProfile, active connection with: " + activeDeviceConnection.getName());
                            if (this.bluetoothProfileManager.isProfileActive(profileType, activeDeviceConnection)) {
                                result = new CommandResult(CommandResultTypes.NOK, "A2DP Profile is already active");
                                Log.i("BluetoothCommandExec::", "Function activateProfile, A2DP profile is already active");
                            } else {
                                if (!this.bluetoothProfileManager.activateProfile(profileType, activeDeviceConnection)) {
                                    Log.i("BluetoothCommandExec::", "Function activateProfile, A2DP profile was not activated");
                                    result = new CommandResult(CommandResultTypes.NOK, "A2DP profile was not activated");
                                } else {
                                    result = new CommandResult(CommandResultTypes.OK, "A2DP profile was activated");
                                    Log.i("BluetoothCommandExec::", "Function activateProfile, A2DP profile was activated");
                                }
                            }
                        } else {
                            Log.i("BluetoothCommandExec::", "Function activateProfile, A2DP profile was not gotten");
                            result = new CommandResult(CommandResultTypes.NOK, "A2DP profile was not gotten");
                        }
                        break;
                }
            } else {
                Log.i("BluetoothCommandExec::", "Function activateProfile, requested target was not paired!");
                result = new CommandResult(CommandResultTypes.NOK, "requested target was not paired");
            }
        } else {
            Log.i("BluetoothCommandExec::", "Function activateProfile, Unknown profile was not requested");
            result = new CommandResult(CommandResultTypes.NOK, "Unknown profile was not requested");
        }
        Log.i("BluetoothCommandExec::", "Function activateProfile, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function activateProfile, Reason::" + result.getErrorReason());
        }
        return result;
    }
    //tested and work
    private CommandResult deactiveProfile(String profile) {
        Log.i("BluetoothCommandExec::", "Function deactiveProfile started");
        CommandResult result = new CommandResult(CommandResultTypes.OK, null);
        ProfileTypes profileType = this.defineProfile(profile);
        BluetoothDevice activeDeviceConnection = getActiveConnection();
        if (activeDeviceConnection != null) {
            if (profileType != ProfileTypes.UNKNOWN_PROFILE) {
                switch (profileType) {
                    case HFP:
                        if (this.isHFPAvailable) {
                            if (!this.bluetoothProfileManager.isProfileActive(profileType, activeDeviceConnection)) {
                                Log.i("BluetoothCommandExec::", "Function activateProfile, HFP profile has already been deactivated!!!");
                                result = new CommandResult(CommandResultTypes.NOK, "HFP profile has already been deactivated");
                            } else {
                                if (this.bluetoothProfileManager.deactivateProfile(profileType, activeDeviceConnection)) {
                                    result = new CommandResult(CommandResultTypes.OK, "HFP profile was deactivated");
                                    Log.i("BluetoothCommandExec::", "Function deactiveProfile, HFP profile was deactivated.");
                                } else {
                                    result = new CommandResult(CommandResultTypes.NOK, "HFP profile was not activated");
                                    Log.i("BluetoothCommandExec::", "Function deactiveProfile, HFP profile was not activated!!");
                                }
                            }
                        } else {
                            result = new CommandResult(CommandResultTypes.NOK, "HFP profile is unavailable");
                            Log.i("BluetoothCommandExec::", "Function deactiveProfile, HFP profile is unavailable!!");
                        }
                        break;
                    case A2DP:
                        if (this.isA2DPAvailable) {
                            if (!this.bluetoothProfileManager.isProfileActive(profileType, activeDeviceConnection)) {
                                Log.i("BluetoothCommandExec::", "Function activateProfile, A2DP profile has already been deactivated!!!");
                                result = new CommandResult(CommandResultTypes.NOK, "A2DP profile has already been deactivated");
                            } else {
                                if (this.bluetoothProfileManager.deactivateProfile(profileType, activeDeviceConnection)) {
                                    result = new CommandResult(CommandResultTypes.OK, "A2DP profile was deactivated");
                                    Log.i("BluetoothCommandExec::", "Function deactiveProfile, A2DP profile was deactivated");
                                } else {
                                    result = new CommandResult(CommandResultTypes.NOK, "A2DP profile was not deactivated");
                                    Log.i("BluetoothCommandExec::", "Function deactiveProfile, A2DP profile was not deactivated");
                                }
                            }
                        } else {
                            result = new CommandResult(CommandResultTypes.NOK, "A2DP profile is unavailable");
                            Log.i("BluetoothCommandExec::", "Function deactiveProfile, A2DP profile is unavailable!!");
                        }
                        break;
                }
            } else {
                result = new CommandResult(CommandResultTypes.NOK, "Unknown profile was requested");
                Log.i("BluetoothCommandExec::", "Function deactiveProfile, Unknown profile was requested!!");
            }
        } else {
            result = new CommandResult(CommandResultTypes.NOK, "Phone does not have active connection");
            Log.i("BluetoothCommandExec::", "Function deactiveProfile, phone does not have active connection");
        }
        Log.i("BluetoothCommandExec::", "Function deactiveProfile, finished with result::" + result.getType().toString());
        if (result.getType() != CommandResultTypes.OK) {
            Log.i("BluetoothCommandExec::", "Function deactiveProfile, Reason::" + result.getErrorReason());
        }
        return result;
    }


    /*
    *accessory methods
    */
    private ProfileTypes defineProfile(String profileName) {
        ProfileTypes profileType = ProfileTypes.UNKNOWN_PROFILE;
        switch (profileName) {
            case "A2DP":
                profileType = ProfileTypes.A2DP;
                break;
            case "HFP":
                profileType = ProfileTypes.HFP;
                break;
        }
        return profileType;
    }

    public void setFoundDevice(BluetoothDevice device){
        this.foundDevice = device;
    }

    private BluetoothDevice getActiveConnection(){
        Log.i("BluetoothCommandExec::", "Function getActiveConnection started");
        BluetoothDevice result = null;
        for(BluetoothDevice device : this.bluetoothAdapter.getBondedDevices()) {
            if(this.bluetoothProfileManager.isProfileActive(ProfileTypes.HFP, device)){
                Log.i("BluetoothCommandExec::", "Function getActiveConnection, HFP, active device: " + device.getName());
                result = device;
                break;
            }
            if(this.bluetoothProfileManager.isProfileActive(ProfileTypes.A2DP, device)){
                Log.i("BluetoothCommandExec::", "Function getActiveConnection, A2DP, active device: " + device.getName());
                result = device;
                break;
            }
        }
        Log.i("BluetoothCommandExec::", "Function getActiveConnection finished");
        return result;
    }
}