package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;

import com.liquidcontrols.lcr.iq.sdk.ConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.NetworkConnectionListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_THREAD_CONNECTION_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.network.NETWORK_TYPE;
import com.reactlibrary.LCRManager;

import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LCRNetworkConnectionListener implements NetworkConnectionListener {

  private final Context context;

  public LCRNetworkConnectionListener(Context context){
    this.context = context;
  }

  /**
   * Called when network connection state changed
   * @param networkType		Network type
   * @param connectionOptions	Connection options
   * @param attachedDevices	List of devices what is attach to network
   * @param newValue			New value
   * @param oldValue			Old Value
   */
  @Override
  public void onNetworkConnectionStateChange(
    @NonNull NETWORK_TYPE networkType,
    @NonNull ConnectionOptions connectionOptions,
    @NonNull List<DeviceInfo> attachedDevices,
    @Nullable LCR_THREAD_CONNECTION_STATE newValue,
    @Nullable LCR_THREAD_CONNECTION_STATE oldValue) {

    // Device level network status change is reported also in DeviceConnectionListener#deviceNetworkStateChanged
  }

  /**
   * Called when network connection is success
   * @param networkType		Network type
   * @param connectionOptions	Connection options
   * @param attachedDevices	Devices
   */
  @Override
  public void onNetworkConnected(
    @NonNull NETWORK_TYPE networkType,
    @NonNull ConnectionOptions connectionOptions,
    @NonNull List<DeviceInfo> attachedDevices) {

    LCRManager.getInstance(context).resolvePromise(true, "SDK: Network Connected", "connectDevice");
  }

  /**
   * Called when network is disconnected
   * @param networkType		Network type
   * @param connectionOptions	Connection options
   * @param attachedDevices	Devices
   * @param cause				Cause of error
   */
  @Override
  public void onNetworkDisconnected(
    @NonNull NETWORK_TYPE networkType,
    @NonNull ConnectionOptions connectionOptions,
    @NonNull List<DeviceInfo> attachedDevices,
    @Nullable Throwable cause) {

    String strCause = "(null)";
    if(cause != null) {
      strCause = cause.getMessage();
    }

    Log.d("LCR-SDK", "Network disconnected : " + networkType.name() + " : " + strCause);
    LCRManager.getInstance(context).resolvePromise(true, "Network disconnected : " + networkType.name() + " : " + strCause, "disconnectDevice");
  }

  /**
   * Called when network is on error (need user operations for recover)
   * @param networkType		Network type
   * @param connectionOptions	Connection options
   * @param attachedDevices	Devices
   * @param cause				Cause of error
   */
  @Override
  public void onNetworkError(
    @NonNull NETWORK_TYPE networkType,
    @NonNull ConnectionOptions connectionOptions,
    @NonNull List<DeviceInfo> attachedDevices,
    @Nullable Throwable cause) {

    String strCause = "(null)";
    if(cause != null) {
      strCause = cause.getMessage();
    }
    LCRManager.getInstance(context).resolvePromise(false, "Network error : " + networkType.name() + " : " + strCause, "connectDevice");
  }
}
