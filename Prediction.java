/**
 * Created by Ishrak Khan
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.Statement;

public class Prediction 
{	
	// used by both of our predictive algorithms
	static double MAXIMUM_TEMPERATURE = 5.;
	
	static String[] blacklist = {
		"Heavy Rain",
		"Heavy Snow",
	};
	
	static String[] whitelist = {
		"Rain",
		"Light Rain",
		"Light Snow",
		"Mist",
		"Fog",
	};

	private static boolean descriptionInList(String[] list, String desc)
	{
		for (int i = 0; i < list.length; i++ )
		{
			if ( desc.equals(list[i]))
				return true;
		}
		
		return false;
	}
	
	
	private static boolean suitableWeatherStatus(List<WeatherData> dataset)
	{
		int num_hours_to_check = 12;
		int found = 0;
		
		for (int i = 0; i < num_hours_to_check; ++i )
		{
			WeatherData data = (WeatherData)dataset.get(i);
			
			if (descriptionInList(blacklist, data.description))
				return false;
		
			if (descriptionInList(whitelist, data.description))
				found += 1;
		}
		
		if (found > 0)
			return true;
		
		return false;
	}
	
	private static boolean suitableTemperature(List<WeatherData> dataset)
	{
		int num_hours_to_check = 6;
		
		for (int i = 0; i < num_hours_to_check; ++i)
		{
			WeatherData data = dataset.get(i);
			
			if (data.temperature > MAXIMUM_TEMPERATURE)
				return false;
		}
		
		return true;
	}

	private static boolean suitableWindSpeed(List<WeatherData> dataset)
	{
		int num_hours_to_check = 6;
		double maximum_wind_speed = 36.;
		
		for (int i = 0; i < num_hours_to_check; ++i)
		{
			WeatherData data = dataset.get(i);
			
			if (data.wind_speed > maximum_wind_speed)
				return false;
		}
		
		return true;
	}

	private static boolean suitablePOP(List<WeatherData> dataset)
	{
		int num_hours_to_check = 12;
		double minimum_pop = 70.;
		
		for (int i = 0; i < num_hours_to_check; ++i)
		{
				WeatherData data = dataset.get(i);
				if (data.pop >= minimum_pop)
					return true;
		}
		
		return false;
	}

	private static boolean suitableTemperatureChange(List<WeatherData> dataset)
	{
		int num_hours_to_check = 12;
		double minimum_temperature_change = 11.1;
		
		WeatherData latest = dataset.get(0);
		
		if (latest.temperature > MAXIMUM_TEMPERATURE)
			return false;
		
		for (int i = 1; i < num_hours_to_check; i++) 
		{
				WeatherData data = dataset.get(i);
				float difference = data.temperature - latest.temperature; 
				
				if (difference >= minimum_temperature_change)
						return true;
		}
		
		return false;
	}
	
	private static boolean algorithm_1(List<WeatherData> dataset)
	{
		if (!suitableTemperature(dataset))
			return false;
		
		if (!suitablePOP(dataset))
			return false;
		
		return true;
	}

	private static boolean algorithm_2(List<WeatherData> dataset)
	{
		if (!suitableTemperatureChange(dataset))
			return false;

		return true;
	}
	
	// TODO: we assume that the dataset is already sorted in
	// reverse chronological order
	public static boolean predict(List<WeatherData> dataset)
	{
		if (!suitableWindSpeed(dataset))
			return false;
		
		if (!suitableWeatherStatus(dataset)) 
			return false;
		
		return algorithm_1(dataset) || algorithm_2(dataset);
	}
	
	public static void main(String[] args) {
		// TODO: use this when database is set up properly
		//List<WeatherData> dataset = Database.getLatestData();
		
		// TODO: this is for testing of our prediction algorithms
		List<WeatherData> dataset = new ArrayList<WeatherData>();
		
		// datetime field does not matter because algorithm assumes
		// sorted array. where and fxicon also doesn't matter
		dataset.add(new WeatherData(24, 0, "hell", -4, 30, 70, " Light Rain", 0));
		dataset.add(new WeatherData(23, 0, "hell", -3, 30, 70, "Light Rain", 0));
		dataset.add(new WeatherData(22, 0, "hell", -3, 30, 70, "Clear", 0));
		dataset.add(new WeatherData(21, 0, "hell", -2, 30, 70, "Clear", 0));
		dataset.add(new WeatherData(20, 0, "hell", -2, 30, 70, "Clear", 0));
		dataset.add(new WeatherData(19, 0, "hell", -2, 30, 70, "Clear", 0));
		dataset.add(new WeatherData(18, 0, "hell", -1, 30, 70, "Clear", 0));
		dataset.add(new WeatherData(17, 0, "hell", 0, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(16, 0, "hell", 0, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(15, 0, "hell", 1, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(14, 0, "hell", 2, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(13, 0, "hell", 3, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(12, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(11, 0, "hell", 5, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(10, 0, "hell", 7, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(9, 0, "hell", 8, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(8, 0, "hell", 7, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(7, 0, "hell", 3, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(6, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(5, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(4, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(3, 0, "hell", 6, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(2, 0, "hell", 5, 30, 70, "Rain", 0));
		dataset.add(new WeatherData(1, 0, "hell", 4, 30, 70, "Rain", 0));
		
		int BlackIce = 0;
		
		if ( predict(dataset) )
		{
			//System.out.println("Black ice!");
			BlackIce = 1;
			
		}
	/*else 
		{
			System.out.println("No black ice");
		}*/
		
		WeatherData data = dataset.get(0);
		
		
		
		Database.SendPrediction(data.neighbourhood, BlackIce);
		
	}
}


