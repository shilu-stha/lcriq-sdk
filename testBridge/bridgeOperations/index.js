// defines bridge operations

import { NativeModules, NativeEventEmitter } from 'react-native';
import getValue from './getValue';
import requestDeviceId from './requestDeviceId';
import initializeSdk from './initializeSdk';

const bridge = NativeModules.RNLibLcrModule;
const eventEmitter = new NativeEventEmitter(bridge);

export default {
  getValue: () => getValue(bridge),
  requestDeviceId: () => requestDeviceId(bridge, eventEmitter),
  initializeSdk: () => initializeSdk(bridge)
};
