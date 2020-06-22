package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.SwitchStateListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_SWITCH_STATE;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LCRSwitchStateListener implements SwitchStateListener {

  private static String TAG = "lcr-sdk: LCRSwitchStateListener";
  private final Context context;

  public LCRSwitchStateListener(Context context){
    this.context = context;
  }

  /**
   * Event of Switch State changed
   * @param deviceId		Device identification
   * @param deviceInfo	Device info
   * @param newValue		New switch state
   * @param oldValue		Old switch state
   */
  @Override
  public void onSwitchStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_SWITCH_STATE newValue,
    @Nullable LCR_SWITCH_STATE oldValue) {

    // Make variables for show status (getString formatting don't allow null)
    String newValueText = "(null)";
    if(newValue != null) {
      newValueText = newValue.toString();
    }

    Log.d(TAG, "onSwitchStateChanged: "+ oldValue + " -> " + newValueText);
  }

}
