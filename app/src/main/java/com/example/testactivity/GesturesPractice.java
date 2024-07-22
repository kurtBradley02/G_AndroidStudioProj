package com.example.testactivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

public class GesturesPractice extends AppCompatActivity {

    private View draggableBox1, draggableBox2, draggableBox3;
    private CustomScrollView customScrollView;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures_practice);

        draggableBox1 = findViewById(R.id.draggableBox1);
        draggableBox2 = findViewById(R.id.draggableBox2);
        draggableBox3 = findViewById(R.id.draggableBox3);
        customScrollView = findViewById(R.id.scrollView);

        gestureDetector = new GestureDetector(this, new GestureListener());

        setUpDraggableBox(draggableBox1);
        setUpDraggableBox(draggableBox2);
        setUpDraggableBox(draggableBox3);
    }

    private void setUpDraggableBox(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            private float dX, dY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                switch (MotionEventCompat.getActionMasked(event)) {
                    case MotionEvent.ACTION_DOWN:
                        customScrollView.setEnableScrolling(false);
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        dX = v.getX() - initialTouchX + v.getWidth() / 2;
                        dY = v.getY() - initialTouchY + v.getHeight() / 2;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                                .x(event.getRawX() + dX - v.getWidth() / 2)
                                .y(event.getRawY() + dY - v.getHeight() / 2)
                                .setDuration(0)
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        customScrollView.setEnableScrolling(true);
                        break;
                }
                return true;
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View longPressedView = null;

            for (View view : new View[]{draggableBox1, draggableBox2, draggableBox3}) {
                if (isViewTouched(view, e.getRawX(), e.getRawY())) {
                    longPressedView = view;
                    break;
                }
            }

            if (longPressedView != null) {
                Toast.makeText(GesturesPractice.this, "Long pressed!", Toast.LENGTH_SHORT).show();
            }
        }

        private boolean isViewTouched(View view, float x, float y) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            return x >= location[0] && x <= location[0] + view.getWidth() &&
                    y >= location[1] && y <= location[1] + view.getHeight();
        }
    }
}
