package com.saki.ui;

//
// Decompiled by CFR - 978ms
//
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView
extends ImageView {
	private  Paint paint = new Paint();

	public CircleImageView(Context context) {
		this(context, (AttributeSet)null);
	}

	public CircleImageView(Context context, AttributeSet attributeSet) {
		this(context, attributeSet, 0);
	}

	public CircleImageView(Context context, AttributeSet attributeSet, int n) {
		super(context, attributeSet, n);
	}

	private Bitmap getCircleBitmap(Bitmap bitmap, int n) {
		Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		this.paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		this.paint.setColor(-12434878);
		n = bitmap.getWidth();
		canvas.drawCircle((float)(n / 2), (float)(n / 2), (float)(n / 2), this.paint);
		this.paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, this.paint);
		return bitmap2;
	}

	/*
	 * Enabled force condition propagation
	 * Lifted jumps to return sites
	 */
	@Override
	protected void onDraw(final Canvas canvas) {
		final Drawable drawable = this.getDrawable();
		if (drawable != null) {
			final Bitmap circleBitmap = this.getCircleBitmap(((BitmapDrawable)drawable).getBitmap(), 14);
			final Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
			final Rect rect2 = new Rect(0, 0, this.getWidth(), this.getHeight());
			this.paint.reset();
			canvas.drawBitmap(circleBitmap, rect, rect2, this.paint);
		}
		else {
			super.onDraw(canvas);
		}
	}
}

