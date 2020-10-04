package skarlat.dev.ecoproject.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.VectorDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import skarlat.dev.ecoproject.R;


public class AdviceView extends FrameLayout {
	private ShapeDrawable mDrawable;
	private Path path;
	Paint paint;
	
	public AdviceView(Context context) {
		super(context);
	}
	
	public AdviceView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		 path = new Path();
		 paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(getResources().getColor(R.color.colorBolotoButton));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(4);
		path.reset();
		path.moveTo(2, 0);  // перемещаем курсор наверх
		path.lineTo(2, getHeight() - 30);  // рисуем линию вниз
		path.moveTo(32, getHeight() - 30); // перемещаем курсор для рисования дуги
		path.arcTo(2, getHeight() - 62, 62, getHeight() - 2,
				90, 90, true); // рисуем дугу
		path.moveTo(getWidth(), getHeight() - 2); // перемещаем курсор в правый нижний угол
		path.lineTo(32, getHeight() - 2); // рисуем влево горизонтальный отрезок
		canvas.drawPath(path, paint); // само действие "нарисовать"
		path.reset(); // обязательный сброс пути
	}
	
	private void logs(){
//		Log.d("CustomView", "canvas weight: " + canvas.getWidth() + " high: " + canvas.getHeight());
		Log.d("CustomView", "getX: " +  getX() + " getY()" + getY());
		Log.d("CustomView", "getPivotX: " + getPivotX() + "getPivotY: " + getPivotY());
		Log.d("CustomView", "width: " + getWidth() + "\nheight: " + getHeight());
		Log.d("CustomView", "Measured width: " + getMeasuredWidth() + "\nMeasured height: "
				                    + getMeasuredHeight());
		Log.d("CustomView", "pxFromDp(8) : " + pxFromDp(8));
	}
	
	/**
	 * Конвертация px в dp
	 * @param px Значение в пикслеях
	 * @return Значение в dp (density-independent-pixel)
	 */
	private float dpFromPx(float px) {
		return px/ this
				           .getResources()
				           .getDisplayMetrics()
				           .density;
	}
	
	/**
	 *  Конвертация dp в px
	 * @param dp Значение dp
	 * @return Значение в пикселях
	 */
	private float pxFromDp(float dp) {
		return dp * this
				            .getResources()
				            .getDisplayMetrics()
				            .density;
	}
}
