package com.example.bethanylibrary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //외부 저장소 읽기 권한 설정 변수
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STROAGE = 1001;



    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ////////////////////////////이 부분부터 외부 저장소 권환 허가 요청 코드 //////////////////////////////
        int permissioncheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissioncheck != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "권한 승인이 필요합니다.", Toast.LENGTH_LONG).show();


            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(this, "외부 저장소 접근을 위해 저장소 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }

            else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STROAGE);
                Toast.makeText(this, "외부 저장소 접근을 위해 저장소 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STROAGE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "권한이 허가되어 있습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "아직 승인받지 않았습니다.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    ////////////////////////////////여기까지 외부 저장소 권한 허가 요청 코드/////////////////////////////////


    //////////////////////////////////////csv 확장자 파일 탐색/////////////////////////////////////////
    private ArrayList<String> extensionFilter(File folder){

        ArrayList<String> liblist = new ArrayList<>();
        File[] files = folder.listFiles();

        if(files != null){
            for(File file : files){
                if(file.isDirectory()) liblist.addAll(extensionFilter(file));
                else{
                    if(file.getName().endsWith(".csv")) liblist.add(file.toString());
                }
            }
        }

        return liblist;
    }

    /////////////////////////////"찾기"버튼 터치 시 csv 파일 찾아와 리스트에 추가/////////////////////////////
    public void FindClick(View v){


        ArrayList<String> Interfiles = extensionFilter(Environment.getExternalStorageDirectory());

        ListView listview = (ListView)findViewById(R.id.lib_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Interfiles);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Selectfile = (String) parent.getItemAtPosition(position);
                CSVParsing fileparser = new CSVParsing();
                fileparser.setFilename(Selectfile);

                fileparser.Parser();

                if(fileparser.ErrorCheck != 1) {
                    Intent intent = new Intent(MainActivity.this, LibraryView.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "올바른 형식의 목록이 아닙니다.\n[책번호 - 책이름 - 책위치 - 날짜 - 비고]", Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, "" + fileparser.getCheck(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    /////////////////// 만약 찾기 버튼을 누르고 다른 행동을 해서 리스트 버튼을 눌러도 아무런 반응이 없는 문제가 생긴다면,
    /////////////////// listview를 밖으로 빼서 설정해주고 onItemClick을 새로운 메소드로 다시 만들어보자.




    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
