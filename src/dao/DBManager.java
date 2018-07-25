package dao;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class DBManager {

	private static SqlMapClient sqlMapper;

	static {
		try {
			Reader reader = Resources.getResourceAsReader("/mapping/SqlMapConfig.xml"); //new InputStreamReader(
					//DBManager.class
							//.getResourceAsStream("/mapping/SqlMapConfig.xml"));
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
			//reader.close();
		} catch (Exception e) {
			throw new RuntimeException("Error building the SqlMapClient instance." + e, e);
			//e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static List queryForList(String name, Object param) {
		try {
			return sqlMapper.queryForList(name, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object queryForObject(String name, Object param) {
		try {
			return sqlMapper.queryForObject(name, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List queryForList(String name, String[] names,
			Object[] objects) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < names.length; i++) {
				map.put(names[i], objects[i]);
			}
			return sqlMapper.queryForList(name, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object queryForObject(String name, String[] names,
			Object[] objects) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < names.length; i++) {
				map.put(names[i], objects[i]);
			}
			return sqlMapper.queryForObject(name, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void insertNE(String name, Object param) {
		try {
			sqlMapper.insert(name, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Integer insert(String name, Object param) {
		try {
			return (Integer) sqlMapper.insert(name, param);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void update(String name, Object param) {
		try {
			sqlMapper.update(name, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(String name, String[] names, Object[] objects) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < names.length; i++) {
				map.put(names[i], objects[i]);
			}
			sqlMapper.update(name, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlMapClient getMapper() {
		return sqlMapper;
	}

	public static void delete(String name, Object param) {
		try {
			sqlMapper.delete(name, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}