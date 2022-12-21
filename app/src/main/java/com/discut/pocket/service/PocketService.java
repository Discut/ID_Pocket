package com.discut.pocket.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.service.quicksettings.TileService;
import android.widget.RemoteViews;

import com.discut.pocket.R;
import com.discut.pocket.view.BootActivity;
import com.discut.pocket.view.QuickSettingActivity;

public class PocketService extends TileService {
    public PocketService() {
        super();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
/*        ModalBottomSheet modalBottomSheet = new ModalBottomSheet();

        modalBottomSheet.show(getSupportFragmentManager(), ModalBottomSheet.TAG);*/
        Intent intent = new Intent(this, BootActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
