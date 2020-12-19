package com.poly.duan1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.poly.duan1.Adapter.HoaDonChiTietAdapter;
import com.poly.duan1.DAO.HoaDonChiTietDAO;
import com.poly.duan1.model.HoaDon;
import com.poly.duan1.model.HoaDonChiTiet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HDCTByIDFragment extends Fragment {
    ListView lvHDCTByID;
    List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    HoaDonChiTietAdapter adapter ;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public HDCTByIDFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hdctby_id, container, false);
        lvHDCTByID = view.findViewById(R.id.lvHDCTByID);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                dsHDCT = hoaDonChiTietDAO.getAllHDCTByID(bundle.getString("maHD"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        adapter = new HoaDonChiTietAdapter(getActivity(), dsHDCT);
        lvHDCTByID.setAdapter(adapter);
        return view;
    }

}
