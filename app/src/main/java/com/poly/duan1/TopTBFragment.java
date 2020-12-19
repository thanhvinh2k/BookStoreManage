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
import android.widget.Toast;

import com.poly.duan1.Adapter.ThietBiAdapter;
import com.poly.duan1.DAO.ThietBiDAO;
import com.poly.duan1.model.ThietBi;

import java.util.ArrayList;
import java.util.List;


public class TopTBFragment extends Fragment {
    List<ThietBi> dsTB = new ArrayList<>();
    ListView lvThietBi;
    Spinner spnMonth;
    String month;
    ThietBiAdapter adapter;
    ThietBiDAO thietBiDAO;

    public TopTBFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_tb, container, false);
        lvThietBi = view.findViewById(R.id.lvTopTB);
        spnMonth = view.findViewById(R.id.spnTopTB);
        final String[] arrMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrMonth);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(dataAdapter);
        spnMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = arrMonth[position];
                thietBiDAO = new ThietBiDAO(getActivity());
                dsTB = thietBiDAO.getTop10ThietBi(month);
                adapter = new ThietBiAdapter(getActivity(), dsTB);
                lvThietBi.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
