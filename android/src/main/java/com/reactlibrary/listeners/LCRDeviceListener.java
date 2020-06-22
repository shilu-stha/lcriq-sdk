package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;

import com.liquidcontrols.lcr.iq.sdk.SDKDeviceException;
import com.liquidcontrols.lcr.iq.sdk.interfaces.DeviceListener;
import com.reactlibrary.LCRManager;

import android.support.annotation.NonNull;

public class LCRDeviceListener implements DeviceListener {

  private static String TAG = "lcr-sdk: LCRDeviceListener";
  private final Context context;

  public LCRDeviceListener(Context context){
    this.context = context;
  }

  /**
   * Called when device add operation success
   * @param deviceId	Device identification
   */
  @Override
  public void onDeviceAddSuccess(@NonNull String deviceId) {
    // Logging success
    Log.d(TAG,"Panda Add device success : "+  deviceId);

    // Finally Connect the device.
    LCRManager.getInstance(context).connectDevice();
  }

  /**
   * Called when device add operation failed
   * @param deviceId	Device identification
   * @param cause		Cause of error
   */
  @Override
  public void onDeviceAddFailed(@NonNull String deviceId, SDKDeviceException cause) {
    String strCause = "(null)";
    if(cause != null) {
      strCause = cause.getMessage();
    }
    // Logging add device error
    Log.e(TAG, "Panda Add device failed : "+ strCause);
    LCRManager.getInstance(context).resolvePromise(false, "SDK: Add device failed : "+ strCause, "connectDevice");
  }

  /**
   * Called when device remove operation is success
   * @param deviceId	Device identification
   */
  @Override
  public void onDeviceRemoveSuccess(@NonNull String deviceId) {
    // Logging actions
    Log.d(TAG, "Panda Remove device success: "+ deviceId);

    // Logging actions
//    setTextViewLogger("Remove device success");
  }

  /**
   * Called when device remove operation is failed
   * @param deviceId	Device identification
   * @param cause		Cause of error
   */
  @Override
  public void onDeviceRemoveFailed(@NonNull String deviceId, SDKDeviceException cause) {
    String strCause = "(null)";
    if(cause != null) {
      strCause = cause.getMessage();
    }
    // Logging remove device error
    Log.e(TAG, "Panda Remove device failed : "+ strCause);

    // Logging remove device error
//    setTextViewLogger("Remove device failed : " + strCause);
  }
}
