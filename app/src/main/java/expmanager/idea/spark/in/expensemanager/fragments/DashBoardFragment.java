package expmanager.idea.spark.in.expensemanager.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.DashboardDayModel;
import expmanager.idea.spark.in.expensemanager.model.DashboardModel;
import expmanager.idea.spark.in.expensemanager.model.DashboardMonthModel;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by Haresh.Veldurty on 3/7/2017.
 */

public class DashBoardFragment extends Fragment {
    private LinearLayout chartContainer;
    private GraphicalView mChart;
    private Spinner mothspinner;
    private String[] mMonth = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };


    private TextView txtCurrentMonth, txtCurrentMonthProfit, txtPrevMonth1;
    private TextView txtPrevMonth1Profit, txtPrevMonth2, txtPrevMonth2Profit, txtPrevMonth3, txtPrevMonth3Profit;
    private TextView txtCurrentMonthYear, txtPrevMonth1Year, txtPrevMonth2Year, txtPrevMonth3Year;
    private TextView txtCurrentMonthIncome, txtCurrentMonthTangible, txtCurrentMonthIntangible;

    private TextView txtSelectedMonthProfit, txtSelectedMonthIncome, txtSelectedMonthTangible, txtSelectedMonthInTangible;

    private RelativeLayout rlProfit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_layout,
                container, false);
        chartContainer = (LinearLayout) rootView.findViewById(R.id.chart);
        mothspinner = (Spinner) rootView.findViewById(R.id.mothspinner);


        txtCurrentMonth = (TextView) rootView.findViewById(R.id.txt_current_month);
        txtCurrentMonthProfit = (TextView) rootView.findViewById(R.id.txt_current_month_profit);
        txtPrevMonth1 = (TextView) rootView.findViewById(R.id.txt_prev_month1);

        txtPrevMonth1Profit = (TextView) rootView.findViewById(R.id.txt_prev_month1_profit);
        txtPrevMonth2 = (TextView) rootView.findViewById(R.id.txt_prev_month2);
        txtPrevMonth2Profit = (TextView) rootView.findViewById(R.id.txt_prev_month2_profit);
        txtPrevMonth3 = (TextView) rootView.findViewById(R.id.txt_prev_month3);
        txtPrevMonth3Profit = (TextView) rootView.findViewById(R.id.txt_prev_month3_profit);

        txtCurrentMonthYear = (TextView) rootView.findViewById(R.id.txt_current_year);
        txtPrevMonth1Year = (TextView) rootView.findViewById(R.id.txt_prev_month1_year);
        txtPrevMonth2Year = (TextView) rootView.findViewById(R.id.txt_prev_month2_year);
        txtPrevMonth3Year = (TextView) rootView.findViewById(R.id.txt_prev_month3_year);

        rlProfit = (RelativeLayout) rootView.findViewById(R.id.rl_profit);


        txtCurrentMonthIncome = (TextView) rootView.findViewById(R.id.txt_current_month_income);
        txtCurrentMonthTangible = (TextView) rootView.findViewById(R.id.txt_current_month_tangible);
        txtCurrentMonthIntangible = (TextView) rootView.findViewById(R.id.txt_current_month_intangible);

        txtSelectedMonthProfit = (TextView) rootView.findViewById(R.id.txt_selected_month_profit);
        txtSelectedMonthIncome = (TextView) rootView.findViewById(R.id.txt_selected_month_income);
        txtSelectedMonthTangible = (TextView) rootView.findViewById(R.id.txt_selected_month_tangible);
        txtSelectedMonthInTangible = (TextView) rootView.findViewById(R.id.txt_selected_month_intangible);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.months, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mothspinner.setAdapter(adapter);
        // openChart();
        //  openBarChart();

        callServiceApis();
        callServiceApiForDasboardGraph();

        return rootView;
    }

