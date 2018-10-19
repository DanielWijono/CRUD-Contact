package com.example.daniel.crudcontact.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daniel.crudcontact.R;
import com.example.daniel.crudcontact.RecyclerViewInterface;
import com.example.daniel.crudcontact.model.ContactData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ContactData> contactDataList;
    private RecyclerViewInterface recyclerViewInterface;

    public ContactAdapter(Context context, List<ContactData> contactDataList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.contactDataList = contactDataList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact_list, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
        contactViewHolder.setView(position);
        contactViewHolder.setViewOnClick(position);
    }

    @Override
    public int getItemCount() {
        return contactDataList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_contact_name)
        TextView tvContactName;
        @BindView(R.id.tv_contact_age)
        TextView tvContactAge;
        @BindView(R.id.img_contact)
        ImageView imgContact;
        @BindView(R.id.ll_contact)
        LinearLayout llContact;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setView(int position) {
            tvContactName.setText(contactDataList.get(position).getFirstName() + " " + contactDataList.get(position).getLastName());
            tvContactAge.setText(String.valueOf(contactDataList.get(position).getAge()));
            Glide.with(context).load(contactDataList.get(position).getPhoto()).fitCenter().placeholder(R.drawable.ic_launcher_background).into(imgContact);
        }

        public void setViewOnClick(final int position) {
            llContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface.onRecyclerViewClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
