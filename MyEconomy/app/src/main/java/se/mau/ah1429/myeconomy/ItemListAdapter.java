package se.mau.ah1429.myeconomy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemItemView;
        private final ImageView imageView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemItemView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
        void bind(Item item, final OnItemClickListener onItemClickListener){
        if(item != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private final LayoutInflater mInflater;
    private List<Item> mItems; // Cached copy of words
    private final OnItemClickListener onItemClickListener;



    ItemListAdapter(Context context, OnItemClickListener onItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mItems != null) {
          Item current = mItems.get(position);

            if(current.getType().equals("expense")){
                switch(current.getCategory()) {
                    case "Other":
                        holder.imageView.setImageResource(R.drawable.ic_other);
                        break;
                    case "Spare time":
                        holder.imageView.setImageResource(R.drawable.ic_hobby);
                        break;
                    case "Living":
                        holder.imageView.setImageResource(R.drawable.ic_home);
                        break;
                    case "Travel":
                        holder.imageView.setImageResource(R.drawable.ic_travel);
                        break;
                    case "Food":
                        holder.imageView.setImageResource(R.drawable.ic_food);
                        break;
                    default:

                }
                holder.itemItemView.setText("Title: "+current.getItem() +"\nValue: "+ current.getValue() +"\nDate: "+ current.getDate());
                holder.imageView.setVisibility(View.VISIBLE);
            }else {
                holder.itemItemView.setText("Title: "+current.getItem() +"\nValue: "+ current.getValue() + "\nCategory: "+ current.getCategory() +"\nDate: "+ current.getDate());
                holder.imageView.setVisibility(View.INVISIBLE);
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.itemItemView.setText("No Word");
        }
        holder.bind(mItems.get(position), onItemClickListener);
    }

    void setItems(List<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }
}
