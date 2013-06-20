package com.malta.taskwidget;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Максим on 20.06.13.
 */
public class TaskItemAdapter extends ArrayAdapter <Task> {

    private final int resource;
    private final Activity context;
    private final List<Task> items;
    private final ArrayList<Task> original;
    private Task item;
    private int pos;
    DbAdapter dba;
    ViewHolder holder;

    public TaskItemAdapter(Activity _context, int _resource, List<Task> _items) {
        super(_context, _resource, _items);
        resource=_resource;
        this.context = _context;
        this.items=_items;
        this.original = new ArrayList<Task>(items);
    }

    static class ViewHolder {
        TextView name;
        TextView category;
        TextView date;
        ImageView done;
        ImageView delete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        dba = new DbAdapter(context);
        View rowView = convertView;
        item = getItem(position);
        pos = position;


        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.editlistitem, null);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.tasknameTV);
            holder.category = (TextView) rowView.findViewById(R.id.taskcatTV);
            holder.date = (TextView) rowView.findViewById(R.id.taskdateTV);
            holder.done = (ImageView) rowView.findViewById(R.id.checkIV);
            holder.delete = (ImageView) rowView.findViewById(R.id.deleteIV);

            rowView.setTag(holder);

        }  else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                if (item.getDone() == 0) {
                    item.setDone(1);
                    holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    dba=dba.OpentoWrite();
                    dba.updateStatus(item.getId());
                    dba.Close();
                }
                notifyDataSetChanged();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To change body of implemented methods use File | Settings | File Templates.
                items.remove(pos);
                dba.OpentoWrite();
                dba.removeTask(item.getId());
                notifyDataSetChanged();
                dba.Close();
            }
        });
        if (item.getDone() == 1) {
           holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
           holder.done.setVisibility(View.INVISIBLE);
        } else
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.LINEAR_TEXT_FLAG);


        holder.name.setText(item.getName());
        holder.category.setText(item.getCategory());
        holder.category.setBackgroundColor(getCategoryColor(item.getCategory()));
        holder.date.setText(item.getEnd_date());


        return rowView;
    }

    int getCategoryColor(String Cat){
        if (Cat.equals("семья")) return Color.YELLOW;
        else if (Cat.equals("работа")) return Color.RED;
        else if (Cat.equals("покупки")) return Color.BLUE;
        else return 0;
    }

}
