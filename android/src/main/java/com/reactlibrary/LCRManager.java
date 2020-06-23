package com.reactlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.liquidcontrols.lcr.iq.sdk.ConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.FieldItem;
import com.liquidcontrols.lcr.iq.sdk.LcrSdk;
import com.liquidcontrols.lcr.iq.sdk.RequestField;
import com.liquidcontrols.lcr.iq.sdk.SDKDeviceException;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_COMMAND;
import com.liquidcontrols.lcr.iq.sdk.utils.AsyncCallback;
import com.liquidcontrols.lcr.iq.sdk.utils.TimeSet;
import com.reactlibrary.listeners.LCRCommandListener;
import com.reactlibrary.listeners.LCRDeviceCommunicationListener;
import com.reactlibrary.listeners.LCRDeviceConnectionListener;
import com.reactlibrary.listeners.LCRDeviceListener;
import com.reactlibrary.listeners.LCRDeviceStatusListener;
import com.reactlibrary.listeners.LCRFieldListener;
import com.reactlibrary.listeners.LCRNetworkConnectionListener;
import com.reactlibrary.listeners.LCRPrinterStatusListener;
import com.reactlibrary.listeners.LCRSwitchStateListener;

import java.util.concurrent.TimeUnit;

import static com.reactlibrary.RNLibLcrModule.emitDeviceEvent;

public class LCRManager {

  private static String TAG = "lcr-sdk: LCRManager";
  private static LCRManager instance;
  private LCR_COMMAND deliveryStatus = LCR_COMMAND.END_DELIVERY;

  private LcrSdk lcrSdk;
  private Context context;
  private LCRFieldListener fieldListener;
  private LCRSwitchStateListener switchStateListener;
  private LCRDeviceStatusListener deviceStatusListener;
  private LCRPrinterStatusListener printerStatusListener;
  private FieldItem grossQty = null;
  private FieldItem flowRate = null;

  private LCRManager(Context context){
    this.context = context;
  }

  public static LCRManager getInstance(Context context){
    if(instance == null) {
      instance = new LCRManager(context);
    }
    return instance;
  }

  /**
   * Return response to JS code.
   * We use Arguments.createMap to build an object to return
   *
   * @param isSuccess
   * @param message
   * @param eventId
   */
  public void resolvePromise(Boolean isSuccess, String message, String eventId){
    WritableMap idData = Arguments.createMap();
    idData.putString("message", message);
    idData.putBoolean("success", isSuccess);
    emitDeviceEvent(eventId, idData);
  }

  /**
   * Initialize LCR sdk
   */
  public void initializeSdk() {
    lcrSdk = new LcrSdk(context);
    lcrSdk.init(new AsyncCallback() {
      @Override
      public void onAsyncReturn(@Nullable Throwable error) {
        if (error != null) {
          String strError = "ERROR INIT SDK : " + error.getLocalizedMessage();
          Log.e(TAG, strError);
          resolvePromise(false, strError, "initializeSdk");
        } else {
          Log.d(TAG, "SDK: SDK Init success");
          LCRManager.this.addSDKListeners();
        }
      }
    });
  }

  /**
   * Add necessary listeners to communicate with the sdk.
   */
  private void addSDKListeners() {
    if(lcrSdk == null) {
      Log.e(TAG, "SDK: SDK not initialized");

      resolvePromise(false, "SDK:  SDK not initialized", "initializeSdk");
      return;
    }

    deviceStatusListener = new LCRDeviceStatusListener(context);
    switchStateListener = new LCRSwitchStateListener(context);
    printerStatusListener = new LCRPrinterStatusListener(context);
    fieldListener = new LCRFieldListener(context);

    // Device connection listener
    lcrSdk.addListener(new LCRDeviceConnectionListener(context));
    // Field listener
    lcrSdk.addListener(fieldListener);
    // Command listener
    lcrSdk.addListener(new LCRCommandListener(context));
    // Device status / state
    lcrSdk.addListener(deviceStatusListener);
    // Switch state listener
    lcrSdk.addListener(switchStateListener);
    // Printer status listener
    lcrSdk.addListener(printerStatusListener);
    // Add device communication listener
    lcrSdk.addListener(new LCRDeviceCommunicationListener(context));
    // Device add/remove listener
    lcrSdk.addListener(new LCRDeviceListener(context));
    // Network status listener (for logging purposes)
    lcrSdk.addListener(new LCRNetworkConnectionListener(context));

    Log.d(TAG, "SDK: Add Listeners");
    resolvePromise(true, "SDK: Initialize SDK and Add Listeners", "initializeSdk");
  }

