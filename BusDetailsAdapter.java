package in.linus.busmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import in.linus.busmate.Activity.BusInformationActivity;
import in.linus.busmate.Model.BusmateModelHelper;
import in.linus.busmate.R;
import java.util.List;

public class BusDetailsAdapter extends RecyclerView.Adapter<mViewHolder> {
    Context context;
    public List<BusmateModelHelper> mDataset;

    public static class mViewHolder extends RecyclerView.ViewHolder {
        public TextView mBusname;
        public TextView mDetails;
        public RelativeLayout mLayoutID;

        public mViewHolder(View view) {
            super(view);
            this.mBusname = (TextView) view.findViewById(R.id.m_bus_name);
            this.mDetails = (TextView) view.findViewById(R.id.m_from_to);
            this.mLayoutID = (RelativeLayout) view.findViewById(R.id.mLayoutID);
        }
    }

    public BusDetailsAdapter(List<BusmateModelHelper> list, Context context2) {
        this.mDataset = list;
        this.context = context2;
    }

    public mViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new mViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.m_bus_details_view, viewGroup, false));
    }

    public void onBindViewHolder(mViewHolder mviewholder, final int i) {
        mviewholder.mBusname.setText(this.mDataset.get(i).getBusName());
        TextView textView = mviewholder.mDetails;
        textView.setText(this.mDataset.get(i).getFrom() + " - " + this.mDataset.get(i).getTo());
        mviewholder.mLayoutID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Context context = BusDetailsAdapter.this.context;
                Toast.makeText(context, "Selected : " + BusDetailsAdapter.this.mDataset.get(i).getBusName(), 0).show();
                Intent intent = new Intent(BusDetailsAdapter.this.context, BusInformationActivity.class);
                intent.putExtra("bus_id", BusDetailsAdapter.this.mDataset.get(i).getBusID());
                BusDetailsAdapter.this.context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.mDataset.size();
    }
}
