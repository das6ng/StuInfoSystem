package life.dashyeah.StuInfoSys;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class Login extends EnhancedAction implements SessionAware {
	/**
	 * default UID
	 */
	private static final long serialVersionUID = 1L;
	
	private InputStream inputStream;
	
	private Connection conn = null;
	
	private SessionMap<String,Object> session;
	
	private JSONObject result = new JSONObject();
	
	public Login() {
		this.conn = DBConn.getConn();
	}

	public String login() {
		result.clear();
		System.out.println("[MSG] login:");
		
		JSONObject str = getJsonRequest();
		System.out.print("    user:"+str.get("username")+" passwd:"+str.get("passwd"));
		
		String sql = "select id,passwd from users where id='"+str.get("username")+"';";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() && rs.getString("password").equals(str.get("password"))) {
				System.out.println(" -- Accepted. ["+rs.getString("id")+"]");
				
				session.put("login","true");
				session.put("user", str.get("username"));
				
				result.put("status", "OK");
				result.put("username", str.get("username"));
			}else {
				System.out.println(" -- Denied.");
				
				result.put("status", "ERROR");
				result.put("info", "wrong username or password.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			result.put("status", "ERROR");
		}
		
		System.out.println("    result: "+result.toJSONString());
		
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(
	            re.getBytes(StandardCharsets.UTF_8));
	    return SUCCESS;
	}
	
	public String logout() {
		result.clear();
		System.out.println("[MSG] logout -- user: "+session.get("user"));
		
		result.put("status", "OK");
		result.put("username", session.get("user"));

		session.invalidate();
		
		System.out.println("    result: "+result.toJSONString());
		String re = result.toJSONString();
		inputStream = new ByteArrayInputStream(
	            re.getBytes(StandardCharsets.UTF_8));
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> sess) {
		session = (SessionMap<String, Object>)sess;
	}
	
	public InputStream getInputStream() {
	    return inputStream;
	}
}
