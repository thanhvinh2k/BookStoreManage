package com.poly.duan1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.duan1.Adapter.ThietBiAdapter;
import com.poly.duan1.DAO.LoaiThietBiDAO;
import com.poly.duan1.DAO.ThietBiDAO;
import com.poly.duan1.model.LoaiThietBi;
import com.poly.duan1.model.ThietBi;

import java.util.ArrayList;
import java.util.List;


public class ThietBiFragment extends Fragment {
    List<ThietBi> dsThietBi = new ArrayList<>();
    ThietBiDAO thietBiDAO;
    LoaiThietBiDAO loaiThietBiDAO;
    ThietBiAdapter adapter;
    String loaiThietBi, updateLoaiTB;
    List<LoaiThietBi> arrLoaiTB;
    List<String> loaiTBList = new ArrayList<>();
    ListView lvThietBi;
    Spinner spnLoaiTB;

    public ThietBiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thiet_bi, container, false);
        lvThietBi = view.findViewById(R.id.lvThietBi);

        lvThietBi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu optionTB = new PopupMenu(getActivity(), view);
                optionTB.getMenuInflater().inflate(R.menu.menu_option_thiet_bi, optionTB.getMenu());
                optionTB.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_update_thiet_bi:
                                dialogUpdateTB(dsThietBi.get(position).getMaThietBi(),
                                        dsThietBi.get(position).getTenThietBi(), dsThietBi.get(position).getHangSX(),
                                        dsThietBi.get(position).getSoLuong(), dsThietBi.get(position).getGiaBan());
                                break;
                            case R.id.menu_delete_thiet_bi:
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setIcon(android.R.drawable.ic_delete);
                                builder.setTitle("Xác nhận xóa");
                                builder.setMessage("Bạn có chắc chắn xóa!");
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        thietBiDAO = new ThietBiDAO(getActivity());
                                        thietBiDAO.deleteThietBi(dsThietBi.get(position).getMaThietBi());
                                        dsThietBi.remove(position);
                                        getThietBi();

                                    }
                                });
                                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                            case R.id.menu_thiet_bi_detail:
                                dialogTBDetail(position);
                                break;
                        }
                        return false;
                    }
                });
                optionTB.show();
            }
        });

        FloatingActionButton actionButtonAddTB = view.findViewById(R.id.actionBt_addThietBi);
        actionButtonAddTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddThietBi();
            }
        });
        loaiThietBiDAO = new LoaiThietBiDAO(getActivity());
        arrLoaiTB = new ArrayList<>();
        arrLoaiTB = loaiThietBiDAO.getAllLoaiTB();
        for (int i = 0; i < arrLoaiTB.size() ; i++) {
            loaiTBList.add(arrLoaiTB.get(i).getLoaiThietBi());
        }
        getThietBi();
        return view;
    }

    private void dialogUpdateTB(final String maThietBi, String tenThietBi, String HSX, int soLuong, double giaBan) {
        final Dialog dialogUDTB = new Dialog(getActivity());
        dialogUDTB.setContentView(R.layout.dialog_update_thiet_bi);
        final EditText edTenTB = dialogUDTB.findViewById(R.id.edUpdateTB_TenThietBi);
        final EditText edHSX = dialogUDTB.findViewById(R.id.edUpdateTB_HSX);
        final EditText edSoLuong = dialogUDTB.findViewById(R.id.edUpdateTB_SoLuong);
        final EditText edGiaBan = dialogUDTB.findViewById(R.id.edUpdateTB_GiaBan);
        edTenTB.setText(tenThietBi);
        edHSX.setText(HSX);
        edSoLuong.setText(String.valueOf(soLuong));
        edGiaBan.setText(String.valueOf(giaBan));
        Button btnSaveTB = dialogUDTB.findViewById(R.id.btSaveUDTB);
        Button btnCancelTB = dialogUDTB.findViewById(R.id.btCancelUDTB);
        Spinner spnUpdateLoaiTB = dialogUDTB.findViewById(R.id.spnUpdateTB_LoaiTB);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, loaiTBList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUpdateLoaiTB.setAdapter(dataAdapter);

        spnUpdateLoaiTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateLoaiTB = loaiTBList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnCancelTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUDTB.dismiss();
            }
        });
        btnSaveTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThietBi thietBi = new ThietBi(maThietBi, edTenTB.getText().toString(), updateLoaiTB, edHSX.getText().toString(),
                        Integer.parseInt(edSoLuong.getText().toString()), Double.parseDouble(edGiaBan.getText().toString()));
                thietBiDAO.updateThietBi(thietBi);
                getThietBi();
                dialogUDTB.dismiss();
            }
        });

        dialogUDTB.show();
    }


    private void dialogTBDetail(int position) {
        Dialog dialogTBDetail = new Dialog(getActivity());
        dialogTBDetail.setContentView(R.layout.dialog_thiet_bi_detail);
        ThietBi thietBi = dsThietBi.get(position);
        TextView txtMaTB = dialogTBDetail.findViewById(R.id.tvMaTBDetail);
        TextView txtTenTB = dialogTBDetail.findViewById(R.id.tvThietBiDetail);
        TextView txtLoaiTB = dialogTBDetail.findViewById(R.id.tvLoaiTBDetail);
        TextView txtHSX = dialogTBDetail.findViewById(R.id.tvHSXDetail);
        TextView txtSoLuong = dialogTBDetail.findViewById(R.id.tvSoLuongDetail);
        TextView txtGiaBan = dialogTBDetail.findViewById(R.id.tvGiaBanDetail);
        txtLoaiTB.setText("Loại thiết bị: " + thietBi.getLoaiThietBi());
        txtMaTB.setText("Mã thiết bị: " + thietBi.getMaThietBi());
        txtTenTB.setText("Tên thiết bị: " + thietBi.getTenThietBi());
        txtHSX.setText("Hãng sản xuất: " + thietBi.getHangSX());
        txtSoLuong.setText("Số lượng: " + thietBi.getSoLuong());
        txtGiaBan.setText("Giá bán: " + thietBi.getGiaBan());
        dialogTBDetail.show();
    }

    private void dialogAddThietBi() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_thiet_bi);
        final EditText edMaTB = dialog.findViewById(R.id.edMaThietBi);
        final EditText edTenTB = dialog.findViewById(R.id.edTenThietBi);
        final EditText edHSX = dialog.findViewById(R.id.edHangSX);
        final EditText edSoLuong = dialog.findViewById(R.id.edSoLuong);
        final EditText edGiaBan = dialog.findViewById(R.id.edGiaBan);


        spnLoaiTB = dialog.findViewById(R.id.spnLoaiTB);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, loaiTBList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiTB.setAdapter(dataAdapter);

        spnLoaiTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaiThietBi = loaiTBList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnAddTB = dialog.findViewById(R.id.btAddThietBi);
        btnAddTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMaTB.length() == 0 || edTenTB.length() == 0 || edHSX.length() == 0 || edSoLuong.length() == 0 || edGiaBan.length() == 0) {
                    Toast.makeText(getActivity(), "yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    ThietBi thietBi = new ThietBi(edMaTB.getText().toString(), edTenTB.getText().toString(), loaiThietBi,
                            edHSX.getText().toString(), Integer.parseInt(edSoLuong.getText().toString()),
                            Double.parseDouble(edGiaBan.getText().toString()));
                    thietBiDAO = new ThietBiDAO(getActivity());
                    if (thietBiDAO.insertThietBi(thietBi) > 0) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                        getThietBi();
                    } else {
                        Toast.makeText(getActivity(), "thêm thất bại, yêu cầu thay đổi mã thiết bị!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Button btnCancelTB = dialog.findViewById(R.id.btCancelThietBi);
        btnCancelTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getThietBi() {
        thietBiDAO = new ThietBiDAO(getActivity());
        dsThietBi = thietBiDAO.getAllTB();
        adapter = new ThietBiAdapter(getActivity(), dsThietBi);
        lvThietBi.setAdapter(adapter);
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
