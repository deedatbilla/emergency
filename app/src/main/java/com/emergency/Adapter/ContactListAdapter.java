package com.emergency.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emergency.Model.Contact;
import com.emergency.R;


import java.util.List;

public  class ContactListAdapter extends RecyclerView.Adapter< ContactListAdapter.MyViewHolder> {

    private Context context;
    private List<Contact> contactList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView email,phone;


        public MyViewHolder(View view, final onItemClickListener listener) {
            super(view);
            email = view.findViewById(R.id.email);
            phone = view.findViewById(R.id.phone);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
if(listener!=null){
    int position=getAdapterPosition();
    if(position!=RecyclerView.NO_POSITION){
        listener.onItemClick(position);
    }
}
                }
            });
        }
    }


    public ContactListAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactlist, parent, false);

        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Contact item = contactList.get(position);

        holder.email.setText(item.getEmail());
        holder.phone.setText(item.getPhone());



    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }


}
