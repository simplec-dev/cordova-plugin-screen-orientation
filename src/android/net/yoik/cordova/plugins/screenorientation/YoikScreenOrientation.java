package net.yoik.cordova.plugins.screenorientation;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.ViewGroup;

public class YoikScreenOrientation extends CordovaPlugin {

    private static final String TAG = "YoikScreenOrientation";

    /**
     * Screen Orientation Constants
     */

    private static final String UNLOCKED = "unlocked";
    private static final String PORTRAIT_PRIMARY = "portrait-primary";
    private static final String PORTRAIT_SECONDARY = "portrait-secondary";
    private static final String LANDSCAPE_PRIMARY = "landscape-primary";
    private static final String LANDSCAPE_SECONDARY = "landscape-secondary";
    private static final String PORTRAIT = "portrait";
    private static final String LANDSCAPE = "landscape";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        Log.d(TAG, "execute action: " + action);

        // Route the Action
        if (action.equals("screenOrientation")) {
            return routeScreenOrientation(args, callbackContext);
        }

        // Action not found
        callbackContext.error("action not recognised");
        return false;
    }

    private boolean routeScreenOrientation(JSONArray args, CallbackContext callbackContext) {

        String action = args.optString(0);

        if (action.equals("set")) {

            String orientation = args.optString(1);

            Log.d(TAG, "Requested ScreenOrientation: " + orientation);

            Activity activity = cordova.getActivity();

        	ViewGroup.LayoutParams params = this.webView.getLayoutParams();
        	int w = (params.width>params.height)?params.width:params.height; /// w is the landscape width
        	int h = (params.width>params.height)?params.height:params.width; /// h is the landscape height

            if (orientation.equals(UNLOCKED)) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            } else if (orientation.equals(LANDSCAPE_PRIMARY)) {
            	params.width = w;
            	params.height = h;
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (orientation.equals(PORTRAIT_PRIMARY)) {
            	params.width = h;
            	params.height = w;
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (orientation.equals(LANDSCAPE)) {
            	params.width = w;
            	params.height = h;
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else if (orientation.equals(PORTRAIT)) {
            	params.width = h;
            	params.height = w;
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            } else if (orientation.equals(LANDSCAPE_SECONDARY)) {
            	params.width = w;
            	params.height = h;
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            } else if (orientation.equals(PORTRAIT_SECONDARY)) {
            	params.width = h;
            	params.height = w;
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            }

        	this.webView.setLayoutParams(params);
            callbackContext.success();
            return true;

        } else {
            callbackContext.error("ScreenOrientation not recognised");
            return false;
        }
    }
}