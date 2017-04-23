package expmanager.idea.spark.in.expensemanager.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expmanager.idea.spark.in.expensemanager.LoginActivity;
import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.database.DatabaseHandler;
import expmanager.idea.spark.in.expensemanager.model.Invoice;
import expmanager.idea.spark.in.expensemanager.utils.Utils;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class InvoiceEntryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;


    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Spinner spnPaymentMode;
    ImageView invPreview;
    private String[] paymentMode = { "Cash", "Cheque", "Online"};

    EditText txtinvno, txtinvDate, txtinvdesc, txtamt;
    Button btnSubmit;

    DatabaseHandler myDbHelper;
    String path = "EM";

    private DatePickerDialog invDatePickerDialog;
    private SimpleDateFormat invdateFormatter;
    private static final int CAMERA_REQUEST = 1888;

    String filePath = "";

    int weekindex;

    public InvoiceEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceEntryFragment newInstance(int param1, String param2) {
        InvoiceEntryFragment fragment = new InvoiceEntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_invoice_entry, container, false);

        weekindex = mParam1;

        myDbHelper = new DatabaseHandler(getContext());

        txtinvno= (EditText)v.findViewById(R.id.input_invno);
        txtinvDate = (EditText)v.findViewById(R.id.input_invdate);

        //invdateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        txtinvDate.setInputType(InputType.TYPE_NULL);
        Calendar newDate = Calendar.getInstance();
        txtinvDate.setText(Utils.getDateTimeforFormat("dd-MM-yyyy"));
        txtinvDate.setOnClickListener(this);





        Calendar newCalendar = Calendar.getInstance();
        invDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtinvDate.setText(invdateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        txtinvdesc= (EditText)v.findViewById(R.id.input_desc);
        txtamt=(EditText)v.findViewById(R.id.input_amount);

        btnSubmit=(Button)v.findViewById(R.id.btnsubmit);

        invPreview=(ImageView)v.findViewById(R.id.invpreview);
        spnPaymentMode = (Spinner) v.findViewById(R.id.spnpaymentmode);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, paymentMode);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPaymentMode.setAdapter(adapter_state);
        spnPaymentMode.setOnItemSelectedListener(this);
/*
        final File img=new File(getIntent().getStringExtra("path").toString());
        if(img.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
            invPreview.setImageBitmap(myBitmap);
        }*/


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtinvno.getText().length()>0 && txtinvDate.getText().length()>0 && txtinvdesc.getText().length()>0 && txtamt.getText().length()>0 ){

                    String generatedInvoice = myDbHelper.getUniqueInvoice(txtinvno.getText().toString());
                    myDbHelper.insetInvoice(new Invoice(generatedInvoice,txtinvDate.getText().toString(),
                            txtinvdesc.getText().toString(),filePath,spnPaymentMode.getSelectedItem().toString(),
                            generatedInvoice,
                            3,filePath, Double.parseDouble(txtamt.getText().toString()),0.0));



                    mListener.openExpenseEntry(generatedInvoice,txtinvDate.getText().toString(), Double.parseDouble(txtamt.getText().toString()),weekindex,filePath);

                }else{
                    Toast.makeText(getContext(),"Please Enter Invoice Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForPermissions();
        }else {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

        // Inflate the layout for this fragment
        return v;
    }

    private void checkForPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();

        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write external storage");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        },new DialogInterface.OnClickListener(){


                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener onCancelListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", onCancelListener)
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            FileOutputStream outStream = null;
            Calendar c = Calendar.getInstance();



            File videoDirectory = new File(Environment.getExternalStorageDirectory(), path);

            if (!videoDirectory.exists()) {
                videoDirectory.mkdirs();
            }

            try {
                // Write to SD Card
                filePath = videoDirectory.getPath() + "/" + c.getTimeInMillis() + ".jpg";
                outStream = new FileOutputStream(filePath);
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }


            Bitmap realImage;
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 5;

            options.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared

            options.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future


            realImage = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length,options);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(path + c.getTime().getSeconds()
                        + ".jpg");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            invPreview.setImageBitmap(realImage);

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED){
           // mListener.openWeekView();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(view == txtinvDate) {
            invDatePickerDialog.show();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void openExpenseEntry(String invid, String invDate, double invAmt, int weekIndex, String filePath);
        //void openWeekView();
    }
}
