package com.example.android.swipe.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.swipe.Homework.HomeworkMainActivity;
import com.example.android.swipe.ProfileMainActivity;
import com.example.android.swipe.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by android on 10/10/2015.
 */
public class CustomGridViewAdapter extends ArrayAdapter <HashMap<String,String>> implements Animation.AnimationListener {
    public Context context;
    int layoutResourceId;
    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();


    public CustomGridViewAdapter(Context context, int resourceID, List<HashMap<String,String>> aList) {
        super(context, resourceID, aList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        layoutResourceId = resourceID;
        this.aList = aList;


    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txt);
            holder.txtIndex = (TextView) row.findViewById(R.id.index);
            holder.imageItem = (ImageView) row.findViewById(R.id.flag);
            holder.redCircle = (ImageView) row.findViewById(R.id.redCircle);
            row.setTag(holder);
            final Animation scaleImageAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
            scaleImageAnimation.setAnimationListener(this);
            holder.imageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Clicked", "" + position +  "  " + this.toString());
                    v.startAnimation(scaleImageAnimation);
                    if (aList.get(position).get("txt").contains("Odev")){
                        Intent myIntent = new Intent(getContext(), HomeworkMainActivity.class);
                        myIntent.putExtra("userObject", ProfileMainActivity.user);
                        v.getContext().startActivity(myIntent);
                    }

                }




            });



        }
        else {
            holder = (RecordHolder) row.getTag();
        }


        holder.txtTitle.setText(aList.get(position).get("txt"));
        holder.txtIndex.setText(aList.get(position).get("index"));
        if (holder.txtIndex.getText().equals("0")){
            holder.txtIndex.setVisibility(View.GONE);
            holder.redCircle.setVisibility(View.GONE);
        }
        //String imageName = aList.get(position).get("flag");
        //holder.imageItem.setImageResource(Integer.parseInt(aList.get(position).get("flag")));
        Resources resources = getContext().getResources();
        final int resId = resources.getIdentifier(aList.get(position).get("flag"), "drawable", getContext().getPackageName());
        Log.d("res ID", "" + resId);
        Log.d("envelope ID", "" + R.drawable.book_open);
        holder.imageItem.setImageDrawable(resources.getDrawable(resId));
        //holder.imageItem.setImageResource(Resources.getSystem().getIdentifier("envelope_open", "drawable", getContext().getPackageName()));
        //Drawable drawable = Drawable.createFromPath(aList.get(position).get("flag"));
        //holder.imageItem.setImageDrawable(drawable);
        //holder.imageItem.setImageResource(R.drawable.envelope_open);
        //Log.d("Res id", "" + Resources.getSystem().getIdentifier("calendar", "drawable", "android"));
        //Log.d("Res filename", aList.get(position).get("flag"));
        //Resources res = getContext().getResources();
        //Drawable drawable = getContext().getResources().getDrawable(getContext().getResources()
         //     .getIdentifier(aList.get(position).get("flag"), "drawable", getPackageName()));
        //int resourceId= Resources.getSystem().getIdentifier(pDrawableName, "drawable", "android");
        //holder.imageItem.setImageIcon();
        //holder.imageItem.setImageBitmap(aList.get(position).get("flag"));

        return row;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    static class RecordHolder {
        TextView txtTitle;
        TextView txtIndex;
        ImageView imageItem;
        ImageView redCircle;}

}
