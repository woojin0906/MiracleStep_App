package kr.co.company.healthapplication;

import static javax.mail.Session.getDefaultInstance;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import kr.co.company.healthapplication.request.MemberCheckRequest;
import kr.co.company.healthapplication.request.ResetPasswordRequest;

// SMTP 프로토콜(Simple Mail Transfer Protocol)을 이용하여
// 이메일에 인증번호 전송하는 클래스. (2023-01-04 이수)
public class ResetPasswordActivity extends AppCompatActivity {
    private LinearLayout layoutAuthNumber, layoutResetPassword;
    private TextView tvCountDown;
    private EditText etEmail, etAuthNumber, etResetPassword;
    private Button btnSendingEmail, btnAuthNumCheck, btnResetPassword;
    private String authNumber;

    private CountDownTimer mCountDownTimer;
    private static long START_TIME_IN_MILLIS = 300000;;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // 인증번호 이메일에 전송하기
        etEmail = findViewById(R.id.etId);
        btnSendingEmail = findViewById(R.id.btnSendingEmail);

        // 인증번호 체크하기
        layoutAuthNumber = findViewById(R.id.layoutAuth);
        etAuthNumber = findViewById(R.id.etAuthNumber);
        btnAuthNumCheck = findViewById(R.id.btnAuthNumCheck);
        tvCountDown = findViewById(R.id.tvCountDown);

        // 비밀번호 재설정하기
        layoutResetPassword = findViewById(R.id.layoutResetPassword);
        etResetPassword = findViewById(R.id.etResetPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        // 인증번호 전송하기.
        btnSendingEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                memberCheck(email);
            }
        });

        // 인증번호 확인하기.
        btnAuthNumCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNumber = etAuthNumber.getText().toString();
                // 인증 성공인 경우.
                if(inputNumber.equals(authNumber)) {
                    btnSendingEmail.setEnabled(false);  // 이메일 인증 전송버튼 비활성화.
                    btnAuthNumCheck.setEnabled(false);  // 인증번호 체크버튼 비활성화.
                    mCountDownTimer.cancel();   // 타이머 종료.
                    mCountDownTimer = null;
                    layoutResetPassword.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
                // 인증 실패인 경우.
                else {
                    Toast.makeText(getApplicationContext(),"인증이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    btnSendingEmail.setEnabled(true);  // 이메일 인증 전송버튼 활성화.
                    btnAuthNumCheck.setEnabled(false); // 인증번호 체크버튼 비활성화.
                }
            }
        });

        // 비밀번호 재설정하기.
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resetPassword = etResetPassword.getText().toString();
                insertResetPassword(resetPassword);
            }
        });
    }

    // 비밀번호 재설정하는 메소드
    private void insertResetPassword(String password) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    // 비밀번호 재설정이 완료된 경우.
                    if(success) {
                        Toast.makeText(getApplicationContext(),"비밀번호 재설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    // 비밀번호 재설정이 실패된 경우.
                    else {
                        Toast.makeText(getApplicationContext(),"비밀번호 재설정이 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"비밀번호 재설정에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        ResetPasswordRequest reset = new ResetPasswordRequest(password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ResetPasswordActivity.this);
        queue.add(reset);
    }

    // 해당 이메일이 회원인지 확인하는 메서드
    private void memberCheck(String email) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    //String jsonString = jsonObject.toString();
                    //START_TIME_IN_MILLIS = 300000;

                    // 회원 검색이 성공한 경우.
                    if(success) {
                        //mCountDownTimer.start();   // 타이머 시작.
                        startTimer();   // 타이머 시작.
                        sendEmail(email);   // 이메일에 인증번호를 전송.
                        layoutAuthNumber.setVisibility(View.VISIBLE);
                        btnAuthNumCheck.setEnabled(true);  // 인증번호 체크버튼 활성화.
                    }

                    // 회원 검색이 실패한 경우.
                    else {
                        Toast.makeText(getApplicationContext(),"이메일 확인이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"이메일 확인에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        MemberCheckRequest memberCheck = new MemberCheckRequest(email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ResetPasswordActivity.this);
        queue.add(memberCheck);
    }

    // 타이머 시작.
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            // 수행 간격마다 호출되는 함수
            @Override
            public void onTick(long millisUntilFinished) {
                btnSendingEmail.setEnabled(false);  // 이메일 인증 전송버튼 비활성화.
                btnAuthNumCheck.setEnabled(true);  // 인증번호 체크버튼 활성화.
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            // millisInFuture 시간까지 모두 종료시 호출되는 함수
            @Override
            public void onFinish() {
                btnSendingEmail.setEnabled(true);  // 이메일 인증 전송버튼 활성화.
                btnAuthNumCheck.setEnabled(false);  // 인증번호 체크버튼 비활성화.
                layoutAuthNumber.setVisibility(View.INVISIBLE);
                mCountDownTimer.cancel();   // 타이머 종료.
                mCountDownTimer = null;
            }
        }.start();
    }

    // 이메일 인증 남은 시간 TextView에 출력.
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d분 %02d초", minutes, seconds);
        tvCountDown.setText(timeLeftFormatted);
    }

    // 인증번호를 이메일로 전송하는 메서드.
    public String sendEmail(String email) {
        // 1. 발신자의 메일 계정과 비밀번호 설정
        String user = "201945058@itc.ac.kr";    // E-Mail 계정 => (임시 이메일 작성함. / 2023-01-04 이수)
        String password = "비밀번호"; //"비밀번호를 입력할 자리입니다.(깃허브 올릴 때에는 안적을께요~)";    // 패스워드 => 보안에 주의해주세요(ㅠㅠ)
        Properties props = new Properties();

        // 2. Property에 SMTP 서버 정보를 설정한다. => gmail에 맞춰서 설정해두었습니다.
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
        Session session = getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // 4. Apache Commons Lang 라이브러리를 사용해서 랜덤의 인증번호 난수를 6자리로 설정함.
        authNumber = RandomStringUtils.randomNumeric(6);
        Log.d("인증번호",authNumber);

        try {
            // 5. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user)); // 발신자 설정완료.

            // 메일 대상 (수신자)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // 메일 제목
            message.setSubject("Miracle Step Application 인증번호입니다.");

            // 메일 내용
            message.setText("귀하의 이메일 인증번호는 " + authNumber + " 입니다.\n인증번호를 입력해주세요.");

            // send the message
            new Thread(() -> {
                try {
                    // 6. Transport 클래스를 사용하여 작성한 메세지를 전달한다.
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
            Toast.makeText(getApplicationContext(),"인증번호 전송이 완료되었습니다.", Toast.LENGTH_SHORT).show();

        } catch (MessagingException e) {
            Toast.makeText(getApplicationContext(),"인증번호 전송이 실패하였습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return authNumber;
    }
}
