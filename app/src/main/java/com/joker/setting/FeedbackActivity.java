package com.joker.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joker.todo.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedbackEdit;
    private Button feedbackCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_check);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        feedbackEdit = (EditText) findViewById(R.id.feedback_edit);
        feedbackCommit = (Button) findViewById(R.id.feedback_commit);
        feedbackCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:joker_runner@qq.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "ToDo 反馈");
                data.putExtra(Intent.EXTRA_TEXT, feedbackEdit.getText());
                startActivity(data);//调用系统邮件服务 发送反馈
            }
        });
    }
}
