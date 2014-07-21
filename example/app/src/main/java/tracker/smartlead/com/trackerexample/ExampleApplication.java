package tracker.smartlead.com.trackerexample;

import android.app.Application;
import android.util.Log;

import ru.actionpay.tracker.SLAction;
import ru.actionpay.tracker.SLTracker;

public class ExampleApplication extends Application implements SLTracker.Delegate {

  public static final String LOG_TAG = ExampleApplication.class.getCanonicalName();

  @Override
  public void onCreate() {
    super.onCreate();

    SLTracker tracker = SLTracker.initTest(getApplicationContext());
    tracker.setDelegate(this);
    tracker.setUpdateInterval(60 * 1000, true);
  }

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
