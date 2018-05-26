package no.woact.marchr16.menu;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Created by Christian on 05/04/2018.
public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

	@SuppressLint("StaticFieldLeak")
	private ImageView imageView = null;

	/**
	 * Setting imageView to the first element in ImageView[] given as param
	 * Passes data to helping method downloadImgFromHttp() bu using the tag set on the
	 * ImageView sent as param to the class
	 *
	 * @param imageViews required to be an array
	 * @return Bitmap to be set in onPostExecute()
	 */
	@Override
	protected Bitmap doInBackground(ImageView... imageViews) {
		this.imageView = imageViews[0];
		return downloadImgFromHttp((String) imageView.getTag());
	}

	/**
	 * Sets the picture presented in the ImageView to the Bitmap from doInBackground()
	 *
	 * @param bitmap
	 */
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
	}

	/**
	 * Sends a GET-request to a given url, this method only works on pictures
	 *
	 * @param url url to get data from
	 * @return a Bitmap of the picture
	 */
	private Bitmap downloadImgFromHttp(String url) {
		try {
			URL loadUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) loadUrl.openConnection();
			InputStream inputStream = connection.getInputStream();

			return BitmapFactory.decodeStream(inputStream);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return null;
	}
}
