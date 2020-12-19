package com.poly.duan1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.duan1.Adapter.LoaiTBAdapter;
import com.poly.duan1.DAO.LoaiThietBiDAO;
import com.poly.duan1.model.LoaiThietBi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class LoaiTBFragment extends Fragment{

    private LoaiThietBiDAO loaiThietBiDAO;
    LoaiTBAdapter adapter;
    List<LoaiThietBi> dsLoaiTB;
    ListView lvLoaiTB;
    public LoaiTBFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_tb, container, false);
        FloatingActionButton addLoaiTB = view.findViewById(R.id.actionBt_addLoaiTB);
        lvLoaiTB = view.findViewById(R.id.lvLoaiTB);
        dsLoaiTB = new ArrayList<>();
        addLoaiTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddLoaiTB();
            }
        });
        getLoaiTB();
        lvLoaiTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int i, long id) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option_loaitb, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_update_ltb:
                                dialogUpdateLoaiTB(dsLoaiTB.get(i).getMaLoaiThietBi(), dsLoaiTB.get(i).getLoaiThietBi(), dsLoaiTB.get(i).getViTri());
                                break;
                            case R.id.menu_delete_ltb:
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setIcon(android.R.drawable.ic_delete);
                                builder.setTitle("Xác nhận xóa");
                                builder.setMessage("Bạn có chắc chắn xóa!");
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        loaiThietBiDAO = new LoaiThietBiDAO(getActivity());
                                        loaiThietBiDAO.deleteLoaiTB(dsLoaiTB.get(i).getMaLoaiThietBi());
                                        dsLoaiTB.remove(i);
                                        getLoaiTB();

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
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return view;
    }

    private void dialogUpdateLoaiTB(final String maLTB, String loaiTB, String viTri) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_update_loai_tb);
        final EditText edUDLoaiTB = dialog.findViewById(R.id.edUpdateLTB_LoaiTB);
        final EditText edUDViTri = dialog.findViewById(R.id.edUpdateLTB_ViTri);
        edUDLoaiTB.setText(loaiTB);
        edUDViTri.setText(viTri);
        Button btnSaveLTB = dialog.findViewById(R.id.btSaveUDLTB);
        Button btnCancelLTB = dialog.findViewById(R.id.btCancelUDLTB);
        btnSaveLTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiThietBiDAO = new LoaiThietBiDAO(getActivity());
                LoaiThietBi loaiThietBi = new LoaiThietBi(maLTB, edUDLoaiTB.getText().toString(), edUDViTri.getText().toString());
                loaiThietBiDAO.updateLoaiTB(loaiThietBi);
                getLoaiTB();
                dialog.dismiss();
            }
        });

        btnCancelLTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void dialogAddLoaiTB() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_loai_tb);
        final EditText edMaLoaiTB = dialog.findViewById(R.id.edMaLoaiTB);
        final EditText edLoaiTB = dialog.findViewById(R.id.edLoaiTB);
        final EditText edViTri = dialog.findViewById(R.id.edViTri);
        Button btnAddLoaiTB = dialog.findViewById(R.id.btAddLoaiTB);
        Button btnCancel = dialog.findViewById(R.id.btCancelLoaiTB);

        btnAddLoaiTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMaLoaiTB.length() == 0 || edLoaiTB.length() == 0 || edViTri.length() == 0) {
                    Toast.makeText(getActivity(), "yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    loaiThietBiDAO = new LoaiThietBiDAO(getActivity());
                    LoaiThietBi loaiThietBi = new LoaiThietBi(edMaLoaiTB.getText().toString(), edLoaiTB.getText().toString(), edViTri.getText().toString());
                    if (loaiThietBiDAO.insertLoaiTB(loaiThietBi) > 0) {
                        Toast.makeText(getActivity(), "thêm thành công", Toast.LENGTH_LONG).show();
                        getLoaiTB();
                    }else {
                        Toast.makeText(getActivity(), "thêm thất bại, yêu cầu thay đổi mã loại thiết bị", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getLoaiTB() {
        loaiThietBiDAO = new LoaiThietBiDAO(getActivity());
        dsLoaiTB = loaiThietBiDAO.getAllLoaiTB();
        adapter = new LoaiTBAdapter(getActivity(), dsLoaiTB);
        lvLoaiTB.setAdapter(adapter);
    }
}
