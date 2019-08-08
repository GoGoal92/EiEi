package market.goldandgo.videonew1.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class RoundedCornerImageView extends ImageView {
    private int radius = 50;

    public RoundedCornerImageView(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(0, 0, w, h), radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoundedCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRadius(int radius){
        this.radius = radius;
        this.invalidate();
    }
}
