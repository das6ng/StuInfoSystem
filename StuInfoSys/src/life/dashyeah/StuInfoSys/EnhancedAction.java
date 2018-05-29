package life.dashyeah.StuInfoSys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public abstract class EnhancedAction extends ActionSupport {
	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	protected String getStrRequest() {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		InputStream inputStream;
		String strResponse = "";
		try {
			inputStream = request.getInputStream();
			String strMessage = "";
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			while ((strMessage = reader.readLine()) != null) {
				strResponse += strMessage;
			}
			reader.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("[MSG] received: "+strResponse);
		
		return strResponse;
	}
	
	protected JSONObject getJsonRequest() {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		String de = getStrRequest();
		try {
			obj = (JSONObject)parser.parse(de);
		} catch (ParseException e) {
			System.err.println("Error parsing json from String:"+de);
			e.printStackTrace();
		}
		
		return obj;
	}
}
