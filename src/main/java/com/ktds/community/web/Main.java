
package com.ktds.community.web;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Main {
   public static void main(String[] args) throws Exception {
	   // 기본 국내 3/11
      String target = "http://finance.daum.net/quote/rise.daum?stype=P&nil_profile=stocktop&nil_menu=nstock142/";
      
      // 상승 http://finance.daum.net/quote/rise.daum?nil_profile=stockprice&nil_menu=siseleftmenu11&nil_stock=refresh
      // 기본 국내 3/12 
      //http://finance.daum.net/quote/index.daum?nil_profile=stockgnb&nil_menu=sise_top&nil_stock=refresh
      
      Document doc = Jsoup.connect(target).get();
      Elements txt = doc.select("td.txt");
      for(Element node:txt) {
    	  String title = (node.text());
    	  System.out.println(title);
      }
      
      Elements sUp = doc.select(".cUp");
      for(Element node:sUp) {
    	  String price = (node.text());
    	  System.out.println(price);
      }
      
      /*
      String target = "http://finance.daum.net/quote/rise.daum?stype=P&nil_profile=stocktop&nil_menu=nstock142/";
      HttpURLConnection con = (HttpURLConnection) new URL(target).openConnection();
      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
      String temp;

      while ((temp = br.readLine()) != null) {
    	  	
         if (temp.contains("td class=\"txt\"")) {
         	 
            String tempStr = temp.split("href=")[1].split("\">")[1].split("</a>")[0];
            System.out.println(tempStr);
         }
         if (temp.contains("class=\"stUp\"")) {
            String tempNum = temp.split("stUp\">")[1].split("</span>")[0];
            System.out.println(tempNum);
            
         }
      }
      con.disconnect();
      br.close();
      */

   }
}
