/* eslint-disable no-new */
import { NativeModules, DeviceEventEmitter } from 'react-native';

import type { ScannerEvent } from './types';

const LINKING_ERROR =
  `The package '@linvix-sistemas/react-native-sunmi-broadcast-scanner' doesn't seem to be linked. Make sure: \n\n` +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

if (!NativeModules.ReactNativeSunmiBroadcastScanner) {
  new Proxy(
    {},
    {
      get() {
        throw new Error(LINKING_ERROR);
      },
    }
  );
}

const onBarcodeRead = (callback: (ev: ScannerEvent) => void) => {
  return DeviceEventEmitter.addListener('BROADCAST_SCANNER_READ', callback);
};

const ReactNativeSunmiBroadcastScanner = {
  onBarcodeRead,
};

export default ReactNativeSunmiBroadcastScanner;
