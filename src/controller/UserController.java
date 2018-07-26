package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.SystemConst;
import base.base;
import dao.DBManager;
import models.EResultId;
import models.Result;
import models.User;

@RestController
public class UserController {

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public Result login(@RequestBody User userTmp)
	{
		Result result = new Result();
		try {
			String loginPass = userTmp.getPass();
			String loginId = userTmp.getName();
			User user = base.GetLoginUser(loginId);
			if(user != null) {
				if(user.getPass().equals(loginPass))
				{
					result.setId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
					result.setItem(user);
					result.setMessage(loginId + " амжилттай нэвтэрлээ.");
				}
				else
				{
					result.setId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
					result.setItem(loginId);
					result.setMessage(loginId + " хэрэглэгчийн нууц үг буруу байна.");
				}
				return result;
			}
			else {
				result.setId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
				result.setItem(loginId);
				result.setMessage(loginId + " хэрэглэгч бүртгэлгүй байна.");
				return result;
			}
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem("");
			result.setMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
			return result;
		}
	}
	
	/*
	 * Бүх хэрэглэгчийн мэдээлэл унших
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//headers="Accept=application/json")
	public Result getUsers()
	{
		Result result = new Result();
		try {
			List<User> users = (List<User>)DBManager.queryForList("user.getAll", null);
			if(users != null)
				result.setItem(users);
			else result.setItem(new ArrayList<User>());
			result.setId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
			result.setMessage(SystemConst.SUCCESS_READ_DATA);
			return result;
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem("");
			result.setMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
			return result;
		}
	}

	/*
	 * Шинээр хэрэглэгчийн мэдээлэл бүртгэх
	 * */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Result addUser(@RequestBody User user)
	{
		Result result = new Result();
		try {			
			String message = "";
			if(user.getName().equals(""))
				message+=" нэр";
			if(user.getPass().equals(""))
				message+=(message.equals("") ? "" : ", ") + "нууц үг";
			if(user.getType().equals(""))
				message+=(message.equals("") ? "" : ", ") + "төрөл ";
			if(!message.equals(""))
			{
				result.setId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
				result.setItem(user);
				message = "Хэрэглэгчийн " + message + "талбар"  + (message.split(",").length > 1 ? "уудын" : "ын") +  " утгыг бөглөнө үү.";
				result.setMessage(message);
				return result;
			}
			
			Integer resultId = DBManager.insert("user.addUser", user);
			if(resultId != null && resultId == 0) {
				resultId = EResultId.valueOf(EResultId.Error.toString()).ordinal();
				result.setMessage(SystemConst.ERROR_SAVING_DATA);
			} 
			else {
				resultId = EResultId.valueOf(EResultId.Success.toString()).ordinal();
				result.setMessage(SystemConst.SUCCESS_SAVING_DATA);
			}
			
			result.setId(resultId);
			result.setItem(user);
			return result;
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem(user);
			result.setMessage(SystemConst.ERROR_READ_DATA+e.getMessage());
			return result;
		}
	}
	
	/*
	 * Хэрэглэгчийн мэдээлэл устгах
	 * */
	@RequestMapping(value = "/deleteUser/{name}", method = RequestMethod.POST)
	public Result deleteUser(@PathVariable String name)
	{
		Result result = new Result();
		try {
			DBManager.delete("user.deleteUser", name);
			result.setItem("");
			result.setId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
			result.setMessage(SystemConst.SUCCESS_DELETE_DATA);
			return result;
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem("");
			result.setMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
			return result;
		}
	}

}
