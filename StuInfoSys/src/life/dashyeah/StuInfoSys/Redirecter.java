package life.dashyeah.StuInfoSys;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class Redirecter extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SessionMap<String,Object> session;
	
	public String index() {
		String role = (String) session.get("role");
		if("student".equals(role)) {
			System.out.println("--> role: student");
			return "student";
		}
		else if("teacher".equals(role)) {
			System.out.println("--> role: teacher");
			return "teacher";
		}
		else
			return "error";
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = (SessionMap<String, Object>) session;
	}

}
