
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNReactNativeSunmiBroadcastScannerSpec.h"

@interface ReactNativeSunmiBroadcastScanner : NSObject <NativeReactNativeSunmiBroadcastScannerSpec>
#else
#import <React/RCTBridgeModule.h>

@interface ReactNativeSunmiBroadcastScanner : NSObject <RCTBridgeModule>
#endif

@end
