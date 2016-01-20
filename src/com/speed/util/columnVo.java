package com.speed.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class columnVo {
	private static Logger log = Logger.getLogger("InfoFile");
	public static String createColumnVoJsonString(List<String> list,int columnNum){
		//{"list":[{"column1":"2012-12.xls","column2":null,"column3":null,"column4":null,"column5":null,"column6":null,"column7":null,"column8":null,"column9":null,"column10":null}]}
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=null;
		//k从0到 list.size()-1,当k超出list的方位 ，用null补充
		int k=0;
		int rowsNum=(int) Math.ceil((double)(list.size())/columnNum);
		System.out.println(rowsNum);
		for (int i = 0; i < rowsNum; i++) {
			jsonObject=new JSONObject();
			for (int j = 0; j < columnNum; j++) {
				if(!(list.size()>k)){
					jsonObject.put("column"+(j+1), "null");
				}else{
					jsonObject.put("column"+(j+1), list.get(k));
				}
				k++;
			}
			jsonArray.add(jsonObject);
		}
		jsonObject=new JSONObject();
		jsonObject.put("list", jsonArray.toString());
		String json = jsonObject.toString();
		log.info(json);
		return json;
	}
	

}
