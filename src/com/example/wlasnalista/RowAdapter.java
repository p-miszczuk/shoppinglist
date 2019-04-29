package com.example.wlasnalista;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;



public class RowAdapter extends ArrayAdapter<String>{
	
	MainActivity customListner = new MainActivity(); 
	
	Context context;
    int layout;   
   ArrayList<String> data;
  
  
    
 
    public RowAdapter(Context context, int layoutResourceId, ArrayList<String> dane) {
        super(context, layoutResourceId, dane);
        this.layout = layoutResourceId;
        this.context = context;
        this.data = dane;
        
    }
    public void setCustomButtonListner(MainActivity mainActivity) {  
        this.customListner = mainActivity;  
    } 
 
    public View getView(final int position, View convertView, final ViewGroup parent) {
    	
    
    	
        if(convertView ==  null)
        {
        	LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(layout, parent, false);
        }    
            
	        RowBeanHolder holder = new RowBeanHolder();
	        holder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
	        
	        holder.iB = (ImageButton)convertView.findViewById(R.id.imageButton1);
	        
	        holder.iB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
									
					Toast.makeText(context, "Deleted: "+data.get(position), 3000).show();
					
					customListner.adapter.remove(data.get(position));
					customListner.dockAmountOfRepeat();
					customListner.adapter.notifyDataSetChanged();
				}
			});
                     
            convertView.setTag(holder);
            holder.txtTitle.setText(data.get(position));
          //  holder.txtTitle.setText(position);
        //holder.iB.getTag(position);
       
     //   RowBean object = data[position];
      //  holder.txtTitle.setText(object.title);
      //  holder.iB.setImageResource(object.icon);
 
        return convertView;
    }
 
    static class RowBeanHolder
    {
        
        TextView txtTitle;
       ImageButton iB;
    }
    
  
}
