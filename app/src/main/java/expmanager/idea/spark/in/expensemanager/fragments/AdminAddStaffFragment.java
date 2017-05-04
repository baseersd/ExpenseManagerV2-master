package expmanager.idea.spark.in.expensemanager.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.MainActivity;
import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.adapters.MyStaffDetailsAdapter;
import expmanager.idea.spark.in.expensemanager.database.DatabaseHandler;
import expmanager.idea.spark.in.expensemanager.model.AddStaffRequest;
import expmanager.idea.spark.in.expensemanager.model.Staff;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Haresh.Veldurty on 3/9/2017.
 */

public class AdminAddStaffFragment extends Fragment implements  DatePickerDialog.OnDateSetListener{
    Button addstaffbtn,cancelstaffdialog,addstafftoDb;
    EditText staffname,salary,staffemail,designation,staffaddress,staffphonenumber;
    private TextView started;
    Spinner spinnershift1,spinnershift2,spinnertime1,spinnertime2,spinnersal;
    DatabaseHandler db;
    ImageButton imageButtonProfilePic;
    private ImageView imgCalender;
    ListView stafflist;
    List<Staff> list,staff_list;
    private int PICK_IMAGE_REQUEST = 1;
    private String base64Image="";
    private DatePickerDialog datePickerDialog;
    public static MyStaffDetailsAdapter adapt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public AdminAddStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin_addstaff_layout,
                container, false);
        db = new DatabaseHandler(getActivity());
        addstaffbtn = (Button) rootView.findViewById(R.id.addstaffbtn);
        addstaffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddStaffDialog();
            }
        });
        stafflist = (ListView) rootView.findViewById(R.id.stafflist);
        list = db.getAllStaff();
        if(list != null) {
            adapt = new MyStaffDetailsAdapter(getActivity(), R.layout.list_staff_item, list);
            stafflist.setAdapter(adapt);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){

            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeStaffTextColor();

        }
    }

    public void openAddStaffDialog() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addstaffdialog_layout);
        cancelstaffdialog = (Button) dialog.findViewById(R.id.cancelstaffdialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().width = (int) (Utils.getDeviceMetrics(getActivity()).widthPixels*0.55);

        imgCalender = (ImageView)dialog.findViewById(R.id.img_calendar);
        spinnershift1 = (Spinner) dialog.findViewById(R.id.spinnershift1);
        spinnershift2 = (Spinner) dialog.findViewById(R.id.spinnershift2);
        spinnertime1 = (Spinner) dialog.findViewById(R.id.spinnertime1);
        spinnertime2 = (Spinner) dialog.findViewById(R.id.spinnertime2);
        spinnersal = (Spinner) dialog.findViewById(R.id.spinnersal);
        addstafftoDb  = (Button) dialog.findViewById(R.id.addstafftodb);
        staffname = (EditText) dialog.findViewById(R.id.staff_name);
        started = (TextView) dialog.findViewById(R.id.started);
        salary = (EditText) dialog.findViewById(R.id.salary);
        staffemail = (EditText) dialog.findViewById(R.id.staffemail);
        designation = (EditText) dialog.findViewById(R.id.designation);
        staffaddress = (EditText) dialog.findViewById(R.id.staffaddress);
        staffphonenumber = (EditText) dialog.findViewById(R.id.staffphonenumber);
        imageButtonProfilePic = (ImageButton) dialog.findViewById(R.id.btn_staff_pic);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weekArray, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnershift1.setAdapter(adapter);
        spinnershift2.setAdapter(adapter);

        ArrayAdapter<CharSequence> adaptertime = ArrayAdapter.createFromResource(getActivity(),
                R.array.timeArray, R.layout.simple_spinner_item);
        adaptertime.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnertime1.setAdapter(adaptertime);
        spinnertime2.setAdapter(adaptertime);

        ArrayAdapter<CharSequence> adapterper = ArrayAdapter.createFromResource(getActivity(),
                R.array.perArray, R.layout.simple_spinner_item);
        adapterper.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnersal.setAdapter(adapterper);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(
                getContext(), this, year, month, day);


        dialog.show();

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        cancelstaffdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.show();
            }
        });

        imageButtonProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        addstafftoDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isValidEmail(staffemail.getText().toString()))
                    Toast.makeText(getActivity(),"Please enter valid email",Toast.LENGTH_SHORT).show();
                if(!isValidMobile(staffphonenumber.getText().toString()))
                    Toast.makeText(getActivity(),"Please enter valid phone number",Toast.LENGTH_SHORT).show();
                if(!staffname.getText().toString().isEmpty() ) {
                   final  Staff insertstaff = new Staff();
                    insertstaff.setStaff_name(staffname.getText().toString());
                    insertstaff.setShift_days1(spinnershift1.getSelectedItem().toString());
                    insertstaff.setShift_days2(spinnershift2.getSelectedItem().toString());
                    insertstaff.setShift_time1(spinnertime1.getSelectedItem().toString());
                    insertstaff.setShift_time2(spinnertime2.getSelectedItem().toString());
                    insertstaff.setStaff_startdate(started.getText().toString());
                    insertstaff.setPrice_perhr(salary.getText().toString());
                    insertstaff.setPriceType(spinnersal.getSelectedItem().toString());
                    insertstaff.setStaff_email(staffemail.getText().toString());
                    insertstaff.setStaff_designation(designation.getText().toString());
                    insertstaff.setStaff_address(staffaddress.getText().toString());
                    insertstaff.setStaff_phonenumber(staffphonenumber.getText().toString());
                    insertstaff.setStaff_photo(base64Image);
                    AddStaffRequest addStaffRequest = new AddStaffRequest();

                    addStaffRequest.setName(staffname.getText().toString());
                    addStaffRequest.setShiftDayFrom(spinnershift1.getSelectedItem().toString());
                    addStaffRequest.setShiftDayTo(spinnershift2.getSelectedItem().toString());
                    addStaffRequest.setShiftTimeFrom(spinnertime1.getSelectedItem().toString());
                    addStaffRequest.setShiftTimeTo(spinnertime2.getSelectedItem().toString());
                    addStaffRequest.setStarted(started.getText().toString());
                    addStaffRequest.setSalary(Integer.parseInt(salary.getText().toString()));
                    addStaffRequest.setSalaryType(spinnersal.getSelectedItem().toString());
                    addStaffRequest.setStaff_email(staffemail.getText().toString());
                    addStaffRequest.setStaff_designation(designation.getText().toString());
                    addStaffRequest.setStaff_address(staffaddress.getText().toString());
                    addStaffRequest.setStaff_phonenumber(staffphonenumber.getText().toString());
                    addStaffRequest.setStaff_photo(base64Image);

                    dialog.dismiss();


                    SessionManager sessionManager = new SessionManager(getActivity());
                    RetrofitApi.getApi().AddStaff(sessionManager.getAuthToken(), addStaffRequest).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {

                                db.addStaff(insertstaff);
                                AdminAddStaffFragment.adapt.add(insertstaff);
                                AdminAddStaffFragment.adapt.notifyDataSetChanged();

                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();



                            } else {

                                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imageButtonProfilePic.setImageBitmap(bitmap);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();
                base64Image = Base64.encodeToString(ba, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month = month+1;


            started.setText(year+"-"+month+"-"+dayOfMonth);

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
