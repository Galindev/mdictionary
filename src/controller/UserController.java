package controller;

import java.math.BigDecimal;
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

	@RequestMapping(value = "/getUser/{loginId}/{loginPass}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Result login(@PathVariable String loginId, @PathVariable String loginPass)
	{
		Result result = new Result();
		try {
			User user = base.GetLoginUser(loginId);
			if(user != null) {
				if(user.getPass().equals(loginPass))
				{
					result.setResultId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
					result.setResultItem(user);
					result.setResultMessage(loginId + " амжилттай нэвтэрлээ.");
				}
				else
				{
					result.setResultId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
					result.setResultItem(loginId);
					result.setResultMessage(loginId + " хэрэглэгчийн нууц үг буруу байна.");
				}
				return result;	
			}
			else {
				result.setResultId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
				result.setResultItem(loginId);
				result.setResultMessage(loginId + " хэрэглэгч бүртгэлгүй байна.");
				return result;
			}
		} catch (Exception e) {
			result.setResultId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setResultItem(loginId);
			result.setResultMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
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
				result.setResultItem(users);
			else result.setResultItem(new ArrayList<User>());
			result.setResultId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
			result.setResultMessage(SystemConst.SUCCESS_READ_DATA);
			return result;
		} catch (Exception e) {
			result.setResultId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setResultItem("");
			result.setResultMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
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
				result.setResultId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
				result.setResultItem(user);
				message = "Хэрэглэгчийн " + message + "талбар"  + (message.split(",").length > 1 ? "уудын" : "ын") +  " утгыг бөглөнө үү.";
				result.setResultMessage(message);
				return result;
			}
			
			Integer resultId = DBManager.insert("user.addUser", user);
			if(resultId != null && resultId == 0) {
				resultId = EResultId.valueOf(EResultId.Error.toString()).ordinal();
				result.setResultMessage(SystemConst.ERROR_SAVING_DATA);
			} 
			else {
				resultId = EResultId.valueOf(EResultId.Success.toString()).ordinal();
				result.setResultMessage(SystemConst.SUCCESS_SAVING_DATA);
			}
			
			result.setResultId(resultId);
			result.setResultItem(user);
			return result;
		} catch (Exception e) {
			result.setResultId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setResultItem(user);
			result.setResultMessage(SystemConst.ERROR_READ_DATA+e.getMessage());
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
			result.setResultItem("");
			result.setResultId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
			result.setResultMessage(SystemConst.SUCCESS_DELETE_DATA);
			return result;
		} catch (Exception e) {
			result.setResultId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setResultItem("");
			result.setResultMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
			return result;
		}
	}

}
