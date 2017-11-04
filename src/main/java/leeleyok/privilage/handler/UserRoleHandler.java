package leeleyok.privilage.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import leeleyok.privilage.common.constant.AdminActionConstant;
import leeleyok.privilage.common.dataobj.UserRole;
import leeleyok.privilage.common.exception.StorageException;
import leeleyok.privilage.registry.DependentRegistry;
import leeleyok.privilage.storage.PrivilageStorage;

/**
 * 用户角色管理组件，用于用户绑定到角色相关后台操作
 * 
 * @author leeley
 *
 */
public class UserRoleHandler extends Handler {
	
	
	/**
	 * 给用户赋予某个角色
	 * 
	 * @param userIdentifier   用户唯一标识
	 * @param role    角色
	 */
	public static void addUserRole(String userIdentifier, String dataRange, String role) {
		
		if (StringUtils.isBlank(userIdentifier)) {
			throw new IllegalArgumentException("param userIdentifier is null");
		}
		
		if (StringUtils.isBlank(dataRange)) {
			throw new IllegalArgumentException("param dataRange is null");
		}
		
		if (StringUtils.isBlank(role)) {
			throw new IllegalArgumentException("param role is null");
		}
		
        checkComponentRegistry();
        
        //保存用户角色数据
        PrivilageStorage storage = DependentRegistry.getStorage();
        
        storage.addUserRole(userIdentifier, dataRange, role);
        
        
        //上报监控
	    StringBuilder info = new StringBuilder();
	    info.append("role=").append(role);
	
	    DependentRegistry.getReporter().report(genReportMsg(userIdentifier, AdminActionConstant.ACTION_USER_ADD_ROLE, info.toString()));
	}
	
	/**
	 * 删除用户角色
	 * 
	 * @param userIdentifier    用户唯一标识
	 * @param role   角色
	 */
	public static void deleteUserRole(String userIdentifier, String dataRange, String role) {
		
		if (StringUtils.isBlank(userIdentifier)) {
			throw new IllegalArgumentException("param userIdentifier is null");
		}
		
		if (StringUtils.isBlank(dataRange)) {
			throw new IllegalArgumentException("param dataRange is null");
		}
		
		if (StringUtils.isBlank(role)) {
			throw new IllegalArgumentException("param role is null");
		}
		
        checkComponentRegistry();
        
        //保存用户角色数据
        PrivilageStorage storage = DependentRegistry.getStorage();
        
        storage.deleteUserRole(userIdentifier, dataRange, role);
        
        //上报监控
	    StringBuilder info = new StringBuilder();
	    info.append("role=").append(role);
	
	    DependentRegistry.getReporter().report(genReportMsg(userIdentifier, AdminActionConstant.ACTION_USER_DELETE_ROLE, info.toString()));
	}
	
	
	/**
	 * 查询用户在多个受控数据域拥有的角色
	 * 
	 * @param userIdentifier
	 * @return
	 * @throws StorageException
	 */
	public static List<UserRole> queryUserRoles(String userIdentifier) throws StorageException {
		if (StringUtils.isBlank(userIdentifier)) {
			return new ArrayList<UserRole>();
		}
		
        checkComponentRegistry();
		
		PrivilageStorage storage = DependentRegistry.getStorage();
		
		return storage.queryUserRoles(userIdentifier);
	}
	
	
	/**
	 * 查询用户在某个受控数据域所有角色
	 * 
	 * @param userIdentifier   用户唯一标识
	 * 
	 * @return   角色列表
	 */
	public static List<UserRole> queryUserDataRangeRoles(String userIdentifier, String dataRange) {
		
		if (StringUtils.isBlank(userIdentifier) || StringUtils.isBlank(dataRange)) {
			return new ArrayList<UserRole>();
		}
		
		checkComponentRegistry();
		
		PrivilageStorage storage = DependentRegistry.getStorage();
		
		return storage.getUserDataRangeRoles(userIdentifier, dataRange);
	}
	
	/**
	 * 
	 * 查询某个受控数据域所有用户及其角色
	 * 
	 * @param dataRange
	 * @return
	 */
	public static List<UserRole> queryDataRangeUsers(String dataRange) {
		if (StringUtils.isBlank(dataRange)) {
			return new ArrayList<UserRole>();
		}
		
        checkComponentRegistry();
		
		PrivilageStorage storage = DependentRegistry.getStorage();
		
		return storage.queryDataRangeUsers(dataRange);
	}
	
	
}
