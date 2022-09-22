package com.discut.pocket.component;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;

public class RecyclerAnimation extends ItemTouchHelper.SimpleCallback {
    private final Drawable trashIcon;
    private final int circleColor = Color.BLACK;
    private final Paint circlePaint;
    private final int reverseSurfaceColor = Color.GRAY;
    private final float CIRCLE_ACCELERATION = 6f;
    private Context context;
    private SwipeSelectMode left;
    private SwipeSelectMode right;
    private SwipeListener listener;
    private Drawable leftIcon;
    private Drawable rightIcon;
    private int leftColor;
    private int rightColor;

    public RecyclerAnimation(Context context, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        context = context;
        trashIcon = ContextCompat.getDrawable(context, R.drawable.ic_add);
    }

    public RecyclerAnimation(Context context) {
        this(context, 0, ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

/*        if (dX >= -100 && dX<=100){
            viewHolder.itemView.scrollTo((int) (dX>0?dX:-dX),0);

            //super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }else {
            super.onChildDraw(c, recyclerView, viewHolder, dX>0?100:-100, dY, actionState, isCurrentlyActive);

        }*/


        // We don't want to do anything unless the view is being swiped
        Log.d(TAG, "onChildDraw: dx" + dX);
        if (dX == 0f) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
            // 向后滑动
        } else if (dX > 0f) {
            int left = viewHolder.itemView.getLeft();
            int top = viewHolder.itemView.getTop();
            int right = viewHolder.itemView.getRight();
            int bottom = viewHolder.itemView.getBottom();
            // Android draws with the center of the axis in the top left corner
            // Android绘制的轴的中心在左上角
            int width = right - left;
            int height = bottom - top;
            // Saves the canvas state to restore it at the end
            //保存画布状态以在结束时恢复它
            int saveCount = c.save();

            int iconColor = circleColor;
            // Limits the child view to the space left by the viewholder while its being swiped
            //将子视图限制为视图持有者在被滑动时留下的空间
            c.clipRect(left, top, left + dX, bottom);
            c.drawColor(rightColor);

            // The percentage that the child view has moved in the X axis
            //子视图在X轴上移动的百分比
            float progress = dX / width;
            float swipeThreshold = getSwipeThreshold(viewHolder);
            float iconPopThreshold = swipeThreshold + 0.125f;
            float iconPopFinishedThreshold = iconPopThreshold + 0.125f;
            float circleRadius = 0f;
            float iconScale;


            if (progress >= 0f && progress <= swipeThreshold) {
                iconScale = 1f - (progress * 0.2f);
            } else {
                // The radius is the progress relative to the swipeThreshold multiplied by the width and the acceleration
                // The usage of the width allows the radius to adapt to the different screen sizes dynamically in every device
                circleRadius = (progress - swipeThreshold) * width * CIRCLE_ACCELERATION;
                iconColor = reverseSurfaceColor;
                if (progress >= iconPopThreshold && progress <= iconPopFinishedThreshold) {
                    iconScale = 1.2f - progress * 0.2f;
                } else {
                    iconScale = 1f;
                }
            }

            Drawable actionIcon = this.rightIcon;
            if (actionIcon != null) {
                float centerInXAxis = left + (float) 64 + (float) actionIcon.getIntrinsicWidth() / 2.0F;
                float centerInYAxis = top + (float) 64 + (float) actionIcon.getIntrinsicHeight() / 2.0F;
                float iconW = (float) actionIcon.getIntrinsicWidth() / 2;
                float iconH = (float) actionIcon.getIntrinsicHeight() / 2;
                float xOffset = dX;
                centerInXAxis += (float) (xOffset - (1.5 * actionIcon.getIntrinsicWidth()));
                centerInXAxis *= (1 - (0.4 * progress));
                if (centerInXAxis >= width / 2f) {
                    centerInXAxis = right / 2f;
                }
                centerInXAxis += 32;
                int iconL = (int) (centerInXAxis - iconW * iconScale);
                int iconT = (int) (centerInYAxis - iconH * iconScale);
                int iconR = (int) (centerInXAxis + iconW * iconScale);
                int iconB = (int) (centerInYAxis + iconH * iconScale);
                actionIcon.setBounds(iconL, iconT, iconR, iconB);
                actionIcon.setColorFilter((ColorFilter) (new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)));
                if (circleRadius > (float) 0) {
                    c.drawCircle(centerInXAxis, centerInYAxis, circleRadius, this.circlePaint);
                }
                actionIcon.draw(c);
            }

            c.restoreToCount(saveCount);
            // 向前滑动
        } else if (dX < 0f) {
            int left = viewHolder.itemView.getLeft();
            int top = viewHolder.itemView.getTop();
            int right = viewHolder.itemView.getRight();
            int bottom = viewHolder.itemView.getBottom();
            // Android绘制的轴的中心在左上角
            int width = right - left;
            int height = bottom - top;
            //保存画布状态以在结束时恢复它
            int saveCount = c.save();

            int iconColor = circleColor;
            //将子视图限制为视图持有者在被滑动时留下的空间
            c.clipRect(right + dX, top, right, bottom);
            c.drawColor(leftColor);

            //子视图在X轴上移动的百分比
            float progress = -dX / width;
            float swipeThreshold = getSwipeThreshold(viewHolder);
            float iconPopThreshold = swipeThreshold + 0.125f;
            float iconPopFinishedThreshold = iconPopThreshold + 0.125f;
            float circleRadius = 0f;
            float iconScale;


            if (progress >= 0f && progress <= swipeThreshold) {
                iconScale = 1f - (progress * 0.2f);
            } else {
                // The radius is the progress relative to the swipeThreshold multiplied by the width and the acceleration
                // The usage of the width allows the radius to adapt to the different screen sizes dynamically in every device
                circleRadius = (progress - swipeThreshold) * width * CIRCLE_ACCELERATION;
                iconColor = reverseSurfaceColor;
                if (progress >= iconPopThreshold && progress <= iconPopFinishedThreshold) {
                    iconScale = 1.2f - progress * 0.2f;
                } else {
                    iconScale = 1f;
                }
            }

            Drawable actionIcon = this.leftIcon;
            if (actionIcon != null) {
                float centerInXAxis = right - (float) 64 - (float) actionIcon.getIntrinsicWidth() / 2.0F;
                float centerInYAxis = top + (float) 64 + (float) actionIcon.getIntrinsicHeight() / 2.0F;
                float iconW = (float) actionIcon.getIntrinsicWidth() / 2;
                float iconH = (float) actionIcon.getIntrinsicHeight() / 2;
                float xOffset = dX;
                centerInXAxis += (float) (xOffset + (1.5 * actionIcon.getIntrinsicWidth()));
                centerInXAxis *= (1 + (0.4 * progress));
                if (centerInXAxis <= width / 2f) {
                    centerInXAxis = right / 2f;
                }
                centerInXAxis -= 32;
                int iconR = (int) (centerInXAxis + iconW * iconScale);
                int iconT = (int) (centerInYAxis - iconH * iconScale);
                int iconL = (int) (centerInXAxis - iconW * iconScale);
                int iconB = (int) (centerInYAxis + iconH * iconScale);
                actionIcon.setBounds(iconL, iconT, iconR, iconB);
                actionIcon.setColorFilter((ColorFilter) (new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)));
                if (circleRadius > (float) 0) {
                    c.drawCircle(centerInXAxis, centerInYAxis, circleRadius, this.circlePaint);
                }
                actionIcon.draw(c);
            }

            c.restoreToCount(saveCount);
        }

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.setIsRecyclable(false);
        if (listener == null) {
            return;
        }
        if (direction == ItemTouchHelper.LEFT) {
            listener.onLeftSwipe(viewHolder, left);
        } else {
            listener.onRightSwipe(viewHolder, right);
        }
    }

    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        Log.d(TAG, "onMoved: 已经移动了");
    }

    public RecyclerAnimation setMode(@NonNull SwipeSelectMode left, @NonNull SwipeSelectMode right) {
        this.left = left;
        this.right = right;
        return this;
    }

    public RecyclerAnimation setListener(@NonNull SwipeListener listener) {
        this.listener = listener;
        return this;
    }

    public RecyclerAnimation setIcon(Drawable left, Drawable right) {
        this.leftIcon = left;
        this.rightIcon = right;
        return this;
    }

    public RecyclerAnimation setBackColor(@ColorInt int left, @ColorInt int right) {
        this.leftColor = left;
        this.rightColor = right;
        return this;
    }

    public interface SwipeListener {
        void onLeftSwipe(RecyclerView.ViewHolder view, SwipeSelectMode mode);

        void onRightSwipe(RecyclerView.ViewHolder view, SwipeSelectMode mode);
    }

}
