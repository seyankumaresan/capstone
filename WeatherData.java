/**
 * Created by Ishrak
 */

import java.util.Date;

public class WeatherData {
	public int id;
	public Date datetime;
	public String neighbourhood;
	public float temperature;	// in degrees C
	public float wind_speed;	// in km/h
	public float pop;			// in percentage
	public String description;
	public int weather_icon;	// integer value for the description
	
	WeatherData(int id, long millisec, String where, float temp_c, float wind_sp_k,
			    float pop, String desc, int fxicon) 
	{
		this.id = id;
		datetime = new Date(millisec);
		neighbourhood = where;
		temperature = temp_c;
		wind_speed = wind_sp_k;
		this.pop = pop;
		description = desc;
		weather_icon = fxicon;
	}
}