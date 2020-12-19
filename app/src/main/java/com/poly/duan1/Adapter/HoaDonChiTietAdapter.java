package com.poly.duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duan1.DAO.HoaDonChiTietDAO;
import com.poly.duan1.R;
import com.poly.duan1.model.HoaDonChiTiet;

import java.util.List;

public class HoaDonChiTietAdapter extends BaseAdapter {
    List<HoaDonChiTiet> arrHDCT;
    private Context context;
    private LayoutInflater inflater;
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    public HoaDonChiTietAdapter( Context context, List<HoaDonChiTiet> arrHDCT) {
        this.context = context;
        this.arrHDCT = arrHDCT;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHDCT.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHDCT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtMaThietBi;
        TextView txtSoLuongMua;
        TextView txtGiaBan;
        TextView txtThanhTien;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_hoa_don_chi_tiet, null);
            holder.txtMaThietBi = convertView.findViewById(R.id.tvHDCT_MaTB);
            holder.txtSoLuongMua = convertView.findViewById(R.id.tvHDCT_SoLuongMua);
            holder.txtGiaBan = convertView.findViewById(R.id.tvHDCT_GiaBan);
            holder.txtThanhTien = convertView.findViewById(R.id.tvHDCT_ThanhTien);
            holder.imgDelete = convertView.findViewById(R.id.imgHDCT_delete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder =new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa!");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            hoaDonChiTietDAO.deleteHDCT(String.valueOf(arrHDCT.get(position).getMaHDCT()));
                            arrHDCT.remove(position);
                            notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog =builder.create();
                    dialog.show();

                }
            });
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HoaDonChiTiet hoaDonChiTiet = arrHDCT.get(position);
        holder.txtMaThietBi.setText("Mã thiết bị: " + hoaDonChiTiet.getThietBi().getMaThietBi());
        holder.txtSoLuongMua.setText("Số lượng mua: " + hoaDonChiTiet.getSoLuongMua());
        holder.txtGiaBan.setText("Giá bán: " + hoaDonChiTiet.getThietBi().getGiaBan());
        holder.txtThanhTien.setText("Thành tiền: " + (hoaDonChiTiet.getSoLuongMua() * hoaDonChiTiet.getThietBi().getGiaBan()) + "(triệu đồng)") ;
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDonChiTiet> items) {
        this.arrHDCT = items;
        notifyDataSetChanged();
    }
}
