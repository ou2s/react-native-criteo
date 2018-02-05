#ifdef __OBJC__
#import <UIKit/UIKit.h>
#else
#ifndef FOUNDATION_EXPORT
#if defined(__cplusplus)
#define FOUNDATION_EXPORT extern "C"
#else
#define FOUNDATION_EXPORT extern
#endif
#endif
#endif

#import "CriteoAdvertiser.h"
#import "CRTOAppLaunchEvent+Internal.h"
#import "CRTOAppLaunchEvent.h"
#import "CRTOBasketViewEvent+Internal.h"
#import "CRTOBasketViewEvent.h"
#import "CRTODataEvent+Internal.h"
#import "CRTODataEvent.h"
#import "CRTODeeplinkEvent+Internal.h"
#import "CRTODeeplinkEvent.h"
#import "CRTOEvent+Internal.h"
#import "CRTOEvent.h"
#import "CRTOExtraData.h"
#import "CRTOHomeViewEvent+Internal.h"
#import "CRTOHomeViewEvent.h"
#import "CRTOProductListViewEvent+Internal.h"
#import "CRTOProductListViewEvent.h"
#import "CRTOProductViewEvent+Internal.h"
#import "CRTOProductViewEvent.h"
#import "CRTOTransactionConfirmationEvent+Internal.h"
#import "CRTOTransactionConfirmationEvent.h"
#import "CRTODateFormatter.h"
#import "CRTOJSONConstants.h"
#import "CRTOJSONEventSerializer.h"
#import "CRTOEventQueue.h"
#import "CRTOEventQueueItem.h"
#import "CRTONetworkDefines.h"
#import "CRTOBasketProduct.h"
#import "CRTOProduct.h"
#import "CRTOEventService+Internal.h"
#import "CRTOEventService.h"
#import "CRTOAppInfo.h"
#import "CRTODateConverter.h"
#import "CRTODeviceInfo.h"
#import "CRTOMd5Hasher.h"
#import "CRTOSDKInfo.h"

FOUNDATION_EXPORT double CriteoEventsSDKVersionNumber;
FOUNDATION_EXPORT const unsigned char CriteoEventsSDKVersionString[];

