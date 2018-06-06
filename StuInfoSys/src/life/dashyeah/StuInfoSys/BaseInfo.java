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
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class BaseInfo extends ActionSupport implements SessionAware {

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
		
		String sql = "select id,name,gender,dept,birthday from stu_base_info where id='"+user+"';";
		
		Connection conn = DBConn.getConn();
		try {
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			if(rs.next()) {
				result.put("status", "OK");
				
				result.put("id", rs.getString("id"));
				result.put("name", rs.getString("name"));
				result.put("gender", rs.getString("gender"));
				result.put("birthday", rs.getString("birthday"));
				result.put("dept", rs.getString("dept"));
			}else {
				result.put("status", "ERROR");
				result.put("info", "remote database error.");
			}
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
