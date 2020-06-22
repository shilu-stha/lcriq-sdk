package com.reactlibrary.listeners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.CommandListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.COMMAND_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_COMMAND;
import com.reactlibrary.LCRManager;

public class LCRCommandListener implements CommandListener {

  private static String TAG = "lcr-sdk: LCRCommandListener";
  private final Context context;

  public LCRCommandListener(Context context){
    this.context = context;
  }


  /**
   * onCommandStateChanged listener is activated when ever command state is changed
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Information about device
   * @param command		Command
   * @param newValue		New Value
   * @param oldValue		Old Value
   */
  @Override
  public void onCommandStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCR_COMMAND command,
    @Nullable COMMAND_STATE newValue,
    @Nullable COMMAND_STATE oldValue) {

    Log.d(TAG,"Command state : " + oldValue + " -> " + newValue);
  }

  /**
   * onCommandSuccess listener is activated when command is run SUCCESSFULLY
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Information about device
   * @param command		Command
   */
  @Override
  public void onCommandSuccess(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCR_COMMAND command) {

    Log.d(TAG,"SDK: Command Success: "+command);
    LCRManager.getInstance(context).resolvePromise(true, "SDK: Command Success: "+command, "commandRequest");
  }

  /**
   * onCommandFailed listener is activated when command is FAILED to run
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Information about device
   * @param command		Command
   * @param cause			Cause for command fail (can be <code>null</code>)
   */
  @Override
  public void onCommandFailed(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCR_COMMAND command,
    @Nullable Throwable cause) {

    String errorMsg = "(unknown)";
    if(cause != null) {
      errorMsg = cause.getLocalizedMessage();
    }

    Log.d(TAG,"Command failed : " + command + " Cause : " + errorMsg);
    LCRManager.getInstance(context).resolvePromise(false, "Command failed : " + command + " Cause : " + errorMsg, "commandRequest");
  }
}
