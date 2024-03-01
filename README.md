# @linvix-sistemas/react-native-sunmi-broadcast-scanner

Package to listen to Scanners on Sunmi Devices configured with Broadcast

## Installation

```sh
npm install @linvix-sistemas/react-native-sunmi-broadcast-scanner
```

```sh
yarn add @linvix-sistemas/react-native-sunmi-broadcast-scanner
```

### Usage - onBarcodeRead
> See the [example](example/src/App.tsx) folder to see how to use it.

This method is a "listener", that is, it waits for the event to occur, and triggers the callback function when the event happens.
```ts
import ReactNativeSunmiBroadcastScanner from '@linvix-sistemas/react-native-sunmi-broadcast-scanner';


useEffect(() => {
    /**
     * On devices that have a barcode reader or support via external USB scanners.
     * For code reading to work, you need to configure the device to broadcast data and disable TextInput for text output.
     *
     * L2s/L2ks also works here with barcode scanning.
     *
     * It is important to call cleanup to remove the listener from the function when you want to stop receiving the read barcode.
     */
    const cleanup = ReactNativeSunmiBroadcastScanner.onBarcodeRead((ev) => {
      console.log(ev);
    });

    return () => cleanup.remove();
}, []);
```

### Usage - Utils
Utility functions that are used to obtain some data and perform some functions.

```ts
import ReactNativeSunmiBroadcastScanner from '@linvix-sistemas/react-native-sunmi-broadcast-scanner';


/**
 * Get model of device
 */
const model = await ReactNativeSunmiBroadcastScanner.utils.getModel();

/**
 * Get brand of device
 */
const brand = await ReactNativeSunmiBroadcastScanner.utils.getBrand();

/**
 * Get serial number of device
 */
const serialNumber = await ReactNativeSunmiBroadcastScanner.utils.getSerialNumber();

/**
 * Get version code of device
 */
const versionCode = await ReactNativeSunmiBroadcastScanner.utils.getVersionCode();

/**
 * Get version name of device
 */
const versionName = await ReactNativeSunmiBroadcastScanner.utils.getVersionName();

/**
 * Reboot device
 */
const response = await ReactNativeSunmiBroadcastScanner.utils.rebootDevice(reason: string);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
