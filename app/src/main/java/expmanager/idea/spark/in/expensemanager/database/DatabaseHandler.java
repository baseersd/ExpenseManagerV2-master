package expmanager.idea.spark.in.expensemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.common.AppConstants;
import expmanager.idea.spark.in.expensemanager.model.AddExpenseRequest;
import expmanager.idea.spark.in.expensemanager.model.Expense;
import expmanager.idea.spark.in.expensemanager.model.Invoice;
import expmanager.idea.spark.in.expensemanager.model.Sales;
import expmanager.idea.spark.in.expensemanager.model.Staff;
import expmanager.idea.spark.in.expensemanager.model.TanExpenses;
import expmanager.idea.spark.in.expensemanager.utils.Utils;

import static expmanager.idea.spark.in.expensemanager.database.DatabaseConstants.CategoriesTableColumns.*;
import static expmanager.idea.spark.in.expensemanager.database.DatabaseConstants.ExpenseTableColumns.*;
import static expmanager.idea.spark.in.expensemanager.database.DatabaseConstants.TableNames.*;

/**
 * Created by kveldurty on 12/17/16.
 */





public class DatabaseHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expensemanagerv2";

    // Contacts table name
    private static final String TABLE_TANEXPENSE = "tanexpenses";
    private static final String TABLE_STAFFDETAILS = "staffdetails";
    private static final String TABLE_SALES = "sales";
    private static final String TABLE_MANUAL_EXPENSE = "manualexpense";
    private static final String TABLE_USERS = "users";


    // Category  Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_WHEN = "whentime";
    private static final String KEY_PRICE = "price";


    // Staff  Table Columns names
    private static final String KEY_STAFFNAME = "staff_name";
    private static final String KEY_DAY1 = "shift_days1";
    private static final String KEY_DAY2 = "shift_days2";
    private static final String KEY_TIME1 = "shift_time1";
    private static final String KEY_TIME2 = "shift_time2";
    private static final String KEY_STAFFSTARTDATE = "staff_startdate";
    private static final String KEY_STAFFPRICE= "price_perhr";
    private static final String KEY_STAFFEMAIL= "staff_email";
    private static final String KEY_DESIGNATION= "staff_designation";
    private static final String KEY_ADDRESS= "staff_address";
    private static final String KEY_PHONE= "staff_phone";
    private static final String KEY_PROFILEPIC= "staff_photo";

    private static final String SALE_NAME = "saletype";
    private static final String SALE_DATE = "saledate";
    private static final String SALE_PRICE = "saleamount";

    private static final String MEXPENSE_CATEGORY = "category";
    private static final String MEXPENSE_INVOICEID = "invoiceid";
    private static final String MEXPENSE_DATE = "mexpensedate";
    private static final String MEXPENSE_DESCRIPTION = "description";
    private static final String MEXPENSE_UNIT = "unit";
    private static final String MEXPENSE_AMOUNT = "mexpenseamount";


    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PIN = "user_pin";
    private static final String KEY_ISADMIN = "user_isadmin";
    private static final String KEY_ISAUTHORIZED = "user_isauthorized";
    private static final String KEY_COMANYID = "user_comanyid";
    private static final String KEY_STATUS= "user_status";
    private static final String KEY_CUSTOMERID= "user_customerid";
    private static final String KEY_CREATEDAT= "user_createdat";
    private static final String KEY_UDATEDAT= "user_updatedat";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PIN + " TEXT,"
                + KEY_ISADMIN + " TEXT,"
                + KEY_ISAUTHORIZED + " TEXT,"
                + KEY_COMANYID + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_CUSTOMERID + " TEXT,"
                + KEY_CREATEDAT + " TEXT,"
                + KEY_UDATEDAT + " TEXT"
                + ")";


        String CREATE_TANEXPENSE_TABLE = "CREATE TABLE " + TABLE_TANEXPENSE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_WHEN + " TEXT,"
                + KEY_PRICE + " TEXT"
                + ")";

        String CREATE_SALE_TABLE = "CREATE TABLE " + TABLE_SALES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + SALE_NAME + " TEXT,"
                + SALE_DATE + " TEXT,"
                + SALE_PRICE + " TEXT"
                + ")";

        String CREATE_STAFF_TABLE = "CREATE TABLE " + TABLE_STAFFDETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_STAFFNAME + " TEXT,"
                + KEY_DAY1 + " TEXT,"
                + KEY_DAY2 + " TEXT,"
                + KEY_TIME1 + " TEXT,"
                + KEY_TIME2 + " TEXT,"
                + KEY_STAFFSTARTDATE + " TEXT,"
                + KEY_STAFFEMAIL + " TEXT,"
                + KEY_DESIGNATION + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_PROFILEPIC + " TEXT,"
                + KEY_STAFFPRICE + " TEXT"
                + ")";

        String CREATE_MEXPENSE_TABLE = "CREATE TABLE " + TABLE_MANUAL_EXPENSE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + MEXPENSE_CATEGORY + " TEXT,"
                + MEXPENSE_INVOICEID + " TEXT,"
                + MEXPENSE_DATE + " TEXT,"
                + MEXPENSE_DESCRIPTION + " TEXT,"
                + MEXPENSE_AMOUNT + " TEXT"
                + ")";


        db.execSQL(DatabaseConstants.CREATE_CATEGORIES_TABLE);

        db.execSQL(DatabaseConstants.CREATE_EXPENSE_TABLE);

        db.execSQL("CREATE TABLE `income_types` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `description` TEXT, `created_at` TEXT, `created_by` INTEGER )");

        db.execSQL("CREATE TABLE `incomes` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `income_type_id` INTEGER, `date` TEXT, `weekid` INTEGER , `amount` INTEGER, `created_at` TEXT, `created_by` INTEGER )");

        db.execSQL("CREATE TABLE `invoices` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `date` TEXT, `image_path` TEXT, `description` TEXT, `amount` NUMERIC, `discount` NUMERIC, `payment_mode` TEXT, `bill_number` TEXT, `created_at` TEXT, `created_by` INTEGER )");

        db.execSQL(CREATE_TANEXPENSE_TABLE);
        db.execSQL(CREATE_STAFF_TABLE);
        db.execSQL(CREATE_SALE_TABLE);
        db.execSQL(CREATE_MEXPENSE_TABLE);
        db.execSQL(CREATE_USERS_TABLE);

    }


    public void openConnection() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            db.close();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TANEXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFFDETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANUAL_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("drop table IF EXISTS income_types");
        db.execSQL("drop table IF EXISTS incomes");
        db.execSQL("drop table IF EXISTS invoices");

        // Create tables again
        onCreate(db);
    }



    public  void addTanExpenses(TanExpenses texpense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, texpense.getCategory());
        values.put(KEY_WHEN, texpense.getWhen());
        values.put(KEY_PRICE, texpense.getPrice());



        // Inserting Row
        db.insert(TABLE_TANEXPENSE, null, values);
        db.close(); // Closing database connection
    }

    public List<TanExpenses> getAllTanExpenses() {
        List<TanExpenses> expList = new ArrayList<TanExpenses>();


        String selectQuery = "SELECT  * FROM " + TABLE_TANEXPENSE ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                TanExpenses tanexpen = new TanExpenses();
                tanexpen.setCategory(cursor.getString(1));
                tanexpen.setWhen(cursor.getString(2));
                tanexpen.setPrice(cursor.getString(3));



                // Adding contact to list
                expList.add(tanexpen);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expList;
    }

    public  void addStaff(Staff staffdetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STAFFNAME, staffdetails.getStaff_name());
      //  values.put(KEY_DAY1, staffdetails.getShift_days1());
      //  values.put(KEY_DAY2, staffdetails.getShift_days2());
      //  values.put(KEY_TIME1, staffdetails.getShift_time1());