  /**
   * Request SDK to connect to device
   * NOTE: Make sure to request for permission and check bluetooth connect from the app before calling this method
   *
   * */
  public void connectDevice() {
    // Check SDK object (if call this method after close object)
    if(lcrSdk == null) {
      Log.e(TAG, "SDK: SDK not initialized");

      resolvePromise(false, "SDK: SDK not initialized", "connectDevice");
      return;
    }

    // Call SDk to make connect
    lcrSdk.connect(ConnectionUtils.getInstance().getDeviceId());
    Log.d(TAG, "SDK: Device Connected");
  }

  /**
   * Add device details to be connected with the sdk.
   * Including the type and additional network details.
   *
   * @param type (WIFI, BLUETOOTH)
   * @param additionalDetails (Wifi: ipAddress, port; Bluetooth: name)
   */
  protected void addDevice(String type, ReadableMap additionalDetails){
    if(lcrSdk == null) {
      Log.e(TAG, "SDK: SDK not initialized");

      resolvePromise(false, "SDK:  SDK not initialized", "connectDevice");
      return;
    }

    ConnectionOptions connectionOptions = null;

    if(type.equals("WIFI")){
      connectionOptions = ConnectionUtils.getInstance().getWifiConnectionOptions(additionalDetails);
      Log.d(TAG, "SDK: Using Wifi connection");
    } else {
      connectionOptions = ConnectionUtils.getInstance().getBluetoothConnectionOptions(additionalDetails);
      Log.d(TAG, "SDK: Using Bluetooth connection");
    }

    // Synchronize way to add device (no callback need), using try-catch for error detection
    try {

      lcrSdk.addDevice(
        ConnectionUtils.getInstance().getDeviceInfo(),
        connectionOptions);

      /* !! NOTE !! Device add will be confirmed in DeviceListener */

    } catch (Exception e) {
      // Device add request fail
      String strError = "Device add request failed : " + e.getLocalizedMessage();

      Log.e("Main",strError);
      resolvePromise(false, strError, "connectDevice");
    }
  }

  public void start() {
    if(lcrSdk == null) {
      return;
    }
    deliveryStatus = LCR_COMMAND.RUN;
    // Put command
    lcrSdk.addDeviceCommand(
      ConnectionUtils.getInstance().getDeviceId(),
      deliveryStatus);
  }
  public void pause() {
    if(lcrSdk == null) {
      return;
    }
    deliveryStatus = LCR_COMMAND.PAUSE;
    // Put command
    lcrSdk.addDeviceCommand(
      ConnectionUtils.getInstance().getDeviceId(),
      deliveryStatus);
  }
  public void stop() {
    if(lcrSdk == null) {
      return;
    }
    deliveryStatus = LCR_COMMAND.END_DELIVERY;
    // Put command
    lcrSdk.addDeviceCommand(
      ConnectionUtils.getInstance().getDeviceId(),
      deliveryStatus);
  }

  /**
   * Request for field data
   *
   * @param fields 'GROSS_QTY', 'FLOW_RATE'
   */
  public void requestFieldData(ReadableArray fields) {
    for (int i = 0; i < fields.size(); i++) {
      ReadableMap item = fields.getMap(i);
      String field = item.getString("fieldName");
      switch (field){
        case "GROSS_QTY":
          grossQty = fieldListener.grossQty;
          break;
        case "FLOW_RATE":
          flowRate = fieldListener.flowRate;
          break;
      }
    }

    // If gross quantity field is available
    if (grossQty != null) {
      Log.d(TAG, "SDK : Send command for request field " + grossQty.getFieldName() + " with 1 second update interval");

      lcrSdk.requestFieldData(
        ConnectionUtils.getInstance().getDeviceId(),
        new RequestField(
          grossQty,
          new TimeSet(1, TimeUnit.SECONDS)),
        new AsyncCallback() {
          @Override
          public void onAsyncReturn(@Nullable Throwable throwable) {
            if(throwable != null) {
              Log.e(TAG, "SDK : Request command for field " + grossQty.getFieldName() + " failed : " + throwable.getLocalizedMessage(), throwable );
              resolvePromise(false, "SDK : Request command for field " + grossQty.getFieldName() + " failed : " + throwable.getLocalizedMessage(), "requestFieldData");
            } else {
              Log.d(TAG, "SDK : Request command for field " + grossQty.getFieldName() + " success");
              resolvePromise(true, "SDK : Request command for field " + grossQty.getFieldName() + " success ", "requestFieldData");
            }
          }
        });
    }

    if (flowRate != null) {
      lcrSdk.requestFieldData(
        ConnectionUtils.getInstance().getDeviceId(),
        new RequestField(
          flowRate,
          new TimeSet(2, TimeUnit.SECONDS)),
        new AsyncCallback() {
          @Override
          public void onAsyncReturn(@Nullable Throwable throwable) {
            if(throwable != null) {
              Log.e(TAG, "SDK : Request command for field " + flowRate.getFieldName() + " failed : " + throwable.getLocalizedMessage(), throwable );
              resolvePromise(false, "SDK : Request command for field " + flowRate.getFieldName() + " failed : " + throwable.getLocalizedMessage(), "requestFieldData");
            } else {
              Log.d(TAG, "SDK : Request command for field " + flowRate.getFieldName() + " failed : " );
              resolvePromise(true, "SDK : Request command for field " + flowRate.getFieldName() + " success ", "requestFieldData");
            }
          }
        });
    }
  }

