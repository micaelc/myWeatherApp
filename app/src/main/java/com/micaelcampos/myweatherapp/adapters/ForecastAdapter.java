package com.micaelcampos.myweatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.micaelcampos.myweatherapp.R;
import com.micaelcampos.myweatherapp.models.Forecast;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

	private List<Forecast> mForecasts;
	private Context mContext;


	public ForecastAdapter(List<Forecast> forecasts, Context context) {
		mForecasts = forecasts;
		mContext = context;
	}


	public Context getContext() {
		return mContext;
	}

	@Override
	public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View cityItem = inflater.inflate(R.layout.forecast_city_item, parent, false);
		ForecastViewHolder viewHolder = new ForecastViewHolder(cityItem);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ForecastViewHolder holder, int position) {
		Forecast data = mForecasts.get(position);

		holder.mCity.setText(data.getName() + ", " + data.getCountry());
		holder.mDescription.setText(data.getDescription());
		holder.mTemperature.setText(data.getTemp() + "º");

	}

	@Override
	public int getItemCount() {
		return mForecasts.size();
	}





	public class ForecastViewHolder extends RecyclerView.ViewHolder {

		public TextView mCity;
		public TextView mDescription;
		public TextView mTemperature;


		public ForecastViewHolder(View itemView) {
			super(itemView);

			mCity = (TextView) itemView.findViewById(R.id.tvCity);
			mDescription = (TextView) itemView.findViewById(R.id.tvDescription);
			mTemperature = (TextView) itemView.findViewById(R.id.tvTemperature);


		}
	}
}
