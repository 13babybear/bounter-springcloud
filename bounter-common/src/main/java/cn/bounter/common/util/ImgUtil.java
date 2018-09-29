package cn.bounter.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图片工具类
 * 
 * @author simon
 *
 */
public class ImgUtil {

	/**
	 * Base64图片解码
	 * @param base64Img
	 * @param targetPath
	 *            目标文件全路径，eg:/home/img/avatar_974163457032654849.jpg
	 * @return
	 */
	public static boolean base64ToImage(String base64Img, String targetPath) {
		try {
			if (base64Img == null) {
				return false;
			}

			// 创建文件
			File avatarFile = new File(targetPath);
			if (!avatarFile.getParentFile().exists()) {
				avatarFile.getParentFile().mkdirs();
				avatarFile.createNewFile();
			}

			// 写入图像
			byte[] imgByte = EncodeUtil.decodeBase64(base64Img);
			// 调整异常数据
			for (int i = 0; i < imgByte.length; ++i) {
				if (imgByte[i] < 0) {
					imgByte[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(targetPath);
			out.write(imgByte);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
	}

}
