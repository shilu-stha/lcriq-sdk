
package com.reactlibrary;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class RNLibLcrModule extends ReactContextBaseJavaModule {

  public static final String REACT_CLASS = "RNLibLcrModule";
  private static ReactApplicationContext reactContext = null;

  /**
   * Pass in the context to the constructor and save it so you can emit events
   * @param context
   */
  public RNLibLcrModule(ReactApplicationContext context) {
    super(context);

    reactContext = context;
  }

  /**
   * Tell React the name of the module
   * @return
   */
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public Map<String, Object> getConstants() {
    // Export any constants to be used in your native module
    // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
    final Map<String, Object> constants = new HashMap<>();
    constants.put("EXAMPLE_CONSTANT", "example");

    return constants;
  }

  /**
   * A method for emitting from the native side to JS
   * @param eventName
   * @param eventData
   */
  static void emitDeviceEvent(String eventName, @Nullable WritableMap eventData) {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, eventData);
  }

  @ReactMethod
  public void initialize() {
    LCRManager.getInstance(reactContext).initializeSdk();
  }

  /**
   * Terminate lcr sdk and its connections.
   */
  @ReactMethod
  public void terminate() {
    LCRManager.getInstance(reactContext).terminateSdk();
  }

  /**
   * Connect to LCR meter. Send type of connection and its details.
   *
   * @param type (WIFI, BLUETOOTH)
   * @param additionalDetails (Wifi: ipAddress, port; Bluetooth: name)
   */
  @ReactMethod
  public void connectDevice(String type, ReadableMap additionalDetails) {
    LCRManager.getInstance(reactContext).addDevice(type, additionalDetails);
  }

  /**
   * Send start command to the meter to start refueling and tracking additional fields.
   */
  @ReactMethod
  public void start() {
    LCRManager.getInstance(reactContext).start();
  }

  /**
   * Send pause command to the meter.
   */
  @ReactMethod
  public void pause() {
    LCRManager.getInstance(reactContext).pause();
  }

  /**
   * Send stop command to the meter.
   */
  @ReactMethod
  public void stop() {
    LCRManager.getInstance(reactContext).stop();
  }

  @ReactMethod
  public void readData() {
    LCRManager.getInstance(reactContext).readData();
  }

  @ReactMethod
  public void getValue(final Promise promise) {
    promise.resolve("A real native value");
  }
}