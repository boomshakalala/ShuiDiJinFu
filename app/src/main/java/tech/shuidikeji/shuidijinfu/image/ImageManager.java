package tech.shuidikeji.shuidijinfu.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tech.shuidikeji.shuidijinfu.image.support.CircleBorderTransformation;
import tech.shuidikeji.shuidijinfu.image.support.ImageListener;
import tech.shuidikeji.shuidijinfu.image.support.LoadOption;
import tech.shuidikeji.shuidijinfu.utils.FileUtils;


public class ImageManager {

    public static final boolean DEFAULT_SHOW_TRANSITION = true;
    public static final int DEFAULT_LOADING_IMAGE = 0;
    public static final int DEFAULT_ERROR_IMAGE = 0;

    private static ImageManager mImageManager;
    private Context mContext;
    private ExecutorService cacheThreadPool;

    private ImageManager(){
    }

    public static ImageManager getInstance(){
        synchronized (ImageManager.class){
            if (mImageManager == null){
                mImageManager = new ImageManager();
            }
            return mImageManager;
        }
    }

    public void init(Context context) {
        mContext = context;
    }

    public void loadNet(String url, ImageView imageView) {
        load(Glide.with(imageView.getContext()).load(url), null).into(imageView);
    }

    public void loadNet(String url, ImageView imageView, LoadOption loadOption) {
        load(Glide.with(imageView.getContext()).load(url), loadOption).into(imageView);
    }



    public void loadRes(int resId, ImageView imageView) {
        load(Glide.with(imageView.getContext()).load(resId), null).into(imageView);
    }

    public void loadRes(int resId, ImageView imageView, LoadOption loadOption) {
        load(Glide.with(imageView.getContext()).load(resId), loadOption).into(imageView);
    }

    public void loadAsset(String assetName, ImageView imageView) {
        load(Glide.with(imageView.getContext()).load("file:///android_asset/" + assetName), null).into(imageView);
    }

    public void loadAsset(String assetName, ImageView imageView, LoadOption loadOption) {
        load(Glide.with(imageView.getContext()).load("file:///android_asset/" + assetName), loadOption).into(imageView);
    }

    public void loadFile(File file, ImageView imageView) {
        load(Glide.with(imageView.getContext()).load(file), null).into(imageView);
    }

    public void loadFile(File file, ImageView imageView, LoadOption loadOption) {
        load(Glide.with(imageView.getContext()).load(file), loadOption).into(imageView);
    }

    public void preLoad(String url) {
        Glide.with(mContext).load(url).preload();
    }

    public void getBitmap(Context context, String url, final ImageListener<Bitmap> imageListener) {
        Glide.with(context).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                if (imageListener != null) {
                    imageListener.onFail(e);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (imageListener != null) {
                    imageListener.onSuccess(resource);
                }
                return false;
            }
        }).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    public void downLoadImage(final Context context, final String url, final File targetFile, final ImageListener<File> imageListener) {
        if (cacheThreadPool == null) {
            cacheThreadPool = Executors.newCachedThreadPool();
        }
        cacheThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    File sourceFile = Glide.with(context).asFile().load(url).submit().get();
                    if (FileUtils.copyFile(sourceFile, targetFile) && imageListener != null) {
                        imageListener.onSuccess(targetFile);//回调在后台线程
                    }
                } catch (Exception exception) {
                    if (imageListener != null) {
                        imageListener.onFail(exception);//回调在后台线程
                    }
                }
            }
        });
    }

    public void clearMemoryCache() {
        //Glide要求清除内存缓存需在主线程执行
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(mContext).clearMemory();
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Glide.get(mContext).clearMemory();
                }
            });
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0M";
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public void clearDiskCache() {
        //Glide要求清除内存缓存需在后台程执行
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(mContext).clearDiskCache();
                }
            }).start();
        } else {
            Glide.get(mContext).clearDiskCache();
        }
    }

    private RequestBuilder load(RequestBuilder requestBuilder, LoadOption loadOption) {

        RequestOptions requestOptions = new RequestOptions();

        //使用默认的配置进行设置
        if (loadOption == null) {
            if (DEFAULT_SHOW_TRANSITION) {
                requestBuilder.transition(DrawableTransitionOptions.withCrossFade(600));
            }

            if (DEFAULT_LOADING_IMAGE > 0) {
                requestOptions.placeholder(DEFAULT_LOADING_IMAGE);
            }

            if (DEFAULT_ERROR_IMAGE > 0) {
                requestOptions.error(DEFAULT_ERROR_IMAGE);
            }
        }
        //使用临时的配置进行设置
        else {
            if (loadOption.isShowTransition()) {
                requestBuilder.transition(DrawableTransitionOptions.withCrossFade(600));
            }

            if (loadOption.getLoadingResId() > 0) {
                requestOptions.placeholder(loadOption.getLoadingResId());
            }

            if (loadOption.getErrorResId() > 0) {
                requestOptions.error(loadOption.getErrorResId());
            }

            CircleBorderTransformation circleTransformation = null;
            RoundedCornersTransformation roundedCornersTransformation = null;
            BlurTransformation blurTransformation = null;
            GrayscaleTransformation grayscaleTransformation = null;

            if (loadOption.isCircle()) {
                int borderWidth = loadOption.getBorderWidth();
                int borderColor = loadOption.getBorderColor();
                if (borderWidth > 0 && borderColor != 0) {
                    circleTransformation = new CircleBorderTransformation(borderWidth, borderColor);
                }else{
                    circleTransformation = new CircleBorderTransformation();
                }
            } else if (loadOption.getRoundRadius() > 0) {
                roundedCornersTransformation = new RoundedCornersTransformation(loadOption.getRoundRadius(), 0);
            }

            if (loadOption.getBlurRadius() > 0) {
                blurTransformation = new BlurTransformation(loadOption.getBlurRadius());
            }

            if (loadOption.isGray()) {
                grayscaleTransformation = new GrayscaleTransformation();
            }

            if (circleTransformation !=null || roundedCornersTransformation!=null || blurTransformation!=null ||grayscaleTransformation!=null){
                MultiTransformation multiTransformation = getMultiTransformation(new CenterCrop(),circleTransformation, roundedCornersTransformation, blurTransformation, grayscaleTransformation);
                requestOptions.transform(multiTransformation);
            }

        }
        return requestBuilder.apply(requestOptions);
    }

    private MultiTransformation getMultiTransformation(Transformation... transformations) {
        List<Transformation> list = new ArrayList<>();

        for (int i = 0; i < transformations.length; i++) {
            if (transformations[i] != null) list.add(transformations[i]);
        }

        if (list.isEmpty()) {
            return null;
        } else {
            return new MultiTransformation(list);
        }
    }



}
