package base;

import dao.DBManager;
import models.User;

public class base {

	public static final int PagingSize = 14;
	
	public static User GetLoginUser(String loginId)
	{
		User tmp = new User();
		tmp.setName(loginId);
		return (User)DBManager.queryForObject("user.getUser", tmp);
	}
}