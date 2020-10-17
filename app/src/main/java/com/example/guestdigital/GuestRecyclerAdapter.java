package com.example.guestdigital;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GuestRecyclerAdapter extends RecyclerView.Adapter<GuestRecyclerAdapter.ViewHolder> {
    public ArrayList<GuestEverything> my_arrayList;

    public GuestRecyclerAdapter(ArrayList<GuestEverything> my_arrayList) {
        this.my_arrayList = my_arrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View r_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new ViewHolder(r_view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Tv_rFirstName.setText(my_arrayList.get(position).getV_firstName());
        holder.Tv_rSecondName.setText(my_arrayList.get(position).getV_secondName());
        holder.Tv_rLastName.setText(my_arrayList.get(position).getV_lastName());
        holder.Tv_rAddress.setText(my_arrayList.get(position).getV_address());
        holder.Tv_rPhoneNum.setText(my_arrayList.get(position).getV_phoneNum());
        holder.Tv_rEmail.setText(my_arrayList.get(position).getV_email());
        holder.Tv_rComment.setText(my_arrayList.get(position).getV_comment());
        holder.Tv_rDateOfVisit.setText(my_arrayList.get(position).getV_dateOfVisit());
        holder.Tv_rTimeOfVisit.setText(my_arrayList.get(position).getV_timeOfVisit());
    }

    @Override
    public int getItemCount() {
        return my_arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Tv_rFirstName, Tv_rSecondName, Tv_rLastName, Tv_rAddress, Tv_rPhoneNum,
                Tv_rEmail, Tv_rComment, Tv_rDateOfVisit, Tv_rTimeOfVisit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_rFirstName = itemView.findViewById(R.id.Tv_rFirstName);
            Tv_rSecondName = itemView.findViewById(R.id.Tv_rSecondName);
            Tv_rLastName = itemView.findViewById(R.id.Tv_rLastName);
            Tv_rAddress = itemView.findViewById(R.id.Tv_rAddress);
            Tv_rPhoneNum = itemView.findViewById(R.id.Tv_rPhoneNum);
            Tv_rEmail = itemView.findViewById(R.id.Tv_rEmail);
            Tv_rComment = itemView.findViewById(R.id.Tv_rComment);
            Tv_rDateOfVisit = itemView.findViewById(R.id.Tv_rDateOfVisit);
            Tv_rTimeOfVisit = itemView.findViewById(R.id.Tv_rTimeOfVisit);
        }
    }
}
