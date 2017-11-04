package leeleyok.privilage.storage;

import java.util.List;

import leeleyok.privilage.common.dataobj.Privilage;
import leeleyok.privilage.common.dataobj.UserRole;
import leeleyok.privilage.common.exception.StorageException;

public interface PrivilageStorage {
	
    //角色授权相关
	/**
	 * 保存角色在某个访问控制点的权限
	 * 
	 * @param role
	 * @param accessPoint
	 * @param operates
	 * @throws StorageException
	 */
    void addRolePrivilage(String role, String accessPoint, String operate) throws StorageException;
    
    /**
     * 删除角色在某个访问控制点的权限
     * 
     * @param role
     * @param accessPoint
     * @throws StorageException
     */
    void deleteRolePrivilage(String role, String accessPoint, String operate) throws StorageException;
    
    /**
     * 获取角色在某个访问控制点的权限
     * 
     * @param role
     * @param accessPoint
     * @throws StorageException
     */
    List<String> getRolePrivilage(String role, String accessPoint) throws StorageException;
    
    /**
     * 获取角色所有权限
     * 
     * @param role
     * @return
     * @throws StorageException
     */
    List<Privilage> getRolePrivilage(String role) throws StorageException;
    
    
    //用户授权相关
	/**
	 * 给用户授予角色
	 * 
	 * @param user
	 * @param role
	 * @throws StorageException
	 */
	void addUserRole(String user, String dataRange, String role) throws StorageException;
	
	/**
	 * 删除用户角色
	 * 
	 * @param user
	 * @param role
	 * @throws StorageException
	 */
	void deleteUserRole(String user, String dataRange, String role) throws StorageException;
	
	/**
	 * 查询用户在多个受控数据域拥有的角色
	 * 
	 * @param userIdentifier
	 * @return
	 * @throws StorageException
	 */
	List<UserRole> queryUserRoles(String userIdentifier) throws StorageException;
	
	/**
	 * 
	 * 查询用户在某个受控数据域内所有角色
	 * 
	 * @param userIdentifier
	 * @return
	 * @throws StorageException
	 */
	List<UserRole> getUserDataRangeRoles(String userIdentifier, String dataRange) throws StorageException;
	
	/**
	 * 查询某个受控数据域内授予权限的所有用户及其角色
	 * @param dataRange
	 * @return
	 * @throws StorageException
	 */
	List<UserRole> queryDataRangeUsers(String dataRange) throws StorageException;

}
