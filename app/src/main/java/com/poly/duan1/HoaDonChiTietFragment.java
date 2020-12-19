package com.poly.duan1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.duan1.Adapter.HoaDonChiTietAdapter;
import com.poly.duan1.DAO.HoaDonChiTietDAO;
import com.poly.duan1.DAO.ThietBiDAO;
import com.poly.duan1.model.HoaDon;
import com.poly.duan1.model.HoaDonChiTiet;
import com.poly.duan1.model.ThietBi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HoaDonChiTietFragment extends Fragment {
    EditText edMaHD, edSoLuong;
    Spinner spnMaTB;
    TextView tvThanhTien;
    Button btnThanhToan, btnThemHoaDon;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    ThietBiDAO thietBiDAO;
    List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView listViewHDCT;
    String maTB;
    HoaDonChiTietAdapter adapter;
    double thanhTien = 0;
    public HoaDonChiTietFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don_chi_tiet, container, false);
        edMaHD = view.findViewById(R.id.edHDCT_MaHoaDon);
        spnMaTB = view.findViewById(R.id.spnHDCT_MaThietBi);
        edSoLuong = view.findViewById(R.id.edHDCT_SoLuongMua);
        listViewHDCT = view.findViewById(R.id.lvHDCT);
        tvThanhTien = view.findViewById(R.id.tvThanhTienHDCT);
        btnThemHoaDon = view.findViewById(R.id.btHDCT_ThemHDCT);
        btnThanhToan = view.findViewById(R.id.btThanhToan);

        final List<String> dsMaTB = new ArrayList<>();
        ThietBiDAO thietBiDAO = new ThietBiDAO(getActivity());
        List<ThietBi> dsTB = thietBiDAO.getAllTB();
        for (int i = 0; i < dsTB.size(); i++) {
            dsMaTB.add(dsTB.get(i).getMaThietBi());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dsMaTB);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaTB.setAdapter(dataAdapter);
        spnMaTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTB = dsMaTB.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHDCT();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhToanHoaDon();
            }
        });
        adapter = new HoaDonChiTietAdapter(getActivity(), dsHDCT);
        listViewHDCT.setAdapter(adapter);
        Bundle bd = getArguments();
        if (bd != null) {
            edMaHD.setText(bd.getString("maHoaDon"));
        }
        return view;
    }

    public void AddHDCT() {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
        thietBiDAO = new ThietBiDAO(getActivity());
        try {
            if (edMaHD.getText().toString().isEmpty() || edSoLuong.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
            } else {
                ThietBi thietBi = thietBiDAO.getThietBiByID(maTB);
                if (thietBi != null){
                    int pos = checkMaThietBi(dsHDCT,maTB);
                    HoaDon hoaDon = new HoaDon(edMaHD.getText().toString(),new Date());
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(1, hoaDon, thietBi, Integer.parseInt(edSoLuong.getText().toString()));
                    if (pos >= 0) {
                        int soluong = dsHDCT.get(pos).getSoLuongMua();
                        hoaDonChiTiet.setSoLuongMua(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                        dsHDCT.set(pos,hoaDonChiTiet);
                    }else {
                        dsHDCT.add(hoaDonChiTiet);
                    }
                    adapter.changeDataset(dsHDCT);
                }else {
                    Toast.makeText(getActivity(),"Mã thiết bị không tồn tại",Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception ex) {
            Log.e("ERROR==///", ex.toString());
        }
    }

    public void thanhToanHoaDon() {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
        //tinh tien
        thanhTien = 0;
        try {
            for (HoaDonChiTiet hd: dsHDCT) {
                hoaDonChiTietDAO.insertHDCT(hd);
                thanhTien = thanhTien + hd.getSoLuongMua() *  hd.getThietBi().getGiaBan();
            }
            tvThanhTien.setText("Tổng tiền: " + thanhTien + " (triệu đồng)");
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public int checkMaThietBi(List<HoaDonChiTiet> listHD, String maTB) {
        int pos = -1;
        for (int i = 0; i < listHD.size(); i++ ) {
            HoaDonChiTiet hd = listHD.get(i);
            if (hd.getThietBi().getMaThietBi().equalsIgnoreCase(maTB)) {
                pos = i;
                break;
            }
        }
        return pos;
    }


}
