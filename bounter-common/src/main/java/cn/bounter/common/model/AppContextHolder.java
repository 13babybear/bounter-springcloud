package cn.bounter.common.model;

/**
 * 线程安全的容器类
 * @author Simon
 *
 */
public class AppContextHolder {
	private static final ThreadLocal<AppContext> contextHolder = new ThreadLocal<>();
	
	public static AppContext getContext() {
		AppContext appContext = contextHolder.get();
		
		if(appContext == null) {
			appContext = new AppContext();
			contextHolder.set(appContext);
		}
		
		return appContext;
	}
	
	public static void setContext(AppContext appContext) {
		contextHolder.set(appContext);
	}
}
