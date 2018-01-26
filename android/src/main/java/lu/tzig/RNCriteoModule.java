
package lu.tzig;

import com.criteo.events.AppLaunchEvent;
import com.criteo.events.BasketViewEvent;
import com.criteo.events.DataEvent;
import com.criteo.events.DeeplinkEvent;
import com.criteo.events.EventService;
import com.criteo.events.HomeViewEvent;
import com.criteo.events.ProductListViewEvent;
import com.criteo.events.ProductViewEvent;
import com.criteo.events.TransactionConfirmationEvent;
import com.criteo.events.product.BasketProduct;
import com.criteo.events.product.Product;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Currency;

public class RNCriteoModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static EventService mCriteoEventService = null;

  public RNCriteoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNCriteo";
  }

  @ReactMethod
  public void initCriteoEventService(String accountName, String deviceCountry, String deviceLanguage, String email) {
    mCriteoEventService = new EventService(reactContext);
    mCriteoEventService.setCountry(deviceCountry);
    mCriteoEventService.setLanguage(deviceLanguage);
    mCriteoEventService.setAccountName(accountName);
    if (!email.isEmpty()) {
      criteoSetEmail(email);
    } else {
      criteoSetEmail("");
    }
  }

  @ReactMethod
  public void criteoAppLaunchEvent() {
    if (mCriteoEventService != null) {
      AppLaunchEvent appLaunch = new AppLaunchEvent();
      mCriteoEventService.send(appLaunch);
    }
  }

  @ReactMethod
  public void criteoHomeEvent() {
    if (mCriteoEventService != null) {
      HomeViewEvent homeViewEvent = new HomeViewEvent();
      mCriteoEventService.send(homeViewEvent);
    }
  }

  @ReactMethod
  public void criteoDeeplinkEvent(String url) {
    if (mCriteoEventService != null) {
      DeeplinkEvent deeplinkEvent = new DeeplinkEvent(url);
      mCriteoEventService.send(deeplinkEvent);
    }
  }

  // TODO create generic criteoDataEvent
  @ReactMethod
  public void criteoDataEvent(String type) {
    if (mCriteoEventService != null) {
      DataEvent dataEvent = new DataEvent();
      dataEvent.addExtraData("Tab", type);
      mCriteoEventService.send(dataEvent);
    }
  }

  @ReactMethod
  public void criteoProductViewEvent(String id, double price, String currencyCode) {
    if (mCriteoEventService != null) {
      ProductViewEvent productViewEvent = new ProductViewEvent(id, price);
      productViewEvent.setCurrency(Currency.getInstance(currencyCode));

      mCriteoEventService.send(productViewEvent);
    }
  }

  @ReactMethod
  public void criteoProductListViewEvent(ReadableMap list, String currencyCode) {
    if (mCriteoEventService != null && list != null) {
      ArrayList<Product> productList = new ArrayList<>();
      ReadableMapKeySetIterator it = list.keySetIterator();
      while (it.hasNextKey()) {
        String id = it.nextKey();
        Double price = list.getDouble(id);
        Product product = new Product(id, price);
        productList.add(product);
      }
      ProductListViewEvent productListViewEvent = new ProductListViewEvent(productList);
      productListViewEvent.setCurrency(Currency.getInstance(currencyCode));
      mCriteoEventService.send(productListViewEvent);
    }
  }

  /* private WritableMap getProductParams(String id, double value) {
    WritableMap productMap = Arguments.createMap();
    productMap.putString(ID, id);
    productMap.putDouble(VALUE, value);
  } */

  @ReactMethod
  public void criteoBasketViewEvent(String id, double price, int quantity) {
    if (mCriteoEventService != null) {
      BasketProduct basketProduct = new BasketProduct(id, price, quantity);
      BasketViewEvent basketViewEvent = new BasketViewEvent(basketProduct);
      mCriteoEventService.send(basketViewEvent);
    }
  }

  @ReactMethod
  public void criteoTransactionConfirmationEvent(String timeStampId, String id, double price, int quantity) {
    if (mCriteoEventService != null) {
      BasketProduct basketProduct = new BasketProduct(id, price, quantity);
      TransactionConfirmationEvent transactionConfirmationEvent = new TransactionConfirmationEvent(timeStampId,
          basketProduct);
      mCriteoEventService.send(transactionConfirmationEvent);
    }
  }

  private void criteoSetEmail(String email) {
    if (mCriteoEventService != null) {
      String eMail = computeMD5(email);
      mCriteoEventService.setEmail(eMail, EventService.EmailType.HASHED_MD5);
    }
  }

  private String computeMD5(final String s) {
    if (StringUtils.isNotBlank(s)) {
      final String MD5 = "MD5";
      try {
        // Create MD5 Hash
        MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
          String h = Integer.toHexString(0xFF & aMessageDigest);
          while (h.length() < 2)
            h = "0" + h;
          hexString.append(h);
        }
        return hexString.toString();

      } catch (Exception e) {

      }
    }
    return "";
  }
}