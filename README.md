ActionpayAndroidTracker
=======================

SDK для внедрения в сторонние проекты с целью отслеживания установки приложения а также целевых действий пользователей.

Внедрение
=========

Подключение SDK
---------------

Подключение осуществляется в два этапа.
Сначало добавляем в **repositories** ссылку на maven репозиторий **actionpay.tracker**

```groovy
allprojects {
  repositories {
    // ...
    maven { url "https://github.com/AdOnWeb/ActionpayAndroidTracker/raw/master" }
    // ...
  }
}
```

Затем добавляем в зависимости sdk

```groovy
dependencies {
  // ...
  compile 'ru.actionpay.tracker:SmartTracker:0.1@aar'
  // ...
}
```

Затем инициализируем трэкер.

```java
public class ExampleApplication extends Application implements SLTracker.Delegate {

  public static final String LOG_TAG = ExampleApplication.class.getCanonicalName();

  @Override
  public void onCreate() {
    super.onCreate();

    // Инициализируем параметры трэкера
    SLTracker tracker = SLTracker.init("<my_app_id>", getApplicationContext());
    // Установить делегат для контроля цикла обработки событий
    tracker.setDelegate(this);
    // Установим время автоматической отсылки событий
    tracker.setUpdateInterval(60 * 1000 /* каждые 60 секунд */, true /* повторять */);
  }

  // Методы делегата

  @Override
  public boolean onBeginTrack(Object o, SLAction slAction) {
    Log.d(LOG_TAG, "onBeginTrack: " + slAction.getName());
    return true;
  }

  @Override
  public boolean onBeginSend(Object o, SLAction slAction) {
    Log.d(LOG_TAG, "onBeginSend: " + slAction.getName());
    return true;
  }

  @Override
  public void onEndSend(Object o, SLAction slAction) {
    Log.d(LOG_TAG, "onEndSend: " + slAction.getName() + " => " + slAction.isSent());
  }
}
```

Искользование
-------------

Событие происходит только один раз

```java
SLTracker.sharedTracker().trackAction("login"); // Повторно событие не отработает
```

Событие отрабатывает каждый раз

```java
SLTracker.sharedTracker().trackAction("award", "10", null, null);
```

Методы отслеживания событий

```java
  // name – имя события
  // price – используется при регистрации событий оплаты (если указан этот параметр событие отрабатывает каждый раз)
  // orderId – Идетификатор заказа (Используется совместно с price)
  // apid – Постфикс идентификатор (если хотите указать свой постфикс к имени события, в противном случае он будет формироваться автоматически)

  public boolean trackAction(String name, String price, String orderId, String apid) throws Exception;

  public boolean trackAction(String name, float price, String orderId, String apid) throws Exception;

  public boolean trackAction(String name, float price, String orderId) throws Exception;

  // Простое событие вызывается единожды
  public boolean trackAction(String name) throws Exception;
```

Обратная связь
==============



