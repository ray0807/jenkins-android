package com.example.ray.hellojenkins;


import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button send;
    private EditText userid;
    private EditText password;
    private EditText from;
    private EditText to;
    private EditText subject;
    private EditText body;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        send = (Button) findViewById(R.id.send);
        userid = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.password);
        from = (EditText) findViewById(R.id.from);
        to = (EditText) findViewById(R.id.to);
        subject = (EditText) findViewById(R.id.subject);
        body = (EditText) findViewById(R.id.body);

        send.setText("Send Mail");
        userid.setText("wanglei19910523@163.com"); //你的邮箱用户名
        password.setText("w41615465");         //你的邮箱登陆密码
        from.setText("wanglei19910523@163.com");//发送的邮箱
        to.setText("wanglei@feiniu.com"); //发到哪个邮件去

        subject.setText("邮件主题，请输入...");
        body.setText("邮件内容，请输入...");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new SendThead()).start();
            }
        });
    }


    class SendThead extends Thread {
        @Override
        public void run() {
            super.run();
            // TODO Auto-generated method stub
            try {
                MailSenderInfo mailInfo = new MailSenderInfo();
                mailInfo.setMailServerHost("smtp.163.com");
                mailInfo.setMailServerPort("25");
                mailInfo.setValidate(true);
                mailInfo.setUserName(userid.getText().toString());  //你的邮箱地址
                mailInfo.setPassword(password.getText().toString());//您的邮箱密码
                mailInfo.setFromAddress(from.getText().toString());
                mailInfo.setToAddress(to.getText().toString());
                mailInfo.setSubject(subject.getText().toString());
                mailInfo.setContent(body.getText().toString());

                Log.i("wanglei","1");
                ZipUtils.zipFolder(Environment.getExternalStorageDirectory()+"/zip",Environment.getExternalStorageDirectory()+"/a.zip");
                Log.i("wanglei","2");
                mailInfo.setAttachFileNames(new String[]{Environment.getExternalStorageDirectory()+"/a.zip"});

                //这个类主要来发送邮件
                SimpleMailSender sms = new SimpleMailSender();

//                sms.sendTextMail(mailInfo);//发送文体格式

                sms.sendHtmlMail(mailInfo);//发送html格式

            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
        }
    }
}
