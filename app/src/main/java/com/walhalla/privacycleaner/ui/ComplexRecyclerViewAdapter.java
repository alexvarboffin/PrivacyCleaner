package com.walhalla.privacycleaner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import aa.model.GmailMessageViewModel;
import aa.model.RecyclerMenuViewModel;
import aa.model.ViewModel;
import com.walhalla.privacycleaner.R;

import java.util.List;


public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public void swap(List<ViewModel> objects) {
        this.items.clear();
        this.items.addAll(objects);
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        //        void onItemClicked(View v, int position);
        //void messageSelectedRequest(int id);

        void onItemClicked(View v, Object o);
    }


    private static final int TYPE_MESSAGE = R.layout.item_recycler_message;
    private static final int TYPE_EMPTY_VALUE = R.layout.empty_list_layout;
    private static final int TYPE_DEFAULT = 200;
    private static final int TYPE_ERROR = -1;

    private OnItemClickListener onItemClickListener;
    // The items to display in your RecyclerView
    private List<ViewModel> items;


    public ComplexRecyclerViewAdapter(List<ViewModel> items) {
        this.items = items;
//        this.items.add(new String("222222"));
//        this.items.add(new GmailMessageViewModel("222", "2222"));
    }

    public void setChildItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ((items == null) ? 0 : items.size());
    }


    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof RecyclerMenuViewModel) {
            return TYPE_EMPTY_VALUE;
        } else if (items.get(position) instanceof GmailMessageViewModel) {
            return TYPE_MESSAGE;
        }
        return TYPE_DEFAULT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        switch (viewType) {
            case TYPE_MESSAGE:
                view = inflater.inflate(R.layout.item_recycler_message, viewGroup, false);
                viewHolder = new MessageViewHolder(view);
                break;

            case TYPE_EMPTY_VALUE:
                view = inflater.inflate(R.layout.item_recycler_message
                        /*R.layout.empty_list_layout*/, viewGroup, false);
                viewHolder = new ViewEmptyHolder(view);
                break;
            default:
                view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {

            case TYPE_MESSAGE:
                configureMessageViewHolder((MessageViewHolder) viewHolder, position);
                break;


            case TYPE_EMPTY_VALUE:
                RecyclerMenuViewModel obj = (RecyclerMenuViewModel) items.get(position);
                ViewEmptyHolder holder = (ViewEmptyHolder) viewHolder;
                holder.tvThreadId.setText(obj.threadId);
                holder.tvId.setText(obj.id);
                holder.image.setImageResource(obj.icon);

                break;

            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }


    private void configureMessageViewHolder(MessageViewHolder holder, int position) {
        GmailMessageViewModel obj = (GmailMessageViewModel) items.get(position);
        if (obj != null) {
            holder.tvThreadId.setText(obj.threadId);
            holder.tvId.setText(obj.id);
            holder.image.setImageResource(obj.icon);
            holder.blink.setVisibility(View.VISIBLE);
            //holder.edit.setOnClickListener(v -> onItemClickListener.onItemClicked(v, obj));
        }
    }


/*
    private void configureViewHolder2(ViewHolder2 vh2, int positon) {
        //vh2.getImageView().setImageResource(R.drawable.sample_golden_gate);
        APIError error = (APIError) items.get(positon);
        if (error != null) {
            vh2.error_msg.setText(error.getErrorMsg());
        }
    }
*/

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView tvThreadId;
        private final TextView tvId;
        private final View blink;


        MessageViewHolder(View view1) {
            super(view1);

            tvId = view1.findViewById(R.id.textView1);
            tvThreadId = view1.findViewById(R.id.tv_thread_id);
            image = view1.findViewById(R.id.app_icon);
            blink = view1.findViewById(R.id.blink);
            view1.setOnClickListener(
                    view -> {
                        //onItemClickListener.onItemClicked(view1, items.get(getAdapterPosition()));
                    }
            );
            //messageSelectedRequest(int id);
        }
//        @Override
//        public void onClick(View v) {
//            onItemClickListener.onItemClicked(v, getAdapterPosition());
//        }
    }
//========================================================================================


    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        vh.getLabel().setText(items.get(position).toString());
    }

    static class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

        TextView text1;

        RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }

        public TextView getLabel() {
            return text1;
        }
    }

    public static class ViewEmptyHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView tvThreadId;
        private final TextView tvId;


        ViewEmptyHolder(View view1) {
            super(view1);

            tvId = view1.findViewById(R.id.textView1);
            tvThreadId = view1.findViewById(R.id.tv_thread_id);
            image = view1.findViewById(R.id.app_icon);

            view1.setOnClickListener(
                    view -> {
                        //onItemClickListener.onItemClicked(view1, items.get(getAdapterPosition()));
                    }
            );
            //messageSelectedRequest(int id);
        }
//        @Override
//        public void onClick(View v) {
//            onItemClickListener.onItemClicked(v, getAdapterPosition());
//        }
    }
//    private class ViewEmptyHolder extends RecyclerView.ViewHolder {
//
//        private final TextView text1;
//
//        public ViewEmptyHolder(View v2) {
//            super(v2);
//            text1 = v2.findViewById(R.id.textView1);
//        }
//    }
}

