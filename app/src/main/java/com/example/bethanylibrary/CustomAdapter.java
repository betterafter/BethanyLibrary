package com.example.bethanylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<CustomListItem> mItems = new ArrayList<>();

    public CustomAdapter(ArrayList<CustomListItem> listitem){
        mItems = listitem;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CustomListItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ui, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        TextView bookName = (TextView) convertView.findViewById(R.id.bookname);
        TextView bookNumber = (TextView) convertView.findViewById(R.id.booknumber);
        TextView bookPos = (TextView) convertView.findViewById(R.id.bookposition);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        CustomListItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */

        bookName.setText(myItem.getName());
        bookNumber.setText(myItem.getNumber());
        bookPos.setText(myItem.getPosition());

        return convertView;
    }
}