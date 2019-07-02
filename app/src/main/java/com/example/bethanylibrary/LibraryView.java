package com.example.bethanylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class LibraryView extends AppCompatActivity {

    ArrayList<String[]> booklist_for_activity = new ArrayList<String[]>();
    CSVParsing parser_for_activity = new CSVParsing();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_view);

        //CSVParsing에서 파싱한 리스트 가져오
        booklist_for_activity.addAll(parser_for_activity.getBook());
    }

    //이름으로 찾기
    public void OnFindNameClick(View v){

        EditText text = (EditText)findViewById(R.id.NameOrNumber);
        ListView find_list = (ListView)findViewById(R.id.findlistresult);
        ArrayList<CustomListItem> FindResult = new ArrayList<>();

        String tempString = text.getText().toString();
        tempString = tempString.replaceAll(" ", "");
        tempString = tempString.replaceAll("\\p{Z}", "");


        //문자가 그냥 공백이 아니라면
        if(tempString.length() != 0) {

            String[] temp = text.getText().toString().split(" ");

            for (String[] s : booklist_for_activity) {

                int flag = 0;

                for (String find : temp) {
                    if (!s[1].contains(find)) {
                        flag = 1;
                        break;
                    }
                }

                if (flag == 0) {
                    CustomListItem CItem = new CustomListItem();
                    CItem.name = s[1];
                    CItem.number = s[0];
                    CItem.position = s[2];

                    FindResult.add(CItem);
                }
            }
            CustomAdapter adapter = new CustomAdapter(FindResult);
            find_list.setAdapter(adapter);
        }
    }


    //번호로 찾기
    public void OnFindNumberClick(View v){

        EditText text = (EditText)findViewById(R.id.NameOrNumber);
        ListView find_list = (ListView)findViewById(R.id.findlistresult);
        ArrayList<CustomListItem> FindResult = new ArrayList<>();

        String tempString = text.getText().toString();
        tempString = tempString.replaceAll(" ", "");
        tempString = tempString.replaceAll("\\p{Z}", "");

        if(tempString.length() != 0) {

            for (String[] s : booklist_for_activity) {

                int flag = 0;

                if (s[0].equals(tempString)){

                    CustomListItem CItem = new CustomListItem();
                    CItem.name = s[1];
                    CItem.number = s[0];
                    CItem.position = s[2];

                    FindResult.add(CItem);
                }

            }


            CustomAdapter adapter = new CustomAdapter(FindResult);
            find_list.setAdapter(adapter);
        }
    }

    //전체 책 목록 보기
    public void OnFindAllListrClick(View v){

        EditText text = (EditText)findViewById(R.id.NameOrNumber);
        ListView find_list = (ListView)findViewById(R.id.findlistresult);
        ArrayList<CustomListItem> FindResult = new ArrayList<>();

        for (String[] s : booklist_for_activity) {

        CustomListItem CItem = new CustomListItem();
        CItem.name = s[1];
        CItem.number = s[0];
        CItem.position = s[2];

        FindResult.add(CItem);
        }


        CustomAdapter adapter = new CustomAdapter(FindResult);
        find_list.setAdapter(adapter);

    }


}
