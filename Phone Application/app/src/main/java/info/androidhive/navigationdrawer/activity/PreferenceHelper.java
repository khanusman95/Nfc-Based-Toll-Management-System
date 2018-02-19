package info.androidhive.navigationdrawer.activity;

/**
 * Created by Usman on 6/6/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String CNIC = "cnic";
    private final String NAME = "name";
    private final String EMAIL = "email";
    private final String PHONE = "phone";
    private final String ADDRESS = "address";
    private final String TOUCH = "touch";
    private final String CREDIT = "credit";

    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }


    public void putIsTouched(String touched) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(TOUCH, touched);
        edit.commit();
    }
    public String getIsTouched() {
        return app_prefs.getString(TOUCH, "0");
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.commit();
    }
    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putEmail(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, loginorout);
        edit.commit();
    }
    public String getEmail() {
        return app_prefs.getString(EMAIL, "");
    }

    public void putPhone(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PHONE, loginorout);
        edit.commit();
    }
    public String getPhone() {
        return app_prefs.getString(PHONE, "");
    }

    public void putAddress(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ADDRESS, loginorout);
        edit.commit();
    }
    public String getAddress() {
        return app_prefs.getString(ADDRESS, "");
    }

    public void putCnic(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CNIC, loginorout);
        edit.commit();
    }
    public String getCnic() {
        return app_prefs.getString(CNIC, "");
    }

    public void putCredits(String credits) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CREDIT, credits);
        edit.commit();
    }
    public String getCredits() {
        return app_prefs.getString(CREDIT, "");
    }

}