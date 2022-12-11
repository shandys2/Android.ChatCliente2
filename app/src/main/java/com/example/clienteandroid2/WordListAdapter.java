package com.example.clienteandroid2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.clienteandroid2.database.Mensaje;

public  class WordListAdapter extends ListAdapter<Mensaje, WordViewHolder> {

    public WordListAdapter(@NonNull DiffUtil.ItemCallback<Mensaje> diffCallback) {
        super(diffCallback);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return WordViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Mensaje current = getItem(position);
        holder.bind(current.texto);
    }

    static class WordDiff extends DiffUtil.ItemCallback<Mensaje> {

        @Override
        public boolean areItemsTheSame(@NonNull Mensaje oldItem, @NonNull Mensaje newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Mensaje oldItem, @NonNull Mensaje newItem) {
            return oldItem.texto.equals(newItem.texto);
        }
    }
}