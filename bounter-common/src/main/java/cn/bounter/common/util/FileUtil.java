package cn.bounter.common.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件工具类
 */
public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 读取文件内容到字符串
	 * @param path				文件路径
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(String path) throws IOException {
		return FileUtils.readFileToString(new File(path), "UTF-8");
	}

	/**
	 * 递归删除文件夹下所有文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			//递归删除文件夹下所有文件
			File[] files = file.listFiles();
			for (File f : files) {
				deleteFile(f);
			}

			//删除文件夹自己
			if (file.listFiles().length == 0) {
				logger.info("删除文件夹：[{}]", file);
				file.delete();
			}
		} else {
			// 如果是文件,就直接删除自己
			logger.info("删除文件：[{}]", file);
			file.delete();
		}
	}

    public static void main(String[] args) throws Exception {
//    	System.out.println(readFileToString("d:/xpaper_invite.html"));
    }
}
