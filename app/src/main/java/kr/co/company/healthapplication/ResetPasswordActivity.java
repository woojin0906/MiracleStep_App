package kr.co.company.healthapplication;

import static javax.mail.Session.getDefaultInstance;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.lang3.RandomStringUtils;

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

// SMTP 프로토콜(Simple Mail Transfer Protocol)을 이용하여
// 이메일에 인증번호 전송하는 클래스. (2023-01-04 이수)
public class ResetPasswordActivity extends AppCompatActivity {
    private LinearLayout authLayout;
    private TextView tvCountDown;
    private EditText etEmail, etAuthNumber;
    private Button btnSendingEmail, btnAuthNumCheck;
    private String authNumber;

    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 300000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.etId);
        etAuthNumber = findViewById(R.id.etAuthNumber);
        btnSendingEmail = findViewById(R.id.btnSendingEmail);
        btnAuthNumCheck = findViewById(R.id.btnAuthNumCheck);
        tvCountDown = findViewById(R.id.tvCountDown);
        authLayout = findViewById(R.id.authLayout);

        // 인증번호 전송하기.
        btnSendingEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                authNumber = sendEmail(email);
                authLayout.setVisibility(View.VISIBLE);
                startTimer();
            }
        });

        // 인증번호 확인하기.
        btnAuthNumCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // 타이머 시작.
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSendingEmail.setEnabled(false);  // 이메일 인증 전송버튼 비활성화.
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                btnSendingEmail.setEnabled(true);  // 이메일 인증 전송버튼 활성화.
                authLayout.setVisibility(View.INVISIBLE);
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
        String password = "비밀번호를 입력할 자리입니다.(깃허브 올릴 때에는 안적을께요~)";    // 패스워드 => 보안에 주의해주세요(ㅠㅠ)
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