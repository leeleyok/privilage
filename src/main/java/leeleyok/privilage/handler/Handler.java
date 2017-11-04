package leeleyok.privilage.handler;

import java.util.Date;

import leeleyok.privilage.common.context.PrivilageContext;
import leeleyok.privilage.common.dataobj.MonitorMsg;
import leeleyok.privilage.registry.DependentRegistry;

public class Handler {

	protected static void checkComponentRegistry() {
		
		if (null == DependentRegistry.getStorage()) {
			throw new RuntimeException("PrivilageStorage not registed");
		}
		
	}
	
    protected static MonitorMsg genReportMsg(String operateTarget, String operate, String extendMsg) {
		
		MonitorMsg mm = new MonitorMsg();
		mm.setOperator(PrivilageContext.getUser());
		mm.setTime(new Date());
		mm.setTarget(operateTarget);
		mm.setOperate(operate);
		mm.setExtendMsg(extendMsg);
		
		return mm;
	}
	
}
