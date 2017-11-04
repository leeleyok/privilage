package leeleyok.privilage.common.dataobj;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DataRangePrivilage {
	/**
	 * 受控数据域
	 */
    private String dataRange;
    
    /**
     * 所有被授予权限的访问控制点及授予的权限
     */
    private List<Privilage> privilages;

	public String getDataRange() {
		return dataRange;
	}

	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}

	public List<Privilage> getPrivilages() {
		return privilages;
	}

	public void setPrivilages(List<Privilage> privilages) {
		this.privilages = privilages;
	}
	
	public String toString() {
		 return ToStringBuilder.reflectionToString(this);
	}
}
