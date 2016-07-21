package org.baoting.te.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: FileUtil
 * @Description:
 * @author YinChang-bao
 * @date Nov 9, 2015 1:15:33 PM
 *
 */
public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static final int DATA_CHUNK = 128 * 1024 * 1024;

	private static final long LEN = 2L * 1024 * 1024 * 1024L;

	public static void mvFileNio(String sourcePathFile, String des) {
		try {
			FileChannel srcChannel = new FileInputStream(sourcePathFile).getChannel();
			File storeFile = new File(des);
			if (storeFile.isDirectory()) {
				storeFile.delete();
				storeFile.createNewFile();
			}
			FileChannel dstChannel = new FileOutputStream(des).getChannel();
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

	public static void mvFile(String orgFileName, String descFile) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			InputStream inStream = new FileInputStream(orgFileName);
			byte[] buffer = new byte[128];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			inStream.close();
			File storeFile = new File(descFile);
			if (!storeFile.isFile()) {
				storeFile.delete();
				storeFile = new File(descFile);
			}
			FileOutputStream output = new FileOutputStream(storeFile);
			output.write(outStream.toByteArray());
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getFileNameFromPath(String path) {
		if (StringUtils.isEmpty(path))
			return "";
		String tmStr = path.replaceAll("\\\\", "/");
		String fileName = tmStr.substring(tmStr.lastIndexOf("/") + 1, tmStr.length());
		return fileName;
	}

	public static String getAbsPath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	public static byte[] readFile(String fileName) {
		try {
			FileChannel channel = new FileInputStream(fileName).getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
			channel.read(buffer);
			byte[] result = null;
			while (channel.read(buffer) != -1) {
				buffer.flip();
				result = TeUtil.arrayAppend(result, buffer.array());
				buffer.clear();
			}
			return result;
		} catch (Exception e) {
			logger.error("readNio error " + e.getMessage(), e);
		}
		return null;
	}

	public static byte[] readFile(File file) {
		try {
			FileChannel channel = new FileInputStream(file).getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
			channel.read(buffer);
			byte[] result = null;
			while (channel.read(buffer) != -1) {
				buffer.flip();
				result = TeUtil.arrayAppend(result, buffer.array());
				buffer.clear();
			}
			return result;
		} catch (Exception e) {
			logger.error("readNio error " + e.getMessage(), e);
		}
		return null;
	}

	public static byte[] readFolder(String folder, String filter) {
		try {
			File dir = new File(folder);
			if (!dir.exists() || !dir.isDirectory())
				return null;
			File[] files = dir.listFiles();
			if (files == null || files.length < 1)
				return null;
			byte[] result = null;
			for (File file : files) {
				String fileName = file.getName();
				String sufix = fileName.substring(fileName.lastIndexOf("."));
				if (sufix.toUpperCase().equals(filter.toUpperCase()))
					result = TeUtil.arrayAppend(result, readFile(file));
			}
			return result;
		} catch (Exception e) {
			logger.error("readNio error " + e.getMessage(), e);
		}
		return null;
	}

	public static void writeWithMappedByteBuffer() throws IOException {
		File file = new File("e:/test/mb.dat");
		if (file.exists()) {
			file.delete();
		}

		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		FileChannel fileChannel = raf.getChannel();
		int pos = 0;
		MappedByteBuffer mbb = null;
		byte[] data = null;
		long len = LEN;
		int dataChunk = DATA_CHUNK / (1024 * 1024);
		while (len >= DATA_CHUNK) {
			logger.info("write a data chunk: " + dataChunk + "MB");

			mbb = fileChannel.map(MapMode.READ_WRITE, pos, DATA_CHUNK);
			data = new byte[DATA_CHUNK];
			mbb.put(data);

			data = null;

			len -= DATA_CHUNK;
			pos += DATA_CHUNK;
		}

		if (len > 0) {
			logger.info("write rest data chunk: " + len + "B");

			mbb = fileChannel.map(MapMode.READ_WRITE, pos, len);
			data = new byte[(int) len];
			mbb.put(data);
		}

		data = null;
		unmap(mbb); // release MappedByteBuffer
		fileChannel.close();
	}

	public static void unmap(final MappedByteBuffer mappedByteBuffer) {
		try {
			if (mappedByteBuffer == null) {
				return;
			}

			mappedByteBuffer.force();
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				@Override
				@SuppressWarnings("restriction")
				public Object run() {
					try {
						Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
						getCleanerMethod.setAccessible(true);
						sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
								new Object[0]);
						cleaner.clean();

					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("clean MappedByteBuffer completed");
					return null;
				}
			});

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static File writeIntoFile(String content, String destPath) {
		FileOutputStream fop = null;
		File file = null;

		try {

			file = new File(destPath);
			fop = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			logger.info("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static String tinyFileRead(File file) {
		BufferedReader in = null;
		try {
			if (!file.exists())
				file.createNewFile();
			in = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = in.readLine()) != null)
				sb.append(temp);
			return sb.toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static void tinyFileWrite(String destFile, String content) {
		BufferedWriter out = null;
		try {
			File file = new File(destFile);
			if (!file.exists())
				file.createNewFile();
			out = new BufferedWriter(new FileWriter(file));
			out.write(content);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	public static List<File> iterateFolder(String path, List<String> suffixes) {
		
		int fileNum = 0, folderNum = 0;
		File file = new File(path);
		if (file.exists()) {
			LinkedList<File> list = new LinkedList<File>();

			List<File> targetFiles = new ArrayList<File>();

			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					logger.debug("folder:" + file2.getAbsolutePath());
					list.add(file2);
					fileNum++;
				} else if (suffixes!=null&&suffixes.contains(suffix(file2))) {
					logger.debug("file:" + file2.getAbsolutePath());
					targetFiles.add(file2);
					folderNum++;
				}else if(suffixes==null){
					logger.debug("file:" + file2.getAbsolutePath());
					targetFiles.add(file2);
					folderNum++;
				}
			}
			File temp_file;
			while (!list.isEmpty()) {
				temp_file = list.removeFirst();
				files = temp_file.listFiles();
				for (File file2 : files) {
					if (file2.isDirectory()) {
						logger.debug("folder:" + file2.getAbsolutePath());
						list.add(file2);
						fileNum++;
					} else if (suffixes!=null&&suffixes.contains(suffix(file2))) {
						logger.debug("file:" + file2.getAbsolutePath());
						targetFiles.add(file2);
						folderNum++;
					} else if(suffixes==null){
						logger.debug("file:" + file2.getAbsolutePath());
						targetFiles.add(file2);
						folderNum++;
					}
				}
			}
			return targetFiles;
		} else {
			logger.debug("folder not exist!");
		}
		logger.debug("folder contains sub-folder :" + folderNum + ", and file:" + fileNum);
		return null;
	}
	
	public static String suffix(File file){
		if(file ==null||file.isDirectory())
			return null;
		
		String name = file.getName();
		if(!StringUtil.isEmpty(name)&&name.lastIndexOf(".")>-1)
			return name.substring(name.lastIndexOf("."));
		else
			return name;
	}
	
}
