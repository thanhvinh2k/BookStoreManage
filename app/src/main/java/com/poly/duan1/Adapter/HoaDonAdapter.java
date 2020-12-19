package com.poly.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.poly.duan1.R;
import com.poly.duan1.model.HoaDon;
import java.text.SimpleDateFormat;
import java.util.List;

public class HoaDonAdapter extends BaseAdapter {
    private Context context;
    private List<HoaDon> arrHoaDon;
    private LayoutInflater inflater;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonAdapter (Context context, List<HoaDon> arrHoaDon) {
        this.context = context;
        this.arrHoaDon = arrHoaDon;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView txtMaHoaDon;
        TextView txtNgayMua;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row_hoa_don, null);
            holder.txtMaHoaDon = view.findViewById(R.id.tvRowMaHoaDon);
            holder.txtNgayMua = view.findViewById(R.id.tvRowNgayMua);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        HoaDon entry = arrHoaDon.get(position);
        holder.txtMaHoaDon.setText("Mã loại thiết bị: " + entry.getMaHoaDon());
        holder.txtNgayMua.setText( "Ngày mua: " + sdf.format(entry.getNgayMua()));
        return view;
    }
}
