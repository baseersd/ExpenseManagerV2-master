package expmanager.idea.spark.in.expensemanager.database;


public class DatabaseConstants {

    private static final String KEY_ID = "id";

    public final class TableNames{
        public static final String TABLE_EXPENSES = "expenses";
        public static final String TABLE_CATEGORIES = "categories";
    }


    public static final String CREATE_EXPENSE_TABLE = "CREATE TABLE " + TableNames.TABLE_EXPENSES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_CATEGORY_ID + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_INVOICE_ID + " TEXT,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_DATE + " TEXT,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_PRODUCT_ID + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_PRODUCT_NAME + " TEXT,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_UNIT + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_AMOUNT + " NUMERIC,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_IS_APPROVED + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_IS_RECURSSIVE + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_CREATED_AT + " TEXT,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_CREATED_BY + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_IS_SAVED + " INTEGER,"
            + ExpenseTableColumns.EXPENSE_TBL_COLUMN_WEEK_INDEX + " INTEGER"
            + ")";

    public static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TableNames.TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CategoriesTableColumns.CATEGORIES_TBL_COLUMN_CATEGORY_ID + " INTEGER,"
            + CategoriesTableColumns.CATEGORIES_TBL_COLUMN_CATEGORY_NAME + " TEXT,"
            + CategoriesTableColumns.CATEGORIES_TBL_COLUMN_URL + " TEXT,"
            + CategoriesTableColumns.CATEGORIES_TBL_COLUMN_CREATED_AT + " TEXT,"
            + CategoriesTableColumns.CATEGORIES_TBL_COLUMN_CREATED_BY + " INTEGER"
            + ")";

    public final class ExpenseTableColumns{
        public static final String EXPENSE_TBL_COLUMN_CATEGORY_ID = "category_id";
        public static final String EXPENSE_TBL_COLUMN_INVOICE_ID = "invoice_id";
        public static final String EXPENSE_TBL_COLUMN_DATE = "date";
        public static final String EXPENSE_TBL_COLUMN_PRODUCT_ID = "product_id";
        public static final String EXPENSE_TBL_COLUMN_PRODUCT_NAME = "product_name";
        public static final String EXPENSE_TBL_COLUMN_UNIT = "unit";
        public static final String EXPENSE_TBL_COLUMN_AMOUNT = "amount";
        public static final String EXPENSE_TBL_COLUMN_IS_APPROVED = "is_approved";
        public static final String EXPENSE_TBL_COLUMN_IS_RECURSSIVE = "is_recurssive";
        public static final String EXPENSE_TBL_COLUMN_CREATED_AT = "created_at";
        public static final String EXPENSE_TBL_COLUMN_CREATED_BY = "created_by";
        public static final String EXPENSE_TBL_COLUMN_IS_SAVED = "is_saved";
        public static final String EXPENSE_TBL_COLUMN_WEEK_INDEX = "week_index";
    }

    public class CategoriesTableColumns{
        public static final String CATEGORIES_TBL_COLUMN_CATEGORY_ID = "category_id";
        public static final String CATEGORIES_TBL_COLUMN_CATEGORY_NAME = "category_name";
        public static final String CATEGORIES_TBL_COLUMN_URL = "category_icon_url";
        public static final String CATEGORIES_TBL_COLUMN_CREATED_AT = "created_at";
        public static final String CATEGORIES_TBL_COLUMN_CREATED_BY = "created_by";
    }

    public class ProductsTableColumns{
        public static final String PRODUCTS_TBL_COLUMN_PRODUCT_ID = "product_id";
        public static final String PRODUCTS_TBL_COLUMN_PRODUCT_NAME = "product_name";
        public static final String PRODUCTS_TBL_COLUMN_CATEGORY_ID = "category_id";
        public static final String PRODUCTS_TBL_COLUMN_URL = "product_icon_url";
        public static final String PRODUCTS_TBL_COLUMN_CREATED_AT = "created_at";
        public static final String PRODUCTS_TBL_COLUMN_CREATED_BY = "created_by";
    }
}