//        values.put(KEY_STAFFSTARTDATE, staffdetails.getStaff_startdate());
       // values.put(KEY_STAFFPRICE, staffdetails.getPrice_perhr());





        // Inserting Row
        db.insert(TABLE_STAFFDETAILS, null, values);
        db.close(); // Closing database connection
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<Staff>();


        String selectQuery = "SELECT  * FROM " + TABLE_TANEXPENSE ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Staff detailstaff = new Staff();
                detailstaff.setStaff_name(cursor.getString(1));
              //  detailstaff.setShift_days1(cursor.getString(2));
              //  detailstaff.setShift_days2(cursor.getString(3));
              //  detailstaff.setShift_time1(cursor.getString(4));
              //  detailstaff.setShift_time2(cursor.getString(5));
              //  detailstaff.setStaff_startdate(cursor.getString(6));
               // detailstaff.setPrice_perhr(cursor.getString(7));



                // Adding contact to list
                staffList.add(detailstaff);
            } while (cursor.moveToNext());
        }

        // return contact list
        return staffList;
    }


    // Adding new products
    public  void addSalesDetails(Sales contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SALE_NAME, contact.getSaletype());
        values.put(SALE_DATE, contact.getDate());
        values.put(SALE_PRICE, contact.getSameamount());

        // Inserting Row
        db.insert(TABLE_SALES, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public List<Sales> getAllSalesDetails() {
        List<Sales> contactList = new ArrayList<Sales>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SALES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sales contact = new Sales();
                contact.setSaletype(cursor.getString(1));
                contact.setDate(cursor.getString(2));
                contact.setSameamount(cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Adding new products
    public  void addManualExpenses(AddExpenseRequest contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEXPENSE_CATEGORY, contact.getCategory());
        values.put(MEXPENSE_INVOICEID, contact.getInvoiceNumber());
        values.put(MEXPENSE_DATE, contact.getDate());
        values.put(MEXPENSE_DESCRIPTION, contact.getDescription());
        //values.put(MEXPENSE_UNIT, contact.getSameamount());
        values.put(MEXPENSE_AMOUNT, contact.getAmount());

        // Inserting Row
        db.insert(TABLE_SALES, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Expenses
    public List<AddExpenseRequest> getAllExpenses() {
        List<AddExpenseRequest> contactList = new ArrayList<AddExpenseRequest>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MANUAL_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddExpenseRequest contact = new AddExpenseRequest();
                contact.setCategory(cursor.getString(1));
                contact.setInvoiceNumber(cursor.getString(2));
                contact.setDate(cursor.getString(3));
                contact.setDescription(cursor.getString(4));
                contact.setAmount(Integer.parseInt(cursor.getString(5)));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public ArrayList<Expense> getExpensesforToday(String invoiceId){
        ArrayList<Expense> expList=new ArrayList<Expense>();
        Cursor expListCursor;
        try{

            if(Integer.valueOf(invoiceId)==0){
                expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved =0  and date =? order by created_at DESC",
                        new String[]{Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_DD_MM_YYYY)});
            }else {
                //expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved >0 and invoice_id=? and date =? order by created_at DESC",
//                expListCursor = db.rawQuery("SELECT * FROM expenses where invoice_id=? and date =? order by created_at DESC",
//                        new String[]{String.valueOf(invoiceId),Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_DD_MM_YYYY)});
                expListCursor = db.rawQuery("SELECT * FROM expenses where invoice_id=? order by created_at DESC",
                        new String[]{invoiceId});

            }


            while(expListCursor.moveToNext()){
                expList.add(getExpenseObjectFromCursor(expListCursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expList;
    }

    public ArrayList<Expense> getExpenses(int expId){
        ArrayList<Expense> expList=new ArrayList<Expense>();
        Cursor expListCursor;
        try{

            if(expId==0){
                expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved =0  order by created_at DESC", null);
            }else {
                expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved >0 and id=? order by created_at DESC", new String[]{String.valueOf(expId)});
            }


            while(expListCursor.moveToNext()){
                expList.add(getExpenseObjectFromCursor(expListCursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expList;
    }

    public ArrayList<Expense> getExpenses(String expId){
        ArrayList<Expense> expList=new ArrayList<Expense>();
        Cursor expListCursor;
        try{

            /*if(expId==0){
                expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved =0  order by created_at DESC", null);
            }else {*/
                //expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved >0 and id=? order by created_at DESC", new String[]{String.valueOf(expId)});
            expListCursor = db.rawQuery("SELECT * FROM expenses where invoice_id=? order by created_at DESC", new String[]{expId});
            //}


            while(expListCursor.moveToNext()){
                expList.add(getExpenseObjectFromCursor(expListCursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expList;
    }

    private Invoice getInvoiceFromCursor(Cursor cursor){
        if(cursor == null)
            return null;
        String invNo, invDate, invDesc, invBillNumber, invImgPath, invPayMode, invCreatedAt;
        int invCreateBy;
        double invAmt, invDisc;

        invNo = cursor.getString(0);
        invDate = cursor.getString(1);
        invImgPath = cursor.getString(2);
        invDesc = cursor.getString(3);
        invAmt = cursor.getDouble(4);
        invDisc = cursor.getDouble(5);
        invPayMode = cursor.getString(6);
        invBillNumber = cursor.getString(7);
        invCreatedAt = cursor.getString(8);
        invCreateBy = cursor.getInt(9);

        return new Invoice(invNo,invDate,invDesc,invImgPath,invPayMode,invBillNumber,invCreateBy,
                invCreatedAt,invAmt,invDisc);
    }

    public ArrayList<Invoice> getAllInvoicesForToday(){
        ArrayList<Invoice> invoiceList=new ArrayList<Invoice>();
        Cursor cursor;
        try{

            cursor = db.rawQuery("SELECT * FROM invoices where date = ? order by created_at DESC",
                        new String[]{Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_DD_MM_YYYY)});

            while(cursor != null && cursor.moveToNext()){
                invoiceList.add(getInvoiceFromCursor(cursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return invoiceList;
    }

    public ArrayList<Invoice> getAllInvoices(){
        ArrayList<Invoice> invoiceList=new ArrayList<Invoice>();
        Cursor cursor;
        try{

            cursor = db.rawQuery("SELECT * FROM invoices order by created_at DESC",
                    null);

            while(cursor != null && cursor.moveToNext()){
                invoiceList.add(getInvoiceFromCursor(cursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return invoiceList;
    }

    public String getUniqueInvoice(String invoiceId){
        int nCount = 0;
        nCount = getCountofSameInvoices(invoiceId);
        int index = 0;
        while(nCount >0){
            index++;
            nCount = getCountofSameInvoices(invoiceId+"_"+index);
        }
        if(index > 0) {
            return invoiceId + "_" + index;
        }else{
            return invoiceId;
        }
    }
    public int getCountofSameInvoices(String invoiceId){
        int nCount = 0;
        Cursor cursor = null;
        try{

            cursor = getReadableDatabase().rawQuery("SELECT * FROM invoices where bill_number = ? order by created_at DESC",
                    new String[]{invoiceId});

            if(cursor != null && cursor.moveToFirst()){
               nCount = cursor.getCount();
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Invoices:" + e.getMessage());
        }
        return nCount;
    }
    public Invoice getInvoiceObjectForInvoiceId(String invoiceId){
        Invoice invoiceObj = null;
        Cursor cursor = null;
        try{

            cursor = getReadableDatabase().rawQuery("SELECT * FROM invoices where bill_number = ?",
                    new String[]{invoiceId});

            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                invoiceObj = getInvoiceFromCursor(cursor);
            }
        }catch(Exception e){
            Log.i("DB", "Exception While getInvoiceObjectForInvoiceId:" + e.getMessage());
        }
        return invoiceObj;
    }
    public ArrayList<Invoice> getAllInvoicesForWeek(int weekIndex){
        ArrayList<Invoice> invoiceList=new ArrayList<Invoice>();
        Cursor cursor = null;
        try{

            String query = "select distinct invoices.amount, invoices.bill_number, " +
                    "invoices.description from expenses inner join invoices ON invoices.bill_number = expenses.invoice_id " +
                    "and expenses.week_index = "+weekIndex+" order by expenses.created_at DESC";
            cursor = db.rawQuery(query,null);

            while(cursor != null && cursor.moveToNext()){
                Invoice invoice = new Invoice();
                invoice.setInvAmt(cursor.getDouble(cursor.getColumnIndex("amount")));
                invoice.setInvBillNumber(cursor.getString(cursor.getColumnIndex("bill_number")));
                invoice.setInvDesc(cursor.getString(cursor.getColumnIndex("description")));
                invoiceList.add(invoice);
            }

        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return invoiceList;
    }
    public void insetInvoice(Invoice inv) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("description", inv.getInvDesc());
        cv.put("date", inv.getInvDate());
        cv.put("image_path", inv.getInvImgPath());
        cv.put("amount", inv.getInvAmt());
        cv.put("discount", inv.getInvDisc());
        cv.put("amount", inv.getInvAmt());
        cv.put("payment_mode", inv.getInvPayMode());
        cv.put("bill_number", inv.getInvNo());
        cv.put("created_at", Utils.getDateTime());
        cv.put("created_by", inv.getInvCreateBy());
        db.insert("invoices", null, cv);
    }

    public void deleteExpenseIsnotSaved() {
        Cursor catCursor = null;
        try {
            String query = "delete from expenses where is_saved =0";
            catCursor = db.rawQuery(query, null);
            catCursor.moveToFirst();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(catCursor != null && !catCursor.isClosed())
            catCursor.close();
        }
    }

    public int getCatId(String name) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT id FROM ");//Todo: Fetch Category_id instead of id.
        query.append(TABLE_CATEGORIES);
        query.append(" Where ");
        query.append(CATEGORIES_TBL_COLUMN_CATEGORY_NAME);
        query.append(" =? ");

        Cursor catCursor = null;
        int categoryId = 0;
        try {
            catCursor = db.rawQuery(query.toString(), new String[]{name});

            if (catCursor.getCount() > 0) {
                catCursor.moveToFirst();
                categoryId = catCursor.getInt(catCursor.getColumnIndex(KEY_ID));

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if(catCursor != null && !catCursor.isClosed()){
                catCursor.close();
            }
        }
        return categoryId;
    }

    public void insetCategory(String catName, int catId, int catCreatedBy) {
        ContentValues cv = new ContentValues();
        cv.put(CATEGORIES_TBL_COLUMN_CATEGORY_NAME, catName);
        cv.put(CATEGORIES_TBL_COLUMN_CATEGORY_ID, catId);
        cv.put(CATEGORIES_TBL_COLUMN_CREATED_BY, catCreatedBy);
        cv.put(CATEGORIES_TBL_COLUMN_CREATED_AT, Utils.getDateTime());
        db.insert(TABLE_CATEGORIES, null, cv);
    }

    public int getInvId(String name) {
        String query = "SELECT id FROM invoices where bill_number =?"; //Todo: Change the id to invoice_id
        Cursor catCursor = db.rawQuery(query, new String[]{name});

        if (catCursor.getCount() > 0) {
            catCursor.moveToFirst();
            return catCursor.getInt(catCursor.getColumnIndex("id"));

        } else {
            return 0;
        }
    }

    private ContentValues getContentValue(Expense exp){
        ContentValues cv = new ContentValues();
        cv.put(EXPENSE_TBL_COLUMN_CATEGORY_ID, exp.getExpCatId());
        cv.put(EXPENSE_TBL_COLUMN_INVOICE_ID, exp.getExpInvId());
        cv.put(EXPENSE_TBL_COLUMN_DATE, exp.getExpDate());
        cv.put(EXPENSE_TBL_COLUMN_PRODUCT_ID, exp.getExpProductId());
        cv.put(EXPENSE_TBL_COLUMN_PRODUCT_NAME, exp.getExpProductName());
        cv.put(EXPENSE_TBL_COLUMN_UNIT, exp.getExpUnit());
        cv.put(EXPENSE_TBL_COLUMN_AMOUNT, exp.getExpAmt());
        /*cv.put(EXPENSE_TBL_COLUMN_IS_APPROVED, exp.getExpIsApproved());
        cv.put(EXPENSE_TBL_COLUMN_IS_RECURSSIVE, exp.getExpIsRecursive());*/
        cv.put(EXPENSE_TBL_COLUMN_CREATED_AT, Utils.getDateTime());
        cv.put(EXPENSE_TBL_COLUMN_CREATED_BY, exp.getExpCreateBy());
        cv.put(EXPENSE_TBL_COLUMN_IS_SAVED, exp.getIsSaved());
        cv.put(EXPENSE_TBL_COLUMN_WEEK_INDEX, exp.getExpWeekIndex());
        return cv;
    }
    public void insetExpense(Expense exp) {
        db.insert(DatabaseConstants.TableNames.TABLE_EXPENSES, null, getContentValue(exp));
    }

    public void updateExpense(Expense exp){

        db.update("expenses",
                getContentValue(exp),
                "id= ?",
                new String[]{String.valueOf(exp.getExpid())});
    }


    public double getUnSaveExpAmt() {
        String query = "SELECT sum(amount) as tot FROM expenses where is_saved =0";
        Cursor catCursor = db.rawQuery(query, null);

        if (catCursor.getCount() > 0) {
            catCursor.moveToFirst();
            return catCursor.getDouble(catCursor.getColumnIndex("tot"));
        } else {
            return 0;
        }
    }

    public void updateExpenseSaved() {
        String query = "update expenses set is_saved = 1 where is_saved =0";
        Cursor catCursor = db.rawQuery(query, null);
        catCursor.moveToFirst();
        catCursor.close();
    }

    public int[] getExpCatId() {
        String query = "SELECT distinct category_id FROM expenses where is_saved=0";
        Cursor catCursor = db.rawQuery(query, null);

        if (catCursor.getCount() > 0) {
            int[] str = new int[catCursor.getCount()];
            int i = 0;
            while (catCursor.moveToNext()) {
                str[i] = catCursor.getInt(0);
                i++;
            }
            return str;
        } else {
            return new int[]{};
        }
    }

    private Expense getExpenseObjectFromCursor(Cursor expListCursor){
        String expDate = expListCursor.getString(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_DATE));
        int expProdId = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_PRODUCT_ID));
        String expProdName = expListCursor.getString(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_PRODUCT_NAME));
        int expcreatedby = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_CREATED_BY));
        int expUnit = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_UNIT));
        String expcreatedAt = expListCursor.getString(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_CREATED_AT));
        int expcatId = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_CATEGORY_ID));
        String expInvId = expListCursor.getString(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_INVOICE_ID));
        int expIsApproved = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_IS_APPROVED));
        int expIsRecursive = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_IS_RECURSSIVE));
        double expAmt = expListCursor.getDouble(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_AMOUNT));
        int isSaved = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_IS_SAVED));
        int weekindex1 = expListCursor.getInt(expListCursor.getColumnIndex(EXPENSE_TBL_COLUMN_WEEK_INDEX));
        int id =  expListCursor.getInt(expListCursor.getColumnIndex(KEY_ID));
        String expcategoryName = getCatName(expcatId);

        return new Expense(expDate,expProdId,expProdName,expcatId, expcategoryName, expInvId,expUnit,expIsApproved,
                expIsRecursive,expAmt,expcreatedby,expcreatedAt,isSaved,weekindex1,id);

    }
    public ArrayList<Expense> getExpensesload(int id){
        ArrayList<Expense> expList=new ArrayList<Expense>();
        Cursor expListCursor;
        try{
            expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved = 0 and category_id=? order by created_at DESC", new String[]{String.valueOf(id)});

            while(expListCursor.moveToNext()){
                expList.add(getExpenseObjectFromCursor(expListCursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expList;
    }

    public String getCatName(int id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(CATEGORIES_TBL_COLUMN_CATEGORY_NAME);
        query.append(" FROM ");
        query.append(TABLE_CATEGORIES);
        query.append(" where category_id =");
        query.append(id);
        Cursor catCursor = db.rawQuery(query.toString(), null);

        if (catCursor.getCount() > 0) {
            catCursor.moveToFirst();
            return catCursor.getString(catCursor.getColumnIndex(CATEGORIES_TBL_COLUMN_CATEGORY_NAME));
        } else {
            return "";
        }
    }


    public ArrayList<Expense> getExpenses(int catId, int weekindex){
        ArrayList<Expense> expList=new ArrayList<Expense>();
        Cursor expListCursor;
        try{
            if(weekindex==0){
                expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved >0 and category_id = ? order by created_at DESC" ,new String[]{String.valueOf(catId)});
            }else{
                expListCursor = db.rawQuery("SELECT * FROM expenses where is_saved >0 and category_id = ? and week_index= ?  order by created_at DESC" ,new String[]{String.valueOf(catId),String.valueOf(weekindex)});
            }

            while(expListCursor.moveToNext()){
                expList.add(getExpenseObjectFromCursor(expListCursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expList;
    }

    public ArrayList<Expense> getExpensesEdit(int expId){
        ArrayList<Expense> expList=new ArrayList<Expense>();
        Cursor expListCursor;
        try{

            expListCursor = db.rawQuery("SELECT * FROM expenses where id=?", new String[]{String.valueOf(expId)});

            while(expListCursor.moveToNext()){
                expList.add(getExpenseObjectFromCursor(expListCursor));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expList;
    }

    public String[] getCatname() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ");
        queryString.append(CATEGORIES_TBL_COLUMN_CATEGORY_NAME);
        queryString.append(" FROM ");
        queryString.append(TABLE_CATEGORIES);

        Cursor catCursor = null;
        String[] str = null;
        try {
            catCursor = db.rawQuery(queryString.toString(), null);

            if (catCursor != null && catCursor.getCount() > 0) {
                str = new String[catCursor.getCount()];
                int i = 0;
                while (catCursor.moveToNext()) {
                    str[i] = catCursor.getString(catCursor.getColumnIndex(CATEGORIES_TBL_COLUMN_CATEGORY_NAME));
                    i++;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(catCursor != null && !catCursor.isClosed()){
                catCursor.close();
            }
        }
        if(str != null)
            return str;
        else
            return new String[]{};
    }
    public void deleteExpense(int id) {
        String query = "delete from expenses where id=?";
        Cursor catCursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        catCursor.moveToFirst();
        catCursor.close();
    }

    public void deleteExpenseEntries(String invoiceId){
        String query = "delete from expenses where invoice_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{invoiceId});
        Log.d(getClass().getName(),""+cursor.getCount());
        cursor = db.rawQuery("delete from invoices where bill_number = ?", new String[]{invoiceId});
        Log.d(getClass().getName(),""+cursor.getCount());
    }

    public ArrayList<String> getExpenseNameforDate(String date){
        ArrayList<String> expenseNameList = new ArrayList<>();

        Cursor cursor;
        //Select invoices.description from invoices inner join expenses1 on invoices.bill_number = expenses1.invoice_id where expenses1.date = '01-04-2017'
        try{
            cursor = db.rawQuery("Select invoices.description from invoices inner join expenses on invoices.bill_number = expenses.invoice_id where expenses.date = ?", new String[]{date});
            //cursor = db.rawQuery("Select invoices.description from invoices inner join expenses on invoices.bill_number = expenses.invoice_id", null);

            while(cursor.moveToNext()){
                expenseNameList.add(cursor.getString(cursor.getColumnIndex("invoices.description")));
            }
        }catch(Exception e){
            Log.i("DB", "Exception While Get Categories:" + e.getMessage());
        }
        return expenseNameList;
    }
}