package liufantech.com.yingxiongmao.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.content.cache.PicRamCache;
import liufantech.com.yingxiongmao.content.cache.SDcardCache;
import liufantech.com.yingxiongmao.custom.base.NetworkUtil;
import liufantech.com.yingxiongmao.custom.manager.OkHttpClientManager;
import liufantech.com.yingxiongmao.main.MainConstant;


/**
 * Created by HL0521 on 2015/11/15.
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
        Bitmap bitmap;

        bitmap = SDcardCache.get(MainConstant.PIC_CACHE).getAsBitmap(mImageUrl);

        if (bitmap != null) {
            PicRamCache.getInstance().getmLruCache().put(mImageUrl, bitmap);
            return bitmap;
        } else {
            InputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            System.out.println("=============================000000000=======================");

            if (!NetworkUtil.getInstance().isNetworkConnected()) {
                System.out.println("========" + TAG + "=========没有连网=======");
                return null;
            }

            try {
                Response response = OkHttpClientManager.getSync(mImageUrl);
                inputStream = response.body().byteStream();

                byte[] buffer = new byte[1024];
                int lengh;
                while ((lengh = inputStream.read(buffer)) > -1) {
                    byteArrayOutputStream.write(buffer, 0, lengh);
                }
                byteArrayOutputStream.flush();

                InputStream isTemp = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                bitmap = BitmapFactory.decodeStream(isTemp);
                isTemp.close();
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

                if (bitmap != null) {
                    PicRamCache.getInstance().getmLruCache().put(mImageUrl, bitmap);
                    SDcardCache.get(MainConstant.PIC_CACHE).put(mImageUrl, bitmap);
                }

                return bitmap;
            }
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
        if (bitmap == null) {
            mImageView.setImageResource(R.drawable.icon_pink_like);
        } else {
            mImageView.setImageBitmap(bitmap);
        }
    }

}
