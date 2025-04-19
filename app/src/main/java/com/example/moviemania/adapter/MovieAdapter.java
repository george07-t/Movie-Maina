package com.example.moviemania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemania.R;
import com.example.moviemania.classes.Movie;
import com.example.moviemania.databinding.ImageGvItemBinding;

public class MovieAdapter extends ListAdapter<Movie, MovieAdapter.ViewHolder> {

    private final Context mContext;
    private final ClickListener clickListener;
    private Movie curItem = null;

    private static final DiffUtil.ItemCallback<Movie> diffCallback = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.fullyEquals(newItem);
        }
    };

    public MovieAdapter(Context mContext, ClickListener clickListener) {
        super(diffCallback);

        this.mContext = mContext;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ImageGvItemBinding binding = ImageGvItemBinding.bind(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.image_gv_item, parent, false)
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        curItem = getItem(position);

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w200/"+curItem.image)
                .into(holder.binding.imagePoster);

        holder.binding.textRating.setText( curItem.rating );
        holder.binding.textTitle.setText( curItem.title );
        holder.binding.textReleaseDate.setText( curItem.releaseDate );

        holder.binding.getRoot().setOnClickListener(v -> clickListener.onItemClicked( getItem(holder.getAdapterPosition()) ));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageGvItemBinding binding;

        public ViewHolder(ImageGvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ClickListener{
        void onItemClicked(Movie movie);
    }

}
