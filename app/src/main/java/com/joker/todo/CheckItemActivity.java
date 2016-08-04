package com.joker.todo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.joker.data.Constant;

import jp.wasabeef.richeditor.RichEditor;

public class CheckItemActivity extends AppCompatActivity {

    private TextView dateLineText;
    private TextView labelText;
    private TextView titleText;
    private RichEditor psRichText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        init();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(Constant.LABEL_ID)) {
            case Constant.DEFAULT_LABEL_ID:
                labelText.setText("默认集");
                break;
            case Constant.LABEL_ID_1:
                labelText.setText("集合1");
                break;
            case Constant.LABEL_ID_2:
                labelText.setText("集合2");
        }
        dateLineText.setText(bundle.getString(Constant.DATE_LINE));
        titleText.setText(bundle.getString(Constant.TITLE));
        psRichText.setHtml(bundle.getString(Constant.PS));
    }

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

        dateLineText = (TextView) findViewById(R.id.dateline_text);
        labelText = (TextView) findViewById(R.id.label_text);
        titleText = (TextView) findViewById(R.id.title_text);
        psRichText = (RichEditor) findViewById(R.id.rich_text);
        psRichText.setFocusable(false);
        psRichText.setEditorHeight(200);
        psRichText.setEditorFontSize(16);
        psRichText.setEditorFontColor(Color.BLACK);
        //psRichText.setEditorBackgroundColor(Color.BLUE);
        //psRichText.setBackgroundColor(Color.BLUE);
        //psRichText.setBackgroundResource(R.drawable.bg);
        psRichText.setPadding(10, 10, 10, 10);
        //    psRichText.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        psRichText.setPlaceholder("详情...");
    }


}
