package dev.aldy.kafekat.Listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import dev.aldy.kafekat.R;

import java.util.ArrayList;


public class UserAdapter extends ArrayAdapter<User> {
private Context mContext;
private int mResource;

    public UserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        TextView txtmain = convertView.findViewById(R.id.maintxt);
        txtmain.setText(getItem(position).getTitle());
        return convertView;
    }


}

