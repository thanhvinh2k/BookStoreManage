package com.poly.duan1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.duan1.Adapter.HoaDonAdapter;
import com.poly.duan1.DAO.HoaDonChiTietDAO;
import com.poly.duan1.DAO.HoaDonDAO;
import com.poly.duan1.model.HoaDon;
import com.poly.duan1.model.HoaDonChiTiet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HoaDonFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText edMaHD;
    //fragment
    List<HoaDon> dsHoaDon = new ArrayList<>();
    HoaDonDAO hoaDonDAO;
    ListView lvHoaDon;
    HoaDonAdapter hoaDonAdapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        FloatingActionButton actionButtonAddHD = view.findViewById(R.id.actionBt_addHoaDon);
        actionButtonAddHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddHD();
            }
        });
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        try {
            getHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option_hoa_don, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_update_hd:
                                dialogUpdateHD(dsHoaDon.get(position).getMaHoaDon(), sdf.format(dsHoaDon.get(position).getNgayMua()));
                                break;
                            case R.id.menu_delete_hd:
                                hoaDonDAO = new HoaDonDAO(getActivity());
                                HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(getActivity());
                                if (hoaDonChiTietDAO.checkHoaDon(dsHoaDon.get(position).getMaHoaDon())) {
                                    Toast.makeText(getActivity(), "bạn phải xóa hóa đơn chi tiết trước khi xóa hóa đơn này!", Toast.LENGTH_LONG).show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setIcon(android.R.drawable.ic_delete);
                                    builder.setTitle("Xác nhận xóa");
                                    builder.setMessage("Bạn có chắc chắn xóa!");
                                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            hoaDonDAO.deleteHoaDon(dsHoaDon.get(position).getMaHoaDon());
                                            dsHoaDon.remove(position);
                                            try {
                                                getHoaDon();
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }

                                break;
                            case R.id.menu_detail_hd:
                                mListener.getMaHoaDon(dsHoaDon.get(position).getMaHoaDon());
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

    private void dialogAddHD() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_hoa_don);
        edMaHD = dialog.findViewById(R.id.edAddHD_MaHoaDon);
        final EditText edNgayMua = dialog.findViewById(R.id.edAddHD_NgayMua);
        Button btnPickDate = dialog.findViewById(R.id.btAddHD_PickDate);
        Button btnAddHoaDon = dialog.findViewById(R.id.btAddHoaDon);

        btnAddHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hoaDonDAO = new HoaDonDAO(getActivity());
                try {
                    if (edMaHD.length() == 0 || edNgayMua.length() == 0) {
                        Toast.makeText(getActivity(), "yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    } else {
                        HoaDon hoaDon = new HoaDon(edMaHD.getText().toString(), sdf.parse(edNgayMua.getText().toString()));
                        if (hoaDonDAO.insertHoaDon(hoaDon) > 0) {
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            mListener.onFragmentInteraction(edMaHD.getText().toString());
                            getMaHD(edMaHD.getText().toString());
                            getHoaDon();
                        } else {
                            Toast.makeText(getActivity(), "Thêm thất bại, yêu cầu sửa mã hóa đơn!", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DAY_OF_MONTH);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        edNgayMua.setText(sdf.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
                pickerDialog.show();
            }
        });

        dialog.show();
    }


    private void getHoaDon() throws ParseException {
        hoaDonDAO = new HoaDonDAO(getActivity());
        dsHoaDon = hoaDonDAO.getALLHoaDon();
        hoaDonAdapter = new HoaDonAdapter(getActivity(), dsHoaDon);
        lvHoaDon.setAdapter(hoaDonAdapter);
    }

    private void dialogUpdateHD(final String maHD, String ngayMua) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_update_hoa_don);
        final EditText edNgay = dialog.findViewById(R.id.edUpdateHD_ngayMua);
        edNgay.setText(ngayMua);
        Button btPickDate = dialog.findViewById(R.id.btUpdateHD_pickDate);
        Button btSave = dialog.findViewById(R.id.btSaveUDHD);
        Button btCancel = dialog.findViewById(R.id.btCancelUDHD);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoaDonDAO = new HoaDonDAO(getActivity());
                try {
                    HoaDon hoaDon = new HoaDon(maHD, sdf.parse(edNgay.getText().toString()));
                    hoaDonDAO.updateHoaDon(hoaDon);
                    getHoaDon();
                    dialog.dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DAY_OF_MONTH);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        edNgay.setText(sdf.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
                pickerDialog.show();
            }
        });
        dialog.show();
    }

    public static void getMaHD(String maHD) {
        HoaDonFragment hoaDonFragment = new HoaDonFragment();
        Bundle bd = new Bundle();
        bd.putString("MaHoaDon", maHD);
        hoaDonFragment.setArguments(bd);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String maHD);

        void getMaHoaDon(String maHoaDon);
    }


}
