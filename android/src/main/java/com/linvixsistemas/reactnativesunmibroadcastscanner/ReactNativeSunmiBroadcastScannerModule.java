package com.linvixsistemas.reactnativesunmibroadcastscanner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.sunmi.scanner.IScanInterface;

@ReactModule(name = ReactNativeSunmiBroadcastScannerModule.NAME)
public class ReactNativeSunmiBroadcastScannerModule extends ReactContextBaseJavaModule {
  public static final String NAME = "RNSunmiBroadcastScanner";
  private final ReactApplicationContext reactContext;
  private static final String ACTION_DATA_CODE_RECEIVED = "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";
  private static final String DATA = "data";
  private static final String SOURCE = "source_byte";
  private static final String JS_EVENT_NAME = "BROADCAST_SCANNER_READ";

  private static IScanInterface scanInterface;

  public ReactNativeSunmiBroadcastScannerModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;

    try {
      RegisterReceiver();
      BindScannerService();
    } catch (Exception ignored) {
    }
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private final BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      try {
        String code = intent.getStringExtra(DATA);
        byte[] arr = intent.getByteArrayExtra(SOURCE);

        // parameters that will be sent to react native
        WritableMap params = new WritableNativeMap();

        if (code != null && !code.isEmpty()) {
          Log.d(NAME, code);

          // monta o params
          params.putString("code", code);
          params.putString("bytes", arr != null ? new String(arr) : null);

          // send event to react native
          reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(JS_EVENT_NAME, params);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  };

  private static final ServiceConnection conn = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      Log.d(NAME, "Broadcast scanner service connected");
      scanInterface = IScanInterface.Stub.asInterface(iBinder);
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
      Log.d(NAME, "Broadcast scanner service disconnected");
      scanInterface = null;
    }
  };

  public void BindScannerService() {
    Intent intent = new Intent();
    intent.setPackage("com.sunmi.scanner");
    intent.setAction("com.sunmi.scanner.IScanInterface");

    reactContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
  }

  private void RegisterReceiver() {
    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_DATA_CODE_RECEIVED);
    reactContext.registerReceiver(receiver, filter);
  }
}
