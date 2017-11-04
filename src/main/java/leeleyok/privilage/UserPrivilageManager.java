package leeleyok.privilage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import leeleyok.privilage.common.dataobj.DataRangePrivilage;
import leeleyok.privilage.common.dataobj.Privilage;
import leeleyok.privilage.common.dataobj.UserRole;
import leeleyok.privilage.handler.RolePrivilageHandler;
import leeleyok.privilage.handler.UserRoleHandler;
import leeleyok.privilage.registry.DependentRegistry;
import leeleyok.privilage.storage.PrivilageStorage;

/**
 * 前端权限控制Manager，用于：
 * 
 * 1. 查询用户绑定的受控数据域
 * 2. 查询用户在某个访问控制点的权限
 * 
 * @author leeley
 *
 */
public class UserPrivilageManager {
    
    public UserPrivilageManager(PrivilageStorage storage) {
    	
		if (null == storage) {
            throw new RuntimeException("PrivilageStorage is null, must regist a PrivilageStorage");
      	}
		
		if (null == DependentRegistry.getStorage()) {
	        DependentRegistry.registStorage(storage);
	    }
    }
    
    /**
     * 查询用户所有受控数据域，及在访问控制点的权限
     * 
     * 查询步骤：
     * 1. 先查出来用户所属的所有受控数据域及在这些数据域分配的角色
     * 2. 将用户角色按受控数据域分类聚合
     * 3. 根据角色查询允许的操作列表
     * 4. 将操作按访问控制点分类聚合
     * 
     * @param userIdentifier
     * @return
     */
    public List<DataRangePrivilage> queryUserDataRangesAndPrivilages(String userIdentifier) {
    	
    	List<DataRangePrivilage> dataRangePrivilageList = new ArrayList<DataRangePrivilage>();
    	
    	if (StringUtils.isBlank(userIdentifier)) {
			return dataRangePrivilageList;
		}
    	
    	//查询用户所有角色列表
    	List<UserRole> userRoleList = UserRoleHandler.queryUserRoles(userIdentifier);
    	
    	if (CollectionUtils.isEmpty(userRoleList)) {
    		return dataRangePrivilageList;
    	}
    	
    	//将用户所有角色按dataRange聚合
    	Map<String, List<UserRole>> map = new HashMap<String, List<UserRole>>();
    	
    	for (UserRole userRole : userRoleList) {
    		String dataRange = userRole.getDataRange();
    		
    		if (map.containsKey(dataRange)) {
    			map.get(dataRange).add(userRole);
    		} else {
    			List<UserRole> list = new ArrayList<>();
    			list.add(userRole);
    			
    			map.put(dataRange, list);
    		}
    	}
    	
    	//每个dataRange的权限按accessPoint聚合操作权限
    	for (String dataRange : map.keySet()) {
    		
    		List<UserRole> roleList = map.get(dataRange);
    		
    		Map<String, List<String>> accessPointOptsMap = this.aggregateOptsByAccessPoint(roleList); //用于按accessPoint聚合操作权限， key-accessPoint, value-operate list
    		
    		if (accessPointOptsMap.isEmpty()) {
    			continue;
    		}
    		
    		
    		List<Privilage> dataRangePrivilages = new ArrayList<Privilage>();   //一个dataRange下的所有accessPoint及对其操作权限列表
    		
    		for (String accessPoint : accessPointOptsMap.keySet()) {
    			List<String> optList = accessPointOptsMap.get(accessPoint);
    			
    			if (CollectionUtils.isEmpty(optList)) {
    				continue;
    			}
    			
    			Privilage privilage = new Privilage();   //一个accessPoint下的所有操作权限
    			privilage.setAccessPoint(accessPoint);
    			privilage.setOperates(optList);
    			
    			dataRangePrivilages.add(privilage);
    			
    		}
    		
    		
    		DataRangePrivilage drp = new DataRangePrivilage();
    		drp.setDataRange(dataRange);
    		drp.setPrivilages(dataRangePrivilages);
    		
    		dataRangePrivilageList.add(drp);
    	}
    	
    	
    	return dataRangePrivilageList;
    }
    
	
    /**
     * 查询用户所属所有受控数据域
     * 
     * 查询步骤：
     * 1. 先查出来用户所属的所有受控数据域及在这些数据域分配的角色
     * 2. 将受控数据域去重（一个用户在一个受控数据域分配多个角色的情况）
     * 
     * @param userIdentifier
     * @return
     */
	public List<String> queryUserDataRanges(String userIdentifier) {
		
		List<String> dataRangeList = new ArrayList<String>();
		
		if (StringUtils.isBlank(userIdentifier)) {
			return dataRangeList;
		}
		
		List<UserRole> userRoleList = UserRoleHandler.queryUserRoles(userIdentifier);
		
		if (CollectionUtils.isEmpty(userRoleList)) {
			return dataRangeList;
		}
		
		for (UserRole role : userRoleList) {
			String dataRange = role.getDataRange();
			
			if (!dataRangeList.contains(dataRange)) {
				dataRangeList.add(dataRange);
			}
		}
		
		return dataRangeList;
	}
	
	
	/**
	 * 查询用户在某个受控数据域内的所有权限
	 * 
	 * 查询步骤：
     * 1. 先查出来用户在当前受控数据域分配的角色
     * 3. 根据角色查询允许的操作列表
     * 4. 将操作按访问控制点分类聚合
	 * 
	 * @param userIdentifier
	 * @param dataRange
	 * @return
	 */
	public List<Privilage> queryUserPrivilages(String userIdentifier, String dataRange) {

		//查询用户在当前受控数据域所属角色
        List<UserRole> userRoleList = UserRoleHandler.queryUserDataRangeRoles(userIdentifier, dataRange);
		
        //将用户操作权限按accessPoint聚合去重（用户属于多个角色，每个角色都有对某个访问控制的一些操作权限）
        Map<String, List<String>> privilageMap = this.aggregateOptsByAccessPoint(userRoleList); //key-accessPoint, value-operate list，用于按accessPoint聚合操作权限
        
		//返回结果
		List<Privilage> resultList = new ArrayList<Privilage>();
		
		for (String accessPoint : privilageMap.keySet()) {
			Privilage privilage = new Privilage();
			privilage.setAccessPoint(accessPoint);
			privilage.setOperates(privilageMap.get(accessPoint));
			
			resultList.add(privilage);
		}
		
		return resultList;
	}
	
	
	
	
	
