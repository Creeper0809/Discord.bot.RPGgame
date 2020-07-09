package creeper0809.discordbot.chosungquiz;

import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DicCrawl {
	
	public String isthere(String word) {
		String noun = word;
		String url = "https://dict.naver.com/search.nhn?dicQuery=" + noun + "&query=" + noun
				+ "&target=dic&ie=utf8&query_utf=&isOnlyViewEE=";
		Document doc = null;
		String explain = "";
		String n = "없음";
		try {
			doc = Jsoup.connect(url).get();
			Elements element = doc.getElementsByClass("kr_dic_section search_result dic_kr_entry");
			Elements ele = element.select("ul li p");
			String qe = ele.get(0).select("span.c_b").text();
			explain = qe+"(으)로 검색 됨.\n"+ele.get(1).text();
			return explain;
		} catch (Exception e) {
			return n;
		}
		
}
}
