package com.github.itswisdomagain.developers;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {
    private List<GithubUser> users;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    Context context;

    public UsersRecyclerViewAdapter(Context context, List<GithubUser> galleryList, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.users = galleryList;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
        this.context = context;
    }

    @Override
    public UsersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_recycler_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(users.get(i).getUsername());
        Picasso.with(context).load(users.get(i).getImageUrl()).noFade().into(viewHolder.img);

        final int index = viewHolder.getAdapterPosition();

        viewHolder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewItemClickListener.onItemClicked(index, UsersRecyclerViewAdapter.this.users.get(index));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private CircleImageView img;
        private View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;

            title = (TextView)view.findViewById(R.id.tvUsername);
            img = (CircleImageView) view.findViewById(R.id.profile_image);
        }

        public void setOnItemClickListener(View.OnClickListener listener)
        {
            view.setOnClickListener(listener);
        }
    }
}
