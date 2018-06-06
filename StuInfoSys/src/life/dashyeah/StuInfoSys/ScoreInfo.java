package life.dashyeah.StuInfoSys;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class ScoreInfo extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SessionMap<String,Object> session;
	
	private InputStream inputStream;

	private JSONObject result = new JSONObject();
	
	@SuppressWarnings("unchecked")
	public String get() {
		result.clear();
		String user = (String) session.get("user");
		System.out.print("[MSG] login: ");
		
		String sql = "select Cid,Cname,weight,semester,nature,grades from choice_info where Sid='"+user+"';";
		
		Connection conn = DBConn.getConn();
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			result.put("status", "OK");
			JSONArray scores = new JSONArray();
			while(rs.next()) {
				JSONObject score = new JSONObject();
				score.put("id", rs.getString("Cid"));
				score.put("name", rs.getString("Cname"));
				score.put("weight", rs.getString("weight"));
				score.put("semester", rs.getString("semester"));
				score.put("nature", rs.getString("nature"));
				score.put("grades", rs.getString("grades"));
				scores.add(score);
			}
			result.put("scores", scores);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("status", "ERROR");
			result.put("info", "remote database error.");
		}
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = (SessionMap<String, Object>) session;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
}
