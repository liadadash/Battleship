package com.afeka.liadk.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afeka.liadk.battleship.Logic.Tile;

public class TileView extends View {

    private boolean drowned;

    public TileView(Context context) {
        super(context);
        drowned = false;
    }

    public void ClickMe(Tile.TileState state) {
        AnimationDrawable explosionAnimation;
        switch (state) {
            default:
            case INJURED_WITH_SHIPS:
                explosionAnimation = (AnimationDrawable) getResources().getDrawable(R.drawable.explosion_animation, null);
                break;
            case MISS:
                explosionAnimation = (AnimationDrawable) getResources().getDrawable(R.drawable.water_animation, null);
                break;
            case DROWNED:
                explosionAnimation = (AnimationDrawable) getResources().getDrawable(R.drawable.drowned_animation, null);
                drowned = true;
                break;
        }
        setBackground(explosionAnimation);
        explosionAnimation.start();
    }

    public boolean isDrowned() {
        return drowned;
    }
}
