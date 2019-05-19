package edu.android.and53_firetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "test_tag"; // 로그확인

    private static String ID;

    private EditText editName, editAge, editId, editEmail, editPw, editPw2, editGame;
    private TextView textView;

    private FirebaseDatabase database;  // 데이터베이스에 접근할 수 있는 진입점 클래스
    private DatabaseReference myRef;    // 데이터베이스의 주소 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        textView = findViewById(R.id.textView);
        editId = findViewById(R.id.editId);
        editEmail = findViewById(R.id.editEmail);
        editPw = findViewById(R.id.editPw);
        editPw2 = findViewById(R.id.editPw2);
        editGame = findViewById(R.id.editGame);

        database = FirebaseDatabase.getInstance();  // 현재 데이터 베이스를 접근할 수 있는 진입점
        // Person의 child로 이동
        // 데이터베이스의 루트 폴더 주소 값을 반환하는 메소드
        myRef = database.getReference("Person");

    } // end onCreate()

    private Person.Game_1 saveGameScore(String score){

        Person.Game_1 game1 = new Person.Game_1(score);
        return game1;

    }


    // 데이터 저장 메소드
    private void writeNewPerson(String userId, String name, String age, String email, String pw, String pw2){
        String score = editGame.getText().toString();
        Person.Game_1 game_1 = saveGameScore(score);
        Person person = new Person(ID, name, age, email, pw, pw2, game_1);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = person.toMap();

        childUpdates.put("/" + ID + "/", postValues);
        myRef.updateChildren(childUpdates);

        Map<String, Object> childToChildUpdate = new HashMap<>();
        Map<String, Object> gameValues = person.toMapGame();

        childToChildUpdate.put("/" + ID + "/" + "game1/", gameValues);
        myRef.updateChildren(childToChildUpdate);

//        myRef.child("/"+ID+"/game1"+)


    } // end writeNewPerson()


    // 데이터 저장 버튼
    public void onClickSaveData(View view) {
        String name = editName.getText().toString();
        String age = editAge.getText().toString();
        ID = editEmail.getText().toString().split("@")[0];
        String email = editEmail.getText().toString();

        String pw = editPw.getText().toString();
        String pw2 = editPw2.getText().toString();

        if (pw.equals(pw2)) {

            // 저장 메소드 호출
            writeNewPerson(ID, name, age, email, pw, pw2);
        } else {
            Toast.makeText(this, "비밀번호 다릅니다", Toast.LENGTH_SHORT).show();
        }


        editName.setText("");
        editAge.setText("");
    } // end saveData()

    // 데이터 가져오기 버튼
    public void onClickGetData(View view) {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder builder = new StringBuilder();
                Person person = null;
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    person = snapShot.getValue(Person.class);
                    String[] info = {person.getId(), person.getName(), person.getAge(), person.getEmail(), person.getPw()};
                    String result = info[0] + " - " + info[1] + " - " + info[2] + "\n";
                    builder.append(result);
                }

                textView.setText(builder);
            } // end onDataChange()

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    } // end getData()


    public void onClickUpdateData(View view) {

        String id = editId.getText().toString();
        String updateName = editName.getText().toString();


        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("/" + id + "/name",updateName);
        myRef.updateChildren(updateMap);


    } // end onClickUpdateData()
} // end class MainActivity
