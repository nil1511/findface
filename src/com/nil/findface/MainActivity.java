package com.nil.findface;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private int count = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView im = (ImageView) findViewById(R.id.imageView1);
		AssetManager assetManager = getAssets();
		try {
			InputStream i = assetManager.open("img.jpg");
			Options options = new BitmapFactory.Options();
			// options.inSampleSize=8;
			options.inPreferredConfig = Config.RGB_565;
			Bitmap bitmapImage = BitmapFactory.decodeStream(i, null, options);
			Face[] faces = new Face[4];
			FaceDetector fd = new FaceDetector(bitmapImage.getWidth(),
					bitmapImage.getHeight(), count);
			int l = fd.findFaces(bitmapImage, faces);
			bitmapImage = bitmapImage.copy(Config.RGB_565, true);
			Canvas c = new Canvas(bitmapImage);
			System.out.println(l);
			Paint mP = new Paint();
			mP.setColor(Color.GREEN);
			mP.setStyle(Paint.Style.STROKE);
			mP.setStrokeWidth(3);

			for (int j = 0; j < l; j++) {
				Face face = faces[j];
				PointF midPoint = new PointF();
				face.getMidPoint(midPoint);
				System.out.println(midPoint);
				float eyeDistance = face.eyesDistance();
				System.out.println(eyeDistance);
				RectF a = new RectF(midPoint.x - eyeDistance, midPoint.y
						- eyeDistance, midPoint.x + eyeDistance, midPoint.y
						+ (1.5f * eyeDistance));
				c.drawRect(a, mP);
				System.out.println(face.confidence());
			}
			im.setImageBitmap(bitmapImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}