package com.mohamed.ezz.nearestmasjids.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohamed.ezz.nearestmasjids.R;
import com.mohamed.ezz.nearestmasjids.models.Masjid;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.MyViewHolder> {
    private final ItemClickListener mItemClickListener;
    private Context mContext;
    private List<Masjid> mMasjidsList;

    public MasjidAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_masjid, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Masjid masjid = mMasjidsList.get(position);
        holder.itemView.setTag(masjid);
        holder.tvName.setText(masjid.fetchName());
        holder.tvDistance.setText(masjid.fetchDistance());
    }

    @Override
    public int getItemCount() {
        if (mMasjidsList == null) {
            return 0;
        }
        return mMasjidsList.size();
    }

    public void setMasjidsList(List<Masjid> newMasjidsList) {
        mMasjidsList = newMasjidsList;
        notifyDataSetChanged();
    }

    public List<Masjid> getMasjidsList() {
        return mMasjidsList;
    }

    public interface ItemClickListener {
        void onItemClickListener(String masjidName, String masjidDistance);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDistance)
        TextView tvDistance;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Masjid masjid = mMasjidsList.get(getAdapterPosition());
            mItemClickListener.onItemClickListener(masjid.fetchName(), masjid.fetchDistance());
        }

    }

}
