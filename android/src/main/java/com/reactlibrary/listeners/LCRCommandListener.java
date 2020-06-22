package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.CommandListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.COMMAND_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_COMMAND;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LCRCommandListener implements CommandListener {

  private final Context context;
  private TextView tvLogger;

  public LCRCommandListener(Context context){
    this.context = context;
//    tvLogger = (TextView) ((MainActivity)context).findViewById(R.id.tv_data_logger);

    Log.d("Panda", "LCRCommandListener"+ context);
  }

//  private void setTextViewLogger(String text) {
//    Logs logs = Logs.getInstance();
//    List<String> dataList = logs.setTextViewLogger(text);
//
//    String textBuffer = "";
//    // Make data to print
//    for (String str : dataList) {
//      textBuffer = textBuffer + str + "\n";
//    }
//    // Print data
//    tvLogger.setText(textBuffer);
//    Log.d("Panda", textBuffer);
//  }

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

//    setTextViewLogger("Command state : " + oldValue + " -> " + newValue);
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

//    setTextViewLogger("Command success : " + command);
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
//    setTextViewLogger("Command failed : " + command + " Cause : " + errorMsg);
  }
}
