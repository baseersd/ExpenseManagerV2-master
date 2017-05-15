package expmanager.idea.spark.in.expensemanager.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.adapters.ListAdapter;
import expmanager.idea.spark.in.expensemanager.model.ScanInvoiceModel;
import expmanager.idea.spark.in.expensemanager.ocr_usage.CaptureActivity;
import expmanager.idea.spark.in.expensemanager.utils.RequestPermissionsTool;
import expmanager.idea.spark.in.expensemanager.utils.RequestPermissionsToolImpl;


/**
 * Created by Ramana.Reddy on 2/28/2017.
 */

public class AddExpenseFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    ArrayList<String> filterdataamount=null;
    ArrayList<String> filterdataname=null;
    private ImageView imageRescan;
    ListView list;
    private Uri outputFileUri;
    private TessBaseAPI tessBaseApi;
    private static final int PHOTO_REQUEST_CODE = 1;

    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/ExpenseManager/";
    private static final String TESSDATA = "tessdata";
    private static final String lang = "eng";

    private static final String TAG = AddExpenseFragment.class.getSimpleName();
    String result = "empty";

    private RequestPermissionsTool requestTool; //for API >=23 only
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_expense,
                container, false);

        list=(ListView)rootView.findViewById(R.id.list);
        imageRescan = (ImageView) rootView.findViewById(R.id.img_rescan);

        imageRescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraActivity();
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }

        return rootView;
    }

    /**
     * to get high resolution image from camera
     */
    private void startCameraActivity() {
       // verifyStoragePermissions(getActivity());
      /*  try {
            String IMGS_PATH = Environment.getExternalStorageDirectory().toString() + "/ExpenseManager/imgs";
            prepareDirectory(IMGS_PATH);

            String img_path = IMGS_PATH + "/ocr.jpg";

            outputFileUri = Uri.fromFile(new File(img_path));

            final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE);
            }
        } catch (Exception e) {
            Log.e("Add Expense", e.getMessage());
        } */

        try {
            Intent myIntent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(myIntent,1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void prepareDirectory(String path) {

        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e("Add Expense", "ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
            }
        } else {
            Log.i("Add Expense", "Created directory " + path);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        //making photo
       /*  if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            doOCR();
        } else {
            Toast.makeText(getActivity(), "ERROR: Image was not obtained.", Toast.LENGTH_SHORT).show();
        } */

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");


                ArrayList<String> datas=new ArrayList<>();
                ArrayList<ArrayList<String>> dd=new ArrayList<ArrayList<String>>();
                String[] separated = result.split("\n");

                for(int k=0;k<separated.length;k++)
                {
                    datas=splitString(separated[k]);


                    dd.add(datas);




                }


                filterdataamount=new ArrayList<>();
                filterdataname=new ArrayList<>();


                for (int i=0;i<dd.size();i++)
                {


                    // ArrayList<String> filterdataname=new ArrayList<>();

                    for(int k=0;k<dd.get(i).size();k++)
                    {


                        if(k==0)
                        {
                            if(dd.get(i).get(0).equals(""))
                            {

                            }else
                            {
                                filterdataname.add(dd.get(i).get(0));
                                Log.i("product name","product name"+ filterdataname);

                            }

                        }



                        // Log.i("Hash map","Hash map"+dd.get(i).get(k)) ;
                        // Log.i("Hash map","Hash map"+hashMap.get(String.valueOf(i)).get(i)) ;
                        if(isFloat(dd.get(i).get(k)))
                        {

                        }
                        if(isDouble(dd.get(i).get(k)))
                        {
                            filterdataamount.add(dd.get(i).get(k));
                            Log.i("product amount","product amount"+ filterdataamount);

                            // Log.i("total","total"+datas.get(i));

                        } if(isValidInteger(dd.get(i).get(k)))
                    {

                    }

                    }

                }

                ArrayList<ScanInvoiceModel> scanInvoiceModels = new ArrayList<>();

                for (int i = 0; i <filterdataname.size() ; i++) {

                    ScanInvoiceModel scanInvoiceModel = new ScanInvoiceModel();
                    scanInvoiceModel.setProductName(filterdataname.get(i));
                    if(filterdataname.size()==filterdataamount.size()){

                        scanInvoiceModel.setPrice(filterdataamount.get(i));

                    }else if(filterdataname.size()>filterdataamount.size()) {

                        if(i>=filterdataamount.size()){

                            scanInvoiceModel.setPrice("");
                        }else {

                            scanInvoiceModel.setPrice(filterdataamount.get(i));
                        }

                    }

                    scanInvoiceModels.add(scanInvoiceModel);

                }
                ListAdapter adapter=new ListAdapter(getActivity(),scanInvoiceModels);
                list.setAdapter(adapter);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }


    }

    private void doOCR() {
        prepareTesseract();

    }

    private void prepareTesseract() {
        try {
            prepareDirectory(DATA_PATH + TESSDATA);

            startOCR(outputFileUri);
        } catch (Exception e) {
            e.printStackTrace();
        }

       copyTessDataFiles(TESSDATA);
    }

    private void startOCR(Uri imgUri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4; // 1 - means max size. 4 - means maxsize/4 size. Don't use value <4, because you need more memory in the heap to store your data.
            Bitmap bitmap = BitmapFactory.decodeFile(imgUri.getPath(), options);

            String result = extractText(bitmap);

            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

            //textView.setText(result);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Copy tessdata files (located on assets/tessdata) to destination directory
     *
     * @param path - name of directory with .traineddata files
     */
    private void copyTessDataFiles(String path) {
        try {
            String fileList[] = getActivity().getAssets().list(path);

            for (String fileName : fileList) {

                // open file within the assets folder
                // if it is not already there copy it to the sdcard
                String pathToDataFile = DATA_PATH + path + "/" + fileName;
                if (!(new File(pathToDataFile)).exists()) {

                    InputStream in = getActivity().getAssets().open(path + "/" + fileName);

                    OutputStream out = new FileOutputStream(pathToDataFile);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    Log.d(TAG, "Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to copy files to tessdata " + e.toString());
        }
    }


    private String extractText(Bitmap bitmap) {

        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (tessBaseApi == null) {
                Log.e(TAG, "TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }

        Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
        tessBaseApi.setDebug(true);
        tessBaseApi.init(DATA_PATH, lang);

//       //EXTRA SETTINGS
//        //For example if we only want to detect numbers
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
//
//        //blackList Example
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
//                "YTRWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");

        Log.d(TAG, "Training file loaded");
        tessBaseApi.setImage(bitmap);
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
        } catch (Exception e) {
            Log.e(TAG, "Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }


//    public static void verifyStoragePermissions(Activity activity) {
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    activity,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }

    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        requestTool = new RequestPermissionsToolImpl();
        requestTool.requestPermissions(getActivity(), permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean grantedAllPermissions = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                grantedAllPermissions = false;
            }
        }

        if (grantResults.length != permissions.length || (!grantedAllPermissions)) {

            requestTool.onPermissionDenied();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public ArrayList<String> splitString(String dataline)
    {
        ArrayList<String> words=new ArrayList<String>();
        String[] separated = dataline.split(" ");
        for (String item : separated)
        {
            System.out.println("itemssssssssssssssss = " + item);
            words.add(item);
        }

        return words;
    }

    public static boolean isValidInteger(String value) {
        try {
            Integer val = Integer.valueOf(value);
            if (val != null)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean isString(String str) {
        try {
            boolean atleastOneAlpha = str.matches(".*[a-zA-Z]+.*");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
