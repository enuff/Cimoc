package com.hiroshi.cimoc.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Hiroshi on 2016/7/1.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<T> mDataSet;
    LayoutInflater mInflater;

    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    public BaseAdapter(Context context, List<T> list) {
        mContext = context;
        mDataSet = list;
        mInflater = LayoutInflater.from(context);
    }

    public void add(T data) {
        if (mDataSet.add(data)) {
            notifyItemInserted(mDataSet.size());
        }
    }

    public void add(int location, T data) {
        mDataSet.add(location, data);
        notifyItemInserted(location);
    }

    public void addAll(Collection<T> collection) {
        addAll(mDataSet.size(), collection);
    }

    public void addAll(int location, Collection<T> collection) {
        if (mDataSet.addAll(location, collection)) {
            notifyItemRangeInserted(location, location + collection.size());
        }
    }

    public boolean exist(T data) {
        return mDataSet.indexOf(data) != -1;
    }

    public void remove(T data) {
        int position = mDataSet.indexOf(data);
        if (position != -1) {
            remove(position);
        }
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll(Collection<T> collection) {
        mDataSet.removeAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataSet.clear();
        notifyDataSetChanged();
    }

    public List<T> getDateSet() {
        return mDataSet;
    }

    public void setData(List<T> list) {
        mDataSet.clear();
        mDataSet.addAll(list);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mLongClickListener = onItemLongClickListener;
    }

    public abstract RecyclerView.ItemDecoration getItemDecoration();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(v, getAdapterPosition());
            }
            return true;
        }
    }

}
