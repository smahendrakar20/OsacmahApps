package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osm00apps.lrmah.synonymclusterforgre.R;

import data.DatabaseSchemas;
import data.WordsDatabaseContentProvider;

public class MyCursorAdapter extends CursorAdapter {

    String list="NoList";
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    public MyCursorAdapter(Context context, Cursor c,String list) {

        super(context, c,0);
    this.list=list;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout ll1=(LinearLayout) view.findViewById(R.id.listItemId);
        TextView tv1= (TextView) view.findViewById(R.id.name);
        TextView tv2 = (TextView) view.findViewById(R.id.summary);

        int docName = cursor.getColumnIndex("Word");
        int docField = cursor.getColumnIndex("Meaning");




        String name = cursor.getString(docName);
        String field = cursor.getString(docField);

        view.setBackgroundResource(0);
        tv1.setTextColor(Color.parseColor("#abb2bc"));


        if(list.equals("Searched")||list.equals(DatabaseSchemas.SearchedTableSchema.Notes)) {
            int Marked=cursor.getColumnIndex("Marked");
            int Learned=cursor.getColumnIndex("Learned");
            String Marked1=cursor.getString(Marked);
            String Learned1=cursor.getString(Learned);

            if (Marked1.equals("1") && Learned1.equals("1")) {

                tv1.setTextColor(ContextCompat.getColor(context,R.color.myRed));
                view.setBackgroundResource(R.drawable.green_round_rect);
            }
            else {
                if (Marked1.equals("1")) {
                    view.setBackgroundResource(R.drawable.red_rounded_rect);
                }
                if (Learned1.equals("1")) {
                    view.setBackgroundResource(R.drawable.green_round_rect);
                }
            }
        }

            tv1.setText(name);
            tv2.setText(field);

    }
}
