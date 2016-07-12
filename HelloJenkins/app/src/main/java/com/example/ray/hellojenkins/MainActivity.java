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

        send.setText("发送log测试报告");
        userid.setText("wanglei19910523@163.com"); //你的邮箱用户名
        password.setText("");         //你的邮箱登陆密码
        from.setText("wanglei19910523@163.com");//发送的邮箱
        to.setText("wanglei@feiniu.com"); //发到哪个邮件去
        subject.setText("测试log发送");
        body.setText("测试log发送");
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

                File file1 = new File(Environment.getExternalStorageDirectory() + "/zip");
                if (!file1.exists()) {
                    file1.mkdir();
                }
                File file2 = new File(Environment.getExternalStorageDirectory() + "/zip/upload");
                if (!file2.exists()) {
                    file2.mkdir();
                }
                File file3 = new File(Environment.getExternalStorageDirectory() + "/zip/fnlog.zip");
                if (file3.exists()) {
                    file3.delete();
                }
                ZipUtils.zipFolder(Environment.getExternalStorageDirectory() + "/zip/upload", Environment.getExternalStorageDirectory() + "/zip/fnlog.zip");
                mailInfo.setAttachFileNames(new String[]{Environment.getExternalStorageDirectory() + "/zip/fnlog.zip"});
                //这个类主要来发送邮件
                SimpleMailSender sms = new SimpleMailSender();
//                sms.sendTextMail(mailInfo);//发送文体格式
//                sms.sendHtmlMail(mailInfo);//发送html格式


                sms.sendMultipleEmail(mailInfo);
            } catch (Exception e) {
                Log.e("wanglei", e.getMessage(), e);
            }
        }
    }

}
