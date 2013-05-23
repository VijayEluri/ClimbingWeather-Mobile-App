package com.climbingweather.cw;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastDay {
    
    // Date YYYY-MM-DD
    private String d;
    
    // Formatted date
    private String dd;
    
    // Formatted day
    private String dy;
    
    // High temp
    private String hi;
    
    // Low temp
    private String l;
    
    // Precip day
    private String pd;
    
    // Precip night
    private String pn;
    
    // Symbol
    private String sy;
    
    // Humidity
    private String h;
    
    // Rain amount
    private String r;
    
    // Snow amount
    private String s;
    
    // Wind speed
    private String ws;
    
    // Wind gust
    private String wg;
    
    // Weather
    private String w;
    
    // Symbol
    // Conditions
    private String c;
    
    private ArrayList<ForecastHour> hours;
    
    public ForecastDay(String date, String high, String low, String precipDay, String precipNight, String symbol)
    {
        d = date;
        hi = high;
        l = low;
        pd = precipDay;
        pn = precipNight;
        sy = symbol;
    }
    
    public String toString()
    {
        return d + " H/l:" + hi + "/" + l;
    }
    
    public ForecastHour getHour(int position)
    {
        return hours.get(position);
    }
    
    public boolean hasHours()
    {
        return hours != null;
    }
    
    public int getHourCount()
    {
        return hours.size();
    }
    
    public String getDate()
    {
        return d;
    }
    
    public void addHour(ForecastHour hour)
    {
        if (hours == null) {
            hours = new ArrayList<ForecastHour>();
        }
        hours.add(hour);
    }
    
    public String getSymbol()
    {
        return sy;
    }
    
    public String getDayOfWeek()
    {
        return dy;
    }
    
    public String getShortDate()
    {
        return dd;
    }
    
    public String getConditions()
    {
        return c;
    }

    // Get list row view
    public View getListRowView(View view, ViewGroup parent, LayoutInflater inflater, Context context)
    {
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_daily, parent,false);
        }
        
        TextView dyTextView = (TextView) view.findViewById(R.id.dy);
        TextView ddTextView = (TextView) view.findViewById(R.id.dd);
        TextView conditionsTextView = (TextView) view.findViewById(R.id.conditions);
        ImageView symbolImageView = (ImageView) view.findViewById(R.id.sy);
        TextView hiTextView = (TextView) view.findViewById(R.id.hi);
        TextView lTextView = (TextView) view.findViewById(R.id.l);
        TextView pdTextView = (TextView) view.findViewById(R.id.pd);
        TextView pnTextView = (TextView) view.findViewById(R.id.pn);
        TextView wsTextView = (TextView) view.findViewById(R.id.ws);
        TextView hTextView = (TextView) view.findViewById(R.id.h);
        
        String symbol = sy.replace(".png", "");
        symbolImageView.setImageResource(context.getResources()
                .getIdentifier(symbol, "drawable", "com.climbingweather.cw"));
        
        dyTextView.setText(dy == null ? "--" : dy);
        ddTextView.setText(dd == null ? "--" : dd);
        
        conditionsTextView.setText(c);
        if (c.length() == 0) {
            conditionsTextView.setVisibility(View.GONE);
        } else {
            conditionsTextView.setVisibility(View.VISIBLE);
        }
        hiTextView.setText(hi == null ? "--" : hi + (char) 0x00B0);
        lTextView.setText(l == null ? "--" : l + (char) 0x00B0);
        pdTextView.setText(pd == null ? "--" : pd + "%");
        pnTextView.setText(pn == null ? "--" : pn + "%");
        wsTextView.setText(ws == null ? "--" : ws + " mph");
        hTextView.setText(h == null ? "--" : h + "%");
 
        return view;
    }
}