	/**
	 * 查询用户在某个受控数据域内的某个访问控制点的操作权限列表
	 * 
	 * 查询步骤：
	 * 1. 先查询用户在当前受控数据域的所有角色
	 * 2. 查询每个角色在当前访问控制点上的操作权限
	 * 3. 将操作权限聚合
	 * 
	 * @param userIdentifier   用户唯一标识
	 * @param dataRange        受控数据域唯一标识
	 * @param accessPoint      访问控制点
	 * 
	 * @return  操作代号列表
	 */
	public List<String> queryUserPrivilages(String userIdentifier, String dataRange, String accessPoint) {
		
		List<String> permitOptList = new ArrayList<String>();
		
		//查询用户角色
		List<UserRole> userRoleList = UserRoleHandler.queryUserDataRangeRoles(userIdentifier, dataRange);
		
		if (CollectionUtils.isEmpty(userRoleList)) {
			return permitOptList;
		}
		
		//查询角色在当前访问控制点的操作权限
		for (UserRole userRole : userRoleList) {
			String role = userRole.getRole();
			
			if (StringUtils.isBlank(role)) {
				continue;
			}
			
			List<String> rolePriviges = DependentRegistry.getStorage().getRolePrivilage(role, accessPoint);
			
			for (String opt : rolePriviges) {
				if (StringUtils.isNotBlank(opt) && !permitOptList.contains(opt)) {
					permitOptList.add(opt);
				}
			}
		}
		
		return permitOptList;
	}
	
	
	/**
	 * 检查用户在某个受控数据域中的某个访问控制点是否拥有指定的权限
	 * 
	 * 查询步骤：
	 * 1. 调用已有接口查询用户在当前受控数据域的某个访问控制点上的所有操作权限
	 * 2. 再检查上面的所有操作权限中有无当前操作权限
	 * 
	 * @param userIdentifier 用户唯一标识
	 * @param dataRange      受控数据域唯一标识
	 * @param accessPoint    访问控制点
	 * @param operate        操作代号
	 * 
	 * @return true or false
	 */
    public boolean hasPrivilage(String userIdentifier, String dataRange, String accessPoint, String operate) {
	     	
		List<String> permitOptList = this.queryUserPrivilages(userIdentifier, dataRange, accessPoint);
		
		if (CollectionUtils.isNotEmpty(permitOptList)) {
			for (String opt : permitOptList) {
				if (StringUtils.equals(operate, opt)) {
					return true;
				}
			}
		}
		
		return false;
	}
    
    
    /**
	 * 将用户操作权限按accessPoint聚合去重（用户属于多个角色，每个角色都有对某个访问控制的一些操作权限）
	 */
	private Map<String, List<String>> aggregateOptsByAccessPoint(List<UserRole> userRoleList) {
		Map<String, List<String>> resultMap = new HashMap<String, List<String>>();  //key-accessPoint, value-operate list，用于按accessPoint聚合操作权限
		
		
		//查询角色权限,并按accessPoint合并
		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (UserRole userRole : userRoleList) {
				String role = userRole.getRole();
				
				if (StringUtils.isBlank(role)) {
					continue;
				}
				
				List<Privilage> rolePrivilages = RolePrivilageHandler.queryRolePrivilage(role);
				
				if (CollectionUtils.isNotEmpty(rolePrivilages)) {
					for (Privilage privilage : rolePrivilages) {
						String accessPoint = privilage.getAccessPoint();
						List<String> operates = privilage.getOperates();
						
						if (StringUtils.isBlank(accessPoint) || CollectionUtils.isEmpty(operates)) {
							continue;
						}
						
						if (resultMap.containsKey(accessPoint)) {
							
							List<String> allOperates = resultMap.get(accessPoint);
							
							for (String opt : operates) {
								if (!allOperates.contains(opt)) {
									allOperates.add(opt);
								} 
							}
						} else {
							resultMap.put(accessPoint, operates);
						}
					}
				}
			}
		}
		
		return resultMap;
	}
	
}
