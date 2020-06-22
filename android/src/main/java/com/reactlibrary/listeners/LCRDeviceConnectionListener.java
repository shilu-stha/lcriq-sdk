package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.liquidcontrols.lcr.iq.sdk.BlueToothConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.ConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.WiFiConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.interfaces.DeviceConnectionListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_DEVICE_CONNECTION_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_THREAD_CONNECTION_STATE;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LCRDeviceConnectionListener implements DeviceConnectionListener {

  private static String TAG = "lcr-sdk: LCRDeviceConnectionListener";
  private final Context context;

  public LCRDeviceConnectionListener(Context context){
    this.context = context;
  }

  /**
   * Called when connection to LCR device is made with all relevant information.
   * @param deviceId 		Device identification string
   * @param deviceInfo	Device information
   */
  @Override
  public void deviceOnConnect(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo) {

    String logText = "Device on CONNECTED : "
      + deviceId
      + " LCP SDK Address : "
      + deviceInfo.getSdkAddress().toString()
      + " LCP Device Address : "
      + deviceInfo.getDeviceAddress().toString();

    Log.d(TAG, logText);
  }

  /**
   * Called when device lost connection
   * @param deviceId		Device identification string
   * @param deviceInfo	Device information
   * @param cause			Reason for connection lost
   */
  @Override
  public void deviceOnDisconnect(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable Throwable cause) {

    // Log text about disconnecting
    String causeString = "unknown";
    if(cause != null) {
      causeString = (cause.getLocalizedMessage());
    }

    Log.e(TAG, "deviceOnDisconnect: ", cause);
//    setTextViewLogger("Device on DISCONNECTED : " + deviceId + " Cause : " + causeString);
  }

  /**
   * Called when device connection enter in error state
   * @param deviceId		Device identification string
   * @param deviceInfo	Device information
   * @param cause			Cause of error
   */
  @Override
  public void deviceOnError(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable Throwable cause) {

    String errorMsg = "";
    if(cause != null) {
      errorMsg = (cause.getLocalizedMessage());
    }
//    setTextViewLogger("Device on ERROR : " + deviceId + " Cause : " + errorMsg);
  }


  /**
   * Notify any status change events
   * @param deviceId		Device identification string
   * @param deviceInfo	Device information
   * @param newValue		New State
   * @param oldValue		Old State
   */
  @Override
  public void deviceConnectionStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_DEVICE_CONNECTION_STATE newValue,
    @Nullable LCR_DEVICE_CONNECTION_STATE oldValue) {

//    setTextViewLogger("Device connection state changed : " + oldValue + " -> " + newValue);
  }

  /**
   * Device network status changed
   * @param deviceId			Device identification string
   * @param deviceInfo		Device info
   * @param connectionOptions	Connection info
   * @param newValue			Network new State
   * @param oldValue			Network old state
   */
  @Override
  public void deviceNetworkStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable ConnectionOptions connectionOptions,
    @NonNull LCR_THREAD_CONNECTION_STATE newValue,
    @NonNull LCR_THREAD_CONNECTION_STATE oldValue) {
    String networkAdressData;


    if(connectionOptions != null) {
      if(connectionOptions instanceof BlueToothConnectionOptions) {
        Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: Bluetooth");
      } else if(connectionOptions instanceof WiFiConnectionOptions) {

        Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: WIFI");
      } else {
        Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: Unknown");
      }
    } else {
      Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: Unknown");
    }

//    setTextViewLogger("Network connection state changed : " + oldValue + " -> " + newValue);
  }
}
