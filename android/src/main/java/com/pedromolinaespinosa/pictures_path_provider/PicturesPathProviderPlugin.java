package com.pedromolinaespinosa.pictures_path_provider;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * PicturesPathProviderPlugin
 */
public class PicturesPathProviderPlugin implements FlutterPlugin, MethodCallHandler {
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "pictures_path_provider");
        channel.setMethodCallHandler(new PicturesPathProviderPlugin());
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "pictures_path_provider");
        channel.setMethodCallHandler(new PicturesPathProviderPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getPicturesPath")) {
            String path;
            try {
                path = getPicturesPath();
                result.success(path);
            } catch (Exception e) {
                Log.e("pictures_path_provider", "Error: " + e.getMessage());
                result.error("Error", "Error while getting path. Message: " + e.getMessage(), e);
            }
        } else {
            result.notImplemented();
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private String getPicturesPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }
}
