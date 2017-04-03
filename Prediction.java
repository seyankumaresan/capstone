/**
 * Created by Ishrak
 */

import WeatherData;

public class Prediction 
{	
	// used by both of our predictive algorithms
	float MAXIMUM_TEMPERATURE = 5.;
	
	String[] blacklist = {
		"Heavy Rain",
		"Heavy Snowfall",
	};
	
	String[] whitelist = {
		"Rain",
		"Light Rain",
		"Light Snowfall",
		"Misty",
		"Foggy",
	};

	private static boolean suitableWeatherStatus(List<WeatherData> dataset)
	{
		int num_hours_to_check = 12;
		int found = 0;
		
		for (int i = 0; i < num_hours_to_check; ++i )
		{
			if (DescriptionBlacklisted(data.description))
				return false;
		
			if (DescriptionWhitelisted(data.description))
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
		float maximum_wind_speed = 36.;
		
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
		float minimum_pop = 70.;
		
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
		float minimum_temperature_change = 11.1;
		
		WeatherData latest = dataset.get(0);
		
		if (latest.temperature > MAXIMUM_TEMPERATURE)
			return false;
		
		for (i = 1; i < num_hours_to_check; i++) 
		{
				WeatherData data = dataset.get(i);
				float difference = data.temperature - latest.temperature; 
				
				if (difference >= minimum_temperature_change)
				{
						return true;
				}
		}
		
		return false;
	}
	
	private static boolean algorithm_1(List<WeatherData> dataset)
	{
		if (!suitableTemperature(dataset))
			return false;
		
		if (!suitableWindSpeed(dataset))
			return false;
		
		if (!suitableWeatherStatus(dataset)) 
			return false;
		
		if (!suitablePOP(dataset))
			return false;
		
		return true;
	}

	private static boolean algorithm_2(List<WeatherData> dataset)
	{
		if (!suitableTemperatureChange(dataset))
			return false;
		
		if (!suitableWindSpeed(dataset))
			return false;
		
		if (!suitableWeatherStatus(dataset)) 
			return false;
		
		return true;
	}
	
	// TODO: we assume that the dataset is already sorted in
	// reverse chronological order
	public static boolean predict(List<WeatherData> dataset)
	{
		return algorithm_1(dataset) || algorithm_2(dataset);
	}
	
	public static void main(String[] args) {
		// TODO: use this when database is set up properly
		//List<WeatherData> dataset = Database.getLatestData();
		
		// TODO: this is for testing of our prediction algorithms
		List<WeatherData> dataset = new ArrayList<WeatherData>();
		
		// datetime field does not matter because algorithm assumes
		// sorted array. where and fxicon also doesn't matter
		dataset.add(new Weather(24, 0, "hell", -4, 30, 70, " Light Rain", 0));
		dataset.add(new Weather(23, 0, "hell", -3, 30, 70, "Light Rain", 0));
		dataset.add(new Weather(22, 0, "hell", -3, 30, 70, "Clear", 0));
		dataset.add(new Weather(21, 0, "hell", -2, 30, 70, "Clear", 0));
		dataset.add(new Weather(20, 0, "hell", -2, 30, 70, "Clear", 0));
		dataset.add(new Weather(19, 0, "hell", -2, 30, 70, "Clear", 0));
		dataset.add(new Weather(18, 0, "hell", -1, 30, 70, "Clear", 0));
		dataset.add(new Weather(17, 0, "hell", 0, 30, 70, "Rain", 0));
		dataset.add(new Weather(16, 0, "hell", 0, 30, 70, "Rain", 0));
		dataset.add(new Weather(15, 0, "hell", 1, 30, 70, "Rain", 0));
		dataset.add(new Weather(14, 0, "hell", 2, 30, 70, "Rain", 0));
		dataset.add(new Weather(13, 0, "hell", 3, 30, 70, "Rain", 0));
		dataset.add(new Weather(12, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new Weather(11, 0, "hell", 5, 30, 70, "Rain", 0));
		dataset.add(new Weather(10, 0, "hell", 7, 30, 70, "Rain", 0));
		dataset.add(new Weather(9, 0, "hell", 8, 30, 70, "Rain", 0));
		dataset.add(new Weather(8, 0, "hell", 7, 30, 70, "Rain", 0));
		dataset.add(new Weather(7, 0, "hell", 3, 30, 70, "Rain", 0));
		dataset.add(new Weather(6, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new Weather(5, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new Weather(4, 0, "hell", 4, 30, 70, "Rain", 0));
		dataset.add(new Weather(3, 0, "hell", 6, 30, 70, "Rain", 0));
		dataset.add(new Weather(2, 0, "hell", 5, 30, 70, "Rain", 0));
		dataset.add(new Weather(1, 0, "hell", 4, 30, 70, "Rain", 0));
		
		if ( predict(dataset) )
		{
			System.out.println("Black ice!");
		}
		else 
		{
			System.out.println("No black ice");
		}
	}
}


