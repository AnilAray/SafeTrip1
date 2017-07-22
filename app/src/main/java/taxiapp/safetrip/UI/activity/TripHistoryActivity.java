package taxiapp.safetrip.UI.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import taxiapp.safetrip.R;


public class TripHistoryActivity extends AppCompatActivity {
   /* RecyclerView recyclerView;

    @BindView(R.id.tv_title) TextView tvTitle;
    //@BindView(R.id.tv_drivernamehistory)  TextView DriverName;
    //@BindView(R.id.tv_ridefarehistory) TextView RideFare;
    //@BindView(R.id.tv_tripdate_time) TextView RideTime;


    Typeface custom_font, custom_font2, custom_font3, custom_font4;
    private static int pos;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
       /* ButterKnife.bind(this);
        recyclerView= (RecyclerView) findViewById(R.id.myTripsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        String[] array={"araam","asd","asdasd","asdas","asdds"};
        MyAdapter myAdapter=new MyAdapter(array);
        recyclerView.setAdapter(myAdapter);

        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Light.ttf");
        custom_font2 = Typeface.createFromAsset(getAssets(),  "fonts/Montserrat-Medium.ttf");
        custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        custom_font4 = Typeface.createFromAsset(getAssets(), "fonts/PTS75F.ttf");

          tvTitle.setText("Your Trips");
       // tvTitle.setTypeface(custom_font3);
//        DriverName.setTypeface(custom_font);
  //      RideFare.setTypeface(custom_font);
    //    RideTime.setTypeface(custom_font);*/

    }

/*

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  {
        private String[] mDataset;




        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
              pos=this.getAdapterPosition();
           //     Toast.makeText(TripHistoryActivity.this, "pos "+this.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),TripDetailsActivity.class));
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trips_history, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(view);

            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element



        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
    public void back(View view){
        this.finish();
    }
*/

}
