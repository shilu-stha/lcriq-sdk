// defines bridge operations

import { NativeModules, NativeEventEmitter } from 'react-native';
import getValue from './getValue';
import requestDeviceId from './requestDeviceId';
import initializeSdk, { terminate } from './initializeSdk';
import connectDevice from './connectDevice';
import { start, stop, pause, requestData } from './fieldCommands';

const bridge = NativeModules.RNLibLcrModule;
const eventEmitter = new NativeEventEmitter(bridge);

export default {
  getValue: () => getValue(bridge),
  requestDeviceId: () => requestDeviceId(bridge, eventEmitter),
  initializeSdk: () => initializeSdk(bridge, eventEmitter),
  terminate: () => terminate(bridge),
  connectDevice: (type, details) => connectDevice(type, details, bridge, eventEmitter),
  start:() => start(bridge),
  stop:() => stop(bridge),
  pause:() => pause(bridge),
  requestData:() => requestData(bridge)
};
