package com.example.cateringservice;

public class CSConstants {
    public static int ACTIVITY_RESULT_CODE = 1002;

    public static int DRINK_ACTIVITY_REQUEST_CODE = 2001;
    public static int BREAKFAST_ACTIVITY_REQUEST_CODE = 2002;
    public static int LUNCH_ACTIVITY_REQUEST_CODE = 2003;

    RecyclerViewOnClickListener itemClickListener;

    public interface RecyclerViewOnClickListener {
        void OnItemClicked(int position);
    }
}
