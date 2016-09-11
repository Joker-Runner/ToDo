package com.joker.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.joker.data.Constant;
import com.joker.todo.R;

public class SettingActivity extends AppCompatActivity {

    private RelativeLayout defaultLabelSetting;
    private RelativeLayout feedback;
    private int setDefaultLabel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();

        /**
         * 新建Item时的默认集设置
         */
        defaultLabelSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] label_set = new String[]{"默认集", "集合1", "集合2"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setIcon(R.drawable.ic_build_black_24dp);
                builder.setTitle("选择作为默认的集合");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(label_set, sharedPreferences.getInt(Constant.DEFAULT_LABEL_ID_SETTING,
                        Constant.DEFAULT_LABEL_ID), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                setDefaultLabel = Constant.DEFAULT_LABEL_ID;
                                break;
                            case 1:
                                setDefaultLabel = Constant.LABEL_ID_1;
                                break;
                            case 2:
                                setDefaultLabel = Constant.LABEL_ID_2;
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //点击取消什么事都不做
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putInt(Constant.DEFAULT_LABEL_ID_SETTING, setDefaultLabel);
                    }
                });
                builder.show();

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化
     */
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_check);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        defaultLabelSetting = (RelativeLayout) findViewById(R.id.default_label_setting);
        feedback = (RelativeLayout) findViewById(R.id.feedback);

        sharedPreferences = getSharedPreferences(Constant.SHARED_SETTING, Context.MODE_APPEND);
        editor = sharedPreferences.edit();

        setDefaultLabel = sharedPreferences.getInt(Constant.DEFAULT_LABEL_ID_SETTING, Constant.DEFAULT_LABEL_ID);
    }

}
