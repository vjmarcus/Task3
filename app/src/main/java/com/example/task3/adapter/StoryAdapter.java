package com.example.task3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.task3.R;
import com.example.task3.RecyclerViewClickListener;
import com.example.task3.model.Story;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private List<Story> storyList;
    private RecyclerViewClickListener recyclerViewClickListener;

    public StoryAdapter(List<Story> storyList, RecyclerViewClickListener recyclerViewClickListener) {
        this.storyList = storyList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public void clear() {
        storyList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Story> list) {
        storyList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item,
                parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.titleTextView.setText(story.getTitle());
        holder.authorTextView.setText(story.getAuthor());
        holder.publishedAtTextView.setText(story.getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView authorTextView;
        private final TextView titleTextView;
        private final TextView publishedAtTextView;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}