  /**
   * Remove request for data for selected fields.
   *
   * @param fields 'GROSS_QTY', 'FLOW_RATE'
   */
  public void removeFieldRequestData (ReadableArray fields) {
    for (int i = 0; i < fields.size(); i++) {
      ReadableMap item = fields.getMap(i);
      String field = item.getString("fieldName");
      switch (field){
        case "GROSS_QTY":
          grossQty = fieldListener.grossQty;
          break;
        case "FLOW_RATE":
          flowRate = fieldListener.flowRate;
          break;
      }

    }

    if (grossQty != null) {
      Log.d(TAG, "SDK : Send command for removing field data request " + grossQty.getFieldName());
      lcrSdk.removeFieldDataRequest(
        ConnectionUtils.getInstance().getDeviceId(),
        grossQty,
        new AsyncCallback() {
          @Override
          public void onAsyncReturn(@Nullable Throwable throwable) {
            if(throwable != null) {
              Log.e(TAG, "SDK : Remove command for field data request " + grossQty.getFieldName() + " failed : " + throwable.getLocalizedMessage());
              resolvePromise(false, "SDK : Remove command for field " + grossQty.getFieldName() + " failed : " + throwable.getLocalizedMessage(), "removeFieldRequestData");
            } else {
              Log.d(TAG, "SDK : Remove command for field data request " + grossQty.getFieldName() + " success");
              resolvePromise(true, "SDK : Remove command for field " + grossQty.getFieldName() + " success ", "removeFieldRequestData");
            }
          }
        });
    }

    if (flowRate != null) {
      Log.d(TAG, "SDK : Send command for removing field data request " + flowRate.getFieldName());
      lcrSdk.removeFieldDataRequest(
        ConnectionUtils.getInstance().getDeviceId(),
        flowRate,
        new AsyncCallback() {
          @Override
          public void onAsyncReturn(@Nullable Throwable throwable) {
            if(throwable != null) {
              Log.e(TAG, "SDK : Remove command for field data request " + flowRate.getFieldName() + " failed : " + throwable.getLocalizedMessage(), throwable);
              resolvePromise(false, "SDK : Remove command for field " + flowRate.getFieldName() + " failed : " + throwable.getLocalizedMessage(), "removeFieldRequestData");
            } else {
              Log.d(TAG, "SDK : Remove command for field data request " + flowRate.getFieldName() + " success");
              resolvePromise(true, "SDK : Remove command for field " + flowRate.getFieldName() + " success ", "removeFieldRequestData");
            }
          }
        });
    }
  }

  public void reset() {

  }

  /**
   * Remove device and terminate connection with the sdk.
   */
  public void terminateSdk() {
    if(lcrSdk != null) {
      // Remove device
      try {
        lcrSdk.removeDevice(ConnectionUtils.getInstance().getDeviceId());
      } catch (SDKDeviceException e) {
        e.printStackTrace();
      }
      // Remove used listeners
      lcrSdk.removeAllListeners();
      // Request SDK perform quit actions
      lcrSdk.quit();
    }
  }

  /** Request SDK to disconnect from device */
  public void disconnectDevice() {
    // Check SDK object (if call this method after close object)
    if(lcrSdk == null) {
      return;
    }
    // Call SDK to make disconnect command for device
    lcrSdk.disconnect(ConnectionUtils.getInstance().getDeviceId());
    Log.d(TAG, "SDK: Device Disconnected");
  }
}
