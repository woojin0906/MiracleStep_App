package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatbotAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ChatbotData> arrayList;

    private EditText etUserInput;
    private Button btnSend;
    private TextView tvResponse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        etUserInput = findViewById(R.id.etUserInput);
        btnSend = findViewById(R.id.btnSend);

        // List 설정
        recyclerView = (RecyclerView) findViewById(R.id.chatbotList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = "me: "+(etUserInput.getText().toString());

                ChatbotData mainData = new ChatbotData(userInput);

                arrayList.add(mainData);

                adapter = new ChatbotAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                new Thread(new Runnable() {
                    String result = "";

                    @Override
                    public void run() {
                        try {
                            result = chatbotTest(userInput);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        (ChatbotActivity.this).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                adapter = new ChatbotAdapter(arrayList);
                                recyclerView.setAdapter(adapter);

                                //adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
            }
        });

    }

    // chatbotAPI 요청
    private String chatbotTest(String userInput) throws Exception {
        try {
            URL url = new URL("https://chatbotapi-gpt-inofu.run.goorm.site/ms");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject data = new JSONObject();
            data.put("user_input", userInput);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data.toString());
            out.flush();
            out.close();

            String temp = "";
            String content = "";
            InputStream responseBody = conn.getInputStream();
            InputStreamReader responseBodyReader =
                    new InputStreamReader(responseBody, "UTF-8");
            BufferedReader br = new BufferedReader( responseBodyReader );
            while ((temp = br.readLine()) != null) {
                content += temp;
            }
            JSONObject responseJson = new JSONObject(content);
            Log.d("chatGPT 응답", responseJson.toString(2));
            br.close();

            StringBuilder sb = new StringBuilder(responseJson.toString(2));

            String[] arr = sb.toString().split("");

            for(int i=0; i<arr.length; i++) {
                if(arr[i].equals("{") || arr[i].equals("}") || arr[i].equals("\"")) {
                    arr[i] = "";
                }
            }

            String chat = String.join("", arr);
            Log.d(">>>> text", chat);

            ChatbotData mainData = new ChatbotData(chat);

            arrayList.add(mainData);

            return chat;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}