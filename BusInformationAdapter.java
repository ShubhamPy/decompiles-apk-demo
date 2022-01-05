package in.linus.busmate.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import in.linus.busmate.Model.BusmateModelHelper;
import in.linus.busmate.R;
import java.util.List;

public class BusInformationAdapter extends RecyclerView.Adapter<mViewHolder> {
    Context context;
    Double laitude;
    Double longitude;
    public List<BusmateModelHelper> mDataset;

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public TextView mBusStopname;
        public TextView mDetails;
        public RelativeLayout mLayoutID;

        public mViewHolder(View view) {
            super(view);
            this.mBusStopname = (TextView) view.findViewById(R.id.m_bus_stop_name);
            this.mDetails = (TextView) view.findViewById(R.id.m_bus_details);
            this.mLayoutID = (RelativeLayout) view.findViewById(R.id.mLayoutID);
        }
    }

    public BusInformationAdapter(List<BusmateModelHelper> list, Context context2, String str, String str2) {
        this.mDataset = list;
        this.context = context2;
        this.laitude = Double.valueOf(Double.parseDouble(str));
        this.longitude = Double.valueOf(Double.parseDouble(str2));
    }

    public mViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new mViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.m_bus_info_layout, viewGroup, false));
    }

    public void onBindViewHolder(mViewHolder mviewholder, int i) {
        mviewholder.mBusStopname.setText(this.mDataset.get(i).getBus_stop_name());
        TextView textView = mviewholder.mDetails;
        textView.setText("Arrives: " + this.mDataset.get(i).getArriveTime() + "  Departure: " + this.mDataset.get(i).getDepartTime());
        if (this.laitude.doubleValue() == Double.parseDouble(this.mDataset.get(i).getBus_stop_latitude()) && this.longitude.doubleValue() == Double.parseDouble(this.mDataset.get(i).getBus_stop_longitude())) {
            mviewholder.mLayoutID.setBackgroundColor(Color.parseColor("#00c853"));
        } else {
            mviewholder.mLayoutID.setBackgroundColor(Color.parseColor("#494949"));
        }
    }

    public int getItemCount() {
        return this.mDataset.size();
    }
}
