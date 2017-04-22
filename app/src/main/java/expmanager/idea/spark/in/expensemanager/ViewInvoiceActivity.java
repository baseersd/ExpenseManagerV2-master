package expmanager.idea.spark.in.expensemanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import static android.R.attr.x;

public class ViewInvoiceActivity extends AppCompatActivity {

    private ImageView mImgViewInvoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);
        initUI();
    }

    private void initUI(){
        mImgViewInvoice = (ImageView)findViewById(R.id.imgViewInvoice);
        String base64Data = getIntent().getExtras().getString("path");
        byte[] byteArrayData = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayData, 0, byteArrayData.length);
        mImgViewInvoice.setImageBitmap(Bitmap.createScaledBitmap(bmp, 500,
                500, false));
    }
}
