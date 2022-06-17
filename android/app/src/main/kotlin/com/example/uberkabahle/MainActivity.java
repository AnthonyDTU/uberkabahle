package com.example.uberkabahle;

import android.os.Bundle;


import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;
import android.widget.Toast;

import androidx.annotation.NonNull;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import android.content.Intent;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "BackendChannel";
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine){

        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
	    Communicator comm = new Communicator();
	
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(
            (call, result) -> {
                if (call.method.equals("initTable")){
                    // 7 cards
                    String data = call.argument("data");
                    comm.initStartTable(data);
                    result.success(true);
                }
                else if (call.method.equals("updateTable")){
                    // 8 cards
                    String data = call.argument("data");
                    
                    comm.updateTable(data);
                    result.success(true);
                }
                else if (call.method.equals("getNextMove")){
                    //call comm.getNextMove, which returns an int array

                    String[] moves = comm.getNextMove();
                    StringBuilder returnString = new StringBuilder();
                    for (String move: moves) {
                        returnString.append(move).append(';');
                    }
                    result.success(returnString.toString());
                }
                else {
                    result.notImplemented();
                }
            });
    }
}
