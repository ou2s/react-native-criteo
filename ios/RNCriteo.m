
#import "RNCriteo.h"
#import <CriteoAdvertiser/CriteoAdvertiser.h>

@implementation RNCriteo

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()



RCT_EXPORT_METHOD(initCriteoEventService:(NSString *)accountName
                  deviceCountry:(NSString *)deviceCountry
                  deviceLanguage:(NSString *)deviceLanguage
                  email:(NSString *)email)
{
    CRTOEventService *eventService = [CRTOEventService sharedEventService];
    eventService.country = deviceCountry;
    eventService.language = deviceLanguage;
    NSString *emailToSend = email ?: @"";
    eventService.customerEmail = emailToSend;
}


RCT_EXPORT_METHOD(criteoAppLaunchEvent)
{
    CRTOAppLaunchEvent *appLaunch = [[CRTOAppLaunchEvent alloc] init];
    [[CRTOEventService sharedEventService] send:appLaunch];
}

RCT_EXPORT_METHOD(criteoHomeEvent)
{
    CRTOAppLaunchEvent *appLaunch = [[CRTOAppLaunchEvent alloc] init];
    [[CRTOEventService sharedEventService] send:appLaunch];
}


RCT_EXPORT_METHOD(criteoDataEvent:(NSString *)type)
{
    // TODO
}

RCT_EXPORT_METHOD(criteoDeeplinkEvent:(NSString *)url)
{
    // Instantiate a deeplink event and pass the deeplink to its initializer
    CRTODeeplinkEvent *deeplinkEvent = [[CRTODeeplinkEvent alloc] initWithDeeplinkLaunchUrl:url];
    // Send the deeplink event
    [[CRTOEventService sharedEventService] send:deeplinkEvent];
}

RCT_EXPORT_METHOD(criteoProductViewEvent:(NSString *)eventId price:(double)price currency:(NSString *)currency)
{
    // Create an instance of CRTOProduct for the item shown to the user
    CRTOProduct *product = [[CRTOProduct alloc] initWithProductId:eventId price:price];
    // Create an instance of CRTOProductViewEvent using the product as a parameter
    CRTOProductViewEvent *productView = [[CRTOProductViewEvent alloc] initWithProduct:product];
    // Send the event
    [[CRTOEventService sharedEventService] send:productView];
}

RCT_EXPORT_METHOD(criteoProductListViewEvent:(NSArray *)products price:(double)price currency:(NSString *)currency)
{
    // Create instances of CRTOProduct for the item shown to the user
    NSMutableArray *productsCRTO = [NSMutableArray array];

    for (NSString *identifier in products)
    {
        if (identifier == nil)
        {
            continue;
        }
        CRTOProduct *product = [[CRTOProduct alloc] initWithProductId:identifier
                                                                price:price];
        [productsCRTO addObject:product];
    }
    // Create an array of products
    // Create an instance of CRTOProductListViewEvent using the array as a parameter
    CRTOProductListViewEvent *listView = [[CRTOProductListViewEvent alloc] initWithProducts:productsCRTO];
    // Send the event
    [[CRTOEventService sharedEventService] send:listView];
}

RCT_EXPORT_METHOD(criteoBasketViewEvent:(NSString *)identifier
                  price:(double)price
                  quantity:(NSInteger)quantity
                  currency:(NSString *)currency)
{
    NSParameterAssert(identifier);
    // Create instances of CRTOBasketProduct to represent items shown to the user
    CRTOBasketProduct *product1 = [[CRTOBasketProduct alloc] initWithProductId:identifier
                                                                         price:price
                                                                      quantity:quantity];

    // Create an array of basket products
    NSArray *products = @[ product1 ];

    // Create an instance of CRTOBasketViewEvent and pass the array to its initializer
    CRTOBasketViewEvent *basketView = [[CRTOBasketViewEvent alloc] initWithBasketProducts:products
                                                                                 currency:currency];

    // Send the basket view event
    [[CRTOEventService sharedEventService] send:basketView];
}

RCT_EXPORT_METHOD(criteoTransactionConfirmationEvent:(NSString *)timeStampId
                  identifier:(NSString *)identifier
                  price:(double)price
                  quantity:(NSInteger)quantity
                  currency:(NSString *)currency)
{
    // Create instances of CRTOBasketProduct to represent items sold to the user
    CRTOBasketProduct *product1 = [[CRTOBasketProduct alloc] initWithProductId:identifier
                                                                         price:price
                                                                      quantity:quantity];

    NSArray *products = @[ product1 ];

    // Create an instance of CRTOTransactionConfirmationEvent and pass the array to its initializer
    CRTOTransactionConfirmationEvent *transactionEvent = [[CRTOTransactionConfirmationEvent alloc] initWithBasketProducts:products
                                                                                                            transactionId:timeStampId
                                                                                                                 currency:currency];

    // Send the transaction confirmation event
    [[CRTOEventService sharedEventService] send:transactionEvent];

}

@end

