package org.agilesource.android.ilovetravelling.util;

import java.util.ArrayList;
import java.util.List;

import org.agilesource.android.ilovetravelling.bean.Weather;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

    
    private List<Weather> weatherList;
    
    private boolean inForcast;
    
    private Weather currentWeather;
    
    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public XmlHandler() {
        
        weatherList = new ArrayList<Weather>();
        inForcast = false;
        
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        
        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
        
        if(tagName.equals("forecast_conditions")) {
            
            inForcast = true;
            currentWeather = new Weather();
            
        }
        
        if(inForcast) {
            
            if(tagName.equals("day_of_week")) {   
                currentWeather.setDay(attributes.getValue("data"));
            }else if(tagName.equals("low")) {
                currentWeather.setLowTemp(attributes.getValue("data"));
            }else if(tagName.equals("high")) {
                currentWeather.setHighTemp(attributes.getValue("data"));
            }else if(tagName.equals("icon")) {
                currentWeather.setImageUrl(attributes.getValue("data"));
            }else if(tagName.equals("condition")) {
                currentWeather.setCondition(attributes.getValue("data"));
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
        
        if(tagName.equals("forecast_conditions")) {
            inForcast = false;
            weatherList.add(currentWeather);
        }
    }

}
