package cn.bounter.common.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 */
public class FileUtil {

	/**
	 * 读取文件内容到字符串
	 * @param path				文件路径
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(String path) throws IOException {
		return FileUtils.readFileToString(new File(path), "UTF-8");
	}
	
    public static void main(String[] args) throws Exception {
    	System.out.println(readFileToString("d:/xpaper_invite.html"));
    }
}
