package Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.JsonObject;

public class SharePreferenceHelper {

	public static final String PREFERENCE_LIVE_ROOM_INFO = "live_room_info";

	public static void saveSharePreferenceFromString(Context context, String name, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void saveSharePreferenceFromBoolean(Context context, String name, String key, boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void saveSharePreferenceFromInteger(Context context, String name, String key, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static boolean getSharePreferenceFromBoolean(Context context, String name, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, false);
	}

	public static String getSharePreferenceFromString(Context context, String name, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");

	}

	public static int getSharePreferenceFromInteger(Context context, String name, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		try {
			return sharedPreferences.getInt(key, 0);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}

	public static int getSharePreferenceFromNegativeInteger(Context context, String name, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		try {
			return sharedPreferences.getInt(key, -1);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}

	}

	/**
	 * 保存用户信息
	 *
	 * @param jo
	 * @param x  1代表登录，2代表获取用户信息
	 */
	public static boolean saveUserInfo(Context context, JsonObject jo, int x) {
		switch (x) {
			case 1:
				try {
					SharePreferenceHelper.saveSharePreferenceFromBoolean(context, "user_info", "isLogin", true);
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "uid", jo.get("uid").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "avatar", jo.get("avatar").getAsString());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "nickname", jo.get("nickname").getAsString());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "avatar_big", jo.get("avatar_big").getAsString());
					if (jo.has("token")) {
						jo = jo.get("token").getAsJsonObject();
						SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "login_token", jo.get("login_token").getAsString());
						SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "refresh_token", jo.get("refresh_token").getAsString());
						SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "login_token_expiresin", (int) (System.currentTimeMillis() / 1000 + jo.get("login_token_expiresin").getAsInt()));
						SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "refresh_token_expiresin", (int) (System.currentTimeMillis() / 1000 + jo.get("refresh_token_expiresin").getAsInt()));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				break;
			case 2:
				try {
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "uid", jo.get("uid").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "avatar", jo.get("avatar").getAsString());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "nickname", jo.get("nickname").getAsString());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "avatar_big", jo.get("avatar_big").getAsString());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "friends_count", jo.get("friends_count").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "followers_count", jo.get("followers_count").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "level", jo.get("level").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "exp", jo.get("exp").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "verified", jo.get("verified").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "tickets", jo.get("tickets").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "history_tickets", jo.get("history_tickets").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "sent_tickets", jo.get("sent_tickets").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromInteger(context, "user_info", "coins", jo.get("coins").getAsInt());
					SharePreferenceHelper.saveSharePreferenceFromString(context, "user_info", "gender", jo.get("gender").getAsString());
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				break;

		}
		return true;
	}

}
