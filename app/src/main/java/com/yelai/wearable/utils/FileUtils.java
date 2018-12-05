package com.yelai.wearable.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {
    private Context context;
	public FileUtils(Context context){
		this.context = context;
	}

	/**
	 * 删除文件以及文件夹。若传入是文件则直接删除，若是文件夹则删除里边所有文件再将文件夹删除
	 * 
	 * @param path
	 * @return 2015-9-9
	 */
	public static void deleteDirectory(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null || files.length == 0) {
				file.delete();
				return;
			}
			for (int i = 0; i < files.length; i++) {
				deleteDirectory(files[i]);
			}
		}
		file.delete();
	}

	/**
	 * 获得软件的根目录//Android/data/com.babyhealthy
	 * @return
	 * 2015-3-3
	 */
	public String getRootDirectory(){
		String root="";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			root = Environment.getExternalStorageDirectory().getPath()
					+"/Android/data"+File.separator+context.getPackageName()+File.separator;
		}else{
			root = Environment.getRootDirectory().getPath()+
					"/Android/data"+File.separator+context.getPackageName()+File.separator;
		}
		File file = new File(root);
		if(!file.isDirectory())
			file.mkdirs();
		return root;
	}

	/**
	 * 获得图片保存路径
	 * @return
	 * 2015-3-3
	 */
	public String getPicDirectory(){
		String root = getRootDirectory();
		String picPath = root + "picture"+File.separator;
		File file = new File(picPath);
		if(!file.isDirectory())
			file.mkdir();
		return picPath;
	}
}