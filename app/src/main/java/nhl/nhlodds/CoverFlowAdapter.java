package nhl.nhlodds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {
	
	private ArrayList<GameEntity> mData = new ArrayList<>(0);
	private Context mContext;

	public CoverFlowAdapter(Context context) {
		mContext = context;
	}
	
	public void setData(ArrayList<GameEntity> data) {
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.image1 = (ImageView) rowView.findViewById(R.id.image);

			rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.image1.setImageResource(mData.get(position).team1_logo);


		return rowView;
	}

    static class ViewHolder {
        public TextView text1;
		public ImageView image1;
		public TextView text2;
		public ImageView image2;
    }
}
