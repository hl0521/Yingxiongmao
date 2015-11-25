package liufantech.com.yingxiongmao.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;


/**
 * Created by HL0521 on 2015/11/15.
 * 暂时抛充，没用
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

    private static final String TAG = "DownloadImageTask";

    private String mImageUrl;

    private ImageView mImageView;

    // used for test
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public DownloadImageTask(ImageView imageView) {
        super();
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // TODO Auto-generated method stub
        mImageUrl = params[0];
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = null;

        System.out.println("=============================000000000=======================");

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            final Request request = new Request.Builder().url(mImageUrl).build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            inputStream = response.body().byteStream();

            byte[] buffer = new byte[1024];
            int lengh;
            while ((lengh = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, lengh);
            }
            byteArrayOutputStream.flush();

            InputStream isTemp1 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            bitmap = BitmapFactory.decodeStream(isTemp1);
            isTemp1.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mImageView.setImageBitmap(bitmap);
    }

}
