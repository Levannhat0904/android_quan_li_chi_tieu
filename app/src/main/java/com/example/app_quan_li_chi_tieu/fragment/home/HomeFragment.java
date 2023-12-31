package com.example.app_quan_li_chi_tieu.fragment.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieu;
import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieuAdapter;
import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private DatabaseHelper_chitieu dbHelper_chitieu;
    private ChiTieuAdapter chiTieuAdapter;
    private  ImageButton search;
    ArrayAdapter<String> adapter; //dùng cho autocomplete
    private Cursor cursor;
//    DatabaseHelper_chitieu db;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
//        DatabaseHelper_chitieu dbHelper_chitieu;
//        dbHelper_chitieu = new DatabaseHelper_chitieu(getContext());
//        dbHelper_chitieu.onCreate(dbHelper_chitieu.getWritableDatabase());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategoryData("");

    }

    public static HomeFragment newInstance() {

        return new HomeFragment();


    }
    private void initializeDatabaseHelper() {
        dbHelper_chitieu = new DatabaseHelper_chitieu(getActivity());
        chiTieuAdapter = new ChiTieuAdapter(getActivity(), new ArrayList<>());

        dbHelper_chitieu.getWritableDatabase(); // Mở cơ sở dữ liệu ở đây
        loadCategoryData("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        loadCategoryData();
        // Inflate the layout for this fragment
        initializeDatabaseHelper();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        lam chức năng tìm kiếm

        AutoCompleteTextView editText = rootView.findViewById(R.id.edt_key);
//                lấy các tên phân loaji cho vào list
        List<String> list = new ArrayList<>();
        DatabaseHelper_phanloai db_helper_phanloai = new DatabaseHelper_phanloai(getContext());
        list = db_helper_phanloai.getExpenseNameList(list);
        System.out.println(list);
         adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        editText.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        editText.setThreshold(1);
//        hiển thị listview

         search = rootView.findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = editText.getText().toString();

//                String text = editText.getText().toString();
                if(text.equals("")){
                    loadCategoryData("");
                }else {
                    loadCategoryData(text);
                }
            }
        });
        ListView listView = rootView.findViewById(R.id.lv_home);
        listView.setAdapter(chiTieuAdapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ChiTieu chitieu = chiTieuAdapter.getItem(position);

                // Hiển thị dialog với các tùy chọn sửa hoặc xóa danh mục
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Tùy chọn danh mục");
                builder.setItems(new CharSequence[]{"Sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
//                                goi bottom sheet
                                int id_chitieu = chitieu.getId();
                                CustomBottomSheetDialog_home_chinhsua bottomSheetDialog = new CustomBottomSheetDialog_home_chinhsua(view.getContext(), id_chitieu);
                                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_view_home_chinh_sua, null);
                                // Gắn layout cho BottomSheetDialog
                                bottomSheetDialog.setContentView(bottomSheetView);

                                // Hiển thị BottomSheetDialog
                                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        loadCategoryData("");
                                    }
                                });
                                bottomSheetDialog.show();

                                break;
                            case 1:
                                // Sử dụng category.getId() để biết danh mục nào đang được xóa
                                int id = chitieu.getId();
                                dbHelper_chitieu.deleteExpense(id);
                                loadCategoryData("");
                                break;
                        }
                    }
                });
                builder.show();
                return true;
        });
        rootView.findViewById(R.id.button_thaotac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một BottomSheetDialog
                CustomBottomSheetDialog_home bottomSheetDialog = new CustomBottomSheetDialog_home(view.getContext());

                // Bây giờ, bạn cần gắn nội dung (layout) cho BottomSheetDialog.
                // Ví dụ, bạn có thể sử dụng LayoutInflater để nạp một tệp layout XML:
                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_view_home, null);
                // Gắn layout cho BottomSheetDialog
                bottomSheetDialog.setContentView(bottomSheetView);

                // Hiển thị BottomSheetDialog
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        loadCategoryData("");
                    }
                });
                bottomSheetDialog.show();
//                adapter.notifyDataSetChanged();

            }
        });

        return rootView;
    }
    private void loadCategoryData(String key_search) {

        List<ChiTieu> ChiTieuList = getChiTieuDataFromSQLite(key_search);
        chiTieuAdapter.clear();
        chiTieuAdapter.addAll(ChiTieuList);
        chiTieuAdapter.notifyDataSetChanged();

    }

    private List<ChiTieu> getChiTieuDataFromSQLite(String key_search) {
        List<ChiTieu> ChiTieuList = new ArrayList<>();
        SQLiteDatabase db_chitieu = dbHelper_chitieu.getReadableDatabase();
        try {
            DatabaseHelper_phanloai db_helper_phanloai = new DatabaseHelper_phanloai(getContext());
            if (key_search.equals("")) {

                cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME, null);
            } else {
                try {
                    int id_cat[] = db_helper_phanloai.getExpenseId(key_search);
//                System.out.println(id_cat[0]);
//                lấy các chi tiêu có trong mảng id
                    String where = "`cat_id`";
                    for (int i=0; i<id_cat.length;i++){
                        where += " = " + id_cat[i]+" OR `cat_id`";
                    }
                    where = where.substring(0, where.length()-11);
                    System.out.println(where);
//                cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME + " WHERE " + where, null);
//                cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME + " WHERE " + DatabaseHelper_chitieu.COLUMN_CAT_ID + " LIKE '%" + id_cat[0] + "%'", null);
                    cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME + " WHERE " + where, null);
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getContext(), "Không tìm thấy kết quả", Toast.LENGTH_SHORT);
                    toast.show();
                    cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME, null);
                }

            }
//
//             cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME, null);
//            cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME + " WHERE " + DatabaseHelper_chitieu.COLUMN_CAT_ID + " LIKE '%" + key_search + "%'", null);
//            String sql = "SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME + " WHERE " + DatabaseHelper_chitieu.COLUMN_NOTE + " LIKE '%" + key_search + "%'";
             ImageButton search = getActivity().findViewById(R.id.btn_search);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_ID));
                int price = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_PRICE));
                int cat_id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_CAT_ID)); // Thay đổi tên cột
                String dateString = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_DATE));
//                Date date = new Date(dateString); // Chuyển đổi ngày tháng từ chuỗi sang đối tượng Date
                String note = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_NOTE));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_TYPE));
                ChiTieu chiTieu = new ChiTieu(id, price, dateString, cat_id, note, type);
                ChiTieuList.add(chiTieu);
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db_chitieu.close();
        }
        return ChiTieuList;
    }
}