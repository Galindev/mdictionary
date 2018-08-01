package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import base.SystemConst;
import dao.DBManager;
import models.Dictionary;
import models.EResultId;
import models.Result;
import models.User;

@RestController
public class DictionaryController {

	/*
	 * Шинээр үг бүртгэх
	 * */
	@RequestMapping(value = "/addWord", method = RequestMethod.POST)
	public Result addWord(@RequestBody Dictionary dictionary)
	{
		Result result = new Result();
		String message = "";
		if(dictionary.getMon().equals(""))
			message+=" монгол үг";
		if(dictionary.getEng().equals(""))
			message+=(message.equals("") ? "" : ", ") + "англи үг";
		if(!message.equals(""))
		{
			result.setId(EResultId.valueOf(EResultId.Warning.toString()).ordinal());
			result.setItem(dictionary);
			message = "Та " + message + "талбар"  + (message.split(",").length > 1 ? "уудын" : "ын") +  " утгыг бөглөнө үү.";
			result.setMessage(message);
			return result;
		}
		try {
			Integer resultId = DBManager.insert("dictionary.addWord", dictionary);
			if(resultId != null && resultId == 0) {
				resultId = EResultId.valueOf(EResultId.Warning.toString()).ordinal();
				result.setMessage("Бүртгэгдсэн үг байна.");
			} 
			else {
				resultId = EResultId.valueOf(EResultId.Success.toString()).ordinal();
				result.setMessage(SystemConst.SUCCESS_SAVING_DATA);
			}
			
			result.setId(resultId);
			result.setItem(dictionary);
			return result;
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem(dictionary);
			result.setMessage(SystemConst.ERROR_READ_DATA+e.getMessage());
			System.out.println("AAAA " + e.getMessage());
			return result;
		}
	}

	/*
	 * Бүх үгсийг унших
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getWords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//headers="Accept=application/json")
	public Result getWords(@RequestHeader int status)
	{
		Result result = new Result();
		try {
			List<Dictionary> words = (List<Dictionary>)DBManager.queryForList("dictionary.getAll", status);
			if(words != null)
				result.setItem(words);
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchWordMon", method = RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Result searchWordMon(@RequestParam(name = "word") String word)
	{
		Result result = new Result();
		try {
			Dictionary dic = new Dictionary();
			dic.setMon(word);
			List<Dictionary> words = (List<Dictionary>)DBManager.queryForList("dictionary.searchWordMon", dic);
			System.out.println(words.size());
			result.setItem(words);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchWord", method = RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Result searchWord(@RequestParam(name = "status") int status,
			@RequestParam(name = "isLatin") Boolean isLatin,
			@RequestParam(name = "word") String word)
	{
		Result result = new Result();
		try {
			Dictionary dic = new Dictionary();
			dic.setStatus(status);
			dic.setMon(word);
			dic.setEng(word);
			List<Dictionary> words = new ArrayList<>();
			if(isLatin) words = (List<Dictionary>)DBManager.queryForList("dictionary.searchWordEng", dic);
			else words = (List<Dictionary>)DBManager.queryForList("dictionary.searchWordMon", dic);
			System.out.println(words.size());
			result.setItem(words);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchWordEng", method = RequestMethod.GET)
	public Result searchWordEng(@RequestHeader String word)
	{
		Result result = new Result();
		try {
			Dictionary dic = new Dictionary();
			dic.setEng(word);
			System.out.println(word);
			List<Dictionary> words = (List<Dictionary>)DBManager.queryForList("dictionary.searchWordEng", dic);
			System.out.println(words.size());
			result.setItem(words);
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

	@RequestMapping(value = "/deleteWord", method = RequestMethod.POST)
	public Result deleteWord(@RequestHeader int id)
	{
		Result result = new Result();
		try {
			DBManager.delete("dictionary.deleteWord", id);
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

	@RequestMapping(value = "/confirmWord", method = RequestMethod.POST)
	public Result confirmWord(@RequestHeader int id)
	{
		Result result = new Result();
		try {
			DBManager.update("dictionary.confirmWord", id);
			result.setItem("");
			result.setId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
			result.setMessage(SystemConst.SUCCESS_SAVING_DATA);
			return result;
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem("");
			result.setMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
			return result;
		}
	}

	@RequestMapping(value = "/updateWord", method = RequestMethod.POST)
	public Result confirmWord(@RequestBody Dictionary dictionary)
	{
		Result result = new Result();
		try {
			DBManager.update("dictionary.updateWord", dictionary);
			result.setItem(dictionary);
			result.setId(EResultId.valueOf(EResultId.Success.toString()).ordinal());
			result.setMessage(SystemConst.SUCCESS_SAVING_DATA);
			return result;
		} catch (Exception e) {
			result.setId(EResultId.valueOf(EResultId.Error.toString()).ordinal());
			result.setItem("");
			result.setMessage(SystemConst.ERROR_READ_DATA + e.getMessage());
			return result;
		}
	}

}
