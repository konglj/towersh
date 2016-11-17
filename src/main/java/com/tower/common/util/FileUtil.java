package com.tower.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

	/**
	 * 从f1复制到f2
	 * 
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 */
	public static boolean fileCopyForTransfer(File f1, File f2)
			throws Exception {
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		int i = 0;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return true;
			}
			if ((inC.size() - inC.position()) < 20971520)
				length = (int) (inC.size() - inC.position());
			else
				length = 20971520;
			inC.transferTo(inC.position(), length, outC);
			inC.position(inC.position() + length);
			i++;
		}
	}

	public static void  zip(String zipFilePath, String inputFolderName)
			throws Exception {
		String zipFileName = zipFilePath; // 打包后文件名字
		File zipFile = new File(inputFolderName);
		zip(zipFileName, zipFile);
	}

	private static void zip(String zipFileName, File inputFolder) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(zipFileName);
		ZipOutputStream out = new ZipOutputStream(fileOut);
		zip(out, inputFolder, "");
		out.close();
		fileOut.close();
	}

	private static void zip(ZipOutputStream out, File inputFolder, String base)
			throws Exception {
		if (inputFolder.isDirectory()) {
			File[] fl = inputFolder.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				if(fl[i].getName().endsWith(".zip"))
					continue;
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(inputFolder);
			int b;
			// System.out.println(base);
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}
	
	
	/**
	 * 删除文件夹及其子文件
	 * @param dir
	 * @return
	 */
	 public  static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            //递归删除目录中的子目录下
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }
}
