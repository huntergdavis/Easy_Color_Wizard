package com.hunterdavis.easycolorwizard;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class EasyColorWizard extends Activity {

	int color1 = 0;
	int color2 = 0;
	int color3 = 0;
	int resultColor = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		// listener for frequency button
		OnClickListener regenListener = new OnClickListener() {
			public void onClick(View v) {
					genInitialColors();
				}

			};
		
		
		Button myregenbutton = (Button) findViewById(R.id.resetbutton);
		myregenbutton.setOnClickListener(regenListener);
		
		
		// image listeners
		// photo on click listener
		ImageView imageOne = (ImageView) findViewById(R.id.ImageViewOne);
		ImageView imageTwo = (ImageView) findViewById(R.id.ImageViewTow);
		ImageView imageThree = (ImageView) findViewById(R.id.ImageViewThree);
		
		imageOne.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResultColor(color1);
				setOtherColorsFromResult();
			}
		});
		
		imageTwo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResultColor(color2);
				setOtherColorsFromResult();
			}
		});
		
		imageThree.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResultColor(color3);
				setOtherColorsFromResult();
			}
		});
		
		
		

		genInitialColors();

		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
	}

	public Boolean genInitialColors() {
		// set first initial color
		// set second initial color
		// set third initial color
		// set result color
		int randomColor = randomFromRange(0, Integer.MAX_VALUE);
		setResultColor(randomColor);
		setOtherColorsFromResult();
		return true;

	}

	public Boolean setOtherColorsFromResult() {
		ImageView imgviewone = (ImageView) findViewById(R.id.ImageViewOne);
		ImageView imgviewtwo = (ImageView) findViewById(R.id.ImageViewTow);
		ImageView imgviewthree = (ImageView) findViewById(R.id.ImageViewThree);

		color1 = getRedDistance();
		color2 = getBlueDistance();
		color3 = getGreenDistance();
		genColor(imgviewone, color1);
		genColor(imgviewtwo, color2);
		genColor(imgviewthree, color3);
		return true;

	}

	int getRedDistance() {
		int redcolor = Color.red(resultColor);
		int bluecolor = Color.blue(resultColor);
		int greencolor = Color.green(resultColor);
		// return a random number + red value and old value
		int randomRed = randomFromRange(0, 255);
		randomRed = (randomRed + redcolor) % 255;
		return Color.rgb(randomRed, greencolor, bluecolor);
	}

	int getBlueDistance() {
		// return a random number + blue value and old value
		int redcolor = Color.red(resultColor);
		int bluecolor = Color.blue(resultColor);
		int greencolor = Color.green(resultColor);
		// return a random number + red value and old value
		int randomBlue = randomFromRange(0, 255);
		randomBlue = (randomBlue + bluecolor) % 255;
		return Color.rgb(redcolor, greencolor, randomBlue);

	}

	int getGreenDistance() {
		// return a random number + green value and old value
		int redcolor = Color.red(resultColor);
		int bluecolor = Color.blue(resultColor);
		int greencolor = Color.green(resultColor);
		int randomGreen = randomFromRange(0, 255);
		randomGreen = (randomGreen + greencolor) % 255;
		return Color.rgb(redcolor, randomGreen, bluecolor);
	}

	public Boolean setResultColor(int color) {
		resultColor = color;
		ImageView imgview = (ImageView) findViewById(R.id.ImageViewResult);
		genColor(imgview, color);

		TextView colorText = (TextView) findViewById(R.id.colortext);
		int red = Color.red(resultColor);
		int blue = Color.blue(resultColor);
		int green = Color.green(resultColor);
		String redHexString = Integer.toHexString(red);
		String blueHexString = Integer.toHexString(blue);
		String greenHexString = Integer.toHexString(green);
		
		// test for single value and preface with 0 if so
		if(redHexString.length() == 1)
		{
			redHexString = "0"+redHexString;
		}
		if(blueHexString.length() == 1)
		{
			blueHexString = "0"+blueHexString;
		}
		if(greenHexString.length() == 1)
		{
			greenHexString = "0"+greenHexString;
		}

		String textString = "R: " + red + " G:" + green + " B:" + blue + " Hex:#"
				+ redHexString + greenHexString
				+ blueHexString;
		colorText.setText(textString);

		return true;
	}

	public Boolean genColor(ImageView imgview, int Color) {

		// create a width*height long int array and populate it with random 1 or
		// 0
		// final Random myRandom = new Random();
		int rgbSize = 100 * 100;
		int[] rgbValues = new int[rgbSize];
		for (int i = 0; i < rgbSize; i++) {
			rgbValues[i] = Color;
		}

		// create a width*height bitmap
		BitmapFactory.Options staticOptions = new BitmapFactory.Options();
		staticOptions.inSampleSize = 2;
		Bitmap staticBitmap = Bitmap.createBitmap(rgbValues, 100, 100,
				Bitmap.Config.RGB_565);

		// set the imageview to the static
		imgview.setImageBitmap(staticBitmap);

		return true;

	}

	int randomFromRange(int low, int high) {
		if (low == high) {
			return low;
		}
		final Random myRandom = new Random();
		Float myNewRandomPercent = myRandom.nextFloat();
		Integer actualResult = (int) Math.round(low
				+ (Math.abs((high - low)) * (myNewRandomPercent)));
		return actualResult;
	}

}