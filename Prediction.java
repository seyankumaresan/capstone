/**
 * Created by Ishrak Khan
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
//import java.sql.Statement;

public class Prediction 
{	
	// used by both of our predictive algorithms
	static double MAXIMUM_TEMPERATURE = 5.;
	static int NUMHOODS = 140;
	
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
		//get neighbourhoods

/*String Name = "Trinity-Bellwoods,West Humber-Clairville,Mount Olive-Silverstone-Jamestown,Humber Summit,Thistletown-Beaumond Heights,Humbermede,Rexdale-Kipling,Elms-Old Rexdale,Pelmo Park-Humberlea,Downsview-Roding-CFB,Kingsview Village-The Westway,Weston,Rustic,Humber Heights-Westmount,Brookhaven-Amesbury,Mount Dennis,Willowridge-Martingrove-Richview,Princess-Rosethorn,Eringate-Centennial-West Deane,Edenbridge-Humber Valley,Rockcliffe-Smythe,Markland Wood,Etobicoke West Mall,Islington-City Centre West,Alderwood,Long Branch,New Toronto,Mimico,Stonegate-Queensway,Kingsway South,Lambton Baby Point,High Park-Swansea,Runnymede-Bloor West Village,High Park North,Junction Area,Roncesvalles,South Parkdale,Dufferin Grove,Little Portugal,Dovercourt-Wallace Emerson-Juncti,Niagara,Weston-Pellam Park,Palmerston-Little Italy,University,Kensington-Chinatown,Waterfront Communities-The Island,Annex,Wychwood,Corso Italia-Davenport,Oakwood-Vaughan,Humewood-Cedarvale,Casa Loma,Forest Hill South,Yonge-St Clair,Bay Street Corridor,Rosedale-Moore Park,Church-Yonge Corridor,Caledonia-Fairbank,North St James Town,Cabbagetown South St James Town,Moss Park,Regent Park,Playter Estates Danforth,South Riverdale,North Riverdale,Blake-Jones,Danforth Village Toronto,Broadview North,Old East York,Danforth Village East York,Greenwood-Coxwell,Woodbine Corridor,Maple Leaf,Beechborough-Greenbrook,Keelesdale-Eglinton West,Briar Hill-Belgravia,Yorkdale-Glen Park,Forest Hill North,Englemount-Lawrence,Bedford Park-Nortown,Lawrence Park South,Yonge-Eglinton,Mount Pleasant West,Lawrence Park North,Mount Pleasant East,Black Creek,Glenfield-Jane Heights,York University Heights,Bathurst Manor,Westminster-Branson,Clanton Park,Lansing-Westgate,Willowdale West,Newtonbrook West,Newtonbrook East,Willowdale East,Bayview Woods-Steeles,Bayview Village,St Andrew-Windfields,Don Valley Village,Henry Farm,Pleasant View,Parkwoods-Donalda,Tam O''Shanter-Sullivan,Bridle Path-Sunnybrook-York Mills,Leaside-Bennington,Banbury-Don Mills,Thorncliffe Park,Flemingdon Park,Victoria Village,O''Connor-Parkview,The Beaches,East End-Danforth,Woodbine-Lumsden,Crescent Town,Birchcliffe-Cliffside,Oakridge,Clairlea-Birchmount,Wexford-Maryvale,Dorset Park,Ionview,Kennedy Park,Cliffcrest,Eglinton East,Bendale,Scarborough Village,Woburn,Guildwood,Morningside,West Hill,Rouge,Centennial Scarborough,Highland Creek,Malvern,Hillcrest Village,Steeles,L''Amoreaux,Agincourt South-Malvern West,Milliken,Agincourt North,";
String temp;
int i =0;

	String[] tokens = Name.split(",");
	//if(tokens.length!=2){throw new IllegalArgumentException();}

while (i<NUMHOODS)
{ 
	temp = tokens[i];

	List<WeatherData> dataset = Database.AddWeatherData(temp);
	i ++;

}*/







		List<Neighbourhood> neighbourhoods = Database.getNeighbourhoods();

		
			int i = 0;
			while(i < NUMHOODS)
			{

						//get data from the raw_data table that has weather information
				List<WeatherData> dataset = Database.getLatestData(neighbourhoods.get(i).neighbourhood);
				
				int BlackIce = 0;
				
				if ( predict(dataset) )
				{
					//System.out.println("Black ice!");
					BlackIce = 1;
					
				}
				
				//
				WeatherData data = dataset.get(0);
				
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
				//System.out.println(data.neighbourhood + " " +data.datetime);//.getTime());
				//System.out.println(dataset.get(23).neighbourhood + " " + dataset.get(23));//.datetime);
				
				Database.SendPrediction(data.datetime, neighbourhoods.get(i).neighbourhood, BlackIce);

				i++;

			}
		

	
		
	}
}


