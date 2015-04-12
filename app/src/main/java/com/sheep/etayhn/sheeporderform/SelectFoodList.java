package com.sheep.etayhn.sheeporderform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SelectFoodList extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView myListView;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent $ = new Intent();
        $.putExtra(Constants.CHOSEN_FOOD_TAG, ((SelectFoodListAdapter.ViewHolder) view.getTag()).text.getText());
        setResult(Activity.RESULT_OK, $);
        finish();
    }

    // The adapter I'm using for my custom list view.
    private class SelectFoodListAdapter extends BaseAdapter {

        private ArrayList<String> listValues;

        private class ViewHolder {
            public ImageView image;
            public TextView text;
        }

        public SelectFoodListAdapter(ArrayList<String> listValues) {
            this.listValues = listValues;
        }

        @Override
        public int getCount() {
            return listValues.size();
        }

        @Override
        public Object getItem(int position) {
            return listValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.select_foods_list_item, null);

                viewHolder = new ViewHolder();
                viewHolder.image = (ImageView) view.findViewById(R.id.iv_select_food_image);
                viewHolder.text = (TextView) view.findViewById(R.id.tv_select_food_text);

                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            String foodType = listValues.get(position);
            int textId = getResources().getIdentifier(foodType, "string", getPackageName());
            viewHolder.text.setText(getResources().getString(textId));
//            Log.d("IMG_LIST", "getView: imageUri: " + imageName);
            int imgId = getResources().getIdentifier(foodType, "drawable", getPackageName());
//            Log.d("IMG_LIST", "getView: imgId: " + imgId);
            viewHolder.image.setImageResource(imgId);

            return view;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food_list);

        myListView = (ListView) findViewById(R.id.lv_selectFoods);
        myListView.setOnItemClickListener(this);

        ArrayList<String> values = new ArrayList<>();
        values.add("watermelon_man");
        values.add("snake_cake");
        values.add("pineapple_hedgehog");
        values.add("rice_piggies");
        values.add("turtle_burger");
        values.add("cauliflower_sheep");
        values.add("orange_kitten");
        values.add("baby_moses");


        SelectFoodListAdapter adapter = new SelectFoodListAdapter(values);
        myListView.setAdapter(adapter);
    }

}
