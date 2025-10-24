package acab.naiveha.lockscreen;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class QuickSettingsTileService extends TileService {
    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Tile tile = getQsTile();
        tile.setSubtitle("Tap to turn off");
        tile.setState(Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    @Override
    public void onClick() {
        if (isLocked()) {
            return;
        }
        super.onClick();
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityAndCollapse(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE));
    }

    @Override
    public void onStartListening() {
        Tile tile = getQsTile();
        if (isLocked()){
            tile.setSubtitle("Screen is locked");
            tile.setState(Tile.STATE_UNAVAILABLE);
        } else {
            tile.setSubtitle("Tap to turn off");
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        Tile tile = getQsTile();
        if (isLocked()){
            tile.setSubtitle("Screen is locked");
            tile.setState(Tile.STATE_UNAVAILABLE);
        } else {
            tile.setSubtitle("Tap to turn off");
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
        super.onStopListening();
    }

    // Without this, the tile may not be in the correct state after boot
    @Override
    public IBinder onBind(Intent intent) {
        TileService.requestListeningState(this, new ComponentName(this, QuickSettingsTileService.class));
        return super.onBind(intent);
    }

}