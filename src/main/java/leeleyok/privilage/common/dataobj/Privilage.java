package leeleyok.privilage.common.dataobj;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Privilage {
	/**
	 * 访问控制点
	 */
	private String accessPoint; 
	
	/**
	 * 允许的操作类型
	 */
	private List<String> operates;

	public String getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(String accessPoint) {
		this.accessPoint = accessPoint;
	}

	public List<String> getOperates() {
		return operates;
	}

	public void setOperates(List<String> operates) {
		this.operates = operates;
	}

	public String toString() {
		 return ToStringBuilder.reflectionToString(this);
	}
}
