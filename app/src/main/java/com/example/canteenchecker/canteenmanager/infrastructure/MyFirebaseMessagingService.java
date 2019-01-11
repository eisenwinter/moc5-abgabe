package com.example.canteenchecker.canteenmanager.infrastructure;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String REMOTE_MESSAGE_TYPE_KEY = "type";
    private static final String REMOTE_MESSAGE_TYPE_VALUE = "canteenDataChanged";

    private static final String RATINGS_CHANGED_INTENT_ACTION = "CanteenChangedAction";

    public static IntentFilter updatedRatingsMessage() {
        return new IntentFilter(RATINGS_CHANGED_INTENT_ACTION);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        if (REMOTE_MESSAGE_TYPE_VALUE.equals(data.get(REMOTE_MESSAGE_TYPE_KEY))) {
            Intent intent = new Intent(RATINGS_CHANGED_INTENT_ACTION);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

}