//    private void openBarChart() {
//
////        ArrayList<String> labels = new ArrayList<String>();
////        labels.add("1");
////        labels.add("2");
////        labels.add("3");
////        labels.add("4");
////        labels.add("5");
////        labels.add("6");
////        labels.add("7");
////        labels.add("8");
////        labels.add("9");
////        labels.add("10");
////        labels.add("11");
////        labels.add("12");
////        labels.add("13");
////        labels.add("14");
////        labels.add("15");
////        labels.add("16");
////        labels.add("17");
////        labels.add("18");
////        labels.add("19");
////        labels.add("20");
////        labels.add("21");
////        labels.add("22");
////        labels.add("23");
////        labels.add("24");
////        labels.add("25");
////        labels.add("26");
////        labels.add("27");
////        labels.add("28");
////        labels.add("29");
////        labels.add("30");
//
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(8f, 0));
//        entries.add(new BarEntry(2f, 1));
//        entries.add(new BarEntry(5f, 2));
//        entries.add(new BarEntry(20f, 3));
//        entries.add(new BarEntry(15f, 4));
//        entries.add(new BarEntry(19f, 5));
//        entries.add(new BarEntry(9f, 6));
//
//        BarDataSet bardataset = new BarDataSet(entries, "Cells");
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("2017");
//        labels.add("2016");
//        labels.add("2015");
//        labels.add("2014");
//        labels.add("2013");
//        labels.add("2012");
//        labels.add("2011");
//
//        BarData data = new BarData(labels, bardataset);
//        chartContainer.setData(data); // set the data and list of lables into chart
//
//        chartContainer.setDescription("Set Bar Chart Description");  // set the description
//
//        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        chartContainer.animateY(5000);
//
//    }

    private void callServiceApis() {

        SessionManager sessionManager = new SessionManager(getActivity());
       final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();
        RetrofitApi.getApi().GetDashboard(sessionManager.getAuthToken(), dateFormat.format(date).toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.isSuccessful()) {


                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<DashboardModel>>() {
                        }.getType();
                        List<DashboardModel> myModelList = gson.fromJson(response.body().string(), listType);

                        setData(myModelList, dateFormat.format(date).toString().substring(0,4));


                    } else {

                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void callServiceApiForDasboardGraph() {

        SessionManager sessionManager = new SessionManager(getActivity());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        RetrofitApi.getApi().GetDashboardGraph(sessionManager.getAuthToken(), dateFormat.format(date).toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response.isSuccessful()) {


                        Gson gson = new Gson();
                        DashboardMonthModel dashboardMonthModel = gson.fromJson(response.body().string(), DashboardMonthModel.class);

                        int profit = (int) (dashboardMonthModel.getSale() - (dashboardMonthModel.getTangible() + dashboardMonthModel.getIntangible()));

                        txtSelectedMonthProfit.setText("$"+profit);
                        txtSelectedMonthIncome.setText("$"+(int) dashboardMonthModel.getSale());
                        txtSelectedMonthTangible.setText("$"+(int) dashboardMonthModel.getTangible());
                        txtSelectedMonthInTangible.setText("$"+(int) dashboardMonthModel.getIntangible());

                        openChart(dashboardMonthModel.getDashboardDayModels());


                    } else {

                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void setData(List<DashboardModel> myModelList,String year) {

        txtCurrentMonthYear.setText(year);
        txtPrevMonth1Year.setText(year);
        txtPrevMonth2Year.setText(year);
        txtPrevMonth3Year.setText(year);


        for (int i = 0; i < myModelList.size(); i++) {

            switch (i) {

                case 0:
                    txtCurrentMonth.setText(myModelList.get(i).getMonth().substring(0,3));

                    double income = myModelList.get(i).getSale();
                    double tangible = myModelList.get(i).getTangible();
                    double intangible = myModelList.get(i).getIntangible();

                    long profit = (long) (income - (tangible + intangible));

                    if(profit < 0) {
                        txtCurrentMonthProfit.setText("$" + -1 * profit);
                        rlProfit.setBackgroundColor(getResources().getColor(R.color.loss_color));
                    }else{
                        txtCurrentMonthProfit.setText("$" + profit);
                    }

                    txtCurrentMonthIncome.setText("$"+(long) income);
                    txtCurrentMonthTangible.setText("$"+(long) tangible);
                    txtCurrentMonthIntangible.setText("$"+(long) intangible);

                    break;

                case 1:
                    txtPrevMonth1.setText(myModelList.get(i).getMonth().substring(0,3));

                    double income1 = myModelList.get(i).getSale();
                    double tangible1 = myModelList.get(i).getTangible();
                    double intangible1 = myModelList.get(i).getIntangible();

                    long profit1 = (long) (income1 - (tangible1 + intangible1));

                    txtPrevMonth1Profit.setText("$"+profit1);
                    break;

                case 2:
                    txtPrevMonth2.setText(myModelList.get(i).getMonth().substring(0,3));

                    double income2 = myModelList.get(i).getSale();
                    double tangible2 = myModelList.get(i).getTangible();
                    double intangible2 = myModelList.get(i).getIntangible();

                    long profit2 = (long) (income2 - (tangible2 + intangible2));

                    txtPrevMonth2Profit.setText("$"+profit2);
                    break;

                case 3:
                    txtPrevMonth3.setText(myModelList.get(i).getMonth().substring(0,3));

                    double income3 = myModelList.get(i).getSale();
                    double tangible3 = myModelList.get(i).getTangible();
                    double intangible3 = myModelList.get(i).getIntangible();

                    long profit3 = (long) (income3 - (tangible3 + intangible3));

                    txtPrevMonth3Profit.setText("$"+profit3);
                    break;

            }

        }


    }

    private void openChart(List<DashboardDayModel> list) {
//        int[] x = { "0","1","2",3,4,5,6,7, 8, 9, 10, 11 };
//        int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800, 0,0,0,0};
//        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0, 0, 0 };

// Creating an XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Income");

// Creating an XYSeries for Tangible
        XYSeries tangibleSeries = new XYSeries("Tangible");
        // Creating an XYSeries for InTangible
        XYSeries inTangibleSeries = new XYSeries("InTangible");
// Adding data to Income and Expense Series
        for (int i = 0; i < list.size(); i++) {
            incomeSeries.add(i, list.get(i).getIncome());
            tangibleSeries.add(i, list.get(i).getTangible());
            inTangibleSeries.add(i, list.get(i).getIntangible());
        }
        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
// Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
// Adding Expense Series to dataset
        dataset.addSeries(tangibleSeries);
        dataset.addSeries(inTangibleSeries);

// Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();

        incomeRenderer.setColor(Color.CYAN); //color of the graph set to cyan
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2f);
        incomeRenderer.setDisplayChartValues(true);
//setting chart value distance
        // incomeRenderer.setDisplayChartValuesDistance(10);
//setting line graph point style to circle
        // incomeRenderer.setPointStyle(PointStyle.CIRCLE);
//setting stroke of the line chart to solid
        // incomeRenderer.setStroke(BasicStroke.SOLID);

// Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.GREEN);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2f);
        expenseRenderer.setDisplayChartValues(true);
//setting line graph point style to circle
        //expenseRenderer.setPointStyle(PointStyle.SQUARE);
//setting stroke of the line chart to solid
        // expenseRenderer.setStroke(BasicStroke.SOLID);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer inTangibleRenderer = new XYSeriesRenderer();
        inTangibleRenderer.setColor(Color.RED);
        inTangibleRenderer.setFillPoints(true);
        inTangibleRenderer.setLineWidth(2f);
        inTangibleRenderer.setDisplayChartValues(true);
//setting line graph point style to circle
        //expenseRenderer.setPointStyle(PointStyle.SQUARE);
//setting stroke of the line chart to solid
        // expenseRenderer.setStroke(BasicStroke.SOLID);

// Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        // multiRenderer.setChartTitle(&quot;Expense Chart&quot;);
        multiRenderer.setXTitle("Year 2017");
        multiRenderer.setYTitle("Amount in Dollars");

/***
 * Customizing graphs
 */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
//setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
//setting lines to display on y axis
        multiRenderer.setShowGridY(false);
//setting lines to display on x axis
        multiRenderer.setShowGridX(false);
//setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(false);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);
//setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
//setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
//setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setYLabels(10);
// setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
// if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(4000);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
//setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(31);
//setting bar size or space between two bars
        multiRenderer.setBarSpacing(0.5);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getActivity().getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        // multiRenderer.setScale(2f);
//setting x axis point size
        // multiRenderer.setPointSize(4f);
//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});
        // multiRenderer.addSeriesRenderer(expenseRenderer);
        for (int i = 0; i < list.size(); i++) {
            multiRenderer.addXTextLabel(i, i + 1 + "");
        }

// Adding incomeRenderer and expenseRenderer to multipleRenderer
// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
// should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);
        multiRenderer.addSeriesRenderer(inTangibleRenderer);

//this part is used to display graph on the xml

//remove any views before u paint the chart
        chartContainer.removeAllViews();
//drawing bar chart
        mChart = ChartFactory.getBarChartView(getActivity(), dataset, multiRenderer, BarChart.Type.DEFAULT);
//adding the view to the linearlayout
        chartContainer.addView(mChart);

    }


}
