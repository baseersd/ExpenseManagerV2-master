package expmanager.idea.spark.in.expensemanager;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import expmanager.idea.spark.in.expensemanager.fragments.OrganizationFragment;

public class MainActivity extends AppCompatActivity {
    private TextView txtOrganization,txtTangibleExpenses,txtStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        OrganizationFragment fragmentorg = new OrganizationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentorg).commit();

        txtOrganization = (TextView)findViewById(R.id.txt_organization);
        txtTangibleExpenses = (TextView)findViewById(R.id.txt_tangible);
        txtStaff = (TextView)findViewById(R.id.txt_staff);



//        // Assign adapter to ListView
//        navigation_list.setAdapter(adapter);
//
//        // ListView Item Click Listener
//        navigation_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i) {
//
//                    case 0:
//                        OrganizationFragment fragmentorg = new OrganizationFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentorg).commit();
//                        break;
//                    case 1:
//                        TangibleExpenseFragment fragmenttangibleexp = new TangibleExpenseFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragmenttangibleexp).commit();
//
//                        break;
//                    case 2:
//                        StaffFragment fragmentstaff = new StaffFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentstaff).commit();
//                        break;
//
//                }
//            }
//        });
    }

    public void changeOrganizationTextColor(){

        txtOrganization.setTextColor(ContextCompat.getColor(this,R.color.blue));
        txtTangibleExpenses.setTextColor(ContextCompat.getColor(this,R.color.ashcolor));
        txtStaff.setTextColor(ContextCompat.getColor(this,R.color.ashcolor));

    }

    public void changeTangibleExpensesTextColor(){

        txtTangibleExpenses.setTextColor(ContextCompat.getColor(this,R.color.blue));
        txtStaff.setTextColor(ContextCompat.getColor(this,R.color.ashcolor));
        txtOrganization.setTextColor(ContextCompat.getColor(this,R.color.ashcolor));

    }

    public void changeStaffTextColor(){

        txtStaff.setTextColor(ContextCompat.getColor(this,R.color.blue));
        txtOrganization.setTextColor(ContextCompat.getColor(this,R.color.ashcolor));
        txtTangibleExpenses.setTextColor(ContextCompat.getColor(this,R.color.ashcolor));

    }

}
