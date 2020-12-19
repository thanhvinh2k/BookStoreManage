package com.poly.duan1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poly.duan1.DAO.HoaDonChiTietDAO;


public class ThongKeFragment extends Fragment {
    TextView tvTKNgay, tvTKThang, tvTKNam;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    public ThongKeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        tvTKNgay = view.findViewById(R.id.tvThongKeNgay);
        tvTKThang = view.findViewById(R.id.tvThongKeThang);
        tvTKNam = view.findViewById(R.id.tvThongKeNam);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
        tvTKNgay.setText("Hôm nay: " + hoaDonChiTietDAO.getDoanhThuTheoNgay() + " (triệu)");
        tvTKThang.setText("Tháng này: " + hoaDonChiTietDAO.getDoanhThuTheoThang() + " (triệu)");
        tvTKNam.setText("Năm nay: " + hoaDonChiTietDAO.getDoanhThuTheoNam() + " (triệu)");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
