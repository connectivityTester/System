package hmc_dis2.androidtracesource.executors.bluetooth.receivers;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hmc_dis2.androidtracesource.types.ProfileTypes;

public class BluetoothProfileManager implements BluetoothProfile.ServiceListener {

    private BluetoothHeadset headsetProfile;
    private BluetoothA2dp a2dpProfile;

    public BluetoothProfileManager() {
    }

    public boolean activateProfile(ProfileTypes profileType, BluetoothDevice deviceToActivate) {
        Log.i("BluetoothProfileManag::", "Function activateProfile started");
        Log.i("BluetoothProfileManag::", "Function activateProfile, device to activate: " + deviceToActivate.getName());
        boolean result = false;
        switch (profileType) {
            case A2DP:
                try {
                    Class c = this.a2dpProfile.getClass();
                    Class[] paramTypes = new Class[] { BluetoothDevice.class };
                    Method method = c.getMethod("connect", paramTypes);
                    Object[] args = new Object[] { deviceToActivate };
                    result = (boolean) method.invoke(this.a2dpProfile, args);
                    Log.i("BluetoothProfileManag::", "Function activateProfile, profile A2DP was acitaved - " + result);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    Log.i("BluetoothProfileManag::", "Function activateProfile, can not invoke activation A2DP profile!!");
                }
                break;
            case HFP:
                try {
                    Class c = this.headsetProfile.getClass();
                    Class[] paramTypes = new Class[] { BluetoothDevice.class };
                    Method method = c.getMethod("connect", paramTypes);
                    Object[] args = new Object[] { deviceToActivate };
                    result = (boolean) method.invoke(this.headsetProfile, args);
                    Log.i("BluetoothProfileManag::", "Function activateProfile, profile HFP was acitaved - " + result);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    Log.i("BluetoothProfileManag::", "Function activateProfile, can not invoke activation HFP profile!!");
                }
                break;
            case UNKNOWN_PROFILE:
                break;
        }
        Log.i("BluetoothProfileManag::", "Function activateProfile finished");
        return result;
    }

    public boolean deactivateProfile(ProfileTypes profileType, BluetoothDevice deviceToDeactivate) {
        Log.i("BluetoothProfileManag::", "Function deactivateProfile started");
        boolean result = false;
        switch (profileType) {
            case A2DP:
                try {
                    Class c = this.a2dpProfile.getClass();
                    Class[] paramTypes = new Class[] { BluetoothDevice.class };
                    Method method = c.getMethod("disconnect", paramTypes);
                    Object[] args = new Object[] { deviceToDeactivate };
                    result = (boolean) method.invoke(this.a2dpProfile, args);
                    Log.i("BluetoothProfileManag::", "Function deactivateProfile, profile A2DP was deacitaved - " + result);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    Log.i("BluetoothProfileManag::", "Function deactivateProfile, can not invoke deactivation A2DP profile!!");
                }
                break;
            case HFP:
                try {
                    Class c = this.headsetProfile.getClass();
                    Class[] paramTypes = new Class[] { BluetoothDevice.class };
                    Method method = c.getMethod("disconnect", paramTypes);
                    Object[] args = new Object[] { deviceToDeactivate };
                    result = (boolean) method.invoke(this.headsetProfile, args);
                    Log.i("BluetoothProfileManag::", "Function deactivateProfile, profile HFP was deacitaved - " + result);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    Log.i("BluetoothProfileManag::", "Function deactivateProfile, can not invoke deactivation HFP profile!!");
                }
                break;
            case UNKNOWN_PROFILE:
                break;
        }
        Log.i("BluetoothProfileManag::", "Function deactivateProfile finished");
        return result;
    }

    public boolean isProfileActive(ProfileTypes profile, BluetoothDevice device) {
        Log.i("BluetoothProfileManag::", "Function isProfileActive  started");
        boolean isProfileActive = false;
        Log.i("BluetoothProfileManag::", "Function isProfileActive, profile: " + profile.toString());
        switch (profile) {
            case HFP:
                if (this.headsetProfile != null) {
                    if (this.headsetProfile.getConnectionState(device) == BluetoothHeadset.STATE_CONNECTED ||
                            this.headsetProfile.getConnectionState(device) == BluetoothHeadset.STATE_CONNECTING) {
                        Log.i("BluetoothProfileManag::", "Function isProfileActive, profile " + profile.toString() + " is active");
                        isProfileActive = true;
                    } else {
                        isProfileActive = false;
                        Log.i("BluetoothProfileManag::", "Function isProfileActive, profile " + profile.toString() + " is not active");
                    }
                } else {
                    isProfileActive = false;
                    Log.i("BluetoothProfileManag::", "Function isProfileActive, profile HFP is disconnected!");
                }
                break;
            case A2DP:
                if (this.a2dpProfile != null) {
                    if (this.a2dpProfile.getConnectionState(device) == BluetoothA2dp.STATE_CONNECTED ||
                            this.a2dpProfile.getConnectionState(device) == BluetoothA2dp.STATE_CONNECTING) {
                        Log.i("BluetoothProfileManag::", "Function isProfileActive, profile " + profile.toString() + " is active");
                        isProfileActive = true;
                    } else {
                        Log.i("BluetoothProfileManag::", "Function isProfileActive, profile " + profile.toString() + " is not active");
                    }
                } else {
                    isProfileActive = false;
                    Log.i("BluetoothProfileManag::", "Function isProfileActive, profile A2DP is disconnected!");
                }
                break;
        }
        Log.i("BluetoothProfileManag::", "Function isProfileActive  finished");
        return isProfileActive;
    }

    @Override
    public void onServiceConnected(int profile, BluetoothProfile proxy) {
        switch (profile) {
            case BluetoothProfile.HEADSET:
                Log.i("BluetoothProfileManag::", "Function onServiceConnected, HFP profile is available for application");
                this.headsetProfile = (BluetoothHeadset) proxy;
                break;
            case BluetoothProfile.A2DP:
                Log.i("BluetoothProfileManag::", "Function onServiceConnected, A2DP profile is available for application");
                this.a2dpProfile = (BluetoothA2dp) proxy;
                break;
        }
        Log.i("BluetoothProfileManag::", "Function onServiceConnected finished");
    }

    @Override
    public void onServiceDisconnected(int profile) {
        Log.i("BluetoothProfileManag::", "Function onServiceDisconnected started");
        switch (profile) {
            case BluetoothProfile.HEADSET:
                Log.i("BluetoothProfileManag::", "Function onServiceConnected, " + BluetoothProfile.HEADSET + " profile is disconnected");
                this.headsetProfile = null;
                break;
            case BluetoothProfile.A2DP:
                Log.i("BluetoothProfileManag::", "Function onServiceConnected, " + BluetoothProfile.A2DP + " profile is disconnected");
                this.a2dpProfile = null;
                break;
        }
        Log.i("BluetoothProfileManag::", "Function onServiceDisconnected finished");
    }
}
